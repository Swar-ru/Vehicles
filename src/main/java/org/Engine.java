package org;

public abstract class Engine implements IEngine {
    protected String model;
    protected double power;
    protected FuelType fuelType;

    protected Engine(String model, double power, FuelType fuelType) {
        this.model = model;
        this.power = power;
        this.fuelType = fuelType;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public FuelType getFuelType() {
        return fuelType;
    }

    @Override
    public abstract void start();

    @Override
    public abstract void stop();
}
