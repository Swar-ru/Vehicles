package org;

public class Vehicles {
    public static void main(String[] args) {
        System.out.println("🚗 СИСТЕМА УПРАВЛЕНИЯ ТРАНСПОРТОМ 🚗");
        System.out.println("====================================");

        TransportManager manager = new TransportManager();
        manager.start();
    }
}