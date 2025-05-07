package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class VehicleSpeedHistoryDialog extends JDialog implements TrafficSimObserver{
	
	private int status = 0;
	private Controller _ctrl;
	public VehicleSpeedHistoryDialog(Frame f, Controller c) {
		super(f, true);
		_ctrl = c;
		initGUI();
	}
	private void initGUI() {
		setTitle("Vehicle Speed History");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS) );
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
		JLabel text = new JLabel("<html>Select a speed limit and press update to see which vehicle exceeds the speed on each tick.</html>");
		textPanel.add(text);
		mainPanel.add(textPanel);
		JPanel spinner = new JPanel();
		spinner.setLayout(new BoxLayout(spinner, BoxLayout.X_AXIS));
		JLabel spinnerText = new JLabel("Speed Limit:");
		spinner.add(spinnerText);
		JSpinner spin = new JSpinner();
		spin.setMaximumSize(new Dimension(100, 20));
		spinner.add(spin);
		mainPanel.add(spinner);
		TicksVehicleTable t=new TicksVehicleTable(_ctrl);
		JPanel TableView = createViewPanel(new JTable(t), "Vehicles");
		JPanel Buttons = new JPanel();
		Buttons.setLayout(new BoxLayout(Buttons, BoxLayout.X_AXIS));
		JButton Cancel = new JButton("Close");
		Cancel.addActionListener(e->{
			status = 1;
			this.setVisible(false);
		});
		Buttons.add(Cancel);
		JButton Update = new JButton("Update");
		Update.addActionListener(e->{
			status = 2;
			t.setSpeedLim((int)spin.getValue());
		});
		Buttons.add(Update);
		mainPanel.add(Buttons);
		
		mainPanel.add(TableView);
		this.setSize(375, 500);
		this.add(mainPanel);
		this.setVisible(false);
		}
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		p.add(new JScrollPane(c));
		return p;
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
		// TODO Auto-generated method stub
		
	}
	public int getStatus() {
		return this.status;
	}

}
