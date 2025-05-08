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
static class Info {
	int time;
	ArrayList<VehicleWithSpeedData> _data;
	public Info(int t, ArrayList<VehicleWithSpeedData> d) {
		time = t;
		_data = d;
	}
	public int getTime() {
		return time;
	}
	public ArrayList<VehicleWithSpeedData> getVehicleList(){
		return _data;
	}
}
	
	
	private ArrayList<Info> _updVehicles;
	private ArrayList<Info> _nonUpdVehicles;
	private String[] _colNames = { "Ticks", "Vehicles" };
	private int value = 0;
	
	public TicksVehicleTable(Controller ctrl) {
		_updVehicles = new ArrayList<Info>();
		_nonUpdVehicles = new ArrayList<Info>();
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
			return _updVehicles.get(rowIndex).getTime();
		}else {
				ArrayList<VehicleWithSpeedData> l =_updVehicles.get(rowIndex).getVehicleList();
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
		fireTableDataChanged();
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
			else {
				vehicle1.add(new VehicleWithSpeedData("",0));
			}
		}
		this._nonUpdVehicles.add(new Info(time, vehicle2));
		this._updVehicles.add(new Info(time, vehicle1));
	}
	private void Update() {
		for(int i = 0; i < this._nonUpdVehicles.size(); i++) {
			ArrayList<VehicleWithSpeedData> vehicle1 = new ArrayList<VehicleWithSpeedData>();
			if(_nonUpdVehicles.get(i) != null) {
				for(VehicleWithSpeedData v : _nonUpdVehicles.get(i).getVehicleList()) {
					if(v.getSpeed()>this.value) {
						vehicle1.add(new VehicleWithSpeedData(v.getId(),v.getSpeed()));
					}
				}
				this._updVehicles.add(new Info(i+1, vehicle1));
			}
		}
	}
}
