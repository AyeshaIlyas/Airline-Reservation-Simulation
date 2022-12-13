// Graph.java

package graph;

import java.util.HashSet;
import java.util.Set;

/******************************************************************************
 * A <CODE>Graph</CODE> is a weighted, labeled graph with a fixed number of vertices. 
 * Loops are allowed, but multiple edges are not.
 *
 * @author Ayesha Ilyas
 *
 * @since Dec 8, 2022
 ******************************************************************************/
public class Graph<T> implements Cloneable
{
	// Invariant of the Graph class:
	//   1. The vertex numbers range from 0 to labels.length-1.
	//   2. For each vertex number i, labels[i] contains the label for vertex i. The label must be unique to that vertex in the entire graph
	//   3. For any two vertices i and j, edges[i][j] is true if there is a
	//      vertex from i to j; otherwise edges[i][j] is false. 
	// 	 4. For any two vertices i and j, if an edge exists between them, then the weight of that
	//      edge is stored at weights[i][j]. If there is not edge between a vertices i and j, then it doesnt matter what is stored at weights[i][j]
	//   5. Weights must be >= 0
	private boolean[ ][ ] edges;
	private int[][] weights;
	private Object[ ] labels;


	// CONSTRUCTORS // 

	/**
	 * Initialize a <CODE>Graph</CODE> with <CODE>n</CODE> vertices,
	 * no edges, and null labels.
	 * @param <CODE>n</CODE>
	 *   the number of vertices for this <CODE>Graph<CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   <CODE>n</CODE> is nonnegative.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Graph</CODE> has <CODE>n</CODE> vertices, numbered 
	 *   <CODE>0<CODE> to <CODE>n-1</CODE>. It has no edges and all
	 *   vertex labels are null.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for the specified number of nodes.
	 * @exception NegativeArraySizeException
	 *   Indicates that <CODE>n</CODE> is negative. 
	 **/   
	public Graph(int n) {
		edges = new boolean[n][n];  // All values initially false
		labels = new Object[n];     // All values initially null
		weights = new int[n][n];
	}


	/**
	 * Initialize a <CODE>Graph</CODE> with <CODE>n</CODE> vertices,
	 * no edges, and specified labels.
	 * @param <CODE>n</CODE>
	 *   the number of vertices for this <CODE>Graph<CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   <CODE>n</CODE> is nonnegative.
	 * @param labels - an array of size n of unique labels for each vertex. The first label corresponds to vertex 0, the second to vertex 2, etc..
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Graph</CODE> has <CODE>n</CODE> vertices, numbered 
	 *   <CODE>0<CODE> to <CODE>n-1</CODE>. It has no edges and all
	 *   vertex labels are set.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for the specified number of nodes.
	 * @exception NegativeArraySizeException
	 *   Indicates that <CODE>n</CODE> is negative. 
	 **/   
	public Graph(int n, T[] labels) {
		// their must be one label per vertex
		if (labels.length != n) 
			throw new IllegalArgumentException("Incorrect number of labels");

		// labels cannot be null and they must all be unique
		Set<T> set = new HashSet<>();
		for (T label: labels) {
			if (label == null)
				throw new IllegalArgumentException("Null labels not allowed");
			set.add(label);
		}
		if (set.size() != labels.length)
			throw new IllegalArgumentException("labels must be unique");


		edges = new boolean[n][n];  // All values initially false
		this.labels = labels;     // All values initially null
		weights = new int[n][n];

	}


	// EDGES //

	public boolean isEdge(int source, int target) {
		return edges[source][target];
	}


	public boolean isEdge(T source, T target) {
		return edges[getVertex(source)][getVertex(target)];
	}


	public void removeEdge(int source, int target) {
		edges[source][target] = false;
	}


	public void removeEdge(T source, T target) {
		edges[getVertex(source)][getVertex(target)] = false;
	}


	public Graph<T> addEdge(int source, int target, int weight) {
		if (weight < 0) 
			throw new IllegalArgumentException("weights must be >= 0");

		edges[source][target] = true;
		weights[source][target] = weight;
		return this;
	}


	public Graph<T> addEdge(T source, T target, int weight) {
		return addEdge(getVertex(source), getVertex(target), weight);
	}


	// WEIGHTS // 
	public int getEdgeWeight(int source, int target) {
		if (edges[source][target]) 
			return  weights[source][target];
		return -1;
	}


	public int getEdgeWeight(T source, T target) {
		return getEdgeWeight(getVertex(source), getVertex(source));
	}


