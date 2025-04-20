package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		Border bordeGrueso = new LineBorder(Color.BLACK, 2);
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);

		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);

		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);

		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
        TitledBorder bordeConTitulo1 = BorderFactory.createTitledBorder(bordeGrueso, "Events");
		eventsView.setBorder(bordeConTitulo1);
		eventsView.setPreferredSize(new Dimension(500, 200));
		JScrollPane scroller1 = new JScrollPane(eventsView);
		tablesPanel.add(scroller1);
		
		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
        TitledBorder bordeConTitulo2 = BorderFactory.createTitledBorder(bordeGrueso, "Vehicles");
		vehiclesView.setBorder(bordeConTitulo2);
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		JScrollPane scroller2 = new JScrollPane(vehiclesView);
		tablesPanel.add(scroller2);
		
		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
        TitledBorder bordeConTitulo3 = BorderFactory.createTitledBorder(bordeGrueso, "Roads");
		roadsView.setBorder(bordeConTitulo3);
		roadsView.setPreferredSize(new Dimension(500, 200));
		JScrollPane scroller3 = new JScrollPane(roadsView);
		tablesPanel.add(scroller3);
		
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
        TitledBorder bordeConTitulo4 = BorderFactory.createTitledBorder(bordeGrueso, "Junctions");
		junctionsView.setBorder(bordeConTitulo4);
		junctionsView.setPreferredSize(new Dimension(500, 200));
		JScrollPane scroller4 = new JScrollPane(junctionsView);
		tablesPanel.add(scroller4);
		
		
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		JPanel MapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map By Road");
		MapByRoadView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(MapByRoadView);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		//this.setResizable(false);
		this.setVisible(true);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		p.add(new JScrollPane(c));
		return p;
	}
}
