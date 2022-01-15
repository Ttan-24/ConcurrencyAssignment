package uclan.com;

public class Source {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Create data
		EntryPoint South = new EntryPoint("South");
		Road SouthtoA = new Road(10);
		EntryPoint East = new EntryPoint("East");
		Road EasttoA = new Road(10);
		Junction A = new Junction();
		Road AtoShoppingCentre = new Road(10);
		CarPark ShoppingCentre = new CarPark(10, "Shopping Centre");
		Road AtoIndustrialPark = new Road(10);
		CarPark IndustrialPark = new CarPark(20, "Industrial Park");
		Clock time = new Clock();

		// Timings
		South.entryPointClock = time;
		SouthtoA.roadClock = time;
		East.entryPointClock = time;
		EasttoA.roadClock = time;
		A.junctionClock = time;
		AtoShoppingCentre.roadClock = time;
		ShoppingCentre.carParkClock = time;
		AtoIndustrialPark.roadClock = time;
		IndustrialPark.carParkClock = time;

		// Join data
		South.road = SouthtoA;
		East.road = EasttoA;
		A.entryRoadArray[0] = EasttoA;
		A.entryRoadArray[1] = SouthtoA;
		A.exitRoadArray[0] = AtoShoppingCentre;
		A.exitRoadArray[1] = AtoIndustrialPark;
		ShoppingCentre.road = AtoShoppingCentre;
		IndustrialPark.road = AtoIndustrialPark;

		// Main loop
		// for (int i = 0; i < 100)
		// {
		// Production consumption etc.

		// South.Produce();
		// South.Produce();
		// South.Produce();
		// A.TakeVehicle();
		// IndustrialPark.consume();
		// IndustrialPark.consume();
		// IndustrialPark.consume();

		South.start();
		East.start();
		A.start();
		IndustrialPark.start();
		ShoppingCentre.start();
		time.start();
//		South.produce();
//		South.produce();
//		IndustrialPark.consume();

//		South.Produce();
//		A.TakeVehicle();
//		IndustrialPark.consume();

		// Display updated results
		// System.out.println("Total cars produced: " + (South.idCount - 1));
		// System.out.println("Total cars in road: ");
		// total cars in the road
		// total cars in the carpark
		// System.out.println("Total cars in the Industrial carpark " + " out of 1000
		// spaces");
		// }
	}

}
