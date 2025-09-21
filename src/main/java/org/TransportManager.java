package org;

import java.util.Scanner;

public class TransportManager {
    private ITransport currentTransport;
    private final Scanner scanner;
    private boolean isMoving;
    private Thread movementThread;

    public TransportManager() {
        this.scanner = new Scanner(System.in);
        this.isMoving = false;
    }

    // Добавляем метод pause
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void start() {
        while (true) {
            showMainMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1:
                    createTransport();
                    break;
                case 2:
                    if (currentTransport != null && !currentTransport.isCrashed()) {
                        manageTransport();
                    } else if (currentTransport != null) {
                        System.out.println("Транспорт разбит! Создайте новый.");
                    } else {
                        System.out.println("Сначала создайте транспорт!");
                    }
                    break;
                case 3:
                    if (currentTransport != null) {
                        showTransportInfo();
                    } else {
                        System.out.println("Сначала создайте транспорт!");
                    }
                    break;
                case 4:
                    System.out.println("Выход из программы...");
                    stopMovement();
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Создать новый транспорт");
        System.out.println("2. Управлять текущим транспортом");
        System.out.println("3. Показать информацию о транспорте");
        System.out.println("4. Выход");
        System.out.println("====================");
    }

    private void createTransport() {
        System.out.println("\n=== ВЫБОР ТРАНСПОРТА ===");
        System.out.println("1. Автомобиль");
        System.out.println("2. Самолет");
        System.out.println("3. Корабль");
        System.out.println("4. Велосипед");
        System.out.println("5. Мотоцикл");
        System.out.println("6. Назад");
        System.out.println("=======================");

        int choice = getIntInput("Выберите тип транспорта: ");

        if (choice == 6) return;

        stopMovement(); // Останавливаем предыдущее движение

        try {
            currentTransport = TransportFactory.createTransport(
                    TransportType.values()[choice - 1]
            );
            System.out.println("Транспорт создан: " + currentTransport.getName());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Неверный выбор транспорта!");
        }
    }

    private void manageTransport() {
        while (true) {
            System.out.println("\n=== УПРАВЛЕНИЕ ТРАНСПОРТОМ ===");
            System.out.println("1. Запустить двигатель");
            System.out.println("2. Начать движение/Набрать высоту");

            if (currentTransport instanceof Airplane) {
                Airplane airplane = (Airplane) currentTransport;
                if (airplane.getCurrentAltitude() > 500) {
                    System.out.println("3. Начать снижение");
                } else if (airplane.getCurrentAltitude() > 0) {
                    System.out.println("3. Выполнить посадку");
                } else {
                    System.out.println("3. Остановиться");
                }
            } else {
                System.out.println("3. Остановиться");
            }

            System.out.println("4. Остановить двигатель");
            System.out.println("5. Назад в главное меню");
            System.out.println("=============================");

            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1:
                    startEngine();
                    break;
                case 2:
                    startMovement();
                    break;
                case 3:
                    if (currentTransport instanceof Airplane) {
                        Airplane airplane = (Airplane) currentTransport;
                        if (airplane.getCurrentAltitude() > 500) {
                            stopMovement();
                            airplane.startDescent(); // Новая опция - начать снижение
                        } else if (airplane.getCurrentAltitude() > 0) {
                            stopMovement();
                            airplane.performLanding();
                        } else {
                            airplane.stop();
                            pause(2000); // Используем метод pause
                        }
                    } else {
                        stopMovement();
                    }
                    break;
                case 4:
                    stopEngine();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private void startEngine() {
        if (currentTransport.isEngineRunning()) {
            System.out.println("Двигатель уже запущен!");
            return;
        }
        currentTransport.startEngine();
    }

    private void stopEngine() {
        if (!currentTransport.isEngineRunning()) {
            System.out.println("Двигатель уже остановлен!");
            return;
        }
        stopMovement();
        currentTransport.stopEngine();
    }

    private void startMovement() {
        if (isMoving) {
            System.out.println("Транспорт уже движется!");
            return;
        }

        if (!currentTransport.isEngineRunning()) {
            System.out.println("Сначала запустите двигатель!");
            return;
        }

        if (currentTransport.isCrashed()) {
            System.out.println("Транспорт разбит! Движение невозможно.");
            return;
        }

        currentTransport.move();
        isMoving = true;

        // Запускаем поток только для подсчета расстояния
        movementThread = new Thread(() -> {
            try {
                while (isMoving && !currentTransport.isCrashed()) {
                    Thread.sleep(1000); // Каждую секунду

                    // Обновляем расстояние
                    double speed = currentTransport.getMaxSpeed() / 3.6; // м/с
                    currentTransport.updateDistance(speed / 1000); // км за секунду

                    // Для самолета также обновляем высоту при движении
                    if (currentTransport instanceof Airplane && currentTransport.isEngineRunning()) {
                        Airplane airplane = (Airplane) currentTransport;
                        // Легкий набор высоты при движении
                        airplane.updateAltitude(5); // +5 м/с при движении

                        System.out.printf("Высота: %.0f м | Пройдено: %.1f км%n",
                                airplane.getCurrentAltitude(),
                                currentTransport.getDistanceTraveled());
                    } else {
                        // Для других видов транспорта
                        System.out.printf("Пройдено: %.1f км%n",
                                currentTransport.getDistanceTraveled());
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        movementThread.start();
    }

    private void stopMovement() {
        if (!isMoving) {
            return;
        }

        isMoving = false;
        if (movementThread != null) {
            movementThread.interrupt();
            try {
                movementThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        currentTransport.stop();
        System.out.println("Движение остановлено");

        // Для самолета: если двигатели работают, но движение остановлено - остаемся на высоте
        if (currentTransport instanceof Airplane && currentTransport.isEngineRunning()) {
            Airplane airplane = (Airplane) currentTransport;
            if (airplane.getCurrentAltitude() > 0) {
                System.out.println("Самолет продолжает полет на высоте " +
                        String.format("%.0f", airplane.getCurrentAltitude()) + " м");
            }
        }
    }

    private void showTransportInfo() {
        System.out.println("\n=== ИНФОРМАЦИЯ О ТРАНСПОРТЕ ===");
        System.out.println(currentTransport.getInfo());
        System.out.println("===============================");
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число!");
            }
        }
    }
}