package simulator.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class CostCalculatorDialog extends JDialog implements TrafficSimObserver{
	private int status;
	DefaultComboBoxModel<String> mOne;
	DefaultComboBoxModel<Integer> mTwo;
	CostTableModel cTB;
	private RoadMap m;
	private Controller c;
	public CostCalculatorDialog(Controller ctrl, Frame f) {
		super(f, true);
		c = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}
	private void initGUI() {
		setTitle("Cost calculator");
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		
		//Vehicle+Passengers+Update area
		JPanel Selectors = new JPanel();
		Selectors.setLayout(new BoxLayout(Selectors, BoxLayout.X_AXIS));
		JLabel v = new JLabel("Vehicle: ");
		Selectors.add(v);
		mOne = new DefaultComboBoxModel<String>();
		JComboBox<String> vComboBox = new JComboBox<String>(mOne);
		Selectors.add(vComboBox);
		JLabel p = new JLabel("Passengers: ");
		Selectors.add(p);
		mTwo = new DefaultComboBoxModel<Integer>();
		JComboBox<Integer> pComboBox = new JComboBox<Integer>(mTwo);
		Selectors.add(pComboBox);
		JButton Upd = new JButton("Update");
		Upd.addActionListener(e->{
			if(pComboBox.getSelectedItem()!=null && vComboBox.getSelectedItem()!=null)
			Update((String)vComboBox.getSelectedItem(), (int)pComboBox.getSelectedItem());
		});
		Selectors.add(Upd);
		main.add(Selectors);
		
		//Table
		cTB = new CostTableModel(c);
		JTable table = new JTable(cTB);
		table.setPreferredSize(new Dimension(500, 200));
		JScrollPane scroller = new JScrollPane(table);
		main.add(scroller);

		//Close
		JButton close = new JButton("Close");
		close.addActionListener(e->{
			this.setVisible(false);
		});
		main.add(close);
		
		setContentPane(main);
		this.setSize(375, 125);
	    setVisible(false);
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
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		
	}
	public void open(RoadMap map) {
		m = map;
		mOne.removeAllElements();
		for(Vehicle v: map.getVehicles()) {
			mOne.addElement(v.getId());
		}
		for(int i = 1; i < 7; i++) {
			mTwo.addElement(i);
		}
		this.setVisible(true);
	}
	private void Update(String v, int numPassengers) {
		Vehicle sel = null;
		for(Vehicle a:m.getVehicles()) {
			if(a.getId().equals(v)) {
				sel = a;
				break;
			}
		}
		if(sel!=null) {
			int cost = sel.getTotalDistance()*(sel.getContClass()+1)/numPassengers;
			int disc = sel.getNumOfTickWithBadWeather()*10;
			int total = cost-disc;
			
			cTB.addCost(new CostData(sel.getId(), cost, disc, total));
		}
	}
}
