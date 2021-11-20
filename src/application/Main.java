package application;

import java.util.Optional;

import javafx.application.Application;

import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			RoomManager rm = RoomManager.getSingleInstance();

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Label lb1 = new Label("Welcome, User");
			lb1.setFont(Font.font("Coo Hew", 15));
			// VBox
			VBox mainArea = new VBox();

			// text input for name of room
			TextInputDialog roomTD = new TextInputDialog("enter name...");
			roomTD.setHeaderText("Set room name");
			roomTD.setTitle("Add Room");

			// Dropdown menu - appliances
			ChoiceBox<String> cb = new ChoiceBox();
			cb.getItems().addAll("TV", "AC", "Lights", "Speaker", "Doorbell", "Oven", "Refrigerator");
			cb.setValue("TV");

			// Room Font
			Font roomFont = Font.font("Cooper Black", FontWeight.BOLD, 25);

			// Setting Regions
			root.setTop(lb1);
			root.setLeft(mainArea);

			// Adding room
			Button roomBtn = new Button("Add room");
			roomBtn.setOnMouseClicked(e -> {
				Optional<String> room_name = roomTD.showAndWait();
				if (room_name.isPresent() && !(room_name.get().equals(""))) {

					// construct room flow pane
					FlowPane roomFP = constructFlowPane();
					mainArea.getChildren().add(roomFP);

					// Add room button
					Button roomLbl = new Button(roomTD.getEditor().getText());
					roomLbl.setFont(roomFont);
					roomLbl.setUnderline(true);
					roomLbl.setStyle("-fx-background-color: transparent");
					roomFP.getChildren().add(roomLbl);

					// creates a room class to store in the room manager as the button for the room
					// label is created

					// Text dialog for room name
					TextInputDialog applTD = new TextInputDialog("enter appliance name...");
					applTD.setHeaderText("Set appliance name");
					applTD.setTitle("Add Appliance");

					// Checks if room name entered is repeated
					boolean isRepeatedRoom = false;
					for (Room r : rm.getRooms()) {
						if (r.getName().equals(room_name.get())) {
							isRepeatedRoom = true;
						}
					}

					// Creates the room if room name is not repeated. Else if true, then room is not
					// created and an error message appears.
					if (isRepeatedRoom == false) {
						Room room = new Room(roomLbl.getText());
						rm.addRoom(room);
					} else if (isRepeatedRoom == true) {
						mainArea.getChildren().remove(roomFP);
						Alert errorMessage = new Alert(AlertType.ERROR);
						errorMessage.setTitle("Error");
						errorMessage.setHeaderText(null);
						errorMessage.setContentText("There already is a room with that name. Please try again.");
						errorMessage.showAndWait();
					}

					// Mouse event for room label
					roomLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							// when mouse is left clicked on room
							if (event.getButton() == MouseButton.PRIMARY) {
								Optional<String> appliance_name = applTD.showAndWait();
								if (appliance_name.isPresent() && !(appliance_name.get().equals(""))) {
									Button appliance = new Button(
											applTD.getEditor().getText() + " (" + cb.getValue() + ")");
									roomFP.getChildren().addAll(appliance);

									// corresponding room is searched in room manager based on room name
									Room room = rm.search(roomLbl.getText());
									// Edge case for repeated name and type of button in room
									boolean isRepeatedAppl = false;
									for (Appliance appl : room.getAppliances()) {
										if (appl.getName().equals(appliance_name.get())
												&& appl.getType().equals(cb.getValue())) {
											isRepeatedAppl = true;
										}
									}
									// Different type of appliance is created and added to room based on user choice
									// from choicebox
									Font buttonFont;
									if (cb.getValue().equals("TV") && isRepeatedAppl == false) {
										room.addAppliance(new TV(applTD.getEditor().getText()));									
									} else if (cb.getValue().equals("AC") && isRepeatedAppl == false) {
										room.addAppliance(new AirConditioner(applTD.getEditor().getText()));
									} else if (cb.getValue().equals("Lights") && isRepeatedAppl == false) {
										room.addAppliance(new Light(applTD.getEditor().getText()));
									} else if (cb.getValue().equals("Speaker") && isRepeatedAppl == false) {
										room.addAppliance(new Speaker(applTD.getEditor().getText()));
									} else if (cb.getValue().equals("Doorbell") && isRepeatedAppl == false) {
										room.addAppliance(new Doorbell(applTD.getEditor().getText()));
									} else if (cb.getValue().equals("Oven") && isRepeatedAppl == false) {
										room.addAppliance(new Oven(applTD.getEditor().getText()));
									} else if (cb.getValue().equals("Refrigerator") && isRepeatedAppl == false) {
										room.addAppliance(new Refrigerator(applTD.getEditor().getText()));
									} else if (isRepeatedAppl == true) {
										//If the user enters a repeated name for the room
										roomFP.getChildren().remove(appliance);
										Alert errorMessage = new Alert(AlertType.ERROR);
										errorMessage.setTitle("Error");
										errorMessage.setHeaderText(null);
										errorMessage.setContentText(
												"The current room already contains an appliance with that name and type. Please try again.");
										errorMessage.showAndWait();
									}
									// Old reference to cb.getValue() upon creation of button
									String buttonType = cb.getValue();

									// Mouse event for appliances in room
									appliance.setOnMouseClicked(new EventHandler<MouseEvent>() {

										@Override
										public void handle(MouseEvent event) {
											// If the user left clicks the appliance
											if (event.getButton() == MouseButton.PRIMARY) {
												// Searches for clicked appliance in database
												Room searchedRoom = rm.search(roomLbl.getText());
												Appliance searchedAppl = searchedRoom.search(appliance_name.get(),
														buttonType);
												//Creates a secondary UI window based on appliance found so details from last session are restored
												Scene scene2 = searchedAppl.createWindow();
												primaryStage.setTitle(appliance_name.get());
												primaryStage.setScene(scene2);
												
												// If the user closes the second window, the home screen switches back
												primaryStage.setOnCloseRequest(e -> {
													if (primaryStage.getScene() == scene2) {
														// Specific scenario on closing an oven window
														if (searchedAppl.getType().equals("Oven")) {
															Oven oven = (Oven) searchedAppl;

															Alert ovenOnClose = new Alert(AlertType.CONFIRMATION);
															ovenOnClose.setTitle("Confirmation");
															ovenOnClose.setHeaderText(null);
															ovenOnClose.setContentText(
																	"Closing this window will cancel the timer. Are you sure you want to exit?");

															// If user hits ok on confirmation, timer will stop and the
															// scene is switched.
															if (ovenOnClose.showAndWait().get() == ButtonType.OK) {
																if(oven.getTimer() != null){
																	oven.setTimeMin(oven.getOriginalTimeMin());
																	oven.getTimer().stop();
																	e.consume();
																	primaryStage.setScene(scene);
																	primaryStage.setTitle("Appliance Manager");
																} else {
																	e.consume();
																	primaryStage.setScene(scene);
																	primaryStage.setTitle("Appliance Manager");
																}
															} else {
																e.consume();
															}

														} else {
															// User closes the window for every other appliance type
															e.consume();
															primaryStage.setScene(scene);
															primaryStage.setTitle("Appliance Manager");
														}
													} else if (primaryStage.getScene() == scene) { 
														//Confirmation on exiting window from home screen
														Alert programOnClose = new Alert(AlertType.CONFIRMATION);
														programOnClose.setTitle("Confirmation");
														programOnClose.setHeaderText(null);
														programOnClose.setContentText(
																"Closing this window will reset the entire program, and any unsaved changes will be lost. "
																		+ "Are you sure you want to exit?");
														if (programOnClose.showAndWait().get() == ButtonType.OK) {
														} else {
															e.consume();
														}
													}
												});
											} else if (event.getButton() == MouseButton.SECONDARY) { // If the user right clicks appliance																								
												Alert delete = new Alert(AlertType.CONFIRMATION);
												delete.setTitle("Confirmation");
												delete.setHeaderText(null);
												delete.setContentText(
														"Are you sure you want to delete this appliance?");
												// If user selects Ok, delete the chosen appliance. Else if user
												// cancels, nothing happens.
												if (delete.showAndWait().get() == ButtonType.OK) {
													// Removes appliance from room
													Room searchedRoom = rm.search(roomLbl.getText());
													Appliance applDelete = searchedRoom.search(appliance_name.get(),buttonType);
													searchedRoom.deleteAppliance(applDelete);
													// Removes appliance button
													roomFP.getChildren().remove(appliance);
												}

											}
										}
									});

								}
							} else if (event.getButton() == MouseButton.SECONDARY) { // when right mouse button is
																						// clicked on a room
								// Confirmation window to delete
								Alert delete = new Alert(AlertType.CONFIRMATION);
								delete.setTitle("Confirmation");
								delete.setHeaderText(null);
								delete.setContentText(
										"Are you sure you want to delete this room, along with its appliances?");
								// If user selects Ok, delete the chosen room along with its appliances. Else if
								// user cancels, nothing happens.
								if (delete.showAndWait().get() == ButtonType.OK) {
									Room searchedRoom = rm.search(roomLbl.getText());
									rm.deleteRoom(searchedRoom);
									// Removes room button
									roomFP.getChildren().clear();
								}
							}
						}
					});
				}
			});

			// bottom HBox
			HBox botBox = new HBox();
			root.setBottom(botBox);
			botBox.getChildren().addAll(roomBtn, cb);

			primaryStage.setTitle("Appliance Manager");
			primaryStage.setScene(scene);
			primaryStage.show();
			//Confirmation on exiting home screen (seems like repeated code but is needed for an edge case)
			primaryStage.setOnCloseRequest(e -> {
				if (primaryStage.getScene() == scene) {
					Alert programOnClose = new Alert(AlertType.CONFIRMATION);
					programOnClose.setTitle("Confirmation");
					programOnClose.setHeaderText(null);
					programOnClose.setContentText(
							"Closing this window will reset the entire program, and any unsaved changes will be lost. "
									+ "Are you sure you want to exit?");
					if (programOnClose.showAndWait().get() == ButtonType.OK) {
					} else {
						e.consume();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FlowPane constructFlowPane() {
		FlowPane fp = new FlowPane(20, 20);
		fp.setPadding(new Insets(10, 10, 10, 10));
		return fp;
	}

	public static void main(String[] args) {
		launch(args);
	}

}