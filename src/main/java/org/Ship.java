package org;

public final class Ship extends AbstractTransport {
    private final CombustionEngine engine;
    private final double displacement;
    private final int decks;

    public Ship(String name, int maxSpeed, CombustionEngine engine, double displacement, int decks) {
        super(name, TransportType.SHIP, maxSpeed);
        this.engine = engine;
        this.displacement = displacement;
        this.decks = decks;
    }

    @Override
    public void startEngine() {
        engine.start();
        engineRunning = true;
        System.out.println("Двигатель корабля " + name + " запущен");
    }

    @Override
    public void stopEngine() {
        engine.stop();
        engineRunning = false;
        System.out.println("Двигатель корабля " + name + " остановлен");
    }

    @Override
    public void move() {
        if (!engineRunning) {
            System.out.println("Сначала запустите двигатель!");
            return;
        }
        System.out.println(name + " отплывает");
    }

    @Override
    public void stop() {
        System.out.println(name + " пришвартовался");
    }

    @Override
    public String getInfo() {
        return "Корабль: " + name + "\n" +
                "Тип: " + type + "\n" +
                "Макс. скорость: " + maxSpeed + " узлов\n" +
                "Двигатель: " + engine.getModel() + "\n" +
                "Водоизмещение: " + displacement + " тонн\n" +
                "Палубы: " + decks + "\n" +
                "Двигатель работает: " + (engineRunning ? "Да" : "Нет") + "\n" +
                "Пройдено: " + String.format("%.1f", distanceTraveled) + " морских миль";
    }
}
