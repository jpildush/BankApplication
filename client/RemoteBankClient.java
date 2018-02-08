package BankApp.client;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.rmi.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import BankApp.common.*;

/**
 * <h1>RemoteBankClient</h1>
 * This is the Remote Bank class, Client side. It is intended to be used by clients.
 * Clients have a user profile and password, which gives each client their privacy.
 * Each client's password has been hashed for complete privacy.
 * Clients have the ability of creating and removing their own accounts.
 * <p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */
public class RemoteBankClient extends Application {
	//These variables are used throughout the code
	private static RemoteBank remoteBank;
	private static Stage currentWindow,secondaryWindow;
	private static String selectedUser,accountType;
	private static Scene listScene;
	private static Account[] currentUserAccount;
	private static Label welcomeLabel;
	public static int currentUserId,accountNumber,optI,maxTrans, yearsOfInv;
	public static double optD,balance,interest, serviceCharge;

	/**
	 * This is the main method of the RemoteBankClient Application.
	 * This method will set the remoteBank to the RMI object from the server.
	 * This method will then call the launch method which invokes the overridden start method.
	 */
	public static void main(String[] args) {
		String url = "rmi://localhost:5678/";
		try{
 			RemoteBank bank = (RemoteBank) Naming.lookup(url + "bank");
 			remoteBank = bank;
 			currentUserAccount = new Account[0];
 			welcomeLabel = new Label();
 			accountNumber = 1357;
 			launch();
		}
		catch(Exception e){System.out.println("Error" + e);}
	}

