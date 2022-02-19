package uclan.com;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;

public class Source {

	enum Roads {
		NORTH, EAST, SOUTH, WEST
	}

	private static void detectDeadlock() {
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		long[] threadIds = threadBean.findDeadlockedThreads();
		boolean deadlock = threadIds != null && threadIds.length > 0;
		System.out.println("deadlocks found: " + deadlock);
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		LogFileManager.openLog(System.getProperty("user.dir"));
		LogFileManager.writeToLog("");
		LogFileManager.writeToLog("");
		LogFileManager.writeToLog(
				"--------------------------------------------------------------------------------------------------");

		LogFileManager.writeToLog("Concurrency Assignment Part 1 started.");
		LogFileManager.writeToLog(
				"--------------------------------------------------------------------------------------------------");

		// Create data
		EntryPoint South = new EntryPoint("South", 550);
		EntryPoint East = new EntryPoint("East", 300);
		EntryPoint North = new EntryPoint("North", 550);

		Junction A = new Junction("A", 60);
		Junction B = new Junction("B", 60);
		Junction C = new Junction("C", 30);
		Junction D = new Junction("D", 30);

		Road SouthtoA = new Road(10, "SouthtoA");
		Road EasttoB = new Road(10, "EasttoB");
		Road NorthtoC = new Road(10, "NorthtoC");
		Road BtoA = new Road(10, "BtoA");
		Road AtoB = new Road(10, "AtoB");
		Road BtoC = new Road(10, "BtoC");
		Road CtoB = new Road(10, "CtoB");
		Road CtoD = new Road(10, "CtoD");
		Road AtoIndustrialPark = new Road(10, "AtoIndustrialPark");
		Road CtoShoppingCentre = new Road(10, "CtoShoppingCentre");
		Road DtoUniversity = new Road(10, "DtoUniversity");
		Road DtoStation = new Road(10, "DtoStation");

		CarPark ShoppingCentre = new CarPark(10, "Shopping Centre");
		CarPark IndustrialPark = new CarPark(20, "Industrial Park");
		CarPark Station = new CarPark(20, "Station");
		CarPark University = new CarPark(20, "University");

		//////////////////////////////////////////////////////////////// ArrayList to
		//////////////////////////////////////////////////////////////// count the total
		//////////////////////////////////////////////////////////////// number//////////////////////////////
		ArrayList<CarPark> CarParkArrayList = new ArrayList<CarPark>();
		CarParkArrayList.add(ShoppingCentre);
		CarParkArrayList.add(IndustrialPark);
		CarParkArrayList.add(Station);
		CarParkArrayList.add(University);

		ArrayList<EntryPoint> EntryPointArrayList = new ArrayList<EntryPoint>();
		EntryPointArrayList.add(South);
		EntryPointArrayList.add(East);
		EntryPointArrayList.add(North);

		ArrayList<Road> RoadArrayList = new ArrayList<Road>();
		RoadArrayList.add(SouthtoA);
		RoadArrayList.add(EasttoB);
		RoadArrayList.add(NorthtoC);
		RoadArrayList.add(BtoA);
		RoadArrayList.add(AtoB);
		RoadArrayList.add(BtoC);
		RoadArrayList.add(CtoB);
		RoadArrayList.add(CtoD);
		RoadArrayList.add(AtoIndustrialPark);
		RoadArrayList.add(CtoShoppingCentre);
		RoadArrayList.add(DtoUniversity);
		RoadArrayList.add(DtoStation);

		////////////////////////////////////////// Clock to each component
		////////////////////////////////////////// ////////////////////////////////////////////////////////////////
		Clock clock = new Clock(CarParkArrayList, EntryPointArrayList, RoadArrayList);
		clock.addCarPark(University);
		clock.addCarPark(Station);
		clock.addCarPark(ShoppingCentre);
		clock.addCarPark(IndustrialPark);

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

		A.addDestinationMapping("Industrial Park", 0);
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

		B.addDestinationMapping("Industrial Park", 1);
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

		C.addDestinationMapping("Industrial Park", 2);
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
