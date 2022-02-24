package uclan.com;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		LogFileManager.openLog(System.getProperty("user.dir"));
		LogFileManager.writeToLog("");
		LogFileManager.writeToLog("");
		LogFileManager.writeToLog(
				"--------------------------------------------------------------------------------------------------");

		LogFileManager.writeToLog("Concurrency Assignment Part 1 started.");
		LogFileManager.writeToLog(
				"--------------------------------------------------------------------------------------------------");

		////////////////////////////////// File Input
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Scanner InputScan = new Scanner(System.in);
		LogFileManager.writeToLog("Enter a file: ");

		String configFileName = InputScan.nextLine();
		LogFileManager.writeToLog("Entered file is: " + configFileName);

		Path configFilePath = Path.of(System.getProperty("user.dir") + configFileName);

		String string = Files.readString(configFilePath);
		String[] elements = string.split("\n");

		String entryPointNorthString = elements[1].split(" ")[1].replaceAll("\\s+", "");
		int entryPointNorth = Integer.valueOf(entryPointNorthString);

		String entryPointEastString = elements[2].split(" ")[1].replaceAll("\\s+", "");
		int entryPointEast = Integer.valueOf(entryPointEastString);

		String entryPointSouthString = elements[3].split(" ")[1].replaceAll("\\s+", "");
		int entryPointSouth = Integer.valueOf(entryPointSouthString);

		String JunctionAString = elements[6].split(" ")[1].replaceAll("\\s+", "");
		int JunctionA = Integer.valueOf(JunctionAString);

		String JunctionBString = elements[7].split(" ")[1].replaceAll("\\s+", "");
		int JunctionB = Integer.valueOf(JunctionBString);

		String JunctionCString = elements[8].split(" ")[1].replaceAll("\\s+", "");
		int JunctionC = Integer.valueOf(JunctionCString);

		String JunctionDString = elements[9].split(" ")[1].replaceAll("\\s+", "");
		int JunctionD = Integer.valueOf(JunctionDString);

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// Create data
		// EntryPoints
		EntryPoint North = new EntryPoint("North", entryPointNorth);
		EntryPoint East = new EntryPoint("East", entryPointEast);
		EntryPoint South = new EntryPoint("South", entryPointSouth);

		// Junctions
		Junction A = new Junction("A", JunctionA);
		Junction B = new Junction("B", JunctionB);
		Junction C = new Junction("C", JunctionC);
		Junction D = new Junction("D", JunctionD);

		// Roads
		Road SouthtoA = new Road(60, "SouthtoA");
		Road EasttoB = new Road(30, "EasttoB");
		Road NorthtoC = new Road(50, "NorthtoC");
		Road BtoA = new Road(7, "BtoA");
		Road AtoB = new Road(7, "AtoB");
		Road BtoC = new Road(10, "BtoC");
		Road CtoB = new Road(10, "CtoB");
		Road CtoD = new Road(10, "CtoD");
		Road AtoIndustrialPark = new Road(15, "AtoIndustrialPark");
		Road CtoShoppingCentre = new Road(7, "CtoShoppingCentre");
		Road DtoUniversity = new Road(15, "DtoUniversity");
		Road DtoStation = new Road(15, "DtoStation");

		// Car Parks
		CarPark ShoppingCentre = new CarPark(400, "Shopping Centre");
		CarPark IndustrialPark = new CarPark(1000, "Industrial Park");
		CarPark Station = new CarPark(150, "Station");
		CarPark University = new CarPark(100, "University");

		//////////////////////////////////////////////////////////////// ArrayList to
		//////////////////////////////////////////////////////////////// count the total
		//////////////////////////////////////////////////////////////// number of cars
		//////////////////////////////////////////////////////////////// created, parked
		//////////////////////////////////////////////////////////////// and queued
		//////////////////////////////////////////////////////////////// //////////////////////////////
		// Car Parks
		ArrayList<CarPark> CarParkArrayList = new ArrayList<CarPark>();
		CarParkArrayList.add(ShoppingCentre);
		CarParkArrayList.add(IndustrialPark);
		CarParkArrayList.add(Station);
		CarParkArrayList.add(University);

		// EntryPoints
		ArrayList<EntryPoint> EntryPointArrayList = new ArrayList<EntryPoint>();
		EntryPointArrayList.add(South);
		EntryPointArrayList.add(East);
		EntryPointArrayList.add(North);

		// Roads
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
		// EntryPoints
		South.entryPointClock = clock;
		East.entryPointClock = clock;
		North.entryPointClock = clock;

		// Junctions
		A.junctionClock = clock;
		B.junctionClock = clock;
		C.junctionClock = clock;
		D.junctionClock = clock;

		// Roads
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

		// Car Parks
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
	}

}
