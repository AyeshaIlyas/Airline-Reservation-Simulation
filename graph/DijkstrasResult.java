// DijkstrasResult.java

package graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class DijkstrasResult<T> {
	
	private int source;
	private Graph<T> graph;
	private int[] distances;
	private int[] predecessors;
	
	// Graph arg cannot be null or empty
	public DijkstrasResult(Graph<T> graph, int source) {
		if (graph == null || graph.size() == 0) 
			throw new IllegalArgumentException("impossible to calculate dijkstra's result for a null or empty graph");
		if (source < 0)
			throw new IllegalArgumentException("vertex must be >= 0");
		
		this.source = source;
		this.graph = graph;
		
		reset();
		
	}
	
	private void reset() {
		distances = new int[graph.size()];
		predecessors = new int[graph.size()];
		Arrays.fill(distances, -1); // -1 sub for infinity/unknown
		Arrays.fill(predecessors, -1); // -1 sub for infinity/unknown
	}
	
	// source must be in graph -> ArrayIndexOutOfBoundException if not
	// if the source has no edges:
	// 		1. the only distance that exists is from the source to itself which is 0
	// 		2. there is only one path possible, which is a one vertex path consisting of the source vertex (path = source)
	// if the source has edges:
	// 		1. the number of distances is guaranteed to be > 1
	// 		2. there are at least two paths leading from the source. (One of them is a path from the source to itself)
	public void compute() {
		
		reset();
		distances[source] = 0;

		Set<Integer> allowedVertices = new HashSet<>();

		for (int processedVertices = 1; processedVertices < graph.size(); processedVertices++) {
			// find vertex closest to start vertex
			int smallest = -1;
			
			for (int i = 0; i < distances.length; i++) {
				if (distances[i] == -1 || allowedVertices.contains(i))
					continue;
				else if (smallest == -1 || distances[i] < distances[smallest])
					smallest = i;
			}

			if (smallest == -1) 
				return;
			
			int next = smallest;
			allowedVertices.add(next);

			int[] neighbors = graph.neighbors(next);
			for (int n: neighbors) {
				if (!allowedVertices.contains(n)) {
					int sum = distances[next] + graph.getEdgeWeight(next, n);
					if (distances[n] == -1 || sum < distances[n]) {
						distances[n] = sum;
						predecessors[n] = next;
					}
				}

			}
		}

	}
	
	public boolean pathExists(int target) {
		return distances[target] != -1;
	}
	
	public boolean pathExists(T target) {
		return pathExists(getVertex(target));
	}

	// - - - - getPath and getLabeledPath overloads - - - - //
	// if there is no path to the target, return null
	// otherwise return a path object with the vertices that compose the path and the total weight of the path
	
	public Path<Integer> getPath(int target) {
		if (!pathExists(target))
			return null;
		
		LinkedList<Integer> path = new LinkedList<Integer>();
		int vertexOnPath = target;
		path.addFirst(vertexOnPath);
		while (vertexOnPath != source) {
			vertexOnPath = predecessors[vertexOnPath];
			path.addFirst(vertexOnPath);
		}
		
		return new Path<Integer>(path, distances[target]);

	}
	
	public Path<Integer> getPath(T target) {
		return getPath(getVertex(target));
	}
	
	
	public Path<T> getLabeledPath(int target) {
		if (!pathExists(target))
			return null;
		
		LinkedList<T> path = new LinkedList<T>();
		int vertexOnPath = target;
		path.addFirst(graph.getLabel(vertexOnPath));
		while (vertexOnPath != source) {
			vertexOnPath = predecessors[vertexOnPath];
			path.addFirst(graph.getLabel(vertexOnPath));
		}
		
		return new Path<T>(path, distances[target]);
	}
	
	
	public Path<T> getLabeledPath(T target) {
		return getLabeledPath(getVertex(target));
	}
	
	
	public int getSourceIndex() {
		return source;
	}

	
	public T getSourceLabel() {
		return graph.getLabel(source);
	}
		
	
	public int[] distances() {
		return distances;
	}
	
	
	@Override
	public String toString() {
		return String.format("Vertex: %d (%s)%nDistances: %s", 
				source, getSourceLabel(), Arrays.toString(distances));
	}
	
	// helper method for mapping labels to vertex indices

	private int getVertex(T label) {
		for (int i = 0; i < graph.getLabels().length; i++) 
			if (graph.getLabels()[i].equals(label)) 
				return i;
		throw new IllegalArgumentException("label does not exist in this graph");
	}

}
