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
				if(j==2963) {
					boolean flag = true;
				}
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

	public boolean getLoadingList(int maxWeight, int targetVal, int currVal, int currWeight, int currItem, ArrayList<Item> loadingList) {
		if(currItem >= itemList.size()) return false; //TODO correct recursion anchor!!!
		System.out.println("In Progress: " + currItem);
		currVal += itemList.get(currItem).getValue();
		currWeight += itemList.get(currItem).getWeight();
		if(currWeight > maxWeight) {
			return false;
		}
		loadingList.add(itemList.get(currItem));
		if(currVal == targetVal) {
			return true;
		}
		if (solvable(currVal, currWeight, currItem, maxWeight, targetVal)) {
			if (getLoadingList(maxWeight, targetVal, currVal, currWeight, currItem + 1, loadingList)) {
				return true;
			} else {
				currVal -= itemList.get(currItem).getValue();
				currWeight -= itemList.get(currItem).getWeight();
				loadingList.remove(loadingList.size()-1);
				return getLoadingList(maxWeight, targetVal, currVal, currWeight, currItem + 1, loadingList);
			} 
		} else {
			return false;
		}
	}

	/**
	 * determines a bound to kill live nodes which can't produce a correct solution
	 * 
	 * @return upper bound for current node
	 */
	private boolean solvable(int currVal, int currWeight, int currItem, int maxWeight, int targetVal) {
		while(true) {
			currItem++;
			if(currItem < itemList.size()) { //kill the node if it can't reach targetVal with the rest of the items
				if(currWeight + itemList.get(currItem).getWeight() > maxWeight) {
					currVal += ((double)(maxWeight-currWeight)/(double)itemList.get(currItem).getWeight())*(double)itemList.get(currItem).getValue();
					if(currVal >= targetVal) {
						return true;
					} else {
						return false;
					}
				} else {
					currWeight += itemList.get(currItem).getWeight();
					currVal += itemList.get(currItem).getValue();			
				}
				if(currVal >= targetVal) {	//first check if the target value can be reached
					return true;
				}
				if(currWeight > maxWeight) { //then kill the node if it already is over maxWeight
					return false;
				}
			} else {
				return false;
			}
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
}