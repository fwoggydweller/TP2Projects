package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class ContaminationStatsTable extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private static final long serialVersionUID = 1L;

	private LinkedList<ContStatData> _stats;
	private String[] _colNames = { "Time", "Vehicle", "Road"};

	public ContaminationStatsTable(Controller ctrl) {
		_stats = new LinkedList<>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	public void addContStat(ContStatData e) {
		_stats.addFirst(e);
		// observar que si no refresco la tabla no se carga
		// La tabla es la represantación visual de una estructura de datos,
		// en este caso de un ArrayList, hay que notificar los cambios.

		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();
	}

	public void reset() {
		_stats.clear();
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
		return _stats == null ? 0 : _stats.size();
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
			s = _stats.get(rowIndex).getTime();
			break;
		case 1:
			s = _stats.get(rowIndex).getVehicle();
			break;
		case 2:
			s = _stats.get(rowIndex).getRoad();
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) { // could be more efficient if individual stats where replaced (mabye)
		// TODO Auto-generated method stub
		getInfo(map, time);
		if(_stats.size() > 10)
			_stats.removeLast();
		
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
	}
	public void getInfo(RoadMap map, int time) {
		String vID = "", rID = "";
		int kohCont = 0;
		for(Vehicle v : map.getVehicles()) {
			if(v.getTotalCO2() > kohCont) {
				kohCont = v.getTotalCO2();
				vID = v.getId();
			}
		}
		vID = vID + "(" + kohCont + ")";
		
		kohCont = 0;
		for(Road r : map.getRoads()) {
			if(r.getTotalCO2() > kohCont) {
				kohCont = r.getTotalCO2();
				rID = r.getId();
			}
		}
		rID = rID + "(" + kohCont + ")";
		
		addContStat(new ContStatData(time, vID, rID));//
	}
}