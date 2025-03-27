package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	RoadMap _roadMap;
    Queue<Event> _events;
    private List<TrafficSimObserver> observers = new ArrayList<>();
    int _time;

    public TrafficSimulator() {
        _roadMap = new RoadMap();
        _events = new PriorityQueue<>();
        _time = 0;
    }
    public void reset() {
    	this._roadMap = new RoadMap();
    	this._events = new PriorityQueue<>();
    	_time = 0;
    	for(TrafficSimObserver o : this.observers) {
    		o.onReset(_roadMap, _events, _time);
    	}
    }
    public void addEvent(Event e) {
    	if(e.getTime() < this._time) {
    		throw new IllegalArgumentException("You can't add an event with less time that the current time");
    	}
    	_events.add(e);
    	for(TrafficSimObserver o : this.observers) {
    		o.onEventAdded(_roadMap, _events, e, _time);
    	}
    }
    
    public void advance() {
    	this._time = this._time+1;
    	Iterator<Event> it = _events.iterator();
    	while (it.hasNext()) {
            Event event = it.next();
            
            if (event.getTime() == this._time) {
                it.remove();  // Elimina el evento de manera segura
                event.execute(_roadMap);
            }
            else {
            	break;
            }
        }
    	for(Junction j :this._roadMap.getJunctions()) {
    		j.advance(_time);
    	}
    	for(Road r: this._roadMap.getRoads()) {
    		r.advance(_time);
    	}
    	for(TrafficSimObserver o : this.observers) {
    		o.onAdvance(_roadMap, _events, _time);
    	}
    }
    public JSONObject report() {
    	JSONObject roadMap = new JSONObject();
    	roadMap.put("time", this._time);
	    roadMap.put("state", this._roadMap.report());
	    return roadMap;
    }
	@Override
	public void addObserver(TrafficSimObserver o) {
		this.observers.add(o);
		o.onRegister(_roadMap, _events, _time);
	}
	@Override
	public void removeObserver(TrafficSimObserver o) {
		this.observers.remove(o);
	}
	public String[] getVehiclesID() {
		List<Vehicle> _vehicles = this._roadMap.getVehicles();
		String[] vID = new String[_vehicles.size()];
		for (int i = 0; i < _vehicles.size(); i++) {
	        vID[i] = _vehicles.get(i).getId();
	    }
		return vID;
	}
	public String[] getRoadsID() {
		// TODO Auto-generated method stub
		List<Road> _roads = this._roadMap.getRoads();
		String[] rID = new String[_roads.size()];
		for (int i = 0; i < _roads.size(); i++) {
	        rID[i] = _roads.get(i).getId();
	    }
		return rID;
	}
}
