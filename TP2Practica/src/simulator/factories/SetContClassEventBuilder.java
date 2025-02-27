package simulator.factories;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.NewVehicleEvent;
import simulator.model.RoundRobinStrategy;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

    public SetContClassEventBuilder() {
        super("set_cont_class", "A new set container class event");
    }

    @Override
    protected void fillInData(JSONObject o) {
        o.put("time", "Time in which the event occurs");
    }

    @Override
    protected Event createInstance(JSONObject data) {
         
    	int time = data.optInt("time", 1);
    	//Pair<String, Integer> cs = new Pair<String,Integer>((String)data.getJSONArray("cs").get(0), (Integer)data.getJSONArray("cs").get(1));
    	List<Pair<String,Integer>> cs = new ArrayList<Pair<String,Integer>>();
    	JSONArray aux = data.getJSONArray("info");
    	
    	for(int i = 0; i < aux.length(); i++) cs.add(new Pair<String, Integer>(aux.getJSONObject(i).getString("vehicle"),aux.getJSONObject(i).getInt("class")));
    	//line above adds a new pair to cs per element in aux which is made up of Json objects that contain the string and int we are looking for
        return new SetContClassEvent(time, cs);
    }

}