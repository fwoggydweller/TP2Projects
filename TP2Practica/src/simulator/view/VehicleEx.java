package simulator.view;

import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class VehicleEx {


	String ID;
	int maxSpeed;
	int totalCO2;
	int CO2Class;
	int currSpeed;
	int distance;
	String status;
	String itinerary;
	

	public VehicleEx(String ID, String status, String itinerary, int CO2Class, int maxSpeed, int currentSpeed, int totalCO2Emitted, int distanceTraveled) {// "#", "Length", "Weather", "Max speed", "Current speed limit", "Total CO2", "CO2 limit"}
		
		this.ID = ID;
		this.maxSpeed = maxSpeed;
		this.currSpeed = currentSpeed;
		distance = distanceTraveled;
		this.status = status;
		this.itinerary = itinerary;
		this.CO2Class = CO2Class;
		this.totalCO2 = totalCO2Emitted;

	}
	public String getID() {
		return ID;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getTotalCO2() {
		return totalCO2;
	}
	public int getCO2Class() {
		return CO2Class;
	}
	public int getDistance() {
		return distance;
	}
	public String getStatus() {
		return status;
	}
	public String getItinerary() {
		return itinerary;
	}
	public int getCurrSpeed() {
		return currSpeed;
	}
}
