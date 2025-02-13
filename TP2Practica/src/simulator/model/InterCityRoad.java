package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() { //Checked
		int x = 0;
		if(this.weatherConditions == Weather.CLOUDY) x = 3;
		else if(this.weatherConditions == Weather.SUNNY) x = 2;
		else if(this.weatherConditions == Weather.STORM) x = 20;
		else if(this.weatherConditions == Weather.WINDY) x = 15;
		else if(this.weatherConditions == Weather.RAINY) x = 10;
		this.totalContamination = ((100 - x) * this.totalContamination) / 100;
	}

	@Override
	void updateSpeedLimit() { //Checked
		if(this.totalContamination > this.contaminationAlarmLimit) {
			this.currentSpeedLimit = this.maximumSpeed / 2;
		}
		else this.currentSpeedLimit = this.maximumSpeed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) { //Checked
		return this.weatherConditions == Weather.STORM? (this.currentSpeedLimit * 8)/10 : this.currentSpeedLimit;
	}

}
