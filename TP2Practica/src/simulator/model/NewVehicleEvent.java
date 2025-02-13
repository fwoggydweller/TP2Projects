package simulator.model;

import java.util.ArrayList;
import java.util.List;
import simulator.model.*;

public class NewVehicleEvent extends Event {
	String id;
	int maxSpeed;
	int contClass;
	List<String> itinerary;
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
	    super(time);
	    this.id = id;
	    this.maxSpeed = maxSpeed;
	    this.contClass = contClass;
	    this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> aux = new ArrayList<Junction>();
		for(String s: itinerary) {	
			aux.add(map._junctionsMap.get(s));
		}	
		Vehicle v = new Vehicle(id, maxSpeed, contClass, aux);
		map._vehicles.add(v);
		map._vehiclesMap.put(id, v);
		v.moveToNextRoad();
	}
}
