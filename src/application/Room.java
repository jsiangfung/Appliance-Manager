package application;

import java.util.ArrayList;

/**
 * Class a room object
 */
public class Room {
	private ArrayList<Appliance> appliances;
	private String roomName;
	
	/**
	 * Constructor for Room class
	 * @param roomName the name of the room
	 */
	public Room(String roomName) {
		this.roomName = roomName;
		appliances = new ArrayList<Appliance>();
	}
	
	/**
	 * Searches through the list of appliances in a particular room for an appliance
	 * @param applianceName name of the given appliance
	 * @param type type of the given appliance
	 * @return the searched appliance
	 */
	public Appliance search(String applianceName, String type) {
		for (Appliance appl : appliances) {
			if (appl.getName().equals(applianceName) && appl.getType().equals(type)) {
				return appl;
			}
		}
		return null;
	}

	/**
	 * Adds a new appliance to the room
	 * @param newApp the new appliance to be added to the room
	 */
	public void addAppliance(Appliance newApp) {
		appliances.add(newApp);
	}

	/**
	 * Removes the given a appliance from the list of appliances
	 * @param app
	 */
	public void deleteAppliance(Appliance app) {
		appliances.remove(app);
	}
	
	/**
	 * Getter for room name
	 * @return the room name
	 */
	public String getName() {
		return roomName;
	}
	
	/**
	 * Getter for the list of appliances in the room 
	 * @return the list of appliances in room
	 */
	public ArrayList<Appliance> getAppliances() {
		return appliances;
	}
}
