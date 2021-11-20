package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AirConditioner extends Appliance{
	
	private int currentTemp = 80; 
	private boolean onSwitch = false;
	private final String type = "AC";
	private int upperLimit = 90;
	private int lowerLimit = 60;
	private Label temperature;
	
	public AirConditioner(String applianceName) {
		super(applianceName);
	}
	
	/**
	 * Turn on Air conditioner.
	 */
	public void turnOn() {
		onSwitch = true;
	}
	
	/**
	 * Turn off Air conditioner.
	 */
	public void turnOff() {
		onSwitch = false;
	}
	
	/**
	 * increase the temperature.
	 */
	public void tempUp() {
		if(currentTemp < upperLimit) {
			currentTemp++;
		}
	}
	
	/**
	 * decrease the temperature.
	 */
	public void tempDown() {
		if(currentTemp > lowerLimit) {
			currentTemp--;
		}
	}
	
	/**
	 * Get a appliance type. 
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Get a current temperature. 
	 */
	public int getACTemp() {
		return currentTemp;
	}
	
	/**
	 * Get a onSwitch status. 
	 */
	public boolean getSwitch() {
		return onSwitch;
	}
	
	/**
	 * Change a label color. 
	 * If temperature is equal to 90 °F, the label color will be red.
	 * If temperature is equal to 60 °F, the label color will be blue.
	 * Otherwise, the label color will be red.
	 */
	public void changeColor(){
		if(getACTemp() == upperLimit) {
			temperature.setTextFill(Color.RED);
		}
		else if(getACTemp() == lowerLimit) {
			temperature.setTextFill(Color.BLUE);
		}
		else {
			temperature.setTextFill(Color.BLACK);
		}
	}
	
	/**
	 * Creates the UI window for the air conditioner appliance type
	 */
	public Scene createWindow() {
		try {
			BorderPane acPane = new BorderPane();
			Scene sc = new Scene(acPane, 200, 200);
			sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			VBox buttonPlace = new VBox();
			buttonPlace.setAlignment(Pos.CENTER);
			
			// button for increasing the temperature
			Button up = new Button ("Up");
			up.setMaxWidth(Double.MAX_VALUE);
			up.setMinHeight(40);
			
			// button for decreasing the temperature
			Button down = new Button ("Down");
			down.setMaxWidth(Double.MAX_VALUE);
			down.setMinHeight(40);
			
			// create label
			temperature = new Label("Power Off");
			if(getSwitch() == false) {
				temperature.setText("Power off");
			}
			else {
				changeColor();
				temperature.setText("Temperature: " + getACTemp() + " °F");
			}
			
			temperature.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			temperature.setAlignment(Pos.CENTER);
			
			// button for on/off switch
			Button acSwitch = new Button();
			
			if(getSwitch() == false) {
				acSwitch.setText("On");
			}
			else {
				acSwitch.setText("Off");
			}
			acSwitch.setMaxWidth(Double.MAX_VALUE);
			acSwitch.setMinHeight(40);
			
			acPane.setCenter(temperature);
			
			// increase the temperature if press the button
			up.setOnAction(e -> {
				if(getSwitch()) {
					tempUp();
					changeColor();
					temperature.setText("Temperature: " + getACTemp() + " °F");
				}
			});
			
			// decrease the temperature if press the button
			down.setOnAction(e -> {
				if(getSwitch()) {
					tempDown();
					changeColor();
					temperature.setText("Temperature: " + getACTemp() + " °F");
				}
			});
			
			// turn on or off the switch if press the button
			acSwitch.setOnAction(e -> {
				if(!getSwitch()) {
					turnOn();
					acSwitch.setText("Off");
					changeColor();
					temperature.setText("Temperature: " + getACTemp() + " °F");
				}
				else {
					turnOff();
					acSwitch.setText("On");
					temperature.setTextFill(Color.BLACK);
					temperature.setText("Power Off");
				}
			});	
			
			buttonPlace.getChildren().addAll(acSwitch, up, down);
			acPane.setBottom(buttonPlace);
			
			return sc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
