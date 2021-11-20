package application;

import java.util.ArrayList;

/**
 * Class representing a RoomManager object
 */
public class RoomManager {
	private ArrayList<Room> rooms;
	private static RoomManager singleInstance = new RoomManager();
	
	/**
	 * Constructor for the RoomManager class following the singleton design pattern
	 */
	private RoomManager() {
		rooms = new ArrayList<Room>();
	}
	
	/**
	 * Gets the static instance of RoomManager object
	 * @return the static instance of RoomManager object
	 */
	public static RoomManager getSingleInstance() {
		return singleInstance;
	}
	
	/**
	 * Searches for a room with the given name from the list of rooms
	 * @param roomName the name of the room
	 * @return the room with the given name
	 */
	public Room search(String roomName) {
		for (Room room : rooms) {
			if (room.getName() == roomName) {
				return room;
			}
		}
		return null;
	}
	
	/**
	 * Adds a room to the list of rooms 
	 * @param room the room to be added
	 */
	public void addRoom(Room room) {
		rooms.add(room);
	}
	
	/**
	 * Deletes a room from the list of rooms 
	 * @param searchedRoom the room to be deleted
	 */
	public void deleteRoom(Room searchedRoom) {
		rooms.remove(searchedRoom);
	}
	
	/**
	 * Getter for the list of rooms
	 * @return the list of rooms
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}

}
