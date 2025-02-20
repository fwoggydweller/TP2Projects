package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	List<Vehicle> _vehicles;
	List<Road> _roads;
	List<Junction> _junctions;
	Map<String, Vehicle> _vehiclesMap;
	Map<String, Road> _roadsMap;
	Map<String, Junction> _junctionsMap;
	
	RoadMap() {
		_vehicles = new ArrayList<Vehicle>();
		_roads = new ArrayList<Road>();
		_junctions = new ArrayList<Junction>();
		_vehiclesMap = new HashMap<String, Vehicle>();
		_roadsMap = new HashMap<String, Road>();
		_junctionsMap = new HashMap<String, Junction>();
	}

	
	void addJunction(Junction j) {
		if(this._junctionsMap.containsKey(j.getId())) {
			throw new IllegalArgumentException("We cannot add the junction to the roadmap.");
		}
		this._junctions.add(j);
		this._junctionsMap.put(j._id, j);
	}

	void addRoad(Road r) {
		if(this._roadsMap.containsKey(r.getId())) {
			throw new IllegalArgumentException("We cannot add the road to the roadmap.");
		}
		this._roads.add(r);
		this._roadsMap.put(r._id, r);
	}

	void addVehicle(Vehicle v) {
		int i = 0;
		while(i < v.getItinerary().size()-1) {
			if(!_roads.contains(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)))){
				throw new IllegalArgumentException("We cannot add the vehicle to the roadmap."); //Invalid itinerary
			}
			else {
				i = i + 1;
			}
		}
		if(this._vehiclesMap.containsKey(v.getId()) || !this._junctions.containsAll(v.getItinerary())) {
			throw new IllegalArgumentException("We cannot add the vehicle to the roadmap.");
		}
		else {
			this._vehicles.add(v);
			this._vehiclesMap.put(v._id, v);
		}
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

	public JSONObject report() { //Checked
		JSONObject roadMap = new JSONObject();
	    JSONArray juncArray = new JSONArray();
	    JSONArray roadArray = new JSONArray();
	    for (Road r : this._roads) {
	        roadArray.put(r.report());
	    }
	    roadMap.put("roads", roadArray);
	    JSONArray vehicleArray = new JSONArray();
	    for (Vehicle v : this._vehicles) {
	        vehicleArray.put(v.report());
	    }
	    roadMap.put("vehicles", vehicleArray);
	    for (Junction j : this._junctions) {
	        juncArray.put(j.report());
	    }
	    roadMap.put("junctions", juncArray);

	    return roadMap;
	}
	
} 
