package simulator.factories;


import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewJunctionEvent;
import simulator.model.NewVehicleEvent;
import simulator.model.Weather;

public class NewJunctionEventBuilder extends Builder<Event> {
	Factory<LightSwitchingStrategy> lsS;
	Factory<DequeuingStrategy> dqS;
    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lsS, Factory<DequeuingStrategy>  dqS) {
        super("new_junction", "A new Junction");
        this.lsS = lsS;
        this.dqS = dqS;
    }

    @Override
    protected void fillInData(JSONObject o) {
        o.put("time", "The time at which the event is executed");
        o.put("id", "The junction's id");
        o.put("coor", "The Junction's coordinates");
        o.put("ls_strategy", "The Junction's ls strategy");
        o.put("dq_strategy", "The Junction's dq strategy");
        
    }

    @Override
    protected Event create_instance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int xCoor = data.getJSONArray("coor").getInt(0);
        int yCoor = data.getJSONArray("coor").getInt(1);
        LightSwitchingStrategy lsStrat;
        DequeuingStrategy dqStrat;
        lsStrat = lsS.create_instance(data.getJSONObject("ls_strategy"));
        dqStrat = dqS.create_instance(data.getJSONObject("dq_strategy"));
        
        return new NewJunctionEvent(time, id, lsStrat, dqStrat, xCoor, yCoor);
    }

}
