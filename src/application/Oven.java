package application;

import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


/**
 * Class representing an Oven object
 * @author Jia Siang Fung
 */
public class Oven extends Appliance {
	private int timeLeftMin = 20;
	private int timeLeftSec = 0;
	private int currentTemp = 400;
	private Circle circle;
	private int originalTimeMin = timeLeftMin;
	private Label timeLeftMinLabel;
	private Label timeLeftSecLabel;
	private Label tempLabelText;
	private Label tempLabelNum;
	private final String type = "Oven";
	private Timeline t1;
	
	/**
	 * Constructor for Oven class
	 * @param applianceName the name of the appliance
	 */
	public Oven(String applianceName) {
		super(applianceName);
	}
	
	/**
	 * Getter for timeLeftMin 
	 * @return timeLeftMin
	 */
	public int getTimeMin() {
		return timeLeftMin;
	}
	
	/**
	 * Getter for timeLeftSec
	 * @return timeLeftSec 
	 */
	public int getTimeSec() {
		return timeLeftSec;
	}
	
	/**
	 * Getter for getOriginalTimeMin
	 * @return getOriginalTimeMin
	 */
	public int getOriginalTimeMin() {
		return originalTimeMin;
	}
	
	/**
	 * Setter for timeLeftMin 
	 * @param newValue new value to set timeLeftMin to 
	 */
	public void setTimeMin(int newValue) {
		timeLeftMin = newValue;
	}
	
	/**
	 * Getter for currentTemp
	 * @return currentTemp
	 */
	public int getTemp() {
		return currentTemp;
	}
	
	/**
	 * Getter for type
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Getter for t1 
	 * @return t1
	 */
	public Timeline getTimer() {
		return t1;
	}

	/**
	 * Sets the timer(in minutes) of the oven
	 */
	public void setTime(int newTime) {
		timeLeftMin = newTime;
		originalTimeMin = newTime;
	}

	/**
	 * Sets the temperature of the oven
	 */
	public void setTemp(int newTemp) {
		currentTemp = newTemp;
	}
	
	/**
	 * Cancels the timer and resets the values and labels for current time left to its original values from before starting timer
	 */
	public void cancelTimer() {
		circle.setFill(Color.RED);
		t1.stop();
		timeLeftMin = originalTimeMin;
		timeLeftMinLabel.setText(Integer.toString(originalTimeMin) + " minutes");
		timeLeftSec = 0;
		timeLeftSecLabel.setText(Integer.toString(getTimeSec()) + " seconds");
	}
	
	/**
	 * Format and creates the text input dialog for setting the time
	 */
	public void setTimeWindow() {
		TextInputDialog setTimeTD = new TextInputDialog("enter time here...");
		setTimeTD.setTitle("Set Time");
		setTimeTD.setHeaderText("Enter a valid time below (in minutes)");
		Optional<String> input = setTimeTD.showAndWait();
		try {
			this.setTime(Integer.parseInt(setTimeTD.getEditor().getText()));
			timeLeftMinLabel.setText(Integer.toString(this.getOriginalTimeMin()) + " minutes");
		} catch (NumberFormatException error) {
			// When it catches the error - NumberFormatException
			if (input.isPresent()) {
				Alert errorText = new Alert(AlertType.ERROR);
				errorText.setTitle("Error");
				errorText.setHeaderText(null);
				errorText.setContentText("Please enter a valid integer.");
				errorText.showAndWait();
			}
		}
	}
	
	/**
	 * Format and creates the text input dialog for setting the temperature
	 */
	public void setTempWindow() {
		//Text input dialog
		TextInputDialog setTempTD = new TextInputDialog("enter temperature here...");
		setTempTD.setTitle("Set Temperature");
		setTempTD.setHeaderText("Enter a valid temperature below (0 - 450) in F");
		Optional<String> input = setTempTD.showAndWait();
		
		try {
			int inputInt = Integer.parseInt(setTempTD.getEditor().getText());
			//If the temperature isn't between 0 and 450, notifies the user to try again
			if (inputInt > 450 || inputInt < 0) {
				Alert errorMessage = new Alert(AlertType.ERROR);
				errorMessage.setTitle("Error");
				errorMessage.setHeaderText(null);
				errorMessage.setContentText("Please enter a valid integer.");
				errorMessage.showAndWait();
			} else {
				//sets the temperature to the user input
				this.setTemp(inputInt);
				tempLabelNum.setText(Integer.toString(this.getTemp()) + " F");
			}
		} catch (NumberFormatException error) {
			// Error message appears if user input is a non-integer
			if (input.isPresent()) {
				Alert errorMessage = new Alert(AlertType.ERROR);
				errorMessage.setTitle("Error");
				errorMessage.setHeaderText(null);
				errorMessage.setContentText("Please enter a valid integer.");
				errorMessage.showAndWait();
			}
		}
	}
	
