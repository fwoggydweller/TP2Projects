package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private static final long serialVersionUID = 1L;

	private List<JunctionData> _junctions;
	private String[] _colNames = { "#", "ID", "Green lit road", "Incoming queues" };

	public JunctionsTableModel(Controller ctrl) {
		_junctions = new ArrayList<>();
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	public void addJunction(JunctionData e) {
		_junctions.add(e);

		// We need to notify changes, otherwise, it does not refresh.
		fireTableDataChanged();
	}

	public void reset() {
		_junctions.clear();
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

	// the number of row, like those in the list
	public int getRowCount() {
		return _junctions == null ? 0 : _junctions.size();
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
			s = _junctions.get(rowIndex).getID();
			break;
		case 2:
			s = _junctions.get(rowIndex).getGreenRoad();
			break;
		case 3:
			s = _junctions.get(rowIndex).getIncoming();
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
	
	void getInfo(RoadMap map) {
		String green = "";
		String que = "";
		List<Vehicle> queue;
		for(Junction j : map.getJunctions()) {
			green = j.getGreenLightIndex() == -1? "NONE" : j.getInRoads().get(j.getGreenLightIndex()).getId();
			que = "";
			for(Road r: j.getInRoads()) {
				que = que.concat(r.getId() + ":[");
				queue = j.getQueueByRoad(r);
				for(int i = 0; i <  queue.size(); i++) {
					que = que.concat(queue.get(i).getId());
					if(i != queue.size() - 1) que = que.concat(",");
				}
				que = que.concat("] ");
			}
			addJunction(new JunctionData(j.getId(), green, que));//
		}
	}
}
