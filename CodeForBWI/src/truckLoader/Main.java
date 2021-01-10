package truckLoader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		ArrayList<Item> loadingList1 = new ArrayList<Item>();
		ArrayList<Item> loadingList2 = new ArrayList<Item>();
		int maxVal1 = tl.getMaxValue(TRUCKLIMIT - DRIVERWEIGHT1);
		int greedyVal1 = tl.greedyAlgorithm(TRUCKLIMIT - DRIVERWEIGHT1, loadingList1);
		int maxVal2 = tl.getMaxValue(TRUCKLIMIT - DRIVERWEIGHT2);
		int greedyVal2 = tl.greedyAlgorithm(TRUCKLIMIT - DRIVERWEIGHT2, loadingList2);
		printList(loadingList1, java.time.LocalTime.now() +": Erster LKW mit " + greedyVal1 + " Wert geladen:");
		printList(loadingList2, "Zweiter LKW mit " + greedyVal2 + " Wert geladen:");
		System.out.println("Process finished");
	}
	

	/**
	 * as described by Kip in this stackoverflow thread: https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
	 * 
	 * @param loadingList
	 */
	private static void printList(ArrayList<Item> loadingList, String initialMessage) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
			fw = new FileWriter("loadingList.txt", true);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
			
			out.println(initialMessage);
			
			while(!loadingList.isEmpty()) {
				String itemType = loadingList.get(0).getId();
				int itemCount = countItem(loadingList);
				out.println(itemType + ": " + itemCount);
			}
			
			out.close();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
	
	/**
	 * @param loadingList
	 * @return count of the first item in the list
	 */
	private static int countItem(ArrayList<Item> loadingList) {
		int count = 0;
		String itemType = loadingList.get(0).getId();
		loadingList.remove(0);
		count++;
		int currItem = 0;
		while(currItem < loadingList.size()) {
			if(loadingList.get(currItem).getId() == itemType) {
				count++;
				loadingList.remove(currItem);
			} else {
				currItem++;
			}
		}
		return count;
	}
}