package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChangeCO2ClassDialog extends JDialog{
	private int _status = 0;
	private String[] comboBOne;
	private String[] comboBTwo = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	public ChangeCO2ClassDialog(Frame f, String[] vh) {
		super(f, true);
		comboBOne = vh;
		initGUI();
	}
	public void initGUI() {
		setTitle("Change contamination class");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS) );
		JTextArea text = new JTextArea("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.");
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		mainPanel.add(text);
		JPanel selector = new JPanel();
		selector.setLayout( new BoxLayout( selector, BoxLayout.X_AXIS) );
		JLabel one = new JLabel("Vehicle: ");
		JComboBox textOne = new JComboBox(comboBOne);
		JLabel two = new JLabel("CO2 class: ");
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
			
		});
		action.add(ok);
		mainPanel.add(action);
		setContentPane(mainPanel);
		this.setSize(375, 125);
	    setVisible(true);
	}
}
