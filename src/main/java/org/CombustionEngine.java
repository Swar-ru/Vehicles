package org;

public final class CombustionEngine extends Engine {
    public CombustionEngine(String model, double power, FuelType fuelType) {
        super(model, power, fuelType);
    }

    @Override
    public void start() {
        System.out.println("Запуск двигателя " + model + " внутреннего сгорания");
    }

    @Override
    public void stop() {
        System.out.println("Остановка двигателя " + model + " внутреннего сгорания");
    }
}
