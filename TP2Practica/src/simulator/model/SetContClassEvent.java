package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event{
	List<Pair<String, Integer>> cs = new ArrayList<Pair<String, Integer>>();
	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		this.cs = cs;
	}
	@Override
	void execute(RoadMap map) {
		for(Pair<String, Integer> e : this.cs) {
			map.getVehicle(e.getFirst()).setContClass(e.getSecond());
		}
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Change CO2 class: [");
		for (int i = 0; i < cs.size(); i++) {
			Pair<String, Integer> p = cs.get(i);
			sb.append("(").append(p.getFirst()).append(",").append(p.getSecond()).append(")");
			if (i < cs.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
