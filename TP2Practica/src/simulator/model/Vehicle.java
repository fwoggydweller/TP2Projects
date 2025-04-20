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
	int indexOfLastJunct;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) { 
		  super(id);
		  this._id = id;
		  if(this._id == null) {
			  throw new IllegalArgumentException("Id must have a value different than null.");
		  }
		  this.maximum_speed = maxSpeed;
		  if(this.maximum_speed < 1) {
			  if(this.maximum_speed < 0) {
				  throw new IllegalArgumentException("Maximum speed cannot be negative");
			  }
			  else {
				  throw new IllegalArgumentException("Maximum speed cannotbe zero.");
			  }
		  }
		  this.current_speed = 0;
		  this.contamination_class = contClass;
		  if(this.contamination_class > 10 || this.contamination_class < 0) {
			  throw new IllegalArgumentException("Contamination class must be between zero and ten.");
		  }
		  this.total_contamination = 0;
		  this.total_travel_distance = 0;
		  this.status = VehicleStatus.PENDING;
		  if(itinerary.size() < 2) {
			  throw new IllegalArgumentException("Itinerary's size must be of size 2 or more.");
		  }
		  this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		  this.road = null;
		  this.indexOfLastJunct = 0;
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
			if(this.status == VehicleStatus.TRAVELING) {
				this.current_speed = minimum(s, this.maximum_speed);
			}
			else {
				this.current_speed = 0;
			}
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
		if(this.status.equals(VehicleStatus.TRAVELING)) {

			int oldLocation = this.location;
			this.location = this.location+this.current_speed; //Location update
			if(this.location > road.length) { //If it exceeds, it updates it to the road's length
				this.location = road.getLength();
			}
			int d = this.location-oldLocation; //Calculate the advanced distance
			this.total_travel_distance = this.total_travel_distance + d; //We update the total travel distance
			int c = d *this.contamination_class; //Calculate the contamination produced
			this.total_contamination = this.total_contamination+c; //Add it to the total contamination of the vehicle
			this.road.addContamination(c); //Add it to the road's contamination

			if(this.location == road.getLength()) { //If it's at the end of the road, we enter the junction
				road.getDest().enter(this); //We enter in the junction queue
				this.status = VehicleStatus.WAITING; //We change the status
				this.current_speed = 0; //We reduce to zero the current speed
				this.indexOfLastJunct++; //We update the index (If we were in the junction zero, we will now be on the junction one
			}
			
		}
		else { //If it's not traveling, we do not advance
			return;
		}
	}
	
	void moveToNextRoad() {//Catches the current junction and the next one and uses roadTo() to calculate which road we should use
		if(this.status == VehicleStatus.PENDING || this.status == VehicleStatus.WAITING) {
			if(this.road == null && this.indexOfLastJunct == 0) { //If we are on the first junction
				this.road = this.itinerary.get(indexOfLastJunct).roadTo(this.itinerary.get(indexOfLastJunct+1)); //We update the road
				this.location = 0;
				this.road.enter(this);
				this.status = VehicleStatus.TRAVELING;
			}
			else { //If it's not, we update everything
				this.road.exit(this);
				if(this.indexOfLastJunct == (this.itinerary.size()-1)) { //If it's the last road, update things
					this.status = VehicleStatus.ARRIVED;
					this.current_speed = 0;
					this.road = null;
				}
				else { //If it's not the last road
					this.road = this.itinerary.get(indexOfLastJunct).roadTo(this.itinerary.get(indexOfLastJunct+1)); //We update the road
					this.location = 0; //Set the location
					this.road.enter(this); //We enter
					this.status = VehicleStatus.TRAVELING; //Change the status
				}
			}
		}
		else {
			throw new IllegalArgumentException("Cannot move a vehicle that is not in a junction.");
		}
	}
	@Override
	public JSONObject report() { 
		JSONObject car = new JSONObject();
		car.put("distance", this.total_travel_distance);
		car.put("co2", this.total_contamination);
		car.put("id", this._id);
		car.put("class", this.contamination_class);
	    car.put("speed", this.current_speed);
	    car.put("status", this.status.toString());
	    if(this.road != null) {
	    	car.put("road", this.road._id);
		    car.put("location", this.location);
	    }
	    return car;
	}
	public int getTotalDistance() {
		return this.total_travel_distance;
	}
	public int getJunctionIndex() {
		return this.indexOfLastJunct;
	}
}
