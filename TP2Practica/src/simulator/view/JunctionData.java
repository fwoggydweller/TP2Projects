package simulator.view;

import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class JunctionData {


	String ID;
	String greenRoad;
	String incomingQueues;
	

	public JunctionData(String ID, String greenLitRoad, String incomingQueues) {
		this.ID = ID;
		greenRoad = greenLitRoad;
		this.incomingQueues = incomingQueues;
	}
	public String getID() {
		return ID;
	}
	public String getGreenRoad() {
		return greenRoad;
	}
	public String getIncoming() {
		return incomingQueues;
	}
}
