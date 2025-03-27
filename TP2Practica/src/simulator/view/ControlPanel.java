package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import java.io.InputStream;

import simulator.control.Controller;

public class ControlPanel extends JPanel{
	JFileChooser fc;
	ChangeWeatherDialog cw;
	private Controller _ctrl;
	ControlPanel(Controller c){
		super();
		this._ctrl = c;
		initGUI();
	}
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); //It will be presented as a box layout with all of the buttons
		
		//We add all of the buttons
		//Load button
		JButton loadEventsFileButton = new JButton();
		ImageIcon img = new ImageIcon("resources/icons/st_small_507x507-pad_600x600_f8f8f8.u1-removebg-preview (1).png");
		loadEventsFileButton.setIcon(img);
		loadEventsFileButton.addActionListener((e)->{
			fc = new JFileChooser();
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
		this.add(loadEventsFileButton);
		//Change contamination button
		JButton changeContaminationClass = new JButton();
		changeContaminationClass.setIcon(new ImageIcon("resources/icons/st_medium_507x507-pad_600x600_f8f8f8-removebg-preview (1).png"));
		ChangeCO2ClassDialog ChangeCO2Dialog = new ChangeCO2ClassDialog((JFrame) SwingUtilities.getWindowAncestor(this), _ctrl.getVehiclesID());
		changeContaminationClass.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
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
		JButton changeWeather = new JButton("Change Weather");
		ImageIcon cwImg = new ImageIcon("resources/icons/cat-wet (1).jpg");
		changeWeather.setIcon(cwImg);
		changeWeather.addActionListener(e->{
			cw = new ChangeWeatherDialog((JFrame) SwingUtilities.getWindowAncestor(this), _ctrl);
		});
		this.add(changeWeather);
		//Run button
		JButton run = new JButton("Run");
		run.addActionListener(null);
		this.add(run);
		//Stop button
		JButton stop = new JButton("Stop");
		stop.addActionListener(null);
		this.add(stop);
		//ticks button
		JButton ticks = new JButton("Ticks");
		ticks.addActionListener(null);
		this.add(loadEventsFileButton);
		
		this.setVisible(true);
	}
}
