package simulator.factories;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.MoveAllStrategy;
import simulator.model.NewVehicleEvent;
import simulator.model.RoundRobinStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

    public MoveAllStrategyBuilder() {
        super("move_all_dqs", "A new move all strategy");
    }

    @Override
    protected void fillInData(JSONObject o) {
    }

    @Override
    protected DequeuingStrategy createInstance(JSONObject data) {        
        return new MoveAllStrategy();
    }

}
