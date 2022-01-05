package uclan.com;

public class Source {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Create data
		EntryPoint South = new EntryPoint("South");
		Road SouthtoA = new Road(10);
		Junction A = new Junction();
		Road AtoIndustrialPark = new Road(10);
		CarPark IndustrialPark = new CarPark(10);

		// Join data
		South.road = SouthtoA;
		A.entryRoad = SouthtoA;
		A.exitRoad = AtoIndustrialPark;
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
		A.start();
		IndustrialPark.start();

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
