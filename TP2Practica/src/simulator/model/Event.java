package simulator.model;

public abstract class Event implements Comparable<Event> {

    private static long _counter = 0;
    protected int _time;
    protected long _time_stamp;

    Event(int time) {
        if (time < 1)
            throw new IllegalArgumentException("Time must be positive (" + time + ")");
        else {
            _time = time;
            _time_stamp = _counter++;
        }
    }

    int getTime() {
        return _time;
    }
    long getTimeStamp() {
        return _time_stamp;
    }

    @Override
    public int compareTo(Event o) {
        // Comparar primero por _time
        int timeComparison = Integer.compare(this._time, o._time);
        
        if (timeComparison != 0) {
            return timeComparison; // Si _time es diferente, devolver la comparación
        }

        // Si _time es igual, comparar por _time_stamp
        return Long.compare(this._time_stamp, o._time_stamp);
    }

    abstract void execute(RoadMap map);
}
