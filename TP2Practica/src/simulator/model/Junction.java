package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{
	List<Road> roadsIn;
	Map<Junction,Road> roadsOut;
	List<List<Vehicle>> qs;
	int currGreen;
	int lastSwitchingTime;
	int currTime;
	LightSwitchingStrategy lStrat;
	DequeuingStrategy dQStrat;
	int x , y;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		if(lsStrategy != null)this.lStrat = lsStrategy; // meter exceptons
		if(dqStrategy != null)this.dQStrat = dqStrategy;
		
		this.roadsIn = new ArrayList<Road>();
		this.qs = new ArrayList<>();
		this.roadsOut = new HashMap<Junction,Road>();
		if(xCoor >= 0) x = xCoor;
		if(yCoor >= 0) y = yCoor;
		// TODO Auto-generated constructor stub
	}

	@Override
	void advance(int time) {
		
		List<Vehicle> l = dQStrat.dequeue(qs.get(currGreen));
		
		for(Vehicle v : l) {
			v.advance(time);
		}
		
		int aux = lStrat.chooseNextGreen(roadsIn, qs, currGreen, lastSwitchingTime, currTime);
		if(aux != currGreen) {
			currGreen = aux;
			lastSwitchingTime = time;
		}
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	void addIncomingRoad(Road r){
		roadsIn.add(r);
	}
	void addOutGoingRoad(Road r){
		if(!roadsOut.containsKey(r.getDestination()) && r.getSource().equals(this)) roadsOut.put(r.getDestination(), r);
	}
	void enter(Vehicle v){
		qs.get(roadsIn.indexOf(v.getRoad())).add(v);
	}
	Road roadTo(Junction j){
		return roadsOut.get(j); // unique keys
	}
}

