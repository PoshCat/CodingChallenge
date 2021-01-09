package truckLoader;

import java.util.ArrayList;
import java.util.Collections;

public class TruckLoader {
	private final ArrayList<Item> itemList;

	public TruckLoader(ArrayList<Item> itemList) {
		this.itemList = itemList;
		Collections.sort(itemList);
	}

	/**
	 * This function computes the maximum value loadable for one truck with given maximum loading capacity.
	 * 
	 * @param maxWeight
	 * @return maximum value for given maximum weight
	 */
	public int getMaxValue(int maxWeight) {
		int n = itemList.size();
		int[] R1 = new int[maxWeight+1]; // as opposed to normal dynamic programming not all states are saved here to save memory. only the lowest two already computed states are saved. this reduces the memory usage to a usable level
		int[] R2 = new int[maxWeight+1];
		for(int i = n-1; i >= 0; i--) {
			for(int j = 1; j <= maxWeight; j++) {
				if(itemList.get(i).getWeight() <= j) {
					R1[j] = Math.max((itemList.get(i).getValue() + R2[j-itemList.get(i).getWeight()]), R2[j]);
				} else {
					R1[j] = R2[j];
				}
			}
			R2 = R1;
		}
		return R1[maxWeight];
	}

	/**
	 * rudimentary algorithm using the ordered  ArraList from the constructor
	 * 
	 * @param maxWeight
	 * @return final value for comparison
	 */
	public int greedyAlgorithm(int maxWeight, ArrayList<Item> loadingList) {
		int weight = 0;
		int val = 0;
		while(weight + itemList.get(0).getWeight() <= maxWeight && !itemList.isEmpty()) { //take items from the ordered list until the truck is full or the list is empty
				weight += itemList.get(0).getWeight();
				val += itemList.get(0).getValue();
				loadingList.add(itemList.get(0));
				itemList.remove(0); //the item is removed from the itemList to be able to load a second truck without loading the same item onto two trucks
		}
		return val;
	}
}