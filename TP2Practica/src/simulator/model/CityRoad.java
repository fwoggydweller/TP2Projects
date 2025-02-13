package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() { //Checked
		int aux = this.totalContamination;
		int curr = 100 - aux;
		if(this.weatherConditions.equals(Weather.SUNNY)) {
			setCurrCont(curr*2);
		}
		else if(this.weatherConditions.equals(Weather.RAINY)) {
			setCurrCont(curr*3);
		}
		else if(this.weatherConditions.equals(Weather.STORM)) {
			setCurrCont(curr*10);
		}
		else if(this.weatherConditions.equals(Weather.CLOUDY)) {
			setCurrCont(curr*15);
		}
		else{
			setCurrCont(curr*20);
		}
	}
	
	@Override
	void updateSpeedLimit() { //Checked
		if(this.contaminationAlarmLimit < this.totalContamination) {
			setMaxSpeed(this.maximumSpeed/2);
		}
		else {
			setMaxSpeed(this.maximumSpeed/2 + this.maximumSpeed);
		}
	}
	
	@Override
	int calculateVehicleSpeed(Vehicle v) { //Checked
		return ((11-v.contamination_class)*this.currentSpeedLimit)/11;
	}

}
