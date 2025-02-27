package simulator.factories;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.MoveAllStrategy;
import simulator.model.MoveFirstStrategy;
import simulator.model.NewVehicleEvent;
import simulator.model.RoundRobinStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

    public MoveFirstStrategyBuilder() {
        super("move_first_dqs", "A new move first strategy");
    }

    @Override
    protected void fillInData(JSONObject o) {
    }

    @Override
    protected DequeuingStrategy createInstance(JSONObject data) {        
        return new MoveFirstStrategy();
    }

}
