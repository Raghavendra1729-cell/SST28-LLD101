public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.addGate("G1");
        parkingLot.addGate("G2");
        parkingLot.setRate("SMALL", 20);
        parkingLot.setRate("MEDIUM", 40);
        parkingLot.setRate("LARGE", 100);

        Level level1 = new Level("L1")
                .addSlot(new ParkingSlot("S1", "L1", "SMALL").setDistance("G1", 2).setDistance("G2", 8))
                .addSlot(new ParkingSlot("M1", "L1", "MEDIUM").setDistance("G1", 5).setDistance("G2", 7))
                .addSlot(new ParkingSlot("L1", "L1", "LARGE").setDistance("G1", 9).setDistance("G2", 3));

        Level level2 = new Level("L2")
                .addSlot(new ParkingSlot("S2", "L2", "SMALL").setDistance("G1", 4).setDistance("G2", 2))
                .addSlot(new ParkingSlot("M2", "L2", "MEDIUM").setDistance("G1", 1).setDistance("G2", 6))
                .addSlot(new ParkingSlot("L2", "L2", "LARGE").setDistance("G1", 8).setDistance("G2", 1));

        parkingLot.addLevel(level1);
        parkingLot.addLevel(level2);

        Ticket t1 = parkingLot.park(new Vehicle("BIKE", "TWO_WHEELER"), "G1");
        Ticket t2 = parkingLot.park(new Vehicle("CAR", "CAR"), "G1");
        Ticket t3 = parkingLot.park(new Vehicle("BUS", "BUS"), "G2");

        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);

        System.out.println(parkingLot.getStatus());

        t2.hours = 2;
        int amount = parkingLot.exit(t2);
        System.out.println("Amount = " + amount);

        System.out.println(parkingLot.getStatus());
    }
}
