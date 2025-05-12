package simulator.view;

public class CostData {
	String id;
	int Cost;
	int Discount;
	int Total;
	public CostData(String id, int c, int d, int t) {
		this.id = id;
		this.Cost = c;
		this.Discount = d;
		this.Total = t;
	}
	public String getId() {
		return this.id;
	}
	public int getCost() {
		return this.Cost;
	}
	public int getDiscount() {
		return this.Discount;
	}
	public int getTotal() {
		return this.Total;
	}
}
