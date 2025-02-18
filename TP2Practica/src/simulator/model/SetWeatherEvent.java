package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	List<Pair<String, Weather>> ws = new ArrayList<Pair<String, Weather>>();
	SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		this.ws = ws;
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String, Weather> e : this.ws) {
			map.getRoad(e.getFirst()).setWeather(e.getSecond());
		}
	}
}
