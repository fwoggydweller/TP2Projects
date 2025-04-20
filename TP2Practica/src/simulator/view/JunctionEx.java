package simulator.view;

import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class JunctionEx {


	String ID;
	String greenRoad;
	String incomingQueues;
	

	public JunctionEx(String ID, String greenLitRoad, String incomingQueues) {
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
