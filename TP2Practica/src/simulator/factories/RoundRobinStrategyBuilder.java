package simulator.factories;


import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewVehicleEvent;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

    public RoundRobinStrategyBuilder() {
        super("round_robin_lss", "A new round robin strategy");
    }

    @Override
    protected void fillInData(JSONObject o) {
        o.put("timeslot", "The cooldown between light switches");
    }

    @Override
    protected LightSwitchingStrategy createInstance(JSONObject data) {
         
    	int timeSlot = data.optInt("timeslot",1);

        return new RoundRobinStrategy(timeSlot);
    }

}