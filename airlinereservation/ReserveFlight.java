// ReserveFlight.java

package airlinereservation;

import java.util.Scanner;

public class ReserveFlight {
	
	public static void main(String[] args) {
		// create the simple, directed, labeled, weighted graph representing the possible flights
		Airline airline = new Airline();
		String[] cities = airline.getCities();
		
		// get origin
		// get dest
		// origin and dest must be different
		Scanner scanner = new Scanner(System.in);
		displayCities(cities);
		String origin = getCity(scanner, "[*] Origin: ", "[x] Please enter a valid city.", cities);
		cities = remove(cities, origin);
		String destination = getCity(scanner, "[*] Destination: ", "[x] Please enter a valid city. (Your destination cannot be the same as your origin.)",  cities);
		System.out.println();
		
		// search for the cheapest flight 
		// use dijkstra's distance algorithm to find the shortest path from origin to dest and display weight and path vertices
		Flight flight = airline.findCheapest(origin, destination);
		if (flight == null)
			System.out.printf("> Currently, there are no flights going from %s to %s.%n  Please check again later or explore flights to other destinations. %n",
					origin, destination);
		else {
			System.out.println("Searching for most affordable flight...");
			flight.printInfo();
		}
	}
	
	
	public static String[] remove(String[] array, String value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(value)) {
				String[] newArray = new String[array.length - 1];
				System.arraycopy(array, 0, newArray, 0, i);
				System.arraycopy(array, i + 1, newArray, i, newArray.length - i);
				return newArray;
			}	
		}
		return array;
	}
	
	
	public static void displayCities(String[] cities) {
		System.out.println("Cities:");
		for (String city: cities) 
			System.out.println(" > " + city);
		System.out.println();
	}
	
	
	public static String getCity(Scanner scanner, String prompt, String error, String[] cities) {
		System.out.print(prompt);
		String input = scanner.nextLine().strip().toLowerCase();
		while (!contains(cities, input)) {
			System.out.println(error);
			System.out.print(prompt);
			input = scanner.nextLine().strip().toLowerCase();
		}
		
		// get only a valid city
		return input;
	}
	
	public static boolean contains(String[] array, String key) {
		for (String elem: array) 
			if (elem.equals(key))
				return true;
		return false;
	}
	
	
	

}
