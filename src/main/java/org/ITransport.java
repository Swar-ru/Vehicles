package org;

public interface ITransport {
    String getName();
    TransportType getType();
    int getMaxSpeed();
    boolean isEngineRunning();
    double getDistanceTraveled();
    double getCurrentAltitude();
    boolean isCrashed();
    void startEngine();
    void stopEngine();
    void move();
    void stop();
    String getInfo();
    void updateDistance(double distance);
    void updateAltitude(double altitudeChange);
}