	// LABELS //
	@SuppressWarnings("unchecked")
	public T getLabel(int vertex) {
		return (T) labels[vertex];
	}
	
	public int getVertex(T label) {
		int vertex = getVertexIndex(label);
		if (vertex == -1)
			throw new IllegalArgumentException("label does not exist in this graph");
		return vertex;
	}

	public void setLabel(int vertex, T newLabel) {
		if (newLabel == null) 
			throw new IllegalArgumentException("label cannot be null");

		// labels must be unique so it label already exists do not add it
		int index = getVertexIndex(newLabel);
		if (index != -1) 
			throw new IllegalArgumentException("label must be unique. '" + newLabel + "' already exists in this graph");
		labels[vertex] = newLabel;
	}


	@SuppressWarnings("unchecked")
	public T[] getLabels() {
		return (T[]) labels;
	}


	// GRAPH ALGORITMS // 
	// TRAVERSAL //

	/**
	 * Static method to print the labels of a graph with a depth-first search.
	 * @param <CODE>g</CODE>
	 *   a nonnull <CODE>Graph</CODE>
	 * @param <CODE>start</CODE>
	 *   a vertex number from the <CODE>Graph g</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   <CODE>start</CODE> is nonnegative and less than <CODE>g.size()</CODE>.
	 * <dt><b>Postcondition:</b><dd>
	 *   A depth-first search of <CODE>g</CODE> has been conducted, starting at
	 *   the specified start vertex. Each vertex visited has its label printed 
	 *   using <CODE>System.out.println</CODE>. Note that vertices that are not 
	 *   connected to the start will not be visited.
	 * @throws NullPointerException
	 *   Indicates that <CODE>g</CODE> is null.
	 * @throws ArrayIndexOutOfBoundsException
	 *   Indicates that the vertex was not a valid vertex number.
	 * @throws OutOfMemoryError
	 *   Indicates that there is insufficient memory for an array of boolean values 
	 *   used by this method.
	 **/
	public static <T> void depthFirstPrint(Graph<T> g, int start) {
		boolean[ ] marked = new boolean [g.size( )];

		depthFirstRecurse(g, start, marked);
	}


	/**
	 * Recursive method to carry out the work of <CODE>depthFirstPrint</CODE>.
	 * @param <CODE>g</CODE>
	 *   a nonnull <CODE>Graph</CODE>
	 * @param <CODE>v</CODE>
	 *   a vertex number from the <CODE>Graph g</CODE>
	 * @param <CODE>marked</CODE>
	 *   an array to indicate which vertices of <CODE>g</CODE> have already
	 *   been visited
	 * <dt><b>Precondition:</b><dd>
	 *   <CODE>v</CODE> is nonnegative and less than <CODE>g.size()</CODE>.
	 *   <CODE>marked.length</CODE> is equal to <CODE>g.size()</CODE>;
	 *   for each vertex <CODE>x</CODE> of </CODE>g</CODE>, <CODE>marked[x]</CODE>
	 *   is <CODE>true</CODE> if <CODE>x</CODE> has already been visited by this
	 *   search; otherwise <CODE>marked[x]</CODE> is <CODE>false</CODE>.
	 *   The vertex </CODE>v</CODE> is an unmarked vertex that the search has
	 *   just arrived at.
	 * <dt><b>Postcondition:</b><dd>
	 *   The depth-first search of <CODE>g</CODE> has been continued through 
	 *   vertex </CODE>v</CODE> and beyond to all vertices that can be reached
	 *   from <CODE>v</CODE> via a path of unmarked vertices. Each vertex visited
	 *   has its label printed using <CODE>System.out.println.</CODE> 
	 * @throws NullPointerException
	 *   Indicates that <CODE>g</CODE> is null.
	 * @throws ArrayIndexOutOfBoundsException
	 *   Indicates that the vertex was not a valid vertex number, or 
	 *   <CODE>marked</CODE> was the wrong size. 
	 **/
	public static <T> void depthFirstRecurse(Graph<T> g, int v, boolean[ ] marked) {
		int[ ] connections = g.neighbors(v);
		int i;
		int nextNeighbor;

		marked[v] = true;
		System.out.println(g.getLabel(v));

		// Traverse all the neighbors, looking for unmarked vertices:
		for (i = 0; i < connections.length; i++) {
			nextNeighbor = connections[i];
			if (!marked[nextNeighbor])
				depthFirstRecurse(g, nextNeighbor, marked);
		} 
	}


