package simulator.view;


import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{
	Controller _ctrl;
	JLabel currTime;
	private JLabel EventText;
	StatusBar(Controller c){
		super();
		this._ctrl = c;
		this._ctrl.addObserver(this);
		initGUI();
	}
	
	public void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Border b = BorderFactory.createLoweredBevelBorder();
		this.setBorder(b);
		JLabel timeText = new JLabel("Time:");
		currTime = new JLabel(" " + _ctrl.getSimulator().getTime());
		JLabel EventAddedText = new JLabel("Event added");
		EventText = new JLabel("()");
		this.add(timeText);
		this.add(currTime);
		this.add( Box.createRigidArea( new Dimension(10,0) ));
		this.add(EventAddedText);
		this.add(EventText);
		this.setVisible(true);
	}
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		currTime.setText(" " + time);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		EventText.setText("(" + e.toString() + ")");
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
}
