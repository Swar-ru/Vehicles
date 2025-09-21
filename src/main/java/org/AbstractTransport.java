package org;

public abstract class AbstractTransport implements ITransport {
    protected String name;
    protected TransportType type;
    protected int maxSpeed;
    protected boolean engineRunning;
    protected double distanceTraveled;
    protected double currentAltitude; // Новое поле
    protected boolean crashed; // Новое поле

    protected AbstractTransport(String name, TransportType type, int maxSpeed) {
        this.name = name;
        this.type = type;
        this.maxSpeed = maxSpeed;
        this.engineRunning = false;
        this.distanceTraveled = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TransportType getType() {
        return type;
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public boolean isEngineRunning() {
        return engineRunning;
    }

    @Override
    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    @Override
    public double getCurrentAltitude() {
        return currentAltitude;
    }

    @Override
    public boolean isCrashed() {
        return crashed;
    }

    @Override
    public void updateDistance(double distance) {
        this.distanceTraveled += distance;
    }
    @Override
    public void updateAltitude(double altitudeChange) {
        this.currentAltitude += altitudeChange;
        if (this.currentAltitude < 0) {
            this.currentAltitude = 0;
        }
        if (this.currentAltitude > 12000 && this instanceof Airplane) {
            this.currentAltitude = 12000; // Ограничение высоты для самолета
        }
    }

    @Override
    public abstract void startEngine();

    @Override
    public abstract void stopEngine();

    @Override
    public abstract void move();

    @Override
    public abstract void stop();

    @Override
    public abstract String getInfo();
}