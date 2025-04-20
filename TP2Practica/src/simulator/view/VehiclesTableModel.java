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

	private List<VehicleEx> _vehicles;
	private String[] _colNames = { "#", "ID", "Status", "Itinerary", "CO2 class", "Max Speed", "Current Speed", "Total CO2 Emitted", "Distance Traveled" };//identifier, its status, its itinerary, its CO2 class, its maximum speed, its current speed, its total CO2 emitted and the total distance traveled.

	public VehiclesTableModel(Controller ctrl) {
		_vehicles = new ArrayList<>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	public void addVehicle(VehicleEx e) {
		_vehicles.add(e);
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.

		// We need to notify changes, otherwise the table does not refresh.
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
		return _vehicles == null ? 0 : _vehicles.size();
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
			addVehicle(new VehicleEx(v.getId(), i , v.getItinerary().toString() , v.getContClass(), v.getMaxSpeed(), v.getSpeed(), v.getTotalCO2(), v.getTotalDistance()));//
		}
	}
}