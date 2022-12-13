// Airline.java

package airlinereservation;

import graph.Graph;
import graph.Path;

public class Airline {
	private Graph<String> g;
	private static final String[] CITIES = 
			{"new york", "chicago", "san francisco", 
			"denver", "dallas", "miami", "san diego", "la"};

	public Airline() {
		g = new Graph<String>(CITIES.length, CITIES);
		g.addEdge("new york", "chicago", 75)
		.addEdge("new york", "denver", 100)
		.addEdge("new york", "dallas", 125)
		.addEdge("new york", "miami", 90)
		.addEdge("chicago", "san francisco", 25)
		.addEdge("chicago", "denver", 20)
		.addEdge("denver", "san francisco", 75)
		.addEdge("denver", "la", 100)
		.addEdge("dallas", "la", 80)
		.addEdge("dallas", "san diego", 90)
		.addEdge("miami", "dallas", 50)
		.addEdge("san francisco", "la", 45)
		.addEdge("san diego", "la", 45);
	}
	
	// precondition: origin and dest are valid labels for the composed graph
	// if the labels are not valid, the graph will throw an IllegalArgumentException "label does not exist in graph"
	// return: null if there is no path from origin to dest, a Flight object if there is 
	public Flight findCheapest(String origin, String dest) {
		Path<String> path = g.findShortestLabeledPath(origin, dest);
		return path == null ? null : new Flight(origin, dest, path.getWeight(), path.getPath());
	}
	
	public String[] getCities() {
		return CITIES;
	}
}
