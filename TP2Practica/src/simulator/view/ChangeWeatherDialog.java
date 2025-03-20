package simulator.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ChangeWeatherDialog extends JDialog {
	ChangeWeatherDialog(){
	    this.setSize(600, 200);
	    this.setLayout(null);
	    this.setTitle("Change Road Weather");
	    
	    JTextArea title = new JTextArea ("Schedule an event to change the weather of a road after a given number of simulation ticks from now:");
	    JLabel road = new JLabel("Road:");
	    JLabel weather = new JLabel("Weather:");
	    JLabel ticks = new JLabel("Ticks:");
	    title.setBounds(0, 0, 530, 20);
	    title.setLineWrap(true);
	    road.setBounds(0, 50, 200, 30);
	    weather.setBounds(100, 50, 200, 30);
	    ticks.setBounds(200, 50, 200, 30);
	    
	    this.add(title);
	    this.add(road);
	    this.add(weather);
	    this.add(ticks);
	
	    JButton closeButton = new JButton("Close");
	    closeButton.setBounds(150, 100, 80, 30);
	    closeButton.addActionListener(e1 -> this.dispose());
	    this.add(closeButton);
	    
	
	    this.setVisible(true);
	}
}
