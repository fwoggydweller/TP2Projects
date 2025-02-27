package simulator.factories;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.NewVehicleEvent;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

    public MostCrowdedStrategyBuilder() {
        super("most_crowded_lss", "A new most crowded strategy");
    }

    @Override
    protected void fillInData(JSONObject o) {
        o.put("timeslot", "The cooldown between light switches");
    }

    @Override
    protected LightSwitchingStrategy createInstance(JSONObject data) {
         
    	int timeSlot = data.optInt("timeslot", 1);

        return new MostCrowdedStrategy(timeSlot);
    }

}
