package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class CostTableModel extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private static final long serialVersionUID = 1L;
	private List<CostData> _events;
	private String[] _colNames = { "Vehicle", "Cost", "Discount", "Total" };

	public CostTableModel(Controller ctrl) {
		_events = new ArrayList<>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	public void addCost(CostData e) {
		_events.add(e);
		// We need to notify changes for it to refresh
		fireTableDataChanged();
	}

	public void reset() {
		_events.clear();
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
		return _events == null ? 0 : _events.size();
	}

	@Override
	// así es como se va a cargar la tabla
	// returns the value of a particular cell
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _events.get(rowIndex).getId();
			break;
		case 1:
			s = _events.get(rowIndex).getCost();
			break;
		case 2:
			s = _events.get(rowIndex).getDiscount();
			break;
		case 3:
			s = _events.get(rowIndex).getTotal();
		}
		return s;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
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
}