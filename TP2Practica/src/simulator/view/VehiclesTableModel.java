package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private static final long serialVersionUID = 1L;

	private List<VehicleData> _vehicles;
	private String[] _colNames = { "#", "ID", "Status", "Itinerary", "CO2 class", "Max Speed", "Current Speed", "Total CO2 Emitted", "Distance Traveled" };//identifier, its status, its itinerary, its CO2 class, its maximum speed, its current speed, its total CO2 emitted and the total distance traveled.

	public VehiclesTableModel(Controller ctrl) {
		_vehicles = new ArrayList<>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	public void addVehicle(VehicleData e) {
		_vehicles.add(e);

		// We need to notify changes
		fireTableDataChanged();
	}

	public void reset() {
		_vehicles.clear();
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}


	// this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override

	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override

	// the number of row, like those in the events list
	public int getRowCount() {
		return _vehicles == null ? 0 : _vehicles.size();
	}

	@Override

	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = rowIndex;
			break;
		case 1:
			s = _vehicles.get(rowIndex).getID();
			break;
		case 2:
			s = _vehicles.get(rowIndex).getStatus();
			break;
		case 3:
			s = _vehicles.get(rowIndex).getItinerary();
			break;
		case 4:
			s = _vehicles.get(rowIndex).getCO2Class();
			break;
		case 5:
			s = _vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 6:
			s = _vehicles.get(rowIndex).getCurrSpeed();
			break;
		case 7:
			s = _vehicles.get(rowIndex).getTotalCO2();
			break;
		case 8:
			s = _vehicles.get(rowIndex).getDistance();
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) { // could be more efficient if individual stats where replaced (mabye)
		// TODO Auto-generated method stub
		reset();
		getInfo(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		reset();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		getInfo(map);
	}
	public void getInfo(RoadMap map) {
		for(Vehicle v : map.getVehicles()) {
			String i;
			VehicleStatus s = v.getStatus();
			if (s == VehicleStatus.TRAVELING)
				i = v.getRoad().getId() + ":" + v.getLocation();
			else if (s == VehicleStatus.WAITING)
				i = "Waiting:" + v.getItinerary().get(v.getJunctionIndex());
			else i = s.toString();
			addVehicle(new VehicleData(v.getId(), i , v.getItinerary().toString() , v.getContClass(), v.getMaxSpeed(), v.getSpeed(), v.getTotalCO2(), v.getTotalDistance()));//
		}
	}
}