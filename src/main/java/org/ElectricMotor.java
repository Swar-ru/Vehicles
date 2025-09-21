package org;

public final class ElectricMotor extends Engine {
    public ElectricMotor(String model, double power) {
        super(model, power, FuelType.ELECTRIC);
    }

    @Override
    public void start() {
        System.out.println("Запуск электродвигателя " + model);
    }

    @Override
    public void stop() {
        System.out.println("Остановка электродвигателя " + model);
    }
}
