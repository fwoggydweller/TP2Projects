package simulator.view;

public class EventEx {

	private Integer _time;// tiempo en el que se produce el evento
	private String _priority;// respecto de otros eventos

	public EventEx(Integer time, String prioridad) {
		_time = time;
		_priority = prioridad;

	}

	public int getTime() {
		return _time;

	}

	public String getDescript() {
		return _priority;

	}

}
