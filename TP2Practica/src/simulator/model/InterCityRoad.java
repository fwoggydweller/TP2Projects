package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		int x = 0;
		if(this.weather == Weather.CLOUDY) x = 3;
		else if(this.weather == Weather.SUNNY) x = 2;
		else if(this.weather == Weather.STORM) x = 20;
		else if(this.weather == Weather.WINDY) x = 15;
		else if(this.weather == Weather.RAINY) x = 10;
		this.cont = ((100 - x) * this.cont) / 100;
	}

	@Override
	void updateSpeedLimit() {
		if(this.maxCont < this.cont) {
			this.speedL = this.maxSpeedL / 2;
		}
		else this.speedL = this.maxSpeedL;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return this.weather == Weather.STORM? (this.speedL * 8)/10 : this.speedL;
	}

}
