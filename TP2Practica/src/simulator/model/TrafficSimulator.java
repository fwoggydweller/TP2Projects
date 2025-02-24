package simulator.model;

import java.util.Iterator;
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
    }
    public JSONObject report() {
    	JSONObject roadMap = new JSONObject();
    	roadMap.put("time", this._time);
	    roadMap.put("state", this._roadMap.report());
	    return roadMap;
    }
}
