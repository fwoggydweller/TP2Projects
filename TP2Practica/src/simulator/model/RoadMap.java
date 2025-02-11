package simulator.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RoadMap {
	List<Vehicle> _vehicles;
	List<Road> _roads;
	List<Junction> _junctions;
	Map<String, Vehicle> _vehiclesMap;
	Map<String, Road> _roadsMap;
	Map<String, Junction> _junctionsMap;
	
	void addJunction(Junction j) {
		
	}

	void addRoad(Road r) {
		
	}

	void addVehicle(Vehicle v) {
		
	}

	Vehicle getVehicle(String id) {
		return _vehiclesMap.get(id);
	}
	Road getRoad(String id) {
		return _roadsMap.get(id);
	}
	Junction getJunction(String id) {
		return _junctionsMap.get(id);
	}

	List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(_vehicles);
	}
	List<Road> getRoads(){
		return Collections.unmodifiableList(_roads);
	}
	List<Junction> getJunctions(){
		return Collections.unmodifiableList(_junctions);
	}

	// report()
	
}
