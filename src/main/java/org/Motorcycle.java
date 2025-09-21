package org;

public final class Motorcycle extends AbstractTransport {
    private final CombustionEngine engine;
    private final String style;

    public Motorcycle(String name, int maxSpeed, CombustionEngine engine, String style) {
        super(name, TransportType.MOTORCYCLE, maxSpeed);
        this.engine = engine;
        this.style = style;
    }

    @Override
    public void startEngine() {
        engine.start();
        engineRunning = true;
        System.out.println("Двигатель мотоцикла " + name + " запущен");
    }

    @Override
    public void stopEngine() {
        engine.stop();
        engineRunning = false;
        System.out.println("Двигатель мотоцикла " + name + " остановлен");
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
        return "Мотоцикл: " + name + "\n" +
                "Тип: " + type + "\n" +
                "Макс. скорость: " + maxSpeed + " км/ч\n" +
                "Двигатель: " + engine.getModel() + " (" + engine.getPower() + " л.с.)\n" +
                "Стиль: " + style + "\n" +
                "Топливо: " + engine.getFuelType() + "\n" +
                "Двигатель работает: " + (engineRunning ? "Да" : "Нет") + "\n" +
                "Пройдено: " + String.format("%.1f", distanceTraveled) + " км";
    }
}
