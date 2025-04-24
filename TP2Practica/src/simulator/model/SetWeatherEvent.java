package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	List<Pair<String, Weather>> ws = new ArrayList<Pair<String, Weather>>();
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		this.ws = ws;
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String, Weather> e : this.ws) {
			map.getRoad(e.getFirst()).setWeather(e.getSecond());
		}
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Change Weather: [");
		for (int i = 0; i < ws.size(); i++) {
			Pair<String, Weather> p = ws.get(i);
			sb.append("(").append(p.getFirst()).append(",").append(p.getSecond()).append(")");
			if (i < ws.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
