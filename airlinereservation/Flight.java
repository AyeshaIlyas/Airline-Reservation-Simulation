// Flight.java

package airlinereservation;

import java.util.List;

public class Flight {
	
	private String origin;
	private String destination;
	private double cost;
	private List<String> route;
	
	public Flight(String origin, String destination, double cost, List<String> route) {
		if (cost < 0)
			throw new IllegalArgumentException("cost must be >= $0");
		
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
		this.route = route;
	}
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	
	public String getDestination() {
		return destination;
	}
	
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	public double getCost() {
		return cost;
	}
	
	
	public void setCost(double cost) {
		if (cost < 0)
			throw new IllegalArgumentException("cost must be >= $0");
		this.cost = cost;
	}
	
	
	public List<String> getRoute() {
		return route;
	}
	
	
	public void setRoute(List<String> route) {
		this.route = route;
	}
	
	
	public void printInfo() {
		String routeString = "";
		for (int i = 0; i < route.size(); i++) 
			routeString += route.get(i) + (i == route.size() - 1 ? "" : " -> ");
	
		System.out.printf("> Flight Information:%n"
						+ "  From: %s%n"
						+ "  To: %s%n"
						+ "  Cost: $%.2f%n"
						+ "  Route: %s%n",
				origin, destination, cost, routeString);
	}
	
	

}
