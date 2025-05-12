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

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private static final long serialVersionUID = 1L;

	private List<RoadData> _roads;
	private String[] _colNames = { "#", "ID", "Length", "Weather", "Max speed", "Current speed limit", "Total CO2", "CO2 limit"};

	public RoadsTableModel(Controller ctrl) {
		_roads = new ArrayList<>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	public void addRoad(RoadData e) {
		_roads.add(e);
		// We need to notify changes, otherwise, it does not refresh.
		fireTableDataChanged();
	}

	public void reset() {
		_roads.clear();
		;
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

	// the number of rows
	public int getRowCount() {
		return _roads == null ? 0 : _roads.size();
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
			s = _roads.get(rowIndex).getID();
			break;
		case 2:
			s = _roads.get(rowIndex).getLength();
			break;
		case 3:
			s = _roads.get(rowIndex).getWeather();
			break;
		case 4:
			s = _roads.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = _roads.get(rowIndex).getCurrSpeed();
			break;
		case 6:
			s = _roads.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = _roads.get(rowIndex).getCO2Limit();
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) { 
		// TODO Auto-generated method stub
		reset();
		for(Road r : map.getRoads()) {
			addRoad(new RoadData(r.getId(), r.getLength(), r.getWeather(), r.getMaxSpeed(), r.getSpeedLimit(), r.getTotalCO2(), r.getContLimit()));
		}
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
		for(Road r : map.getRoads()) {
			addRoad(new RoadData(r.getId(), r.getLength(), r.getWeather(), r.getMaxSpeed(), r.getSpeedLimit(), r.getTotalCO2(), r.getContLimit()));
		}
	}
}
