package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

/**
 * Class representing a Light object
 * @author Jia Siang Fung
 */
public class Light extends Appliance {
	private boolean onSwitch = false;
	private int brightness = 50;
	private Label bLevel;
	private Slider bSlider;
	private Circle circle;
	private final String type = "Lights";
	
	/**
	 * Constructor for Light class 
	 * @param applianceName the name of the appliance
	 */
	public Light(String applianceName) {
		super(applianceName);
	}
	
	/**
	 * Turns on the light
	 */
	public void turnOn() {
		Alert onMessage = new Alert(AlertType.INFORMATION);
		onMessage.setTitle("Notification");
		onMessage.setHeaderText(null);
		if (onSwitch == false) {
			onMessage.setContentText(this.getName() + " is now turned on.");
			onSwitch = true;
			circle.setFill(Color.YELLOW);
		} else {
			onMessage.setContentText(this.getName() + " is already turned on.");
		}
		onMessage.showAndWait();
	}
	
	
	/**
	 * Turns off the light
	 */
	public void turnOff() {
		Alert onMessage = new Alert(AlertType.INFORMATION);
		onMessage.setTitle("Notification");
		onMessage.setHeaderText(null);
		if (onSwitch == true) {
			onMessage.setContentText(this.getName() + " is now turned off.");
			onSwitch = false;
			circle.setFill(Color.BLACK);
		} else {
			onMessage.setContentText(this.getName() + " is already turned off.");
		}
		onMessage.showAndWait();
	}
	
	public void onSliderRelease() {
		int bValue = (int) bSlider.getValue();
		bLevel.setText(Integer.toString(bValue));
		brightness = bValue;
	}

	public String getType() {
		return type;
	}

	/**
	 * Creates the UI window for Light based on its attributes
	 * @return the scene for this UI
	 */
	public Scene createWindow() {
		try {
			// Light appliance UI
			VBox components = new VBox(10);
			components.setPadding(new Insets(20, 20, 20, 20));
			Scene scene = new Scene(components, 250, 250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			FlowPane onOff = new FlowPane(20, 20);
			onOff.setPadding(new Insets(10, 10, 10, 10));
			components.getChildren().add(onOff);

			// Initializing buttons - On/Off - and circle 
			Button onButton = new Button("On");
			Button offButton = new Button("Off");			
		    circle = new Circle();
			circle.setRadius(20.0f);
			if(onSwitch == true) {
				circle.setFill(Color.YELLOW);
			} else {
				circle.setFill(Color.BLACK);
			}
			
			
			onOff.getChildren().addAll(onButton, offButton, circle);

			// Functionality for buttons - On/Off
			onButton.setOnMouseClicked(e -> {turnOn();});
			offButton.setOnMouseClicked(e -> {turnOff();});

			// Brightness label
			Font font = Font.font("Arial", FontWeight.BOLD, 15);
			Label bLabel = new Label("Brightness");
			bLabel.setFont(font);
			components.getChildren().add(bLabel);

			// Brightness slider
			bSlider = new Slider();
			bSlider.setMin(0);
			bSlider.setMax(100);
			bSlider.setValue(brightness);
			bSlider.setShowTickLabels(true);
			bSlider.setShowTickMarks(true);
			bSlider.setMajorTickUnit(50);
			bSlider.setMinorTickCount(4);
			bSlider.setBlockIncrement(10);

			// Label for brightness value
			bLevel = new Label(Double.toString(bSlider.getValue()));
			bSlider.setOnMouseReleased((MouseEvent e) -> {onSliderRelease();});

			components.getChildren().addAll(bSlider, bLevel);
			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
