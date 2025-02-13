package simulator.model;


import java.util.ArrayList;
import java.util.List;

public class NewJunctionEvent extends Event {
	String id;
	LightSwitchingStrategy lsStrategy;
	DequeuingStrategy dqStrategy;
	int xCoor,yCoor;
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
	    super(time);
	    this.id = id;
	    this.lsStrategy = lsStrategy;
	    this.dqStrategy = dqStrategy;
	    this.xCoor = xCoor;
	    this.yCoor = yCoor;
	}   

	@Override
	void execute(RoadMap map) {
		Junction j = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
		map.addJunction(j);
	}
}
