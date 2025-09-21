package org;

public final class Car extends AbstractTransport {
    private final CombustionEngine engine;
    private final int doors;
    private final String color;

    public Car(String name, int maxSpeed, CombustionEngine engine, int doors, String color) {
        super(name, TransportType.CAR, maxSpeed);
        this.engine = engine;
        this.doors = doors;
        this.color = color;
    }

    @Override
    public void startEngine() {
        engine.start();
        engineRunning = true;
        System.out.println("Двигатель автомобиля " + name + " запущен");
    }

    @Override
    public void stopEngine() {
        engine.stop();
        engineRunning = false;
        System.out.println("Двигатель автомобиля " + name + " остановлен");
    }

    @Override
    public void move() {
        if (!engineRunning) {
            System.out.println("Сначала запустите двигатель!");
            return;
        }
        System.out.println(name + " начал движение");
    }

    @Override
    public void stop() {
        System.out.println(name + " остановился");
    }

    @Override
    public String getInfo() {
        return "Автомобиль: " + name + "\n" +
                "Тип: " + type + "\n" +
                "Макс. скорость: " + maxSpeed + " км/ч\n" +
                "Двигатель: " + engine.getModel() + " (" + engine.getPower() + " л.с.)\n" +
                "Топливо: " + engine.getFuelType() + "\n" +
                "Двери: " + doors + "\n" +
                "Цвет: " + color + "\n" +
                "Двигатель работает: " + (engineRunning ? "Да" : "Нет") + "\n" +
                "Пройдено: " + String.format("%.1f", distanceTraveled) + " км";
    }
}