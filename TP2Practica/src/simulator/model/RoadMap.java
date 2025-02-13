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
		this._junctions.add(j);
		this._junctionsMap.put(j._id, j);
	}

	void addRoad(Road r) {
		this._roads.add(r);
		this._roadsMap.put(r._id, r);
	}

	void addVehicle(Vehicle v) {
		this._vehicles.add(v);
		this._vehiclesMap.put(v._id, v);
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
