package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> r = new ArrayList<Vehicle>();
		for(int i = 0; i < q.size(); i++)
		{
			r.add(q.get(i));
		}
		return r;
	}

}
