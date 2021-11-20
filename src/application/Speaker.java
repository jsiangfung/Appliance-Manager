package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Speaker extends Appliance{
	private int currentVolume = 50; 
	private boolean isOn = false;
	private boolean isMuted = false;
	private String type = "Speaker";
	private Button powerBtn = new Button();
	private Label volumeLbl = new Label("-- POWER OFF --");
	
	public Speaker(String applianceName) {
		super(applianceName);
	} 
	
	/**
	 * Turns on the speaker if the speaker was previously off.
	 */
	public void turnOn() {
		isOn = true;
		//change color of power button to green
		powerBtn.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 30px; " +
                "-fx-min-height: 30px; " +
                "-fx-max-width: 30px; " +
                "-fx-max-height: 30px; " +
                "-fx-background-color: #39ED70;"
        );
		//keeps speaker's previous mute/unmute setting
		if(isMuted)
			volumeLbl.setText("-- MUTED --");
		else
			volumeLbl.setText("Volume = " + currentVolume);
	}
	
	/**
	 * Turns off the speaker if the speaker was previously on.
	 */
	public void turnOff() {
		isOn = false;
		//change color of power button to red
		powerBtn.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 30px; " +
                "-fx-min-height: 30px; " +
                "-fx-max-width: 30px; " +
                "-fx-max-height: 30px; " +
                "-fx-background-color: #F95656;"
        );
		volumeLbl.setText("-- POWER OFF --");
	}
	
	/**
	 * Increases the volume of the speaker by 1, up to 100.
	 */
	public void volumeUp() {
		//unmutes if muted
		if(isOn && isMuted == true) {
			isMuted = false;
			volumeLbl.setText("Volume = " + currentVolume);
		}
		//increase volume if below 100
		if(isOn && currentVolume < 100) {
			currentVolume++;
			volumeLbl.setText("Volume = " + currentVolume);
		}
	}
	
	/**
	 * Decreases the volume of the speaker by 1, down to 0.
	 */
	public void volumeDown() {
		//unmutes if muted
		if(isOn && isMuted == true) {
			isMuted = false;
			volumeLbl.setText("Volume = " + currentVolume);
		}
		//reduce volume if above 0
		if(isOn && currentVolume > 0) {
			currentVolume--;
			volumeLbl.setText("Volume = " + currentVolume);
		}
	}
	
	/**
	 * Mutes and unmutes the speaker.
	 */
	public void muteChecker() {
		//Mutes if unmuted
		if(isOn == true && isMuted == false) {
			isMuted = true;
			volumeLbl.setText("-- MUTED --");
		}
		//Unmutes if muted
		else if(isOn && isMuted == true) {
			isMuted = false;
			volumeLbl.setText("Volume = " + currentVolume);
		}
	}
	
	public String getType() {
		return type;
	}
	
	/**
	 * Creates display for the speaker controls. 
	 * 
	 * @return The scene containing the Speaker controls.
	 */
	public Scene createWindow() {
		try {
			BorderPane speakerPane = new BorderPane();
			
			//Top -- Turning Speaker On and Off
			HBox powerBox = new HBox(5);
			powerBox.setPadding(new Insets(5));
			powerBox.setAlignment(Pos.CENTER_LEFT);
			Label powerLbl = new Label("On / Off");
			if(isOn)
				turnOn();
			else
				turnOff();
			powerBtn.setOnAction(new EventHandler<ActionEvent> () {

				@Override
				public void handle(ActionEvent event) {
					if(isOn == true) {
						turnOff();
					}
					else {
						turnOn();
					}
				}
					
			});
			powerBox.getChildren().addAll(powerBtn, powerLbl);
			
			//Center -- Information on Volume
			volumeLbl.setAlignment(Pos.CENTER);
			volumeLbl.setFont(Font.font("Arial", FontWeight.BOLD, 25));
			
			//Bottom -- Controls for Changing Volume
			VBox volumeControl = new VBox(5);
			volumeControl.setPadding(new Insets(5));
			HBox muteBox = new HBox(5); 
			HBox volumeUp = new HBox(5);
			HBox volumeDown = new HBox(5);
			muteBox.setAlignment(Pos.CENTER_LEFT);
			volumeUp.setAlignment(Pos.CENTER_LEFT);
			volumeDown.setAlignment(Pos.CENTER_LEFT);
			
			Button muteBtn = new Button();
			Button incBtn = new Button();
			Button decBtn = new Button();
			//circular buttons
			muteBtn.setStyle(
	                "-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; " 
	        );
			incBtn.setStyle(
	                "-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; " 
	        );
			decBtn.setStyle(
	                "-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; " 
	        );
			
			Label muteLbl = new Label("Mute / Unmute");
			Label incLabel = new Label("Volume +");
			Label decLabel = new Label("Volume -");
			
			muteBox.getChildren().addAll(muteBtn, muteLbl);
			volumeUp.getChildren().addAll(incBtn, incLabel);
			volumeDown.getChildren().addAll(decBtn, decLabel);
			volumeControl.getChildren().addAll(muteBox, volumeUp, volumeDown);
			
			muteBtn.setOnMouseClicked(e -> muteChecker());
			incBtn.setOnMouseClicked(e -> volumeUp());
			decBtn.setOnMouseClicked(e -> volumeDown());
			
			//Assigning Areas
			speakerPane.setTop(powerBox);
			speakerPane.setCenter(volumeLbl);
			speakerPane.setBottom(volumeControl);
			
			Scene scene = new Scene(speakerPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
