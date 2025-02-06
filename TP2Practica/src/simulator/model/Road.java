package simulator.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
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
		this.vehicles = new ArrayList<Vehicle>();
	}
	
	void enter(Vehicle v) {
		this.vehicles.add(v);
	}
	void exit(Vehicle v) {
		this.vehicles.remove(v);// see if it uses equals (must implement a correct equals)
	}
	void setWeather(Weather w){
		if(w != null) this.weather = weather;
	}
	void addContamination(int c){
		this.cont += c;
	}
	public int getLength() {
		return this.leng;
	}
	public int getCurrSpeedLim() {
		return this.speedL;
	}
	public int getMaxSpeedLim() {
		return this.maxSpeedL;
	}
	public int getCurrCont() {
		return this.cont;
	}
	public int getMaxCont() {
		return this.maxCont;
	}
	public Junction getDestination() {
		return this.jDest;
	}
	public Junction getSource() {
		return this.jSrc;
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	void advance(int time){
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle v: vehicles){ // see time to i correlation
			v.advance(time);
		}
	}
	
	public JSONObject report() {
		JSONObject road = new JSONObject();
		road.put("id", this._id);
	    road.put("speedLimit", this.speedL);
	    road.put("weather", this.weather);
	    road.put("co2", this.cont);
	    road.put("vehicles", this.vehicles);
	    return road;
	}
	
}
