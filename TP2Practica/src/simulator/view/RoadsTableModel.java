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

	private List<RoadEx> _roads;
	private String[] _colNames = { "#", "ID", "Length", "Weather", "Max speed", "Current speed limit", "Total CO2", "CO2 limit"};//its length, its weather, its maximum speed, its current speed limit, its total CO2, and its CO2 limit.

	public RoadsTableModel(Controller ctrl) {
		_roads = new ArrayList<>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	public void addRoad(RoadEx e) {
		_roads.add(e);
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.

		// We need to notify changes, otherwise the table does not refresh.
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

	// si no pongo esto no coge el nombre de las columnas
	//
	// this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// método obligatorio, probad a quitarlo, no compila
	//
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// método obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _roads == null ? 0 : _roads.size();
	}

	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
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
	public void onAdvance(RoadMap map, Collection<Event> events, int time) { // could be more efficient if individual stats where replaced (mabye)
		// TODO Auto-generated method stub
		reset();
		for(Road r : map.getRoads()) {
			addRoad(new RoadEx(r.getId(), r.getLength(), r.getWeather(), r.getMaxSpeed(), r.getSpeedLimit(), r.getTotalCO2(), r.getContLimit()));
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
			addRoad(new RoadEx(r.getId(), r.getLength(), r.getWeather(), r.getMaxSpeed(), r.getSpeedLimit(), r.getTotalCO2(), r.getContLimit()));
		}
	}
}
