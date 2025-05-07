package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class TicksVehicleTable extends AbstractTableModel implements TrafficSimObserver{
	private Controller _ctrl;
	private static final long serialVersionUID = 1L;

	private HashMap<Integer, ArrayList<VehicleWithSpeedData>> _updVehicles;
	private HashMap<Integer, ArrayList<VehicleWithSpeedData>> _nonUpdVehicles;
	private String[] _colNames = { "Ticks", "Vehicles" };
	private int value = 0;
	
	public TicksVehicleTable(Controller ctrl) {
		_updVehicles = new HashMap<Integer, ArrayList<VehicleWithSpeedData>>();
		_nonUpdVehicles = new HashMap<Integer, ArrayList<VehicleWithSpeedData>>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}
	public void setSpeedLim(int val) {
		value = val;
		reset();
		Update();
	}
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _updVehicles.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) {
			return rowIndex+1;
		}else {
				ArrayList<VehicleWithSpeedData> l =_updVehicles.get(columnIndex);
				ArrayList<String> id = new ArrayList<String>();
				for(VehicleWithSpeedData v:l) {
					id.add(v.getId());
				}
				return id;
			}
		}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		newEntry(map, time);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		reset();
	}
	private void reset() {
		_updVehicles.clear();
		fireTableDataChanged();
	}
	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
	public void newEntry(RoadMap map, int time) {
		ArrayList<VehicleWithSpeedData> vehicle1 = new ArrayList<VehicleWithSpeedData>();
		ArrayList<VehicleWithSpeedData> vehicle2 = new ArrayList<VehicleWithSpeedData>();
		for(Vehicle v : map.getVehicles()) {
			vehicle2.add(new VehicleWithSpeedData(v.getId(),v.getSpeed()));
			if(v.getSpeed() >= this.value) {
				vehicle1.add(new VehicleWithSpeedData(v.getId(),v.getSpeed()));
			}
		}
		this._nonUpdVehicles.put(time, vehicle2);
		this._updVehicles.put(time, vehicle1);
	}
	private void Update() {
		for(int i = 0; i < this._nonUpdVehicles.size(); i++) {
			ArrayList<VehicleWithSpeedData> vehicle1 = new ArrayList<VehicleWithSpeedData>();
			if(_nonUpdVehicles.get(i) != null) {
				for(VehicleWithSpeedData v : _nonUpdVehicles.get(i)) {
					if(v.getSpeed()>this.value) {
						vehicle1.add(new VehicleWithSpeedData(v.getId(),v.getSpeed()));
					}
				}
				this._updVehicles.put(i, vehicle1);
			}
		}
	}
}
