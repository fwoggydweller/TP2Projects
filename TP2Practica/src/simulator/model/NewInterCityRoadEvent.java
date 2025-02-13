package simulator.model;


import java.util.ArrayList;
import java.util.List;

public class NewInterCityRoadEvent extends Event {
	String id;
	int maxSpeed,contLimit,length;
	Weather weather;
	String srcJunc, destJunc;
	public NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
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
		InterCityRoad r = new InterCityRoad(id, map._junctionsMap.get(srcJunc), map._junctionsMap.get(destJunc), maxSpeed, contLimit, length, weather);
		map.addRoad(r);
	}
}