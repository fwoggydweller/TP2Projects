package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event{
	List<Pair<String, Integer>> cs = new ArrayList<Pair<String, Integer>>();
	SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		this.cs = cs;
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String, Integer> e : this.cs) {
			map.getVehicle(e.getFirst()).setContClass(e.getSecond());
		}
	}
}
