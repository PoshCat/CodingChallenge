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
	 * This function computes the maximum value loadable for two trucks with given max loading capacity.
	 * 
	 * @param maxWeight
	 * @return maximum value for given maximum Weight
	 */
	public int getMaxValue(int maxWeight) {
		int n = itemList.size();
		int[] R1 = new int[maxWeight+1]; // as opposed to normal dynamic programming not all states are saved here to save memory. only the lowest two already computed states are saved
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

	public boolean getLoadingList(int maxWeight, int targetVal, int currVal, int currWeight, int currItem, boolean[] loadingList) {
		if(currItem >= itemList.size()) return false; //TODO correct recursion anchor!!!
		if(currItem % 5 == 0) {
			System.out.println("In Progress");
		}
		currVal += itemList.get(currItem).getValue();
		currWeight += itemList.get(currItem).getWeight();
		if(currWeight > maxWeight) {
			return false;
		}
		loadingList[currItem] = true;
		if(currVal == targetVal) {
			return true;
		}
		if (solvable(currVal, currWeight, currItem, maxWeight, targetVal)) {
			if (getLoadingList(maxWeight, targetVal, currVal, currWeight, currItem + 1, loadingList)) {
				return true;
			} else {
				currVal -= itemList.get(currItem).getValue();
				currWeight -= itemList.get(currItem).getWeight();
				loadingList[currItem] = false;
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
				currWeight += itemList.get(currItem).getWeight();
				currVal += itemList.get(currItem).getValue();
				if(currVal > targetVal) {	//first check if the target value can be reached
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
	 * @return test value for comparison
	 */
	public int getGreedyValue(int maxWeight) {
		int val = 0;
		int weight = 0;
		for(int i = 0; i < itemList.size(); i++) {
			if(weight + itemList.get(i).getWeight() <= maxWeight) {
				val += itemList.get(i).getValue();
				weight += itemList.get(i).getWeight();
			}
		}
		return val;
	}
}