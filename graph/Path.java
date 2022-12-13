// Path.java

package graph;

import java.util.List;

public class Path<T> {

	private List<T> path;
	private int weight;
	
	public Path(List<T> path, int weight) {
		this.path = path;
		this.weight = weight;
	}

	public List<T> getPath() {
		return path;
	}

	public void setPath(List<T> path) {
		this.path = path;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return String.format("path: %s%nweight: %d", path, weight);
	}
}