	// ADJACENCIES //
	/**
	 * Accessor method to obtain a list of neighbors of a specified vertex of
	 * this <CODE>Graph</CODE>
	 * @param <CODE>vertex</CODE>
	 *   a vertex number
	 * @param <CODE>target</CODE>
	 *   a vertex number 
	 * <dt><b>Precondition:</b><dd>
	 *   Both <CODE>source</CODE> and <CODE>target</CODE> are nonnegative and
	 *   less than <CODE>size()</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   <CODE>vertex</CODE> is nonnegative and
	 *   less than <CODE>size()</CODE>.
	 * @return
	 *   The return value is an array that contains all the vertex numbers of
	 *   vertices that are targets for edges with a source at the specified
	 *   <CODE>vertex</CODE>.
	 * @exception ArrayIndexOutOfBoundsException
	 *   Indicates that the <CODE>source</CODE> or <CODE>target</CODE> was not a
	 *   valid vertex number.
	 **/
	public int[ ] neighbors(int vertex) {
		int i;
		int count;

		// First count how many edges have the vertex as their source
		count = 0;
		for (i = 0; i < labels.length; i++) 
			if (edges[vertex][i])
				count++;

		// Allocate the array for the answer
		int[] answer = new int[count];

		// Fill the array for the answer
		count = 0;
		for (i = 0; i < labels.length; i++)
			if (edges[vertex][i])
				answer[count++] = i;

		return answer;
	}


	// DISTANCE & PATHS // 
	
	// dijkstrasAlgorithm overloads //
	// source must be in this graph - ArrayIndexOutOfBoundsException otherwise
	// returns an object with information about:
	// 		1. the shortest distance from the source to every connected vertex 
	//		2. methods for checking if a path exists from the source to a target vertex
	//		3. methods for retrieving the Path from the source to a target. If there is no path, null is returned instead of a Path.
	// 		   (Path contains the vertices making up the path and the total weight of the path)
	
	public DijkstrasResult<T> dijkstrasAlgorithm(int source) {
		DijkstrasResult<T> result = new DijkstrasResult<>(this, source);
		result.compute();
		return result;
	}


	public DijkstrasResult<T> dijkstrasAlgorithm(T source) {
		return dijkstrasAlgorithm(getVertex(source));
	}

	
	// findShortestLabeledPath and findShortestPath overloads
	// the source and target must be in the graph - ArrayIndexOutOfBoundException otherwise
	// If there is a path, a Path object is returned, otherwise null is returned 
	// If the source and target are the same, then a Path is returned 
	// 		and the path consists of one vertex, the source/target, and has a weight of 0
	
	public Path<T> findShortestLabeledPath(int source, int target) {
		// get distances
		DijkstrasResult<T> result = dijkstrasAlgorithm(source);
		return result.getLabeledPath(target);
	}


	public Path<T> findShortestLabeledPath(T source, T target) {
		return findShortestLabeledPath(getVertex(source), getVertex(target));
	}

	public Path<Integer> findShortestPath(int source, int target) {
		// get distances
		DijkstrasResult<T> result = dijkstrasAlgorithm(source);
		return result.getPath(target);
	}


	public Path<Integer> findShortestPath(T source, T target) {
		return findShortestPath(getVertex(source), getVertex(target));
	}


	// UTILITY //
	public int size() {
		return labels.length;
	}


	/**
	 * Generate a copy of this <CODE>Graph</CODE>.
	 * @return
	 *   The return value is a copy of this <CODE>Graph</CODE>. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa. Note that the return
	 *   value must be type cast to a <CODE>Graph</CODE> before it can be used.
	 * @throws OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	@Override
	@SuppressWarnings("unchecked")
	public Object clone( ) { 
		// Clone a Graph object.
		Graph<T> answer;

		try {
			answer = (Graph<T>) super.clone();
		} catch (CloneNotSupportedException e) {  
			throw new RuntimeException("Hmmm this should not be happening");
		}

		answer.edges = (boolean [][]) edges.clone();
		answer.labels = (Object []) labels.clone();
		answer.weights = (int [][]) weights.clone();

		return answer;
	}

	@Override
	public String toString() {
		String output = String.format("Graph (vertices: %d)%n", size());
		for (int i = 0; i < labels.length; i++) 
			output += String.format("v%d: %s%s", i, labels[i], i == labels.length - 1 ? "" : "\n");
		return output;
	}


	// helper method for mapping labels to vertex indices
	private int getVertexIndex(T label) {
		for (int i = 0; i < labels.length; i++) 
			if (labels[i].equals(label)) 
				return i;
		return -1;
	}

}
