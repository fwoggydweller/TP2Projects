package simulator.view;

import simulator.model.Weather;

public class RoadEx {

	int length;
	Weather weather;
	int maxSpeed;
	int currentSpeedLim;
	int totalCO2;
	int CO2Limit;
	String ID;

	public RoadEx( String ID, int length, Weather weather, int maxSpeed, int currentSpeedlimit, int totalCO2, int CO2Limit) {// "#", "Length", "Weather", "Max speed", "Current speed limit", "Total CO2", "CO2 limit"}
		
		this.ID = ID;
		this.length = length;
		this.weather = weather;
		this.maxSpeed = maxSpeed;
		this.currentSpeedLim = currentSpeedlimit;
		this.totalCO2 = totalCO2;
		this.CO2Limit = CO2Limit;

	}
	public String getID() {
		return ID;
	}
	public int getLength() {
		return length;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public int getCurrSpeed() {
		return currentSpeedLim;
	}
	public int getTotalCO2() {
		return totalCO2;
	}
	public int getCO2Limit() {
		return CO2Limit;
	}
	public Weather getWeather() {
		return weather;
	}

}
