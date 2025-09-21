package org;

public final class Bicycle extends AbstractTransport {
    private final int gears;
    private final String frameMaterial;

    public Bicycle(String name, int maxSpeed, int gears, String frameMaterial) {
        super(name, TransportType.BICYCLE, maxSpeed);
        this.gears = gears;
        this.frameMaterial = frameMaterial;
    }

    @Override
    public void startEngine() {
        // У велосипеда нет двигателя
        engineRunning = true;
        System.out.println("Велосипед " + name + " готов к движению");
    }

    @Override
    public void stopEngine() {
        engineRunning = false;
        System.out.println("Велосипед " + name + " остановлен");
    }

    @Override
    public void move() {
        if (!engineRunning) {
            System.out.println("Сначала подготовьте велосипед к движению!");
            return;
        }
        System.out.println(name + " начал движение (педали крутятся)");
    }

    @Override
    public void stop() {
        System.out.println(name + " остановился");
    }

    @Override
    public String getInfo() {
        return "Велосипед: " + name + "\n" +
                "Тип: " + type + "\n" +
                "Макс. скорость: " + maxSpeed + " км/ч\n" +
                "Передачи: " + gears + "\n" +
                "Материал рамы: " + frameMaterial + "\n" +
                "Топливо: " + FuelType.HUMAN_POWER + "\n" +
                "Готов к движению: " + (engineRunning ? "Да" : "Нет") + "\n" +
                "Пройдено: " + String.format("%.1f", distanceTraveled) + " км";
    }
}
