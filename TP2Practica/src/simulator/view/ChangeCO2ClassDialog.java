package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver{
	private int _status = 0;
	private JComboBox textOne;
	private String[] comboBTwo = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	private JSONObject Event;
	public ChangeCO2ClassDialog(Frame f, String[] vh) {
		super(f, true);
		textOne = new JComboBox(vh);
		initGUI();
	}
	public void initGUI() {

		setTitle("Change contamination class");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS) );
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
		JLabel text = new JLabel("<html>Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.</html>");
		textPanel.add(text);
		mainPanel.add(textPanel);
		JPanel selector = new JPanel();
		selector.setLayout( new BoxLayout( selector, BoxLayout.X_AXIS) );
		JLabel one = new JLabel("Vehicle: ");
		JLabel two = new JLabel("CO2 class: ");
		JComboBox textTwo = new JComboBox(comboBTwo);
		JPanel TicksBox = new JPanel();
		TicksBox.setLayout(new BoxLayout(TicksBox, BoxLayout.X_AXIS));
		JLabel ticks = new JLabel("Ticks: ");
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, null, 1);
		JSpinner three = new JSpinner(model);
		TicksBox.add(ticks);
		TicksBox.add(three);
		selector.add(one);
		selector.add(textOne);
		selector.add(two);
		selector.add(textTwo);
		selector.add(TicksBox);
		mainPanel.add(selector);
		JPanel action = new JPanel();
		action.setLayout( new BoxLayout( action, BoxLayout.X_AXIS) );
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) ->{
			_status = 2;
			setVisible(false);
		});
		action.add(cancel);
		JButton ok = new JButton("OK");
		ok.addActionListener((e) ->{
			if(three.getValue() == null || textOne.getSelectedItem() == null || textTwo.getSelectedItem() == null) {
				
			}
			else {
				_status = 1;
				JSONObject changeWeatherJSON = new JSONObject();
				changeWeatherJSON.put("type", "set_cont_class");
				JSONObject data = new JSONObject();
				JSONObject time = new JSONObject();
				data.put("time", three.getValue());
				JSONArray info = new JSONArray();
				JSONObject vehicleCO2Pair = new JSONObject();
				vehicleCO2Pair.put("vehicle",(String) textOne.getSelectedItem());
				vehicleCO2Pair.put("class", (String)textTwo.getSelectedItem());
				info.put(vehicleCO2Pair);
				data.put("info", info);
				changeWeatherJSON.put("data", data);
				Event = changeWeatherJSON;
				setVisible(false);
			}
		});
		action.add(ok);
		mainPanel.add(action);
		setContentPane(mainPanel);
		this.setSize(375, 125);
	    setVisible(false);
	}
	public void UpdateVehicles(String[] vehiclesId) {
		this.textOne.setModel(new DefaultComboBoxModel<>(vehiclesId));
	}
	public int getStatus() {
		return this._status;
	}
	public JSONObject getInfo() {
		return Event;
	}
	@Override
	public void onAdvance(RoadMap map, Collection<simulator.model.Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEventAdded(RoadMap map, Collection<simulator.model.Event> events, simulator.model.Event e, int time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(RoadMap map, Collection<simulator.model.Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(RoadMap map, Collection<simulator.model.Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
}
