package org;

public final class JetEngine extends Engine {
    public JetEngine(String model, double power) {
        super(model, power, FuelType.JET_FUEL);
    }

    @Override
    public void start() {
        System.out.println("Запуск реактивного двигателя " + model);
    }

    @Override
    public void stop() {
        System.out.println("Остановка реактивного двигателя " + model);
    }
}
