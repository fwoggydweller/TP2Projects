package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int aux = getContLimit();
		int curr = 100 - aux;
		if(getWeather()==Weather.SUNNY) {
			setContamination(curr*2);
		}
		else if(getWeather().equals(Weather.RAINY)) {
			setContamination(curr*3);
		}
		else if(getWeather().equals(Weather.STORM)) {
			setContamination(curr*10);
		}
		else if(getWeather().equals(Weather.CLOUDY)) {
			setContamination(curr*15);
		}
		else{
			setContamination(curr*20);
		}
	}
	
	@Override
	void updateSpeedLimit() {
		if(getTotalCO2() < getContLimit()) {
			setMaxSpeed(getMaxSpeed()/2);
		}
		else {
			setMaxSpeed(getMaxSpeed()/2 + getMaxSpeed());
		}
	}
	
	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(getWeather().equals(Weather.STORM)) {
			return (getSpeedLimit()*8)/10;
		}
		return getSpeedLimit();
	}

}
