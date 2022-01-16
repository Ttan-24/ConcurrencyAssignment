package uclan.com;

public class Source {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Create data
		EntryPoint South = new EntryPoint("South");
		EntryPoint East = new EntryPoint("East");
		EntryPoint North = new EntryPoint("North");

		Junction A = new Junction();
		Junction B = new Junction();
		Junction C = new Junction();
		Junction D = new Junction();

		Road SouthtoA = new Road(10);
		Road EasttoB = new Road(10);
		Road NorthtoC = new Road(10);
		Road BtoA = new Road(10);
		Road AtoB = new Road(10);
		Road BtoC = new Road(10);
		Road CtoB = new Road(10);
		Road CtoD = new Road(10);
		Road AtoIndustrialPark = new Road(10);
		Road CtoShoppingCentre = new Road(10);
		Road DtoUniversity = new Road(10);
		Road DtoStation = new Road(10);

		CarPark ShoppingCentre = new CarPark(10, "Shopping Centre");
		CarPark IndustrialPark = new CarPark(20, "Industrial Space");
		CarPark Station = new CarPark(20, "Station");
		CarPark University = new CarPark(20, "University");

		Clock clock = new Clock();
		clock.carPark = IndustrialPark;

		// Timings
		South.entryPointClock = clock;
		East.entryPointClock = clock;
		North.entryPointClock = clock;

		A.junctionClock = clock;
		B.junctionClock = clock;
		C.junctionClock = clock;
		D.junctionClock = clock;

		SouthtoA.roadClock = clock;
		EasttoB.roadClock = clock;
		NorthtoC.roadClock = clock;
		BtoA.roadClock = clock;
		AtoB.roadClock = clock;
		BtoC.roadClock = clock;
		CtoB.roadClock = clock;
		CtoD.roadClock = clock;
		AtoIndustrialPark.roadClock = clock;
		CtoShoppingCentre.roadClock = clock;
		DtoUniversity.roadClock = clock;
		DtoStation.roadClock = clock;

		ShoppingCentre.carParkClock = clock;
		IndustrialPark.carParkClock = clock;
		Station.carParkClock = clock;
		University.carParkClock = clock;

		// Join data
		// Junction A
		South.road = SouthtoA;
		A.entryRoadArray[0] = SouthtoA;
		A.exitRoadArray[0] = AtoIndustrialPark;

		A.exitRoadArray[1] = AtoB;
		A.entryRoadArray[1] = BtoA;

		A.addDestinationMapping("Industrial Space", 0);
		A.addDestinationMapping("Shopping Centre", 1);
		A.addDestinationMapping("Station", 1);
		A.addDestinationMapping("University", 1);

		// Junction B
		East.road = EasttoB;
		B.entryRoadArray[0] = EasttoB;
		B.exitRoadArray[0] = BtoC;

		B.entryRoadArray[1] = CtoB;
		B.entryRoadArray[2] = AtoB;
		B.exitRoadArray[1] = BtoA;

		B.addDestinationMapping("Industrial Space", 1);
		B.addDestinationMapping("Shopping Centre", 0);
		B.addDestinationMapping("Station", 0);
		B.addDestinationMapping("University", 0);

		// Junction C
		North.road = NorthtoC;
		C.entryRoadArray[0] = NorthtoC;
		C.exitRoadArray[0] = CtoD;
		C.exitRoadArray[1] = CtoShoppingCentre;
		C.exitRoadArray[2] = CtoB;
		C.entryRoadArray[1] = BtoC;

		C.addDestinationMapping("Industrial Space", 2);
		C.addDestinationMapping("Shopping Centre", 1);
		C.addDestinationMapping("Station", 0);
		C.addDestinationMapping("University", 0);

		// Junction D
		D.entryRoadArray[0] = CtoD;
		D.exitRoadArray[0] = DtoUniversity;
		D.exitRoadArray[1] = DtoStation;

		D.addDestinationMapping("Station", 1);
		D.addDestinationMapping("University", 0);

		// CarParks
		IndustrialPark.road = AtoIndustrialPark;
		ShoppingCentre.road = CtoShoppingCentre;
		Station.road = DtoStation;
		University.road = DtoUniversity;

		// start threads
		South.start();
		East.start();
		North.start();
		A.start();
		B.start();
		C.start();
		D.start();
		IndustrialPark.start();
		ShoppingCentre.start();
		Station.start();
		University.start();
		clock.start();

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
