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
    	Object[] a = _events.toArray();
    	int i = 0;
    	int time = this._time;
    	while (a.length > i && time == this._time) { //I need to remove the event from the _events too
    		Event event = (Event) a[i];
    		System.out.println(event.toString());
    		time = event.getTime();
    		if(time == this._time) {
    			this._events.remove(event);
    			event.execute(_roadMap);
                i = i+1;
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
	    System.out.println(roadMap.toString());
	    return roadMap;
    }
}
