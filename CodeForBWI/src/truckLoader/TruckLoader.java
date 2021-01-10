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
		int[] m1 = new int[maxWeight+1];
		int[] m2 = new int[maxWeight+1];
		for(int i = 0; i < m1.length; i++) {
			m1[i] = 0;
			m2[i] = 0;
		}
		for(int i = 0; i < itemList.size(); i++) {
			for(int j = 0; j <= maxWeight; j++) {
				if(itemList.get(i).getWeight() > j) {
					m1[j] = m2[j];
				} else {
					m1[j] = Math.max(m2[j], m2[j-itemList.get(i).getWeight()] + itemList.get(i).getValue());
				}
			}
			for(int k = 0; k < maxWeight+1; k++) {
				m2[k] = m1[k];
			}
		}
		return m1[maxWeight];
	}
	
	/**
	 * time consuming method to fill the loading list optimally for one truck using dynamic programming
	 * explanation: https://en.wikipedia.org/wiki/Knapsack_problem
	 * 
	 * @param loadingList
	 * @param itemLim
	 * @param maxWeight
	 */
	public void getLoadingList(ArrayList<Item> loadingList, int itemLim, int maxWeight) {
		int[] m1 = new int[maxWeight+1];
		int[] m2 = new int[maxWeight+1];
		for(int i = 0; i < m1.length; i++) {
			m1[i] = 0;
			m2[i] = 0;
		}
		for(int i = 0; i < itemLim; i++) {
			for(int j = 0; j <= maxWeight; j++) {
				if(itemList.get(i).getWeight() > j) {
					m1[j] = m2[j];
				} else {
					m1[j] = Math.max(m2[j], m2[j-itemList.get(i).getWeight()] + itemList.get(i).getValue());
				}
			}
			if(i != itemLim - 1) {
				for(int k = 0; k < maxWeight+1; k++) {
					m2[k] = m1[k];
				}				
			}
		}
		if(itemLim == 0) {
			return;
		}
		System.out.println(itemLim + System.currentTimeMillis());
		if(m1[maxWeight] > m2[maxWeight]) {
			loadingList.add(itemList.get(itemLim));
			itemList.remove(itemLim);
			getLoadingList(loadingList, itemLim-1, maxWeight-itemList.get(itemLim).getWeight());
		} else {
			getLoadingList(loadingList, itemLim-1, maxWeight);
		}
	}
  
	/**
	 * implements a rudimentary algorithm to create a test value for the main algorithm, which it has to surpass
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
	
	public int getItemListSize() {
		return itemList.size();
	}
}