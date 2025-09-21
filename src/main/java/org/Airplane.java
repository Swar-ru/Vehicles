package org;

public final class Airplane extends AbstractTransport {
    private final JetEngine[] engines;
    private final int wingspan;
    private final int capacity;
    private final double maxAltitude;
    private final double descentRate;
    private Thread descentThread;
    private boolean isDescending;

    public Airplane(String name, int maxSpeed, JetEngine[] engines, int wingspan, int capacity) {
        super(name, TransportType.AIRPLANE, maxSpeed);
        this.engines = engines;
        this.wingspan = wingspan;
        this.capacity = capacity;
        this.maxAltitude = 12000;
        this.descentRate = 30;
        this.isDescending = false;
    }

    @Override
    public void stopEngine() {
        if (!engineRunning) {
            System.out.println("Двигатели уже остановлены!");
            return;
        }

        for (JetEngine engine : engines) {
            engine.stop();
        }
        engineRunning = false;
        System.out.println("Двигатели самолета " + name + " остановлены");

        if (currentAltitude > 0 && !crashed) {
            System.out.println("⚠️  ВНИМАНИЕ: Двигатели остановлены на высоте!");
            System.out.println("⚠️  Самолет начинает снижение!");
            System.out.println("⚠️  Немедленно запустите двигатели!");
            startDescent(); // Запускаем снижение
        }
    }

    @Override
    public void startEngine() {
        if (crashed) {
            System.out.println("Самолет разбит! Двигатели не запускаются.");
            return;
        }

        stopDescent(); // Останавливаем снижение при запуске двигателей

        for (JetEngine engine : engines) {
            engine.start();
        }
        engineRunning = true;
        System.out.println("Двигатели самолета " + name + " запущены");

        if (currentAltitude > 0) {
            System.out.println("Высота: " + String.format("%.0f", currentAltitude) + " м");
        }
    }

    public void startDescent() {
        if (currentAltitude <= 0) {
            System.out.println("Самолет уже на земле!");
            return;
        }

        if (!engineRunning) {
            System.out.println("Сначала запустите двигатели!");
            return;
        }

        System.out.println("✈️  Начинаем снижение для посадки...");
        System.out.println("✈️  Текущая высота: " + String.format("%.0f", currentAltitude) + " м");

        // Плавное снижение на 20 м/с при работающих двигателях
        Thread descentThread = new Thread(() -> {
            try {
                while (currentAltitude > 0 && engineRunning && !crashed) {
                    Thread.sleep(1000);
                    currentAltitude = Math.max(currentAltitude - 20, 0);
                    System.out.printf("✈️  СНИЖЕНИЕ: %.0f м%n", currentAltitude);

                    if (currentAltitude <= 500 && currentAltitude > 0) {
                        System.out.println("✈️  Достигнута высота для посадки!");
                        System.out.println("✈️  Можете выполнить посадку (опция 3)");
                    }

                    if (currentAltitude == 0) {
                        System.out.println("✈️  Самолет приземлился!");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        descentThread.start();
    }

    private void stopDescent() {
        isDescending = false;
        if (descentThread != null) {
            descentThread.interrupt();
            try {
                descentThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void move() {
        if (crashed) {
            System.out.println("Самолет разбит! Движение невозможно.");
            return;
        }

        if (!engineRunning) {
            System.out.println("Сначала запустите двигатели!");
            return;
        }

        stopDescent(); // Останавливаем снижение при начале движения

        if (currentAltitude == 0) {
            System.out.println(name + " взлетает");
            currentAltitude = 100; // Взлетаем на 100 метров
        } else {
            System.out.println(name + " продолжает полет на высоте " +
                    String.format("%.0f", currentAltitude) + " м");
        }
    }

    @Override
    public void stop() {
        if (currentAltitude > 0) {
            System.out.println("Самолет не может остановиться в воздухе!");
            System.out.println("Сначала выполните посадку!");
            return;
        }

        // Если самолет уже на земле
        if (!engineRunning) {
            System.out.println("Самолет " + name + " уже полностью остановлен");
            System.out.println("Двигатели выключены, самолет припаркован");
        } else {
            System.out.println("Самолет " + name + " находится на земле");
            System.out.println("Двигатели работают для руления");
            System.out.println("Для полной остановки остановите двигатели (опция 4)");
        }
    }

    private void crash() {
        crashed = true;
        engineRunning = false;
        isDescending = false;
        System.out.println("💥💥💥 САМОЛЕТ РАЗБИЛСЯ! 💥💥💥");
        System.out.println("💥 " + name + " упал с неба!");
        System.out.println("💥 Все системы отключены!");
        System.out.println("💥 Требуется новый самолет!");
    }

    public boolean canLand() {
        return currentAltitude <= 500 && currentAltitude > 0;
    }

    public void performLanding() {
        if (currentAltitude > 500) {
            System.out.println("✈️  Невозможно выполнить посадку!");
            System.out.println("✈️  Слишком высоко! Текущая высота: " +
                    String.format("%.0f", currentAltitude) + " м");
            System.out.println("✈️  Используйте опцию 'Начать снижение' сначала");
            return;
        }

        if (currentAltitude > 0 && currentAltitude <= 500) {
            stopDescent(); // Останавливаем любое предыдущее снижение
            System.out.println("✈️  Начинаем посадку...");
            // Плавная посадка
            while (currentAltitude > 0 && !crashed) {
                try {
                    Thread.sleep(500);
                    currentAltitude = Math.max(currentAltitude - 10, 0);
                    System.out.printf("✈️  Высота: %.0f м%n", currentAltitude);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (!crashed) {
                System.out.println("✈️  Посадка выполнена успешно!");
                System.out.println("Двигатели работают для руления к терминалу");
            }
        } else {
            System.out.println("Самолет уже на земле");
        }
    }

    @Override
    public String getInfo() {
        String status;
        if (crashed) {
            status = "РАЗБИТ";
        } else if (currentAltitude > 0) {
            status = isDescending ? "СНИЖАЕТСЯ" : "В ПОЛЕТЕ";
        } else {
            status = "НА ЗЕМЛЕ";
        }

        return "Самолет: " + name + "\n" +
                "Тип: " + type + "\n" +
                "Состояние: " + status + "\n" +
                "Макс. скорость: " + maxSpeed + " км/ч\n" +
                "Текущая высота: " + String.format("%.0f", currentAltitude) + " м\n" +
                "Двигатели: " + engines.length + " x " + engines[0].getModel() + "\n" +
                "Двигатели работают: " + (engineRunning ? "Да" : "Нет") + "\n" +
                "Размах крыльев: " + wingspan + " м\n" +
                "Вместимость: " + capacity + " пассажиров\n" +
                "Пройдено: " + String.format("%.1f", distanceTraveled) + " км";
    }
}