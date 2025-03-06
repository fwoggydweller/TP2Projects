package simulator.factories;


import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {

    public NewInterCityRoadEventBuilder() {
        super("new_inter_city_road", "A new Road");
    }

    @Override
    protected void fillInData(JSONObject o) {
        o.put("time", "The time at which the event is executed");
        o.put("maxspeed", "The roads's max speed limit");
        o.put("id", "The road's id");
        o.put("src", "The road's source junction");
        o.put("dest", "The road's destination junction");
        o.put("co2limit", "The road's contamination limit");
        o.put("length", "The road's length");
        o.put("weather", "The road's weather");
    }

    @Override
    protected Event create_instance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        String srcJunc = data.getString("src");
        String destJunc = data.getString("dest");
        int maxSpeed = data.getInt("maxspeed");
        int contLim = data.getInt("co2limit");
        int length = data.getInt("length");
        Weather weather = Weather.valueOf(data.getString("weather").toUpperCase());

        return new NewInterCityRoadEvent(time, id, srcJunc, destJunc, maxSpeed, contLim, length, weather);
    }

}
