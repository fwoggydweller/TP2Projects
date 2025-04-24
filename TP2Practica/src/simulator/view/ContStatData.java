package simulator.view;

public class ContStatData {
	private int time;
	private String vehicle;
	private String road;
	ContStatData (int time, String vehicle, String road){
		this.time = time;
		this.vehicle = vehicle;
		this.road = road;
	}
	public Object getTime() {
		// TODO Auto-generated method stub
		return time;
	}
	public Object getVehicle() {
		// TODO Auto-generated method stub
		return vehicle;
	}
	public Object getRoad() {
		// TODO Auto-generated method stub
		return road;
	}
	
}
