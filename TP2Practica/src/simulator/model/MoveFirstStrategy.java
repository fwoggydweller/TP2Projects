package simulator.model;

import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> r = List.of(q.get(0));
		return r;
	}

}
