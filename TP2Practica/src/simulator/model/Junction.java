package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	List<Road> _inRoads;

	List<List<Vehicle>> _queues; // the i-th queue corresponds to the i-th road in _inRoads

	Map<Road, List<Vehicle>> _queueByRoad; // for efficient lookup of queues

	Map<Junction, Road> _outRoadByJunction; // indicates the road to take to reach a given junction

	int _greenLightIndex; // the index of the road in _inRoads that has a green light (-1 if all lights are red)

	int _lastSwitchingTime; // the last time the green light was switched from one road to another

	LightSwitchingStrategy _lss;

	DequeuingStrategy _dqs;
	
	int x, y;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		if(lsStrategy != null)this._lss = lsStrategy;
		else throw new IllegalArgumentException();
		if(dqStrategy != null)this._dqs = dqStrategy;
		else throw new IllegalArgumentException();
		
		this._inRoads = new ArrayList<Road>();
		this._queues = new ArrayList<>();
		this._outRoadByJunction= new HashMap<Junction,Road>();
		this._queueByRoad = new HashMap<Road, List<Vehicle>>();
		if(xCoor >= 0) x = xCoor;
		else throw new IllegalArgumentException();
		if(yCoor >= 0) y = yCoor;
		else throw new IllegalArgumentException();
	}

	@Override
	void advance(int time) {
		
		List<Vehicle> l = _dqs.dequeue(_queues.get(_greenLightIndex));
		
		for(Vehicle v : l) {
			v.advance(time);
		}
		
		int aux = _lss.chooseNextGreen(_inRoads, _queues, _greenLightIndex, _lastSwitchingTime, time); // not sure if time is correct here
		if(aux != _greenLightIndex) {
			_greenLightIndex = aux;
			_lastSwitchingTime = time;
		}
		
	}

	@Override
	public JSONObject report() {
		JSONObject junct = new JSONObject();
		junct.put("id", this._id);
		if (this._greenLightIndex < 0) {
	        junct.put("green", "none");
	    }
		else {
			 junct.put("green" , "r"+this._greenLightIndex);
		}
		JSONArray queuesArray = new JSONArray();
	    // Recorremos el mapa _queueByRoad
	    for (Map.Entry<Road, List<Vehicle>> entry : this._queueByRoad.entrySet()) {
	        JSONObject queueObj = new JSONObject();
	        // Clave "road": usamos el id de la carretera
	        queueObj.put("road", entry.getKey().getId());
	        
	        // Clave "vehicles": array de los identificadores de los vehículos en la cola
	        JSONArray vehiclesArray = new JSONArray();
	        for (Vehicle v : entry.getValue()) {
	            vehiclesArray.put(v.getId());
	        }
	        queueObj.put("vehicles", vehiclesArray);
	        
	        queuesArray.put(queueObj);
	    }
	    
	    junct.put("queues", queuesArray);
	    return junct;
	}
	void addIncomingRoad(Road r){
		if(r.getDest() == this) {
			_inRoads.add(r);
			List<Vehicle> q = new ArrayList<>();
			this._queues.add(q);
			this._queueByRoad.put(r, q);
		}
	}
	void addOutGoingRoad(Road r){
		if(r.getSrc() == this) _outRoadByJunction.put(r.getDest(), r);
		else {
			throw new IllegalArgumentException("Can't add road " + r._id + " to the outgoing road lists (Another road goes to the junction or the road is not an outgoing road)");
		}
	}
	void enter(Vehicle v){
		_queues.get(_inRoads.indexOf(v.getRoad())).add(v);
	}
	Road roadTo(Junction j){
		return _outRoadByJunction.get(j); // unique keys
	}
}

