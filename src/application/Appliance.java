package application;

import javafx.scene.Scene;

/**
 * Class representing an Appliance object
 */
public class Appliance {
	private String applianceName;

	/**
	 * Constructor for Appliance class
	 * @param applianceName the name of the appliance
	 */
	public Appliance(String applianceName) {
		this.applianceName = applianceName;
	}
	
	/**
	 * Getter for the appliance name
	 * @return the appliance name
	 */
	public String getName() {
		return applianceName;
	}

	/**
	 * Getter for the type 
	 * @return the type 
	 */
	public String getType() {
		return null;
	}
	
	/**
	 * Creates the UI window based on appliance type and attributes
	 * @return null since the subclasses for each appliance type will override this method with their own
	 */
	public Scene createWindow() {
		return null;
	}

}
