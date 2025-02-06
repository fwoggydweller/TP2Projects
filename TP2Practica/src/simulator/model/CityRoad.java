package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int aux = getCurrCont();
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
		if(getMaxCont() < getCurrCont()) {
			setMaxSpeed(getMaxSpeedLim()/2);
		}
		else {
			setMaxSpeed(getMaxSpeedLim()/2 + getMaxSpeedLim());
		}
	}
	
	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(getWeather().equals(Weather.STORM)) {
			return (getCurrSpeedLim()*8)/10;
		}
		return getCurrSpeedLim();
	}

}
