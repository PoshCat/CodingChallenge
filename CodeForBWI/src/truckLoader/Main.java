package truckLoader;

import java.util.ArrayList;

public class Main {
	private static int TRUCKLIMIT = 1100000;
	private static int DRIVERWEIGHT1 = 72400;
	private static int DRIVERWEIGHT2 = 85700;

	public static void main(String[] args) {
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for(int i = 0; i < 205; i++) {
			itemList.add(new Item(40, 2451, "NotebookBüro13''"));
		}
		for(int i = 205; i < 625; i++) {
			itemList.add(new Item(35, 2978, "NotebookBüro14''"));
		}
		for(int i = 625; i < 1075; i++) {
			itemList.add(new Item(80, 3625, "NotebookOutdoor"));
		}
		for(int i = 1075; i < 1135; i++) {
			itemList.add(new Item(30, 717, "MobiltelefonBüro"));
		}
		for(int i = 1135; i < 1292; i++) {
			itemList.add(new Item(60, 988, "MobiltelefonOutdoor"));
		}
		for(int i = 1292; i < 1512; i++) {
			itemList.add(new Item(65, 1220, "MobiltelefonHeavyDuty"));
		}
		for(int i = 1512; i < 2132; i++) {
			itemList.add(new Item(40, 1405, "TabletBüroKlein"));
		}
		for(int i = 2132; i < 2382; i++) {
			itemList.add(new Item(40, 1455, "TabletBüroGroß"));
		}
		for(int i = 2382; i < 2922; i++) {
			itemList.add(new Item(45, 1690, "TabletOutdoorKlein"));
		}
		for(int i = 2922; i < 3292; i++) {
			itemList.add(new Item(68, 1980, "TabletOutdoorGroß"));
		}
		TruckLoader tl = new TruckLoader(itemList);
		int maxVal = tl.getMaxValue(TRUCKLIMIT-DRIVERWEIGHT1);
		System.out.println("Maximale Value: " + maxVal);
		System.out.println("Test Value: " + tl.getGreedyValue(TRUCKLIMIT-DRIVERWEIGHT1));
		ArrayList<Item> loadingList = new ArrayList<Item>();
		if(!tl.getLoadingList(TRUCKLIMIT-DRIVERWEIGHT1, maxVal, 0, 0, 0, loadingList)) {
			System.out.println("Given target value can not be loaded!");
		}
		System.out.println("Test");
	}
}