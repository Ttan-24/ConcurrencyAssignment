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
		CarPark IndustrialPark = new CarPark(20, "Industrial Space");
		Clock clock = new Clock();

		clock.carPark = IndustrialPark;

		// Timings
		South.entryPointClock = clock;
		SouthtoA.roadClock = clock;
		East.entryPointClock = clock;
		EasttoA.roadClock = clock;
		A.junctionClock = clock;
		AtoShoppingCentre.roadClock = clock;
		ShoppingCentre.carParkClock = clock;
		AtoIndustrialPark.roadClock = clock;
		IndustrialPark.carParkClock = clock;

		// Join data
		South.road = SouthtoA;
		East.road = EasttoA;
		A.entryRoadArray[0] = EasttoA;
		A.entryRoadArray[1] = SouthtoA;
		A.exitRoadArray[0] = AtoShoppingCentre;
		A.exitRoadArray[1] = AtoIndustrialPark;
		ShoppingCentre.road = AtoShoppingCentre;
		IndustrialPark.road = AtoIndustrialPark;

		// Junction setup
		A.addDestinationMapping("Industrial Space", 1);
		A.addDestinationMapping("Shopping Centre", 0);

		// start threads
		South.start();
		East.start();
		A.start();
		IndustrialPark.start();
		ShoppingCentre.start();
		clock.start();
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
