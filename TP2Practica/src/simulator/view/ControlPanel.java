package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.Collection;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JToolBar implements TrafficSimObserver{
	JFileChooser fc;
	ChangeWeatherDialog cw;
	private JButton loadEventsFileButton;
	private JButton changeWeather;
	private JButton changeContaminationClass;
	private JButton offButton;
	private Controller _ctrl;
	private boolean _stopped = false;
	private int numTicks = 1;
	ControlPanel(Controller c){
		super();
		this._ctrl = c;
		_ctrl.addObserver(this);
		initGUI();
	}
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); //It will be presented as a box layout with all of the buttons
		
		//We add all of the buttons
		//Load button
		loadEventsFileButton = new JButton();
		ImageIcon img = new ImageIcon("resources/icons/open.png");
		loadEventsFileButton.setIcon(img);
		loadEventsFileButton.addActionListener((e)->{
			if(!_stopped) {
				fc = new JFileChooser();
				fc.setCurrentDirectory(new File("resources/examples"));
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File le = fc.getSelectedFile();
					try (InputStream i = new FileInputStream(le)) { 
			            System.out.println("Loading: " + le.getName());
			            _ctrl.reset();  // 
			            _ctrl.loadEvents(i);  
			        } catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else {
					System.out.println("Could not execute the load");
				}
			}
			else {
				loadEventsFileButton.setEnabled(_stopped);
			}
		});
		this.add(loadEventsFileButton);
		//Change contamination button
		changeContaminationClass = new JButton();
		changeContaminationClass.setIcon(new ImageIcon("resources/icons/co2class.png"));
		ChangeCO2ClassDialog ChangeCO2Dialog = new ChangeCO2ClassDialog((JFrame) SwingUtilities.getWindowAncestor(this), this._ctrl.getVehiclesID());
		changeContaminationClass.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeCO2Dialog.UpdateVehicles(_ctrl.getVehiclesID()); 
		        ChangeCO2Dialog.setVisible(true);
				int status = ChangeCO2Dialog.getStatus();
				if(status == 1) {
					JSONObject CO2Info = ChangeCO2Dialog.getInfo();
					if(CO2Info != null) {
						_ctrl.buildEvent(CO2Info);
					}
				}
				else if(status == 2) {

				}
				
			}
			
		});
		this.add(changeContaminationClass);
		//Change weather button
		changeWeather = new JButton();
		ImageIcon cwImg = new ImageIcon("resources/icons/weather.png");
		changeWeather.setIcon(cwImg);
		changeWeather.addActionListener(e->{
			cw = new ChangeWeatherDialog((JFrame) SwingUtilities.getWindowAncestor(this), _ctrl);
		});
		this.add(changeWeather);
		//Run button
		JButton run = new JButton();
		ImageIcon runImg = new ImageIcon("resources/icons/run.png");
		run.setIcon(runImg);
		run.addActionListener(e -> {
			_stopped = true;
			run_sim(this.numTicks);
		});
		this.add(run);
		//Stop button
		JButton stop = new JButton();
		ImageIcon stopImg = new ImageIcon("resources/icons/stop.png");
		stop.setIcon(stopImg);
		stop.addActionListener(e ->{
			this._stopped = false;	
		});
		this.add(stop);
		//ticks button
		JPanel TicksBox = new JPanel();
		TicksBox.setLayout(new BoxLayout(TicksBox, BoxLayout.X_AXIS));
		JLabel ticks = new JLabel("Ticks: ");
		SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, null, 1);
		JSpinner three = new JSpinner(sModel);
		three.addChangeListener(e -> {
			this.numTicks= (int) three.getValue();
		});
		
		three.setPreferredSize(loadEventsFileButton.getPreferredSize());  
		three.setMaximumSize(loadEventsFileButton.getMaximumSize());  
		three.setMinimumSize(loadEventsFileButton.getMinimumSize());  
		TicksBox.add(ticks);
		TicksBox.add(three);
		this.add(TicksBox);
		
		//Off button
		offButton = new JButton();
		ImageIcon offImg = new ImageIcon("resources/icons/exit.png");
		offButton.setIcon(offImg);
		this.add( Box.createRigidArea( new Dimension(500,0) ));
		JDialog exitDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Confirm Exit", true);
		exitDialog.setSize(300, 100);
		JLabel exitDialogText = new JLabel("<html>Are you sure you want to exit?</html>");
		
		JButton exitDialogYes = new JButton("Confirm");
		exitDialogYes.addActionListener(e -> {
			System.exit(0);
		});
		JButton exitDialogNo = new JButton("Cancel");
		exitDialogNo.addActionListener(e -> {
			exitDialog.setVisible(false);
		});
		JPanel exitDialogPanel = new JPanel();
		JPanel exitDialogButtonPanel = new JPanel();
		exitDialogPanel.setLayout(new BoxLayout(exitDialogPanel, BoxLayout.Y_AXIS));
		exitDialogButtonPanel.setLayout(new BoxLayout(exitDialogButtonPanel, BoxLayout.X_AXIS));
		exitDialogButtonPanel.add(exitDialogYes);
		exitDialogButtonPanel.add( Box.createRigidArea( new Dimension(100,0) ));
		exitDialogButtonPanel.add(exitDialogNo);
		exitDialogText.setAlignmentX(Component.RIGHT_ALIGNMENT);
		exitDialogPanel.add(exitDialogText);
		exitDialogPanel.add(exitDialogButtonPanel);
		exitDialog.add(exitDialogPanel);
		
		offButton.addActionListener(e -> {
			exitDialog.setVisible(true);
		});
		this.add(offButton);
		
		this.setVisible(true);
	}

	private void run_sim(int n) {
		if (n > 0 && _stopped) {
			try {
				this.changeContaminationClass.setEnabled(false);
				this.changeWeather.setEnabled(false);
				this.loadEventsFileButton.setEnabled(false);
				this.offButton.setEnabled(false);
				Thread.sleep(100);//changeable
				_ctrl.run(1);
	         	SwingUtilities.invokeLater(() -> run_sim(n - 1));
			} catch (Exception e) {
				_stopped = false;
				this.changeContaminationClass.setEnabled(true);
				this.changeWeather.setEnabled(true);
				this.loadEventsFileButton.setEnabled(true);
				this.offButton.setEnabled(true);
				throw new IllegalArgumentException("Error when running");
			}
		} else {
			this.changeContaminationClass.setEnabled(true);
			this.changeWeather.setEnabled(true);
			this.loadEventsFileButton.setEnabled(true);
			this.offButton.setEnabled(true);
			_stopped = false;
		}
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
}