	/**
	 *  Creates the UI window for Oven based on its attributes
	 *  @return the scene for this UI
	 */
	public Scene createWindow() {
		try {
			VBox components = new VBox(10);
			components.setPadding(new Insets(20, 20, 20, 20));
			Scene scene = new Scene(components, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			FlowPane set = new FlowPane(20, 20);
			set.setPadding(new Insets(0, 0, 0, 0));
			components.getChildren().add(set);

			// Initializing buttons - Set Time/Set Temp, Start
			Button setTimeButton = new Button("Set Time");
			Button setTempButton = new Button("Set Temp");
			set.getChildren().addAll(setTimeButton, setTempButton);

			FlowPane startCancel = new FlowPane(20, 20);
			startCancel.setPadding(new Insets(0, 0, 0, 0));
			components.getChildren().add(startCancel);
			Button start = new Button("Start");
			startCancel.getChildren().add(start);

			// Current temperature labels
			Font font1 = Font.font("Arial", FontWeight.BOLD, 15);
			tempLabelText = new Label("Current Temperature");
			tempLabelText.setFont(font1);
			components.getChildren().add(tempLabelText);

			Font font2 = Font.font("Arial", FontWeight.BOLD, 25);
			tempLabelNum = new Label(this.getTemp() + " F");
			tempLabelNum.setFont(font2);

			components.getChildren().add(tempLabelNum);

			// Time until finished label
			Label timeLeftText = new Label("Time Until Finished");
			timeLeftText.setFont(font1);
			components.getChildren().add(timeLeftText);
			
			FlowPane timeLeftFP = new FlowPane(20, 20);
			timeLeftFP.setPadding(new Insets(0, 0, 0, 0));
			components.getChildren().add(timeLeftFP);
			
			//Time left in min and seconds labels 
			timeLeftMinLabel = new Label(Integer.toString(originalTimeMin) + " minutes");
			timeLeftMinLabel.setFont(font2);
			
			timeLeftSecLabel = new Label("0 seconds");
			timeLeftSecLabel.setFont(font2);
			timeLeftFP.getChildren().addAll(timeLeftMinLabel, timeLeftSecLabel);
			
			//Circle color indicator when oven is on/off
			circle = new Circle();
			circle.setRadius(20.0f);
			circle.setFill(Color.RED);
			components.getChildren().add(circle);

			/// Action events for buttons
			// Sets time based on user input
			setTimeButton.setOnMouseClicked(e -> { setTimeWindow();});
			// Sets temperature based on user input
			setTempButton.setOnMouseClicked(e -> {setTempWindow();});

			// Starts the oven timer countdown
			start.setOnMouseClicked(e -> {
				// Swaps visibility between start and cancel buttons
				start.setVisible(false);
				Button cancel = new Button("Cancel");
				startCancel.getChildren().add(cancel);

				// Notification that timer has started along with its details
				circle.setFill(Color.GREEN);
				Alert onMessage = new Alert(AlertType.INFORMATION);
				onMessage.setTitle("Notification");
				onMessage.setHeaderText(null);
				onMessage.setContentText(this.getName() + " will now start for " + this.getTimeMin() + " minutes at "
						+ this.getTemp() + " F");
				onMessage.showAndWait();
				
				// mechanism for counting down time using Timeline
				originalTimeMin = timeLeftMin;
				timeLeftSec = 0;
				t1 = new Timeline(new KeyFrame(Duration.millis(1000), ae1 -> {
					if (timeLeftSec > 0) {
						timeLeftSec--;
						timeLeftSecLabel.setText(Integer.toString(timeLeftSec) + " seconds");
					} else {
						if (timeLeftMin > 0) {
							timeLeftSec = 59;
							timeLeftSecLabel.setText(Integer.toString(timeLeftSec) + " seconds");
							timeLeftMin--;
							timeLeftMinLabel.setText(Integer.toString(getTimeMin()) + " minutes");
						}
					}

					// When it finishes counting down
					if (timeLeftMin == 0 && timeLeftSec == 0) {
						// Resets to original state
						circle.setFill(Color.RED);
						timeLeftMinLabel.setText(Integer.toString(originalTimeMin) + " minutes");
						start.setVisible(true);
						startCancel.getChildren().remove(cancel);
						
						//Notifies user the timer has finished counting down
						Alert completeMessage = new Alert(AlertType.INFORMATION);
						completeMessage.setTitle("Notification");
						completeMessage.setHeaderText(null);
						completeMessage.setContentText(this.getName() + "'s oven timer is now complete! ");
						completeMessage.show();
					}
				}));
				t1.setCycleCount(timeLeftMin * 60);
				t1.play();

				// Clicking cancel button resets to original state and cancels timer
				cancel.setOnMouseClicked(ae2 -> {	
					cancelTimer();
					start.setVisible(true);
					startCancel.getChildren().remove(cancel);
					
				});

			});

			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
