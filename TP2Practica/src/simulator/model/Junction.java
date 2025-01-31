package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class Junction extends SimulatedObject{
	List<Road> roads;
	List<List<Road>> qs;
	int currGreen;
	int lastSwitchingTime;
	int currTime;
	Junction(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	void addIncomingRoad(Road r){
		
	}
	void addOutGoingRoad(Road r){
		
	}
	void enter(Vehicle v){
		
	}
	Road roadTo(Junction j){
		return new Road;
	}
}

