package simulator.launcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	static int _timeLimit;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _NumOfSteps = null;
	private static Factory<Event> _eventsFactory = null;

	private static void parseArgs(String[] args) throws IOException {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			//parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			//parseInFileOption(line);
			//parseOutFileOption(line);
			//parseNumOfStepsOption(line);
			//parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").hasArg().desc("specify the number of steps to simulate").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("choose if we want the GUI mode or the console mode").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			//System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	private static void parseModeOption(CommandLine line) throws ParseException, IOException {
		if(line.getOptionValue("m").toLowerCase().equals(" gui") || line.getOptionValue("mode").toLowerCase().equals(" gui")) {
			startGUIMode();
		}
		else if (line.getOptionValue("m").toLowerCase().equals(" console") || line.getOptionValue("mode").toLowerCase().equals(" console")) {
			startBatchMode();
		}else {
			startGUIMode(); //By default
		}
	}
	
	private static void startGUIMode() {
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, _eventsFactory);
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				new MainWindow(ctrl);
			}
		}); 
	}
	private static void parseNumOfStepsOption(CommandLine line) throws ParseException {
		_NumOfSteps = line.getOptionValue("t");
		if(_NumOfSteps == null) {
			_timeLimit = 5;
		}
		else {
			_timeLimit = Integer.parseInt(_NumOfSteps);
		}
	}

	private static void initFactories() {
		//LightSwitching Strategies List
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		//Dequeuing Strategies List
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		//Events List
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));
		ebs.add(new NewCityRoadEventBuilder());
		ebs.add(new NewInterCityRoadEventBuilder());
		ebs.add(new NewVehicleEventBuilder());
		ebs.add(new SetContClassEventBuilder());
		ebs.add(new SetWeatherEventBuilder());
		_eventsFactory = new BuilderBasedFactory<>(ebs);
	}

	private static void startBatchMode() throws IOException {
		InputStream I = new FileInputStream(_inFile);
		OutputStream O;
		if (_outFile == null) {
		    O = System.out;
		} else {
		    O = new FileOutputStream(_outFile);
		}
		TrafficSimulator t = new TrafficSimulator();
		Controller c = new Controller(t, _eventsFactory);
		c.loadEvents(I);
		I.close();
		c.run(_timeLimit, O);
	}

	private static void start(String[] args) throws IOException {
		initFactories(); 
		parseArgs(args);
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			args = new String[] {
				 "-m gui"};
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
