package org;

public interface IEngine {
    String getModel();
    double getPower();
    FuelType getFuelType();
    void start();
    void stop();
}