	/**
	 * This is the overridden start method
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Setting the current stage as the primary stage
		currentWindow = primaryStage;
		secondaryWindow = new Stage();
		secondaryWindow.initModality(Modality.APPLICATION_MODAL);

		/*Calling the startScene method which sets the first scene.
		 * This method also internally sets the remaining scenes.
		 */
		startScene();
	}

	/**
	 * This method will close the main window from anywhere in the application
	 */
	static void closeProgram(){
		currentWindow.close();
	}

	/**PASSWORD ENCRYPTION HASHING
	 * This method will convert the string to hex - meant to be used for password encryption
	 * @param String - This argument is the password that is being converted to a Hex
	 * @return String - Will return the hashed password value
	 */
	static String toHex(String string) {
	    try {
			return String.format("%x", new BigInteger(1, string.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    return "";
	}

	/**
	 * This method Initiates the Start Scene
	 */
	static void startScene(){
		Scene scene;
		Button login,register,exit;
		GridPane layout = new GridPane();
		HBox buttonBox = new HBox(4);
		Label userFLabel,userLLabel,passLabel;
		TextField userFField,userLField;
		PasswordField passField = new PasswordField();

		//Setting Buttons
		login = new Button("Login");
		register = new Button("Register");
		exit = new Button("Exit");

		//Setting Layout Button Box
		GridPane.setConstraints(buttonBox,2,4);
		buttonBox.setPadding(new Insets(10,10,10,10));
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(login,register);

		//Setting Button Constraints
		GridPane.setConstraints(exit,1,4);

		//Configure welcome label
		welcomeLabel = new Label("Sign In to The Bank!");
		GridPane.setConstraints(welcomeLabel,1, 0);

		//Configure User first name Section
		userFLabel = new Label("First Name:");
		userFField = new TextField();
		GridPane.setConstraints(userFLabel,1,1);
		GridPane.setConstraints(userFField,2,1);
		userFField.setPromptText("enter first name");

		//Configure User last name Section
		userLLabel = new Label("Last Name:");
		userLField = new TextField();
		GridPane.setConstraints(userLLabel,1,2);
		GridPane.setConstraints(userLField,2,2);
		userLField.setPromptText("enter last name");

		//Configure Password Section
		passLabel = new Label("Password:");
		GridPane.setConstraints(passLabel,1,3);
		GridPane.setConstraints(passField,2,3);
		passField.setPromptText("enter password");

		//Configure Login Action
		login.setOnAction(e -> {
			selectedUser = setFormatName(new String[]{userFField.getText(),userLField.getText()});

			try
			{
				//Setting the Current User ID (From Profile)
				currentUserId = remoteBank.checkForProfile(selectedUser);
				if(currentUserId > -1)
				{
					if(remoteBank.checkPass(currentUserId, toHex(passField.getText())))
					{
						currentUserAccount = remoteBank.searchByAccountName(selectedUser);

						//Setting the next scene and stage
						listAccountScene();
					}
					else
						Popup.Alert("Password Error!", "Password Does Not Match\n    What is on Record!", "Close", 2);
				}
				else
					Popup.Alert("Login Error", "Incorrect Login Information!", "Close", 2);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		//Configure the field actions
		userFField.setOnAction(e -> login.fire());
		userLField.setOnAction(e -> login.fire());
		passField.setOnAction(e -> login.fire());

		//Configure Register Action
		register.setOnAction(e -> {
			registerScene();

		});

		//Configure Exit Action
		exit.setOnAction(e -> closeProgram());

		//Setting the layout
		layout.setPadding(new Insets(10,10,10,10));
		layout.setVgap(5);
		layout.setHgap(8);
		layout.getChildren().addAll(welcomeLabel,userFLabel,userFField,userLLabel
									,userLField,passLabel,passField,exit,buttonBox);

		//Setting the Start scene
		scene = new Scene(layout,350,400);

		//Setting the stage with the Start scene
		currentWindow.setOnCloseRequest(e -> closeProgram());
		try {
			currentWindow.setTitle(remoteBank.getBankName() + " Bank");
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		//Setting the Main Window
		currentWindow.setScene(scene);
		currentWindow.setMaxWidth(350);
		currentWindow.setMinWidth(350);
		currentWindow.setMinHeight(200);
		currentWindow.setMaxHeight(200);
		currentWindow.show();
	}

	/**
	 * This method initiates the registration scene
	 */
	static void registerScene()
	{
		Button register,exit;
		Scene scene;
		GridPane layout = new GridPane();
		HBox buttonBox = new HBox(4);
		Label userFLabel,userLLabel,passLabel,passLabelCheck;
		TextField userFField,userLField;
		PasswordField passField,passFieldCheck;
		Stage window;

		//Setting window
		window = new Stage();

		//Setting Buttons
		register = new Button("Register");
		exit = new Button("Exit");

		//Setting Layout Button Box
		GridPane.setConstraints(buttonBox,2,5);
		buttonBox.setPadding(new Insets(10,10,10,10));
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(register);

		//Setting Button Constraints
		GridPane.setConstraints(exit,0,5);

		//Configure User first name Section
		userFLabel = new Label("First Name:");
		userFField = new TextField();
		GridPane.setConstraints(userFLabel,0,1);
		GridPane.setConstraints(userFField,2,1);
		userFField.setPromptText("enter first name");

		//Configure User last name Section
		userLLabel = new Label("Last Name:");
		userLField = new TextField();
		GridPane.setConstraints(userLLabel,0,2);
		GridPane.setConstraints(userLField,2,2);
		userLField.setPromptText("enter last name");

		//Configure new Password Section and confirmation Password Section
		passField = new PasswordField();
		passLabel = new Label("Password:");
		GridPane.setConstraints(passLabel,0,3);
		GridPane.setConstraints(passField,2,3);
		passField.setPromptText("enter password");

		passFieldCheck = new PasswordField();
		passLabelCheck = new Label("Re-Enter Password:");
		GridPane.setConstraints(passLabelCheck,0,4);
		GridPane.setConstraints(passFieldCheck,2,4);
		passFieldCheck.setPromptText("enter password");


		//Configure Login Action
		register.setOnAction(e -> {
			if(userFField.getText().length() > 2 && userFField.getText().length() < 12
												 && userLField.getText().length() > 2
												 && userLField.getText().length() < 15)
			{
				if(passField.getText().length() > 4 && passField.getText().length() < 13)
				{
					selectedUser = setFormatName(new String[]{userFField.getText(),userLField.getText()});
					try
					{
						//Setting the Current User ID (From Profile)
						currentUserId = remoteBank.checkForProfile(selectedUser);

						if(currentUserId == -1)
						{
							if(passField.getText().equals(passFieldCheck.getText()))
							{
								//Adding new Profile with hex password
								remoteBank.addProfile(selectedUser, toHex(passField.getText()));

								//Setting the current user id
								currentUserId = remoteBank.checkForProfile(selectedUser);

								//Setting Current Account
								currentUserAccount = remoteBank.searchByAccountName(selectedUser);

								//Configure welcome label
								StringBuffer msg = new StringBuffer("Welcome ");
								msg.append(selectedUser).append("!").append("\nSetup is Running!");
								welcomeLabel = new Label(msg.toString());
								window.close();
								Popup.Alert("Accounts Error!", "There are no Open Accounts!"
												+ "\n Click close to Open an Account!", "Close", 2);
								listAccountScene();
							}
							else
								Popup.Alert("Password Error", "Passwords do NOT match!", "Close", 1);
						}
						else
							Popup.Alert("User Error", "User Already Exists!", "Close", 2);
					}
					catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				else
					Popup.Alert("Password Length Error!", "Password length must be \n between 5 and 12 letters!", "Close", 2);
			}
			else
				Popup.Alert("User Error", "First name must be between 2 and 12 letters! "
						+ "\n Last name must be between 2 and 15 letters!", "Close", 2);

		});

		//Configure the field actions
		userFField.setOnAction(e -> register.fire());
		userLField.setOnAction(e -> register.fire());
		passField.setOnAction(e -> register.fire());
		passFieldCheck.setOnAction(e -> register.fire());

		//Configure Exit Action
		exit.setOnAction(e -> window.close());

		//Setting the layout
		layout.setPadding(new Insets(10,10,10,10));
		layout.setVgap(5);
		layout.setHgap(8);
		layout.getChildren().addAll(userFLabel,userFField,userLLabel
									,userLField,passLabel,passField,passLabelCheck,passFieldCheck,exit,buttonBox);

		//Setting the Start scene
		scene = new Scene(layout,350,400);

		//Setting the stage with the Start scene
		window.setOnCloseRequest(e -> window.close());
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Register a New Profile");
		window.setScene(scene);
		window.setMaxWidth(350);
		window.setMinWidth(350);
		window.setMinHeight(220);
		window.setMaxHeight(220);
		window.showAndWait();
	}

	/**
	 * This method will initiate the Account List Scene
	 */
	static void listAccountScene(){
		Button searchBal,selectId;
		BorderPane layout = new BorderPane();
		ScrollPane scrollBar = new ScrollPane();
		ListView<String> listView = new ListView<>();
		VBox vBoxList,vBoxMain;
		MenuItem logout,exit,addAccount,remButton,chgPass;
		MenuBar hBoxMenu;
		Menu file,edit;
		ArrayList<String> accountType = new ArrayList<>();
		StringBuffer welcome = new StringBuffer();

		//Setting Buttons
		selectId = new Button("Select Account");
		searchBal = new Button("Search for Balance");

		//Setting Menu Items
		logout = new MenuItem("Logout");
		exit = new MenuItem("Exit");
		addAccount = new MenuItem("Add an Account");
		remButton = new MenuItem("Remove an Account");
		chgPass = new MenuItem("Change User Password");

		//Setting layout button vBoxMain
		vBoxMain = new VBox(8);
		vBoxMain.setSpacing(10);
		vBoxMain.setPadding(new Insets(125,20,20,5));
		vBoxMain.getChildren().addAll(selectId,searchBal);
		BorderPane.setAlignment(vBoxMain, Pos.BOTTOM_CENTER);

		/**
		 * Setting the accounts related to the user and setting the welcome label
		 * if the user has any open accounts.
		 * These processes are done after the scene is set so that the window will change prior to these actions
		 */
		if(currentUserAccount.length > 0)
		{
			//Configure welcome label
			welcome.append("Welcome ").append(currentUserAccount[0].getFirstName()).append("!");
			welcomeLabel = new Label(welcome.toString());

			for(int i = 0;i < currentUserAccount.length;i++)
				accountType.add(currentUserAccount[i].getAccType());
		}
		else
		{
			welcome.append(selectedUser).append("!");
			welcomeLabel = new Label(welcome.toString());
			openAccountScene();
			if(currentUserAccount.length > 0)
			{
				welcome.append("Welcome ").append(currentUserAccount[0].getFirstName()).append("!");
				for(int i = 0;i < currentUserAccount.length;i++)
					accountType.add(currentUserAccount[i].getAccType());
			}
		}


		//Setting the List
		listView.getItems().addAll(accountType);
		listView.setMaxWidth(140);
		listView.setMinWidth(140);
		listView.setMaxHeight(300);
		listView.setMinHeight(300);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.getSelectionModel().select(0);

		//Setting layout of vBoxList and scrollBar
		vBoxList = new VBox(8);
		vBoxList.setSpacing(10);
		vBoxList.setPadding(new Insets(20,20,20,20));
		scrollBar.setContent(listView);
		scrollBar.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollBar.setMaxWidth(150);
		scrollBar.setMinWidth(150);
		scrollBar.setMaxHeight(300);
		scrollBar.setMinHeight(300);
		vBoxList.getChildren().addAll(welcomeLabel,scrollBar);

		//Setting Menu Items
		file = new Menu("File");
		file.getItems().addAll(logout,exit);
		edit = new Menu("Account Settings");
		edit.getItems().addAll(addAccount,remButton,chgPass);
		hBoxMenu = new MenuBar();
		hBoxMenu.getMenus().addAll(file,edit);

		//Configure Menu Items Action Events

		//Logout
		logout.setOnAction(e -> {
			startScene();
		});

		//Exit
		exit.setOnAction(e -> currentWindow.close());

		//Add Account
		addAccount.setOnAction(e -> openAccountScene());

		//Change Password
		chgPass.setOnAction(e -> chgPassScene());

		//Remove Account
		remButton.setOnAction(e -> removeAccountScene(listView.getSelectionModel().getSelectedIndex(),
														listView.getSelectionModel().getSelectedItem()));

		//Searching for Accounts with Specific Balances
		searchBal.setOnAction(e -> {
			searchBalanceScene();
		});

		//Selecting an ID
		selectId.setOnAction(e -> accountInfoScene(listView.getSelectionModel().getSelectedIndex(),
				listView.getSelectionModel().getSelectedItem()));

		//Setting the layout
		layout.setPadding(new Insets(0,0,10,0));
		layout.setRight(vBoxMain);
		layout.setCenter(vBoxList);
		layout.setTop(hBoxMenu);


		//Setting the Stage
		listScene = new Scene(layout,500,500);
		currentWindow.setScene(listScene);
		currentWindow.setMaxWidth(385);
		currentWindow.setMinWidth(385);
		currentWindow.setMinHeight(421);
		currentWindow.setMaxHeight(421);
	}

	/**
	 * This method will initiate the Account Information Scene
	 * @param id - The selected ID of the currently selected user
	 * @param type - The type of account that is being checked (CHQ,SAV,GIC)
	 */
	static void accountInfoScene(int id,String type)
	{
		BorderPane border = new BorderPane();
		VBox infoBox = new VBox();
		HBox bottomBox = new HBox();
		Scene scene;
		Label info,windowLabel;
		Button close;
		Account account = currentUserAccount[id];

		//Setting the window label
		windowLabel = new Label("\nAccount: " + type + "| ID: " + (id + 1) + "\n\n");

		//Setting the info of account
		info = new Label(account.toString());

		//Setting the infoBox
		infoBox.getChildren().add(info);

		//Exit Button
		close = new Button("Close");
		close.setOnAction(e -> secondaryWindow.hide());

		//Setting the bottomBox
		bottomBox.setPadding(new Insets(0,0,0,130));
		bottomBox.getChildren().add(close);

		//Setting the BorderPane
		border.setPadding(new Insets(10,30,10,30));
		border.setTop(windowLabel);
		border.setCenter(infoBox);
		border.setBottom(bottomBox);

		//Setting the Scene
		scene = new Scene(border,400,400);

		//Finalizing the Stage
		secondaryWindow.setOnCloseRequest(e -> secondaryWindow.hide());
		secondaryWindow.setScene(scene);
		secondaryWindow.setMaxHeight(360);
		secondaryWindow.setMinHeight(360);
		secondaryWindow.setMaxWidth(380);
		secondaryWindow.setMinWidth(380);
		secondaryWindow.showAndWait();
	}


	/**
	 * This method will initiate the Password Change Scene
	 */
	static void chgPassScene()
	{
		BorderPane border = new BorderPane();
		PasswordField oldText,newText,confPass;
		GridPane grid = new GridPane();
		HBox hBox = new HBox();
		Button submit = new Button("Submit");
		Label mainLabel = new Label("Input Old Password\n     Then the \nDesired New Password");
		Label oldLabel,newLabel,confLabel;
		Scene scene;

		//Setting the Labels
		oldLabel = new Label("Old Password");
		GridPane.setConstraints(oldLabel,1,1);
		newLabel = new Label("New Password");
		GridPane.setConstraints(newLabel,1,4);
		confLabel = new Label("Confirm Password");
		GridPane.setConstraints(confLabel,1,7);
		mainLabel.setAlignment(Pos.CENTER);

		//Setting the TextFields
		oldText = new PasswordField();
		oldText.setPromptText("Old Pass");
		GridPane.setConstraints(oldText,1,2);

		newText = new PasswordField();
		newText.setPromptText("New Pass");
		GridPane.setConstraints(newText,1,5);

		confPass = new PasswordField();
		confPass.setPromptText("Confirm Pass");
		GridPane.setConstraints(confPass,1,8);

		//Setting the hBox
		hBox.getChildren().add(submit);
		hBox.setPadding(new Insets(0,0,0,45));

		//Submit Button
		submit.setOnAction(e -> {
			try
			{
				//Setting the Current User ID
				currentUserId = remoteBank.checkForProfile(selectedUser);

				/**
				 * Checking the old password with the stored password then confirming that the new passwords match.
				 */
				if(remoteBank.checkPass(currentUserId, toHex(oldText.getText())))
				{
					if(newText.getText().equals(confPass.getText()))
					{
						if(newText.getText().length() > 4 && newText.getText().length() < 13
														  && confPass.getText().length() > 4
														  && confPass.getText().length() < 13)
						{
							remoteBank.changePass(currentUserId, toHex(confPass.getText()));

							if(remoteBank.checkPass(currentUserId, toHex(confPass.getText())))
							{
								Popup.Alert("Success!", "Password Changed!", "Close", 1);
								secondaryWindow.hide();
							}
							else
								Popup.Alert("Password Error!", "There has been a/nCritical Error!\nTry Again!", "Close", 2);
						}
						else
							Popup.Alert("Password Length Error!", "Passwords Must be Between\n5 and 12 Characters!", "Close", 2);
					}
					else
						Popup.Alert("Password Error!", "Passwords Do Not Match!", "Close", 1);
				}
				else
					Popup.Alert("Password Error", "Current Password is Wrong!", "Close", 1);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		//Password Field Action
		oldText.setOnAction(e -> submit.fire());
		newText.setOnAction(e -> submit.fire());
		confPass.setOnAction(e -> submit.fire());

		//GridPane
		grid.getChildren().addAll(oldLabel,newLabel,confLabel,oldText,newText,confPass);
		grid.setPadding(new Insets(10,10,10,10));
		grid.setAlignment(Pos.CENTER);

		//BorderPane
		border.setTop(mainLabel);
		border.setCenter(grid);
		border.setBottom(hBox);
		border.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(mainLabel, Pos.CENTER);
		BorderPane.setAlignment(grid, Pos.CENTER);
		BorderPane.setAlignment(hBox, Pos.CENTER);

		//Setting the Scene
		scene = new Scene(border,400,400);

		//Setting the Stage
		secondaryWindow.setOnCloseRequest(e -> secondaryWindow.hide());
		secondaryWindow.setScene(scene);
		secondaryWindow.setMaxHeight(320);
		secondaryWindow.setMinHeight(320);
		secondaryWindow.setMaxWidth(200);
		secondaryWindow.setMinWidth(200);
		secondaryWindow.showAndWait();
	}

	/**
	 * This method will initiate the scene for showing the accounts with a specific searched balance
	 * @param account - An Array List of accounts that have been found under the current users profile,
	 * with a specific search parameter.
	 */
	static void showBalanceScene(ArrayList<Account> account)
	{
		ListView<String> listView = new ListView<>();
		BorderPane border = new BorderPane();
		VBox listBox;
		HBox bottomBox = new HBox();
		Button close = new Button("Close");
		Scene scene;
		Label label = new Label("-Select an Account for More Information-");

		//Setting the List
		for(int i = 0;i < account.size();i++)
			listView.getItems().add(account.get(i).getAccType());

		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.setMaxWidth(340);
		listView.setMaxHeight(340);

		//Set list into box
		listBox = new VBox();
		listBox.getChildren().add(listView);
		listBox.setPadding(new Insets(5,3,5,10));

		//Editing Label
		label.setPadding(new Insets(5,3,3,10));
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setAlignment(label, Pos.CENTER);

		//Setting the close button
		close.setOnAction(e -> secondaryWindow.hide());
		bottomBox.getChildren().addAll(close);
		BorderPane.setAlignment(bottomBox, Pos.CENTER);
		bottomBox.setPadding(new Insets(5,5,5,5));

		//Setting the BorderPane
		border.setTop(label);
		border.setLeft(listBox);
		border.setBottom(bottomBox);

		//Setting the scene
		scene = new Scene(border,400,400);

		//Use this to set the on-cick for list
		listView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {
		      public void changed(ObservableValue<? extends String> observable,
		              String oldValue, String newValue) {
		    	  int i = listView.getSelectionModel().getSelectedIndex();
		    	  Popup.Alert("Account Info", "ID:" + (i+1) + "\n\n" + account.get(i).toString(), "Close", 3);
		    	  secondaryWindow.hide();
		      }
        });

		//Setting the Stage
		secondaryWindow.setScene(scene);
		secondaryWindow.setMaxWidth(265);
		secondaryWindow.setMinWidth(265);
		secondaryWindow.setMinHeight(360);
		secondaryWindow.setMaxHeight(360);
	}

	/**
	 * This method will initiate the Search balance scene which searches all accounts for
	 * a specific balance then filters those accounts based on the currently logged in user.
	 * The found accounts that belong to the user will be placed into an Array List,
	 * that will be passed into the Show Balance scene.
	 */
	static void searchBalanceScene()
	{
		BorderPane border = new BorderPane();
		TextField text = new TextField();
		Label label = new Label("Input the Search Balance");
		Button search = new Button("Search");
		Scene scene;

		//Setting the search button
		search.setOnAction(e -> {
			if(menuOpt(false,text.getText()))
			{
				try {
					//Searches all accounts for a specific balance
					Account [] account1 = new Account[remoteBank.search(optD,currentUserAccount).length];
					ArrayList<Account> account2 = new ArrayList<Account>();
					account1 = remoteBank.search(optD,currentUserAccount);
					for(int i = 0;i < account1.length;i++)
					{
						//If accounts with the current user name are found, place them into an array list
						if(account1[i].getFullName().equalsIgnoreCase(selectedUser))
							account2.add(account1[i]);
					}

					if(account2.size() > 0)
						showBalanceScene(account2);
					else
					{
						Popup.Alert("Search Error!", "No Accounts with That Balance!", "Close", 2);
						secondaryWindow.hide();
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		//Setting the Border
		border.setTop(label);
		border.setCenter(text);
		border.setBottom(search);

		//Launch the search
		text.setOnAction(e -> search.fire());

		//Setting up the scene
		scene = new Scene(border,400,400);

		//Setting the stage
		secondaryWindow.setScene(scene);
		secondaryWindow.setOnCloseRequest(e -> secondaryWindow.hide());
		secondaryWindow.setTitle("Search for A Balance!");
		secondaryWindow.setMaxWidth(200);
		secondaryWindow.setMinWidth(200);
		secondaryWindow.setMaxHeight(170);
		secondaryWindow.setMinHeight(170);
		secondaryWindow.showAndWait();
	}

	/**
	 * This method initiates the Remove Account Scene
	 * @param id - The selected ID of the currently selected user
	 * @param type - The type of account that is being removed (CHQ,SAV,GIC)
	 */
	static void removeAccountScene(int id,String type)
	{
		//Requests for the user to confirm if the account should be deleted
		if(Popup.Confirm("Removing an Account!", "    All Removals are Final!\nRemove "
							+ type + " Account - ID:" + (id+1) + "?", "Yes", "No"))
		{
			try
			{
				//Attempt to remove an account
				Account account = remoteBank.removeAccount(currentUserAccount[id].getAccountNumber());
				currentUserAccount = remoteBank.searchByAccountName(selectedUser);
				Popup.Alert("Removal Successful!", "\n" + type + " - ID:" + (id +1) + "\n" + account + "\nAccount Removed!", "Close", 3);
				listAccountScene();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		else
			Popup.Alert("Removal Failure!", "Removal of "+ type + " Account - ID:" + (id+1) + "Failed!", "Close", 1);
	}

	/**
	 * This method initiates the open/add an account scene
	 */
	static void openAccountScene()
	{
		ListView<String> listView = new ListView<>();
		BorderPane border = new BorderPane();
		VBox listBox = new VBox();
		HBox bottomBox = new HBox();
		Button close = new Button("Close");
		Scene scene;
		Label label = new Label("-Select the Type of-\n-Account for Creation-");

		//Setting the List
		listView.getItems().addAll("CHQ","SAV","GIC");
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.setMaxWidth(340);
		listView.setMaxHeight(340);

		//Set list into box
		listBox.getChildren().add(listView);
		listBox.setPadding(new Insets(5,3,5,10));

		//Editing Label
		label.setPadding(new Insets(5,3,3,10));
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setAlignment(label, Pos.CENTER);

		//Use this to set the on-cick for list
		listView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {
		      public void changed(ObservableValue<? extends String> observable,
		              String oldValue, String newValue) {
		    	  accountType = listView.getSelectionModel().getSelectedItem();
		    	if(accountType.equals("CHQ"))
		    		setCHQScene();
		    	else if(accountType.equals("SAV"))
		    		setSAVScene();
		    	else if(accountType.equals("GIC"))
		    		setGICScene();
		      }
        });

		//Setting the close button
		close.setOnAction(e -> secondaryWindow.close());
		bottomBox.getChildren().addAll(close);
		BorderPane.setAlignment(bottomBox, Pos.CENTER);
		bottomBox.setPadding(new Insets(5,5,5,5));

		//Setting the BorderPane
		border.setTop(label);
		border.setLeft(listBox);
		border.setBottom(bottomBox);

		//Setting the scene
		scene = new Scene(border,400,400);

		//Setting the Secondary Window
		secondaryWindow.setOnCloseRequest(e -> secondaryWindow.hide());
		secondaryWindow.setTitle("Open a New Account!");
		secondaryWindow.setScene(scene);
		secondaryWindow.setMaxWidth(265);
		secondaryWindow.setMinWidth(265);
		secondaryWindow.setMinHeight(360);
		secondaryWindow.setMaxHeight(360);
		secondaryWindow.showAndWait();
	}

	/**
	 * This method will set the scene for adding a new Chequing Account
	 */
	static void setCHQScene()
	{
		//Setting the input Labels
		Label initialBalanceText,serviceChargeText,maxTransText;
		TextField initialBalanceField,serviceChargeField,maxTransField;
		ListView<String> listView = new ListView<>();
		Scene scene;
		BorderPane border = new BorderPane();
		VBox listBox = new VBox();
		HBox bottomBox = new HBox();
		GridPane buttonPane = new GridPane();
		Button submit = new Button("Submit");
		Button close = new Button("Close");
		Label label = new Label("-Select the Type of-\n-Account for Creation-");

		//Setting GridPane
		buttonPane.setPadding(new Insets(10,10,10,10));
		buttonPane.setVgap(5);
		buttonPane.setHgap(8);

		//Set list into box
		listBox.getChildren().add(listView);
		listBox.setPadding(new Insets(5,3,5,10));

		//Editing Main Label
		label.setPadding(new Insets(5,3,3,10));
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setAlignment(label, Pos.CENTER);

		//Setting the List
		listView.getItems().addAll("CHQ","SAV","GIC");
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.getSelectionModel().select(0);

		//Use this to set the on-cick for list
		listView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {
		      public void changed(ObservableValue<? extends String> observable,
		              String oldValue, String newValue) {
		              //set the if ACCOUNTTYPE then SHOW specific SCENE for the right panel BORDERPANE
		    	  accountType = listView.getSelectionModel().getSelectedItem();
		    	if(accountType.equals("SAV"))
		    		setSAVScene();
		    	if(accountType.equals("GIC"))
		    		setGICScene();
		      }
        });

		//Setting the bottomBox
		bottomBox.getChildren().addAll(close,submit);
		BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);
		bottomBox.setPadding(new Insets(5,5,5,5));
		bottomBox.setSpacing(335);

		//Setting Initial Balance Settings
		initialBalanceText = new Label("Initial Balance");
		GridPane.setConstraints(initialBalanceText, 2, 1);
		initialBalanceField = new TextField();
		initialBalanceField.setPromptText("$");
		GridPane.setConstraints(initialBalanceField,2,2);

		//Setting the Service Charge
		serviceChargeText = new Label("Service Charge");
		GridPane.setConstraints(serviceChargeText,2,4);
		serviceChargeField = new TextField();
		serviceChargeField.setPromptText("$");
		GridPane.setConstraints(serviceChargeField,2,5);

		//Setting the Maximum Number of Allowed Transactions
		maxTransText = new Label("Maximum Number of \nAllowed Transactions");
		GridPane.setConstraints(maxTransText,2,7);
		maxTransField = new TextField();
		maxTransField.setPromptText("maximum");
		GridPane.setConstraints(maxTransField,2,8);

		//Adding Elements to GridPane
		buttonPane.getChildren().addAll(initialBalanceText,serviceChargeText,maxTransText
											,initialBalanceField,serviceChargeField,maxTransField);

		//Setting the close button
		close.setOnAction(e -> secondaryWindow.close());

		//Setting the submit button
		submit.setOnAction( e -> {
			if(menuOpt(false,initialBalanceField.getText())
					&& initialBalanceField.getText().length() > 0)
			{
				balance = optD;

				if(menuOpt(false,serviceChargeField.getText())
						&& serviceChargeField.getText().length() > 0)
				{
					serviceCharge = optD;

					if(menuOpt(true,maxTransField.getText())
							&& maxTransField.getText().length() > 0)
					{
						maxTrans = optI;

						try
						{
							//Attempt to add a Chequing account
							remoteBank.addAccount(new Chequing(selectedUser, new Integer(accountNumber).toString()
													, balance, serviceCharge, maxTrans));
							accountNumber++;
							Popup.Alert("Success!", "Chequing Account Added!", "Close", 1);
							secondaryWindow.hide();
							currentUserAccount = remoteBank.searchByAccountName(selectedUser);
							listAccountScene();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
					else
						Popup.Alert("Maximum Transactions Error!", "The Maximum of Allowed Transactions "
																	+ "\nMust be an Integer!", "Close", 2);
				}
				else
					Popup.Alert("Service Charge Error!", "Service Charge must be a Number! \nDecimals are allowed!", "Close", 2);
			}
			else
				Popup.Alert("Balance Error!", "Balance must be a Number! \nDecimals are allowed!", "Close", 2);
		});

		//Setting Action Event for Text Fields
		initialBalanceField.setOnAction(e -> submit.fire());
		serviceChargeField.setOnAction(e -> submit.fire());
		maxTransField.setOnAction(e -> submit.fire());

		//Setting the BorderPane
		border.setCenter(buttonPane);
		border.setTop(label);
		border.setLeft(listBox);
		border.setBottom(bottomBox);

		//Setting the scene
		scene = new Scene(border,500,500);

		//Setting the secondary stage
		secondaryWindow.setScene(scene);
		secondaryWindow.setMaxWidth(470);
		secondaryWindow.setMinWidth(470);
		secondaryWindow.setMinHeight(361);
		secondaryWindow.setMaxHeight(361);
	}

	/**
	 * This method will set the scene for adding a new Savings Account
	 */
	static void setSAVScene()
	{
		//Setting the input Labels
		Label initialBalanceText,annInterestRateText;
		TextField initialBalanceField,annInterestRateField;
		ListView<String> listView = new ListView<>();
		Scene scene;
		BorderPane border = new BorderPane();
		VBox listBox = new VBox();
		HBox bottomBox = new HBox();
		GridPane buttonPane = new GridPane();
		Button submit = new Button("Submit");
		Button close = new Button("Close");
		Label label = new Label("-Select the Type of-\n-Account for Creation-");

		//Setting GridPane
		buttonPane.setPadding(new Insets(10,10,10,10));
		buttonPane.setVgap(5);
		buttonPane.setHgap(8);

		//Set list into box
		listBox.getChildren().add(listView);
		listBox.setPadding(new Insets(5,3,5,10));

		//Editing Main Label
		label.setPadding(new Insets(5,3,3,10));
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setAlignment(label, Pos.CENTER);

		//Setting the List
		listView.getItems().addAll("CHQ","SAV","GIC");
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.getSelectionModel().select(1);

		//Use this to set the on-cick for list
		listView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {
		      public void changed(ObservableValue<? extends String> observable,
		              String oldValue, String newValue) {
		    	  accountType = listView.getSelectionModel().getSelectedItem();
		    	if(accountType.equals("CHQ"))

		    		setCHQScene();
		    	if(accountType.equals("GIC"))
		    		setGICScene();
		      }
        });

		//Setting the bottomBox
		bottomBox.getChildren().addAll(close,submit);
		BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);
		bottomBox.setPadding(new Insets(5,5,5,5));
		bottomBox.setSpacing(335);

		//Setting Initial Balance Settings
		initialBalanceText = new Label("Initial Balance");
		GridPane.setConstraints(initialBalanceText, 2, 1);
		initialBalanceField = new TextField();
		initialBalanceField.setPromptText("$");
		GridPane.setConstraints(initialBalanceField,2,2);

		//Setting the Annual Interest Rate Settings
		annInterestRateText = new Label("Annual Interest Rate");
		GridPane.setConstraints(annInterestRateText,2,4);
		annInterestRateField = new TextField();
		annInterestRateField.setPromptText("$");
		GridPane.setConstraints(annInterestRateField,2,5);

		//Adding Elements to GridPane
		buttonPane.getChildren().addAll(initialBalanceText,annInterestRateText
											,initialBalanceField,annInterestRateField);

		//Setting the close button
		close.setOnAction(e -> secondaryWindow.close());

		//Setting the submit button
		submit.setOnAction( e -> {
			if(menuOpt(false,initialBalanceField.getText())
					&& initialBalanceField.getText().length() > 0)
			{
				balance = optD;

				if(menuOpt(false,annInterestRateField.getText())
						&& annInterestRateField.getText().length() > 0)
				{
					interest = optD;

					try
					{
						//Attempt to add a Savings account
						remoteBank.addAccount(new Savings(selectedUser, new Integer(accountNumber).toString()
												, balance, interest));
						accountNumber++;
						Popup.Alert("Success!", "Savings Account Added!", "Close", 1);
						secondaryWindow.hide();
						currentUserAccount = remoteBank.searchByAccountName(selectedUser);
						listAccountScene();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				else
					Popup.Alert("Interest Error!", "Annual Interest Rate must be a Number! "
													+ "\nDecimals are allowed!", "Close", 2);
			}
			else
				Popup.Alert("Balance Error!", "Balance must be a Number! \nDecimals are allowed!", "Close", 2);
		});

		//Setting Action Events for the Text Fields
		initialBalanceField.setOnAction(e -> submit.fire());
		annInterestRateField.setOnAction(e -> submit.fire());

		//Setting the BorderPane
		border.setCenter(buttonPane);
		border.setTop(label);
		border.setLeft(listBox);
		border.setBottom(bottomBox);

		//Set the scene
		scene = new Scene(border,500,500);

		//Sets the secondary stage
		secondaryWindow.setScene(scene);
		secondaryWindow.setMaxWidth(470);
		secondaryWindow.setMinWidth(470);
		secondaryWindow.setMinHeight(361);
		secondaryWindow.setMaxHeight(361);
	}

	/**
	 * This method will set the scene for adding a new GIC account
	 */
	static void setGICScene()
	{
		//Setting the input Labels
		Label initialBalanceText,yrsOfInvText,annInterestRateText;
		TextField initialBalanceField,yrsOfInvField,annInterestRateField;
		ListView<String> listView = new ListView<>();
		Scene scene;
		BorderPane border = new BorderPane();
		VBox listBox = new VBox();
		HBox bottomBox = new HBox();
		GridPane buttonPane = new GridPane();
		Button submit = new Button("Submit");
		Button close = new Button("Close");
		Label label = new Label("-Select the Type of-\n-Account for Creation-");

		//Setting GridPane
		buttonPane.setPadding(new Insets(10,10,10,10));
		buttonPane.setVgap(5);
		buttonPane.setHgap(8);

		//Set list into box
		listBox.getChildren().add(listView);
		listBox.setPadding(new Insets(5,3,5,10));

		//Editing Main Label
		label.setPadding(new Insets(5,3,3,10));
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setAlignment(label, Pos.CENTER);

		//Setting the List
		listView.getItems().addAll("CHQ","SAV","GIC");
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listView.getSelectionModel().select(2);

		//Use this to set the on-cick for list
		listView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {
		      public void changed(ObservableValue<? extends String> observable,
		              String oldValue, String newValue) {
		    	  accountType = listView.getSelectionModel().getSelectedItem();
		    	if(accountType.equals("CHQ"))
		    		setCHQScene();
		    	if(accountType.equals("SAV"))
		    		setSAVScene();
		      }
        });

		//Setting the bottomBox
		bottomBox.getChildren().addAll(close,submit);
		BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);
		bottomBox.setPadding(new Insets(5,5,5,5));
		bottomBox.setSpacing(335);

		//Setting Initial Balance Settings
		initialBalanceText = new Label("Initial Balance");
		GridPane.setConstraints(initialBalanceText, 2, 1);
		initialBalanceField = new TextField();
		initialBalanceField.setPromptText("$");
		GridPane.setConstraints(initialBalanceField,2,2);

		//Setting the Years of Investment
		yrsOfInvText = new Label("Years of Investment");
		GridPane.setConstraints(yrsOfInvText,2,4);
		yrsOfInvField = new TextField();
		yrsOfInvField.setPromptText("number of years");
		GridPane.setConstraints(yrsOfInvField,2,5);

		//Setting the Maximum Number of Allowed Transactions
		annInterestRateText = new Label("Annual Interest Rate");
		GridPane.setConstraints(annInterestRateText,2,7);
		annInterestRateField = new TextField();
		annInterestRateField.setPromptText("$");
		GridPane.setConstraints(annInterestRateField,2,8);

		//Adding Elements to GridPane
		buttonPane.getChildren().addAll(initialBalanceText,yrsOfInvText,annInterestRateText
											,initialBalanceField,yrsOfInvField,annInterestRateField);

		//Setting the close button
		close.setOnAction(e -> secondaryWindow.close());

		//Setting the submit button
		submit.setOnAction( e -> {
			if(menuOpt(false,initialBalanceField.getText())
					&& initialBalanceField.getText().length() > 0)
			{
				balance = optD;

				if(menuOpt(true,yrsOfInvField.getText())
						&& yrsOfInvField.getText().length() > 0)
				{
					yearsOfInv = optI;

					if(menuOpt(false,annInterestRateField.getText())
							&& annInterestRateField.getText().length() > 0)
					{
						interest = optD;

						try
						{
							//Attempt to add a GIC account
							remoteBank.addAccount(new GIC(selectedUser, new Integer(accountNumber).toString()
													, balance, yearsOfInv, interest));
							accountNumber++;
							Popup.Alert("Success!", "GIC Account Added!", "Close", 1);
							secondaryWindow.hide();
							currentUserAccount = remoteBank.searchByAccountName(selectedUser);
							listAccountScene();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
					else
						Popup.Alert("Interet Error!", "Annual Interest Rate must be a Number! "
								                         + "\nDecimals are allowed ", "Close", 2);
				}
				else
					Popup.Alert("Investment Error!", "Years of Investment must \nbe an Integer!", "Close", 2);
			}
			else
				Popup.Alert("Balance Error!", "Balance must be a Number! \nDecimals are allowed!", "Close", 2);
		});

		//Setting Action Event for Text Fields
		initialBalanceField.setOnAction(e -> submit.fire());
		yrsOfInvField.setOnAction(e -> submit.fire());
		annInterestRateField.setOnAction(e -> submit.fire());

		//Setting the BorderPane
		border.setCenter(buttonPane);
		border.setTop(label);
		border.setLeft(listBox);
		border.setBottom(bottomBox);

		scene = new Scene(border,500,500);

		secondaryWindow.setScene(scene);
		secondaryWindow.setMaxWidth(470);
		secondaryWindow.setMinWidth(470);
		secondaryWindow.setMinHeight(361);
		secondaryWindow.setMaxHeight(361);
	}

	/**
	 * This method will set and return the formatted string for the name of the account holder.
	 * @param nameSet - nameSet is an array containing the first name and last name respectively.
	 * @return This method will return the formatted name string.
	 */
	static String setFormatName(String [] nameSet)
	{
		StringBuffer fullName = new StringBuffer("");

		nameSet[0] = nameSet[0].trim();
		nameSet[1] = nameSet[1].trim();

		fullName.append(nameSet[1]).append(", ").append(nameSet[0]);

		return fullName.toString();
	}

	/**
	 * This method will determine if the argument check is an Integer, checked through the function parseDouble().
	 * @param check - This string is the user input that has already been made and is being now checked if it is an Integer.
	 * @return boolean - True if check is an Integer | False if check is not an Integer
	 */
	static boolean menuChoiceI(String check)
	{
		boolean menu = false;

		//Check, check for appropriate user integer input
		try
		{
			optI = Integer.parseInt(check);
			menu = true;
		}
		catch (NumberFormatException e)
		{
			menu = false;
		}
		return menu;
	}

	/**
	 * This method will determine if the argument check is a Double, checked through the function parseDouble().
	 * @param check - This string is the user input that has already been made and is being now checked if it is a Double.
	 * @return boolean - True if check is an Double | False if check is not an Double
	 */
	static boolean menuChoiceD(String check)
	{
		boolean menu = false;

		//Check, check for appropriate user double input
		try
		{
			optD = Double.parseDouble(check);
			menu = true;
		}
		catch (NumberFormatException e)
		{
			menu = false;
		}
		return menu;
	}

	/**
	 * This method will be used to call the methods to check if the inputed information (check)
	 * is an Integer or a Double.
	 * @param intOrDub - True if check is an integer - False if check is a double
	 * @param check - Check is a string that is being passed through that is being checked
	 * @return boolean - will return the boolean result of whether the conversion was successful,
	 * which comes form the methods menuChoiceI and menuChoiceD.
	 */
	static boolean menuOpt(boolean intOrDub,String check)
	{
		if(intOrDub)
			return menuChoiceI(check);
		else
			return menuChoiceD(check);
	}
}
