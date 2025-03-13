package simulator.view;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.InputStream;

import simulator.control.Controller;

public class ControlPanel extends JFrame{
	JFileChooser fc;
	private Controller _ctrl;
	ControlPanel(Controller c){
		super("Traffic sim");
		this._ctrl = c;
		initGUI();
	}
	private void initGUI() {
		JPanel controlPanel = new JPanel(); //Main window
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS)); //It will be presented as a box layout with all of the buttons
		
		//We add all of the buttons
		//Load button
		JButton loadEventsFileButton = new JButton("Load Events File");
		loadEventsFileButton.addActionListener((e)->{
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
		});
		
		//Change contamination button
		JButton changeContaminationClass = new JButton("Change Contamination Class");
		changeContaminationClass.addActionListener(null);
		
		//Change weather button
		JButton changeWeather = new JButton("Change Weather");
		changeWeather.addActionListener(null);
		
		//Run button
		JButton run = new JButton("Run");
		run.addActionListener(null);
		
		//Stop button
		JButton stop = new JButton("Stop");
		stop.addActionListener(null);
		
		//ticks button
		JButton ticks = new JButton("Ticks");
		ticks.addActionListener(null);
	}
}
