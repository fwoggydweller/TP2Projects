package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	List<Junction> itinerary;
	int maximum_speed;
	int current_speed;
	VehicleStatus status;
	Road road;
	int location;
	int contamination_class;
	int total_contamination;
	int total_travel_distance;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) { //What do I have to do with the road? And with the ID?
		  super(id);
		  this._id = id;
		  this.maximum_speed = maxSpeed;
		  this.current_speed = 0;
		  this.contamination_class = contClass;
		  this.total_contamination = 0;
		  this.total_travel_distance = 0;
		  this.status = VehicleStatus.TRAVELING;
		  this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
	}
	public int getLocation() {
		return location;
	}
	public int getSpeed(){
		return current_speed;
	}
	public int getMaxSpeed(){
		return maximum_speed;
	}
	public int getContClass(){
		return contamination_class;
	}
	public VehicleStatus getStatus(){
		return status;
	}
	public int getTotalCO2(){
		return total_contamination;
	}
	public List<Junction> getItinerary(){
		return itinerary;
	}
	public Road getRoad(){
		return road;
	}
	void setSpeed(int s) throws IllegalArgumentException {
		if(s>-1) {
			this.current_speed = minimum(s, this.maximum_speed);
		}
		else {
			throw new IllegalArgumentException("Speed can't be a negative number.");
		}
	}
	void setContClass(int c) throws IllegalArgumentException {
		if(c>-1 && c<11) {
			this.contamination_class = c;
		}
		else {
			throw new IllegalArgumentException("Contamination class needs to be in between 10 and 0.");
		}
	}
	private int minimum(int a, int b) {
		if(a>b) {
			return b;
		}
		else {
			return a;
		}
	}
	@Override
	void advance(int time) {
		int pastLocation = this.location;
		this.location = minimum(this.location+this.current_speed, this.road.getLength()); //Update of the location
		int l = this.location - pastLocation;
		int c = this.contamination_class * l;
		this.total_contamination += c;  //Update of the contamination
		if(this.location == this.road.getLength()) { //If it's on a junction, checks if it's the last one
			if(itinerary.indexOf(this.road)+1 != itinerary.size()) { //If it's not the last one, it's status is Pending
				this.status = VehicleStatus.PENDING; 
				this.current_speed = 0;
			}
			else { //If it is the last one, it sets the status to arrived
				this.status = VehicleStatus.ARRIVED; 
				this.current_speed = 0;
			}
			
		}
		else { //If it's not a junction, the car is traveling
			this.status = VehicleStatus.TRAVELING; 
		}
	}
	void moveToNextRoad() {//Catches the current junction and the next one and uses roadTo() to calculate which road we should use
		int index = itinerary.indexOf(this.road);
		this.road.exit(this);
		this.location = 0;
		if(this.status == VehicleStatus.PENDING) {
			this.road = itinerary.get(0).roadTo(itinerary.get(1));
		}
		else {
			this.road = itinerary.get(index).roadTo(itinerary.get(index+1));
		}
		this.road.enter(this);
	}
	@Override
	public JSONObject report() {
		JSONObject car = new JSONObject();
		car.put("id", this._id);
	    car.put("speed", this.current_speed);
	    car.put("distance", this.total_travel_distance);
	    car.put("co2", this.total_contamination);
	    car.put("class", this.contamination_class);
	    car.put("status", this.status);
	    car.put("road", this.road._id);
	    car.put("location", this.location);
	    return car;
	}
}
