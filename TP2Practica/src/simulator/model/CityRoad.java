package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() { 
		int aux = this.totalContamination;
		if(this.weatherConditions.equals(Weather.WINDY) || this.weatherConditions.equals(Weather.STORM)) {
			if(aux - 10 > 0) {
				this.totalContamination = aux-10;
			}
			else {
				this.totalContamination = 0;
			}
		}
		else{
			if(aux - 2 > 0) {
				this.totalContamination = aux-2;
			}
			else {
				this.totalContamination = 0;
			}
		}
	}
	
	@Override
	void updateSpeedLimit() { 
		setMaxSpeed(this.maximumSpeed);
	}
	
	@Override
	int calculateVehicleSpeed(Vehicle v) { 
		return ((11-v.contamination_class)*this.currentSpeedLimit)/11;
	}

}
