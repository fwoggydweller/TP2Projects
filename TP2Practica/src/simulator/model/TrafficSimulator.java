package simulator.model;

import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

public class TrafficSimulator {
	RoadMap _roadMap;
    Queue<Event> _events;
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
    }
    public void addEvent(Event e) {
    	if(e.getTime() < this._time) {
    		throw new IllegalArgumentException("You can't add an event with less time that the current time");
    	}
    	_events.add(e);
    }
    
    public void advance() {
    	this._time = this._time+1;
    	while (!_events.isEmpty() && _events.peek().getTime() == this._time) {
            Event event = _events.poll(); // Extrae y elimina el evento
            event.execute(_roadMap);
    	}
    	for(Junction j :this._roadMap._junctions) {
    		j.advance(_time);
    	}
    	for(Road r: this._roadMap._roads) {
    		r.advance(_time);
    	}
    }
    public JSONObject report() {
    	JSONObject roadMap = new JSONObject();
    	roadMap.put("time", this._time);
	    roadMap.put("state", this._roadMap.report());
	    return roadMap;
    }
}
