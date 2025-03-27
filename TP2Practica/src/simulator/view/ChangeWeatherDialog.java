package simulator.view;

import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;

public class ChangeWeatherDialog extends JDialog {
	private int _status = 0;
	private String[] comboBOne;
	private String[] comboBTwo = {"SUNNY", "CLOUDY", "RAINY", "WINDY", "STORM"};
	private Controller _ctrl;
	public ChangeWeatherDialog(Frame f, Controller _ctrl) {
		super(f, true);
		comboBOne = _ctrl.getRoadsID();
		this._ctrl = _ctrl;
		initGUI();
	}
	public void initGUI() {
		setTitle("Change Weather");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS) );
		JTextArea text = new JTextArea("Schedule an event to change the weather of a road after a given number of simulation ticks from now.");
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		mainPanel.add(text);
		JPanel selector = new JPanel();
		selector.setLayout( new BoxLayout( selector, BoxLayout.X_AXIS) );
		JLabel one = new JLabel("Road: ");
		JComboBox textOne = new JComboBox(comboBOne);
		JLabel two = new JLabel("Weather: ");
		JComboBox textTwo = new JComboBox(comboBTwo);
		JLabel three = new JLabel("Ticks: ");
		JTextField textThree = new JTextField();
		selector.add(one);
		selector.add(textOne);
		selector.add(two);
		selector.add(textTwo);
		selector.add(three);
		selector.add(textThree);
		mainPanel.add(selector);
		JPanel action = new JPanel();
		action.setLayout( new BoxLayout( action, BoxLayout.X_AXIS) );
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) ->{
			setVisible(false);
		});
		action.add(cancel);
		JButton ok = new JButton("OK");
		ok.addActionListener((e) ->{
			//new set weather event through builder passing Jason
			JSONObject JWeather = new JSONObject( );
			JWeather.put("type", "set_weather");
			JSONObject data = new JSONObject( );
			data.put("time", Integer.parseInt(textThree.getText()));
			JSONArray info = new JSONArray();
			JSONObject infObject = new JSONObject();
			infObject.put("road", textOne.getSelectedItem());
			infObject.put("weather", textTwo.getSelectedItem());
			info.put(infObject);
			data.put("info", info);
			JWeather.put("data", data);
			
			
		});
		action.add(ok);
		mainPanel.add(action);
		setContentPane(mainPanel);
		this.setSize(375, 125);
	    setVisible(true);
	}
}
