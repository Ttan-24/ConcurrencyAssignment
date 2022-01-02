package uclan.com;

public class Source {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntryPoint South = new EntryPoint();
		Road SouthtoA = new Road(10);
		Junction A = new Junction();
		Road AtoIndustrialPark = new Road(10);
		CarPark IndustrialPark = new CarPark();

		South.road = SouthtoA;
		A.roadList.add(SouthtoA);
		A.roadList.add(AtoIndustrialPark);
		IndustrialPark.road = AtoIndustrialPark;

		AtoIndustrialPark.add(5);
		AtoIndustrialPark.add(6);
		AtoIndustrialPark.remove();
	}

}
