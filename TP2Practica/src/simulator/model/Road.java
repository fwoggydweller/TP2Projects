package simulator.model;

import java.util.List;

public class Road extends SimulatedObject {
	private Junction jSrc, jDest;
	private int leng, speedL, maxSpeedL, cont, maxCont;
	private List<Vehicle> vehicles;
	private Weather weather;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id);
		this.jSrc = srcJunc;
		this.jDest = destJunc;
		int maxSpeedL = maxSpeed;
		int maxCont = contLimit;
		int leng = length;
		this.weather = weather;
	}
	
	void enter(Vehicle v) {
		this.vehicles.add(v);
	}
	void exit(Vehicle v) {
		this.vehicles.remove(v);// see if it uses equals (must implement a correct equals)
	}
}
