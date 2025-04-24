package simulator.view;

public class EventData {

	private Integer _time;// tiempo en el que se produce el evento
	private String _description;// respecto de otros eventos

	public EventData(Integer time, String description) {
		_time = time;
		_description = description;

	}

	public int getTime() {
		return _time;

	}

	public String getDescript() {
		return _description;

	}

}
