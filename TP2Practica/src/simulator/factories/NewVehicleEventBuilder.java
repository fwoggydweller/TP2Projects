package simulator.factories;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

    public NewVehicleEventBuilder() {
        super("new_vehicle", "A new vehicle");
    }

    @Override
    protected void fillInData(JSONObject o) {
        o.put("time", "The time at which the event is executed");
        o.put("maxspeed", "The vehicle's max speed");
        o.put("id", "The veicle's id");
        o.put("class", "The vehicle's contamination class");
        o.put("itinerary", "The id of the junctions the vehicle will traverse");
    }

    @Override
    protected Event createInstance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxspeed");
        int contClass = data.getInt("class");
        List<String> itinerary = new ArrayList<String>();
        JSONArray aux = data.getJSONArray("itinerary");
        
        for(int i = 0; i < aux.length(); i++) itinerary.add(aux.getString(i)); 

        return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
    }

}
