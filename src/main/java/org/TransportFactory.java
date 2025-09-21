package org;

public class TransportFactory {
    public static ITransport createTransport(TransportType type) {
        switch (type) {
            case CAR:
                return new Car(
                        "Toyota Camry", 220,
                        new CombustionEngine("2.5L V6", 181, FuelType.GASOLINE),
                        4, "Синий"
                );

            case AIRPLANE:
                return new Airplane(
                        "Boeing 737", 876,
                        new JetEngine[]{new JetEngine("CFM56-7B", 12000),
                                new JetEngine("CFM56-7B", 12000)}, // 2 двигателя
                        36, 215
                );

            case SHIP:
                return new Ship(
                        "Queen Mary 2", 30,
                        new CombustionEngine("Diesel-Electric", 86400, FuelType.DIESEL),
                        151400, 13
                );

            case BICYCLE:
                return new Bicycle(
                        "Trek Domane", 60,
                        22, "Карбон"
                );

            case MOTORCYCLE:
                return new Motorcycle(
                        "Harley Davidson", 180,
                        new CombustionEngine("Milwaukee-Eight", 93, FuelType.GASOLINE),
                        "Круизер"
                );

            default:
                throw new IllegalArgumentException("Неизвестный тип транспорта");
        }
    }
}
