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
    public int compareTo(Event o) { //0for different time, 1 for same time this obj first, 2 for this obj second
    	
    	if(this._time != o.getTime()) return 0;
    	if(this._time_stamp < o.getTimeStamp()) return 1;
    	return 2;
        
    }

    abstract void execute(RoadMap map);
}
