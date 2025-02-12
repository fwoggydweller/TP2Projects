package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int aux = this.cont;
		int curr = 100 - aux;
		if(this.weather.equals(Weather.SUNNY)) {
			setCurrCont(curr*2);
		}
		else if(this.weather.equals(Weather.RAINY)) {
			setCurrCont(curr*3);
		}
		else if(this.weather.equals(Weather.STORM)) {
			setCurrCont(curr*10);
		}
		else if(this.weather.equals(Weather.CLOUDY)) {
			setCurrCont(curr*15);
		}
		else{
			setCurrCont(curr*20);
		}
	}
	
	@Override
	void updateSpeedLimit() {
		if(this.maxCont < this.cont) {
			setMaxSpeed(this.maxSpeedL/2);
		}
		else {
			setMaxSpeed(this.maxSpeedL/2 + this.maxSpeedL);
		}
	}
	
	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(getWeather().equals(Weather.STORM)) {
			return (this.speedL*8)/10;
		}
		return this.speedL;
	}

}
