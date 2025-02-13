package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	Junction origin;
	Junction destination;
	int length;
	int maximumSpeed;
	int currentSpeedLimit;
	int contaminationAlarmLimit;
	Weather weatherConditions;
	int totalContamination;
	List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){ //Checked
		super(id);
		//We first check if the junctions are null, if they are not, then we join the road with it's origin and destination
		if(srcJunc == null || destJunc == null) {
			throw new IllegalArgumentException("The origin/destination junctions must be different than null.");
		}
		this.origin = srcJunc;
		this.destination = destJunc;
		this.origin.addOutGoingRoad(this);
		this.destination.addIncomingRoad(this);
		//Check if maximum speed is possitive
		this.maximumSpeed = maxSpeed;
		this.currentSpeedLimit = maxSpeed;
		if(this.maximumSpeed < 1) {
			if(this.maximumSpeed < 0) {
				throw new IllegalArgumentException("Maximum speed cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Maximum speed must be positive");
			}
		}
		
		//Contamination must be non negative
		this.contaminationAlarmLimit = contLimit;
		if(this.contaminationAlarmLimit < 0) {
			throw new IllegalArgumentException("Maximum contamination cannot be negative");
		}
		//Length must be positive
		this.length = length;
		if(this.length < 1) {
			if(this.length < 0) {
				throw new IllegalArgumentException("Road length cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Road length must be positive");
			}
		}
		//Check if weather is not null
		this.weatherConditions = weather;
		if(this.weatherConditions == null) {
			throw new IllegalArgumentException("Weather cannot be null");
		}
		this.vehicles = Collections.unmodifiableList(new ArrayList<>());
	}
	
	void enter(Vehicle v) { //Checked
		if(v.getLocation()==0 && v.getSpeed() == 0) { //If it fits in the conditions, then it enters the road's vehicle list
			this.vehicles.add(v);
		}
		else {
			throw new IllegalArgumentException("The vehicle can not be added, as it does not suit the requirements (current speed or location).");
		}
	}
	void exit(Vehicle v) { //Checked
		this.vehicles.remove(v); //We just remove v from the vehicle list
	}
	void setWeather(Weather w){
		if(w != null) this.weatherConditions = w;
		else {
			throw new IllegalArgumentException("Can't set the road's weather,as it's null.");
		}
	}
	void addContamination(int c){ //Checked
		if(c > -1) {
			this.totalContamination += c;
		}
		else {
			throw new IllegalArgumentException("Can't add a contamination that is a negative number to the road.");
		}
	}
	public int getLength() {
		return this.length;
	}
	public int getSpeedLimit() {
		return this.currentSpeedLimit;
	}
	public int getMaxSpeed() {
		return this.maximumSpeed;
	}
	public int getContLimit() {
		return this.contaminationAlarmLimit;
	}
	public int getTotalCO2() {
		return this.totalContamination;
	}
	public Junction getDest() {
		return this.destination;
	}
	public Junction getSrc() {
		return this.origin;
	}
	public Weather getWeather() {
		return this.weatherConditions;
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
		this.contaminationAlarmLimit = c;
	}
	void setCurrCont(int c) {
		if(this.length < 1) {
			if(this.length < 0) {
				throw new IllegalArgumentException("Current contamination cannot be negative");
			}
			else {
				throw new IllegalArgumentException("Current contamination must be positive");
			}
		}
		this.totalContamination = c;
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
		this.maximumSpeed = s;
	}
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	void advance(int time){ //Checked
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle v: vehicles){ //For each vehicle, we must change the speed with calculate Vehicle speed and then advance the vehicle
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		vehicles.sort((o1, o2) -> Integer.compare(o1.location, o2.location)); //We short the vehicles list with the location
	}
	
	public JSONObject report() { //Checked
		JSONObject road = new JSONObject();
		road.put("id", this._id);
	    road.put("speedlimit", this.currentSpeedLimit);
	    road.put("weather", this.weatherConditions.toString());
	    road.put("co2", this.totalContamination);
	    JSONArray vehArray = new JSONArray();
	    for (Vehicle v : this.vehicles) {
	        vehArray.put(v.getId());
	    }
	    road.put("vehicles", vehArray);
	    return road;
	}
	
}
