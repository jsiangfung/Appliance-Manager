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

public class Refrigerator extends Appliance{
	private int currentTemp = 40; 
	private int upperLimit = 50;
	private int lowerLimit = 30;
	private final String type = "Refrigerator";
	private Label temperature;
	
	public Refrigerator(String applianceName) {
		super(applianceName);
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
	public int getRFTemp() {
		return currentTemp;
	}
	
	
	/**
	 * Change a label color. 
	 * If temperature is equal to 50 °F, the label color will be red.
	 * If temperature is equal to 30 °F, the label color will be blue.
	 * Otherwise, the label color will be red.
	 */
	public void changeColor(){
		if(getRFTemp() == upperLimit) {
			temperature.setTextFill(Color.RED);
		}
		else if(getRFTemp() == lowerLimit) {
			temperature.setTextFill(Color.BLUE);
		}
		else {
			temperature.setTextFill(Color.BLACK);
		}
	}
	
	/**
	 * Creates the UI window for the refrigerator appliance type
	 */
	public Scene createWindow() {
		try {
			
			BorderPane rfPane = new BorderPane();
			VBox buttonPlace = new VBox();
			buttonPlace.setAlignment(Pos.CENTER);
			Scene sc = new Scene(rfPane, 200, 200);
			sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// button for increasing the temperature
			Button up = new Button ("Up");
			up.setMaxWidth(Double.MAX_VALUE);
			up.setMinHeight(40);
			
			// button for decreasing the temperature
			Button down = new Button ("Down");
			down.setMaxWidth(Double.MAX_VALUE);
			down.setMinHeight(40);
			
			temperature = new Label("Temperature: " + getRFTemp() + " °F");
			temperature.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			rfPane.setCenter(temperature);
			
			// increase the temperature if press the button
			up.setOnAction(e -> {
				tempUp();
				changeColor();
				temperature.setText("Temperature: " + getRFTemp() + " °F");
			});
			
			// decrease the temperature if press the button
			down.setOnAction(e -> {
				tempDown();
				changeColor();
				temperature.setText("Temperature: " + getRFTemp() + " °F");
			});
			
			buttonPlace.getChildren().addAll(up, down);
			rfPane.setBottom(buttonPlace);
		
			return sc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
