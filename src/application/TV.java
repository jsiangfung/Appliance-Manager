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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TV extends Appliance{
	private int currentVolume = 50; 
	private int currentChannel = 1; 
	private boolean isOn = false;
	private boolean isMuted = false;
	private String type = "TV";
	private Label volumeLbl = new Label();
	private Label channelLbl = new Label("-- POWER OFF --");
	private Button powerBtn = new Button();
	
	public TV(String applianceName) {
		super(applianceName);
	}
	
	/**
	 * Turns on the TV if the TV was previously off.
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
		channelLbl.setText("Channel = " + currentChannel);
		volumeLbl.setText("Volume = " + currentVolume);
		//keeps TV's previous mute/unmute setting
		if(isMuted)
			volumeLbl.setText("-- MUTED --");
		else
			volumeLbl.setText("Volume = " + currentVolume);
	}
	
	/**
	 * Turns off the TV if the TV was previously on.
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
		channelLbl.setText("-- POWER OFF --");
		volumeLbl.setText("");
	}
	
	/**
	 * Increases the volume of the TV by 1, up to 100.
	 */
	public void volumeUp() {
		//Unmutes if muted
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
	 * Decreases the volume of the TV by 1, down to 0.
	 */
	public void volumeDown() {
		//Unmutes if muted
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
	 * Increases the channel of the TV by 1, up to 50.  At 50, the next button press will loop the channel back around to 1.
	 */
	public void changeChannelUp() {
		//increase channel if below 50
		if(isOn && currentChannel < 50) {
			currentChannel++;
			channelLbl.setText("Channel = " + currentChannel);
		}
		//when channel is 50, loop back to 1
		else if(isOn && currentChannel == 50) {
			currentChannel = 1;
			channelLbl.setText("Channel = " + currentChannel);
		}
	}
	
	/**
	 * Decreases the channel of the TV by 1, down to 1.  At 1, the next button press will loop the channel back around to 50.
	 */
	public void changeChannelDown() {
		//reduce channel if above 1
		if(isOn && currentChannel > 1) {
			currentChannel--;
			channelLbl.setText("Channel = " + currentChannel);
		}
		//when channel is 1, loop back to 50
		else if(isOn && currentChannel == 1) {
			currentChannel = 50;
			channelLbl.setText("Channel = " + currentChannel);
		}
	}
	
	/**
	 * Mutes and unmutes the TV.
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
	 * Creates display for the TV controls. 
	 * 
	 * @return The scene containing the TV controls.
	 */
	public Scene createWindow() {
		try {
			BorderPane tvPane = new BorderPane();
			
			//Top -- Turning TV On and Off
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
			
			//Center -- Information on Channel and Volume
			VBox info = new VBox(20);
			channelLbl.setFont(Font.font("Arial", FontWeight.BOLD, 25));
			volumeLbl.setFont(Font.font("Arial", FontWeight.BOLD, 25));
			info.getChildren().addAll(channelLbl, volumeLbl);
			info.setAlignment(Pos.CENTER);
			
			//Bottom -- Controls for Changing Channel and Volume
			HBox control = new HBox(20);
			Region regionSpace = new Region();
			HBox.setHgrow(regionSpace, Priority.ALWAYS);
			VBox volumeControl = new VBox(5);
			volumeControl.setAlignment(Pos.BOTTOM_RIGHT);
			VBox channelControl = new VBox(5);
			channelControl.setAlignment(Pos.BOTTOM_LEFT);
			control.setPadding(new Insets(5));
			HBox channelUp = new HBox(5);
			channelUp.setAlignment(Pos.CENTER_LEFT);
			HBox channelDown = new HBox(5);
			channelDown.setAlignment(Pos.CENTER_LEFT);
			HBox muteBox = new HBox(5);
			muteBox.setAlignment(Pos.CENTER_RIGHT);
			HBox volumeUp = new HBox(5);
			volumeUp.setAlignment(Pos.CENTER_RIGHT);
			HBox volumeDown = new HBox(5);
			volumeDown.setAlignment(Pos.CENTER_RIGHT);
			
			Button incVolumeBtn = new Button();
			Button decVolumeBtn = new Button();
			Button incChannelBtn = new Button();
			Button decChannelBtn = new Button();
			Button muteBtn = new Button();
			//circular buttons
			incChannelBtn.setStyle(
	                "-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; " 
	        );
			decChannelBtn.setStyle(
					"-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; "  
	        );
			incVolumeBtn.setStyle(
	                "-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; " 
	        );
			decVolumeBtn.setStyle(
	                "-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; " 
	        );
			muteBtn.setStyle(
	                "-fx-background-radius: 5em; " +
	                "-fx-min-width: 30px; " +
	                "-fx-min-height: 30px; " +
	                "-fx-max-width: 30px; " +
	                "-fx-max-height: 30px; " 
	        );
			Label channelUpLbl = new Label("Channel +");
			Label channelDownLbl = new Label("Channel -");
			Label muteLbl = new Label("Mute / Unmute");
			Label volumeUpLbl = new Label("Volume +");
			Label volumeDownLbl = new Label("Volume -");
			
			channelControl.getChildren().addAll(channelUp, channelDown);
			volumeControl.getChildren().addAll(muteBox, volumeUp, volumeDown);
			
			channelUp.getChildren().addAll(incChannelBtn, channelUpLbl);
			channelDown.getChildren().addAll(decChannelBtn, channelDownLbl);
			muteBox.getChildren().addAll(muteLbl, muteBtn);
			volumeUp.getChildren().addAll(volumeUpLbl, incVolumeBtn);
			volumeDown.getChildren().addAll(volumeDownLbl, decVolumeBtn);
			
			control.getChildren().addAll(channelControl, regionSpace, volumeControl);
			incVolumeBtn.setOnMouseClicked(e -> volumeUp());
			decVolumeBtn.setOnMouseClicked(e -> volumeDown());
			incChannelBtn.setOnMouseClicked(e -> changeChannelUp());
			decChannelBtn.setOnMouseClicked(e -> changeChannelDown());
			muteBtn.setOnMouseClicked(e -> muteChecker());
			
			//Assigning Areas
			tvPane.setTop(powerBox);
			tvPane.setBottom(control);
			tvPane.setCenter(info);
			
			Scene scene = new Scene(tvPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
