package simulator.launcher;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
    private TrafficSimulator _sim;
    private Factory<Event> _eventsFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        _sim = sim;
        _eventsFactory = eventsFactory;
    }

    public void loadEvents(InputStream in){
    	JSONObject jo = new JSONObject(new JSONTokener(in));
    	JSONArray base = jo.getJSONArray("events"); //We save the list for traversing it later
    	for(Object e : base) {
    		_sim.addEvent(_eventsFactory.createInstance((JSONObject)e));
    	}
    }
    public void run(int n, OutputStream out) {
    	PrintStream p = new PrintStream(out);
    	p.println("{");
    	p.println("  \"states\": [");

    	// loop for the first n-1 states (to print comma after each state)
    	for (int i = 0; i < n - 1; i++) {
    	    _sim.advance();
    	    p.print(_sim.report());
    	    p.println(",");
    	}

    	// last step, only if 'n > 0'
    	if (n > 0) {
    	    _sim.advance();
    	    p.print(_sim.report());
    	}
    	p.println("]");
    	p.println("}");
    }
    public void reset() {
    	_sim.reset();
    }
}