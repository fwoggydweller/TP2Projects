package simulator.view;

public class VehicleWithSpeedData {
	String id;
	int Speed;
	public VehicleWithSpeedData(String id, int s){
		this.id = id;
		this.Speed = s;
	}
	public int getSpeed() {
		return Speed;
	}
	public String getId() {
		return id;
	}
}
