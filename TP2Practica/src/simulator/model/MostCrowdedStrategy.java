package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	int timeSlot;
	public MostCrowdedStrategy (int timeSlot){
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,int currTime) {
		if(roads.isEmpty()) 
			return -1;
		if(currGreen == -1) {
			return searchNextCrowded(qs, 0);
		}
		
		if((currTime - lastSwitchingTime) < timeSlot)
			return currGreen;
		else {
			return searchNextCrowded(qs,(currGreen + 1) % roads.size());
		}
	}
	int searchNextCrowded(List<List<Vehicle>> qs, int start) {
		int aux = qs.get(start).size();
		int pointer = start;
		for(int i = start + 1; i < qs.size(); i++) {
			if(qs.get(i).size() > aux) {
				aux = qs.get(i).size();
				pointer = i;
				
			}
		}
		return pointer;
	}
}

