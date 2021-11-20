package application;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Doorbell extends Appliance{
	private final String type = "Doorbell";
	private Label openLabel = new Label("");
	
	public Doorbell(String applianceName) {
		super(applianceName);
	} 
	
	/**
	 * Confirmation for opening gate for the guest.
	 */
	public void openGateConfirm(){
		Alert gateAlert = new Alert(AlertType.CONFIRMATION);
		gateAlert.setHeaderText("Are you sure you want to unlock the gate?");
		//if user selects OK, message displays and fades out.
		if(gateAlert.showAndWait().get() == ButtonType.OK) {
			openLabel.setText("Gate has been opened.");
			FadeTransition fadeOut = new FadeTransition();
			fadeOut.setNode(openLabel);
			fadeOut.setDuration(Duration.seconds(2));
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			fadeOut.play();
		}
	}
	
	public String getType() {
		return type;
	}
	
	/**
	 * Creates display for the doorbell controls. 
	 * @return The scene of the Doorbell controls.
	 */
	public Scene createWindow() {
		try {
			BorderPane doorbellPane = new BorderPane();
			
			Button gateControl = new Button("Open Gate");
			//circular button
			gateControl.setStyle(
	                "-fx-background-radius: 20em; " +
	                "-fx-min-width: 150px; " +
	                "-fx-min-height: 150px; " +
	                "-fx-max-width: 150px; " +
	                "-fx-max-height: 150px; " +
	                "-fx-font: 20px Arial; " +
	                "-fx-font-weight: bold; " 
	        );
			gateControl.setOnAction(e -> openGateConfirm());
			
			VBox labelBox = new VBox();
			Label emptyLbl = new Label("");
			labelBox.setAlignment(Pos.TOP_CENTER);
			labelBox.getChildren().addAll(openLabel, emptyLbl);
			
			doorbellPane.setCenter(gateControl);
			doorbellPane.setBottom(labelBox);
			Scene scene = new Scene(doorbellPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
