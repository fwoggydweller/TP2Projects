package simulator.model;


import java.util.ArrayList;
import java.util.List;

public class NewCityRoadEvent extends Event {
	String id;
	int maxSpeed,contLimit,length;
	Weather weather;
	Junction srcJunc, destJunc;
	public NewCityRoadEvent(int time, String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
	    super(time);
	    this.id = id;
	    this.maxSpeed = maxSpeed;
	    this.contLimit = contLimit;
	    this.length = length;
	    this.srcJunc = srcJunc;
	    this.destJunc = destJunc;
	    this.weather = weather;
	}

	@Override
	void execute(RoadMap map) {
		CityRoad r = new CityRoad(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		map.addRoad(r);
	}
}
