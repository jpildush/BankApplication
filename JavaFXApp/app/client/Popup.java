package app.client;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * <h1>Assignment 2 - Popup Windows</h1>
 * This class deals with JavaFX Popups that are used throughout the banking application.
 * Popups - Alerts and Confirmations
 * <p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */
public class Popup {
	static boolean resultB;
	static int resultI;
	
	/**
	 * This is the Alert method. It it intended to create a Popup window that alerts the uder
	 * @param title - Title of the window
	 * @param message - A message that is being told to the user
	 * @param buttonText - The text of the close button
	 * @param size - Sizing ranges from 1-3 (smallest to largest, respectively
	 */
	public static void Alert(String title,String message,String buttonText,int size){
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		
		//Small Settings
		if(size == 1)
		{
			window.setMaxWidth(250);
			window.setMinWidth(250);
			window.setMinHeight(100);
			window.setMaxHeight(100);
		}
		//Medium Settings
		else if(size == 2)
		{
			window.setMaxWidth(350);
			window.setMinWidth(350);
			window.setMinHeight(150);
			window.setMaxHeight(150);
		}
		//Large Settings
		else if(size == 3)
		{
			window.setMaxWidth(450);
			window.setMinWidth(450);
			window.setMinHeight(255);
			window.setMaxHeight(255);
		}
		
		Label label = new Label();
		label.setText(message);
		Button close = new Button(buttonText);
		close.setOnAction(e -> window.close());
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label,close);
		layout.setAlignment(Pos.CENTER);
		label.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}
	
	/**
	 * This method is used to confirm a request with the user with a simple yes or no 
	 * @param title - Title of the window
	 * @param message - A message that is being told to the user
	 * @param buttonYes - The text for the confirmation button
	 * @param buttonNo - The text for the rejection button
	 * @return boolean - Will return boolean of the user's confirmation (True - yes | False - no) 
	 */
	public static boolean Confirm(String title,String message,String buttonYes,String buttonNo){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMaxWidth(250);
		window.setMinHeight(200);
		window.setMaxHeight(200);
		
		Label label = new Label();
		label.setText(message);
		Button yesButton = new Button(buttonYes);
		yesButton.setOnAction(e -> {
			resultB=true;
			window.close();
		});
		Button noButton = new Button(buttonNo);
		noButton.setOnAction(e -> {
			resultB=false;
			window.close();
		});
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label,yesButton,noButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setOnCloseRequest(e -> {
			resultB = false;
			window.close();
		});
		window.setScene(scene);
		window.showAndWait();
		return resultB;
	}
}
