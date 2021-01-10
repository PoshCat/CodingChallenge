package truckLoader;

public class Item implements Comparable<Item>{
	private int weight;
	private int value;
	private String id;
	private double ratio;
	
	public Item(int value, int weight, String id) {
		setValue(value);
		setWeight(weight);
		setId(id);
		setRatio((double)value/(double)weight);
	}
	
	public int getWeight() {
		return weight;
	}

	private void setWeight(int weight) {
		this.weight = weight;
	}

	public int getValue() {
		return value;
	}

	private void setValue(int value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public double getRatio() {
		return ratio;
	}
	
	private void setRatio(double ratio) {
		this.ratio = ratio;
	}

	@Override
	public int compareTo(Item arg0) {
		if(arg0.getRatio() == this.getRatio()) {
			return Integer.compare(arg0.getWeight(), this.getWeight());
		} else {
			return Double.compare(arg0.getRatio(), this.getRatio());
		}
	}

}