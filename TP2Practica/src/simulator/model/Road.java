package simulator.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	Junction jSrc, jDest;
	int leng, speedL, maxSpeedL, cont, maxCont;
	List<Vehicle> vehicles;
	Weather weather;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id);
		this.jSrc = srcJunc;
		this.jSrc.addOutGoingRoad(this);
		this.jDest = destJunc;
		this.jDest.addIncomingRoad(this);
		this.maxSpeedL = maxSpeed;
		if(this.maxSpeedL < 1) {
			if(this.maxSpeedL < 0) {
				throw new IllegalArgumentException("Maximum speed cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Maximum speed must be positive");
			}
		}
		this.maxCont = contLimit;
		if(this.maxCont < 1) {
			if(this.maxCont < 0) {
				throw new IllegalArgumentException("Maximum contamination cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Maximum contamination must be positive");
			}
		}
		this.leng = length;
		if(this.leng < 1) {
			if(this.leng < 0) {
				throw new IllegalArgumentException("Road length cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Road length must be positive");
			}
		}
		this.weather = weather;
		if(this.weather == null) {
			throw new IllegalArgumentException("Weather cannot be null");
		}
		this.vehicles = new ArrayList<Vehicle>();
	}
	
	void enter(Vehicle v) {
		if(v.getLocation()==0 && v.getSpeed() == 0) {
			this.vehicles.add(v);
		}
		else {
			throw new IllegalArgumentException("The vehicle can not be added, as it does not suit the requirements (current speed or location).");
		}
	}
	void exit(Vehicle v) {
		this.vehicles.remove(v);// see if it uses equals (must implement a correct equals)
	}
	void setWeather(Weather w){
		if(w != null) this.weather = w;
		else {
			throw new IllegalArgumentException("Can't set the road's weather,as it's null.");
		}
	}
	void addContamination(int c){
		if(c > -1) {
			this.cont += c;
		}
		else {
			throw new IllegalArgumentException("Can't add a contamination that is a negative number to the road.");
		}
	}
	public int getLength() {
		return this.leng;
	}
	public int getSpeedLimit() {
		return this.speedL;
	}
	public int getMaxSpeed() {
		return this.maxSpeedL;
	}
	public int getContLimit() {
		return this.cont;
	}
	public int getTotalCO2() {
		return this.maxCont;
	}
	public Junction getDest() {
		return this.jDest;
	}
	public Junction getSrc() {
		return this.jSrc;
	}
	public Weather getWeather() {
		return this.weather;
	}
	public List<Vehicle> getVehicles(){
		return this.vehicles;
	}
	void setMaxCont(int c) {
		if(c < 1) {
			if(c < 0) {
				throw new IllegalArgumentException("Maximum contamination cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Maximum contamination must be positive");
			}
		}
		this.maxCont = c;
	}
	void setCurrCont(int c) {
		if(this.leng < 1) {
			if(this.leng < 0) {
				throw new IllegalArgumentException("Current contamination cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Current contamination must be positive");
			}
		}
		this.cont = c;
	}
	void setMaxSpeed(int s) {
		if(s < 1) {
			if(s < 0) {
				throw new IllegalArgumentException("Max speed cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Max speed must be positive");
			}
		}
		this.maxSpeedL = s;
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
