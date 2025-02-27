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
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

    public SetWeatherEventBuilder() {
        super("set_weather", "A new set weather event");
    }

    @Override
    protected void fillInData(JSONObject o) {
        o.put("time", "Time in which the event occurs");
    }

    @Override
    protected Event createInstance(JSONObject data) {
         
    	int time = data.optInt("time", 1);
    	List<Pair<String,Weather>> ws = new ArrayList<Pair<String,Weather>>();
    	JSONArray aux = data.getJSONArray("info");
    	
    	for(int i = 0; i < aux.length(); i++) ws.add(new Pair<String, Weather>(aux.getJSONObject(i).getString("road"),Weather.valueOf(aux.getJSONObject(i).getString("weather"))));
    	// explanation similar to SetContClassEventBuilder's just with weather "parsing"
        return new SetWeatherEvent(time, ws);
    }

}