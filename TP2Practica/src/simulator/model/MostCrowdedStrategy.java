package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	int timeSlot;
	MostCrowdedStrategy (int timeSlot){
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,int currTime) {
		if(roads.isEmpty()) 
			return -1;
		if(currGreen == -1) {
			int aux = -1;
			int pointer = 0;
			for(int i = 0; i < qs.size(); i++) {
				if(qs.get(i).size() > aux) {
					aux = qs.get(i).size();
					pointer = i;
				}
			}
			return pointer;
		}
		
		if((currTime - lastSwitchingTime) < timeSlot)
			return currGreen;
		else {
			return (currGreen + 1) % roads.size();
		}
	}
}
