/**
 * 
 */
package app.main;

/**
 * <h1>Assignment 1 - FinancialApp</h1>
 * The FinancialApp class would create an instance of a Bank
 * which loads 6 Accounts into that Bank instance. There are methods
 * within this class that allow creation and deletion of an account, 
 * adjusting of balance of an account, displaying and searching for an account,
 * as well as the tax statement for an account.
 * displaying 
 * <p>
 * @author Joseph Pildush
 * @since 03-10-2017
 */

import app.accounts.*;
import app.bank.*;

import java.text.DecimalFormat;
import java.util.*;

public class FinancialApp {
	//This scanner is used throughout the code.
	private static Scanner input;

	/**
	 * This method will convert an Account obj into an instance of a Chequing, Savings or GIC account
	 * and call its toString method to print out its contents.
	 * @param obj - An account object
	 */
	static void displayAccount(Account obj)
	{
		if(obj instanceof Chequing)
		{
			Chequing account = (Chequing)obj;
			System.out.println(account);
		}
		else if(obj instanceof Savings)
		{
			Savings account = (Savings)obj;
			System.out.println(account);
		}
		else if(obj instanceof GIC)
		{
			GIC account = (GIC)obj;
			System.out.println(account);
		}			
	}
	
	/**
	 * This method will convert an Account obj into an instance of a Savings or GIC account
	 * and call its createTaxStatement method out for display.
	 * @param obj - An account object
	 */
	static void displayStatement(Account obj)
	{
		if(obj instanceof Savings)
		{
			Savings account = (Savings)obj;
			System.out.println(account.createTaxStatement());
		}
		else if(obj instanceof GIC)
		{
			GIC account = (GIC)obj;
			System.out.println(account.createTaxStatement());
		}
	}
	
	/**
	 * This method will load 6 accounts into the bank parameter.
	 * @param bank - An instance of a Bank that has been passed into this method from the main method
	 */
	static void loadBank(Bank bank)
	{
		bank.addAccount(new Chequing("Doe, John","145632",10000,0.20,5));
		bank.addAccount(new Chequing("Ryan, Mary","1456321",6000,0.20,5));
		bank.addAccount(new Savings("Doe, John","145633",10000,0.15));
		bank.addAccount(new Savings("Ryan, Mary","1456322",9000,0.25));
		bank.addAccount(new GIC("Doe, John","145634",6000,2,1.50));
		bank.addAccount(new GIC("Ryan, Mary","1456323",15000,4,2.50));
	}
	
	/**
	 * This method will display all accounts within the Account array parameter as a list of accounts
	 * @param tempArray - An Account array that has been passed from the main method.
	 */
	static void displayListAccounts(Account[] tempArray)
	{
		System.out.println("|------------------------------------------------------------------------------------------|");
		System.out.println("|----------------------------------------List of Accounts----------------------------------|");
		System.out.println("|-ID-|-------------------------------------------------------------------------------------|");
		for(int i=0;i<tempArray.length;i++)
			System.out.println("| " + (i+1) + " |  " + tempArray[i].getAccountNumber() + "  -  "  
									+tempArray[i].getFullName() + " | " + tempArray[i].getAccType() + " | $" 
									+ (new DecimalFormat("###,###,###.##")).format(tempArray[i].getBalance()) + " |");
		System.out.println("|-----------------------------------------------------------------------------------------|");
	}
	
	/**
	 * This method is used for visual purposes only. It is used when the application requires a pause with immediate user input to continue.
	 */
	static void pressEnter()
	{
		input = new Scanner(System.in);
		
		System.out.println();
		System.out.println("...Press Enter to Continue...");
		input.nextLine();
	}
	
	/**
	 * This method is only used for visual appeal. It is used to clear the screen .
	 */
	static void clearScreen()
	{
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
							+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
							+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
							+ "\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	/**
	 * This method will go through continuous user inputs until the user inputs an appropriate Integer, checked through the function parseInt().	
	 * @param set - This boolean determines if check is in use - True = in use | False = not in use -.
	 * @param context - This string is any additional information that needs to be displayed in the output of the error. It can be left as null.
	 * @param check - This string is the user input that has already been made and is being now checked if it is an Integer. If there has not been any user input, this string should be null - not in use -. 
	 * @return This method will return the user's inputed Integer.
	 */
	static int menuChoice(boolean set,String context,String check)
	{
		int menu;
		String stringIn = "";
		input = new Scanner(System.in);

		//While loop meant for continuous input
		while (true) 
		{
			//If set is False, request for user input
			if(!set)
				stringIn = input.next();
			try
			{
				//If set is False, converts strinIn to Integer then breaks while loop
				if(!set)
					menu = Integer.parseInt(stringIn);
				//if set is True, converts the parameter check into an Integer then breaks while loop
				else
					menu = Integer.parseInt(check);
				break;
			} 
			catch (NumberFormatException e)
			{
				//If conversions of stringIn or check fail, the input is going to be requested once more. Set is set to false for this.
				System.out.print("Input is Incorrect! " + (context==null ? "":context) + " Re-enter Now: ");
				set = false;
			}
		}
		return menu;
	}
	
	/**
	 * This method will go through continuous user inputs until the user inputs an appropriate Double, checked through the function parseDouble().	
	 * @param set - This boolean determines if check is in use - True = in use | False = not in use -.
	 * @param context - This string is any additional information that needs to be displayed in the output of the error. It can be left as null.
	 * @param check - This string is the user input that has already been made and is being now checked if it is a Double. If there has not been any user input, this string should be null - not in use -. 
	 * @return This method will return the user's inputed Double.
	 */
	static double menuChoiceD(boolean set,String context, String check)
	{
		double menu;
		String stringIn = "";
		input = new Scanner(System.in);
		
		
		while (true) 
		{
			//If set is False, request for user input
			if(!set)
				stringIn = input.next();
			try
			{
				//If set is False, converts strinIn to Double then breaks while loop
				if(!set)
					menu = Double.parseDouble(stringIn);
				//if set is True, converts the parameter check into a Double then breaks while loop
				else
					menu = Double.parseDouble(check);
				break;
			} 
			catch (NumberFormatException e)
			{
				//If conversions of stringIn or check fail, the input is going to be requested once more. Set is set to false for this.
				System.out.print("Input is Incorrect! " + (context==null ? "":context) + " (Only the Decimal special character is allowed) Re-enter Now: ");
				set = false;
			}
		}
		return menu;
	}
	
	/**
	 * This method will go through continuous user inputs until the user inputs an appropriate Integer. The integer must be between 
	 * 1 and the size of the incoming Account array parameter. There are two options designated by the search boolean.
	 * @param set - True if being used for Tax Statement to set GIC over CHQ
	 * @param account - Account array that is being used to determine if menu is within the size of the Account array and which type of account is being selected.
	 * @param search - This parameter determines if the method is being used for the displayAccounts method which searches through accounts.
	 * @return The return will the integer representing the menu option that the user has selected.
	 */
	static int menuChoiceAcc(boolean set,Account[] account,boolean search)
	{
		//User input
		int menu = menuChoice(false,null,null);
		if(menu == 0)
		{
			//Returns 0 for exit
			return menu;
		}
		else
		{
			//Search will only be true if this method is using the displayAccounts method.
			if(!search)
			{
				while(true)
				{
					//Creates user input to be an array identifier 
					menu = menu - 1;
					if(menu >= 0 && menu < account.length && (account[menu].getAccType().compareTo((set==true ? "GIC":"CHQ")) == 0
																	|| account[menu].getAccType().compareTo("SAV") == 0))
						break;
					else
					{
						System.out.print("Input is Incorrect! (Only " + (set==true ? "GIC":"CHQ") + " and SAV Accounts are Eligible) Re-enter Selection: ");
						//User input
						menu = menuChoice(false,null,null);
					}
				}
			}
			else if(search)
			{
				while(true)
				{
					//Creates menu to be an identifier for an array
					menu = menu - 1;
					if(menu >= 0 && menu < account.length)
						break;
					else
					{
						System.out.print("Input is Incorrect! Re-enter Selection: ");
						//User input
						menu = menuChoice(false,null,null);
					}
				}
			}
		}
		//Returns menu as original user input
		return (menu + 1);
	}
	
	/**
	 * This method will check to see if the name that is being passed in is in the correct format
	 * @param nameSet - nameSet is a string of the name being checked and formatted
	 * @return This method will return the formatted name string.
	 */
	static String setFormatName(String nameSet)
	{
		String fullName;
		String[] name;
		input = new Scanner(System.in);
		
		
		fullName = nameSet;
		do {
			name = fullName.split(",");
			if (name.length == 2)
			{
				name[0] = name[0].trim();			
				name[1] = name[1].trim();
				StringBuffer tempString = new StringBuffer(name[0]).append(", ").append(name[1]);
				fullName = tempString.toString();				
			}
			else 
			{
				System.out.print("Invalid Full Name, please re-enter Full Name Only! (eg. LastName, FirstName): ");
				fullName = input.next();
			}
		} while (name.length != 2);
		
		return fullName;
	}
	
	/**
	 * This method will open a new account and save that account within the bank that has been passed through.
	 * @param bank - Instance of a bank that has been passed through by the main method.
	 */
	static void openAccount(Bank bank)
	{
		String accountType,fullName, number;
		double balance,interest, serviceCharge;
		String[] inputFields;
		int maxTrans, yearsOfInv;
		boolean valid = false;
		String stringIn = "";
		input = new Scanner(System.in);
		
		
		clearScreen();
		//This phase determines if the user would like to create a Chequing, Savings or GIC account.
		System.out.print("Enter the account type now (CHQ/SAV/GIC): ");
		accountType = input.next();
		while(!valid)
		{
			if (accountType.compareToIgnoreCase("CHQ") == 0 
					|| accountType.compareToIgnoreCase("SAV") == 0
					|| accountType.compareToIgnoreCase("GIC") == 0)
				valid = true;
			else
			{
				System.out.print("Invalid Input! Please re-enter your Selection: ");
				accountType = input.next();
			}
		}
		
		valid = false;
		
		//This phase explains to the user the format that the account information needs to be in.
		System.out.println("Please enter account information at one line");
		if(accountType.compareToIgnoreCase("CHQ") == 0)
		{
			System.out.println("-- LastName,FirstName;AccountNumber;Balance;ServiceCharge;MaxAllowedTransactions --");
			System.out.println("(e.g. Doe,John;A1234;1000.00;0.20;5):");
		}
		else if(accountType.compareToIgnoreCase("SAV") == 0)
		{
			System.out.println("-- LastName,FirstName;AccountNumber;Balance;AnnualInterestRate --");
			System.out.println("(e.g. Doe,John;A1234;1000.00;3.65):");
		}
		else if(accountType.compareToIgnoreCase("GIC") == 0)
		{
			System.out.println("-- LastName,FirstName;AccountNumber;Balance;YearsOfInvestment;AnnualInterestRate --");
			System.out.println("(e.g. Doe,John;A1234;1000.00;10;1.75):");
		}
		
		stringIn = input.next();
		
		while(!valid)
		{
			String testBalance;
			
			//This phase will determine if the user inputed information for an account is in the correct format  
			inputFields = stringIn.split(";");
			if(accountType.compareToIgnoreCase("CHQ") == 0 && inputFields.length == 5)
			{
				//This phase calls the menuChoice methods in order to determine if the input for each section of the account is correct. 
				testBalance = inputFields[2];
				//User input for name
				fullName = setFormatName(inputFields[0]);
				//User input for account number
				number = inputFields[1].trim();
				//User input for account balance
				balance = menuChoiceD(true,"Balance",testBalance);
				//User input for this accounts service charge
				serviceCharge = menuChoiceD(true,"Service Charge",inputFields[3]);
				//User input for the maximum of allowed transactions
				maxTrans = menuChoice(true,"Maximum Number of Allowed Transactions",inputFields[4]);
				//When all the information is in the correct format, a new account will be added to bank parameter.
				bank.addAccount(new Chequing(fullName, number, balance, serviceCharge, maxTrans));
				valid = true;
			}
			else if(accountType.compareToIgnoreCase("SAV") == 0 && inputFields.length == 4)
			{
				//This phase calls the menuChoice methods in order to determine if the input for each section of the account is correct. 
				testBalance = inputFields[2];
				//User input for name
				fullName = setFormatName(inputFields[0]);
				//User input for account number
				number = inputFields[1].trim();
				//User input for account balance
				balance = menuChoiceD(true,"Balance",testBalance);
				//User input for interest
				interest = menuChoiceD(true,"Interest",inputFields[3]);
				//When all the information is in the correct format, a new account will be added to bank parameter.
				bank.addAccount(new Savings(fullName, number, balance, interest));
				valid = true;
			}
			else if(accountType.compareToIgnoreCase("GIC") == 0 && inputFields.length == 5)
			{
				//This phase calls the menuChoice methods in order to determine if the input for each section of the account is correct. 
				testBalance = inputFields[2];
				//User input for name
				fullName = setFormatName(inputFields[0]);
				//User input for account number
				number = inputFields[1].trim();
				//User input for account balance
				balance = menuChoiceD(true,"Balance",testBalance);
				//User input for years of investment
				yearsOfInv = menuChoice(true,"Years of Investment",inputFields[3]);
				//User input for annual interest
				interest = menuChoiceD(true,"Annual Interest",inputFields[4]);
				//When all the information is in the correct format, a new account will be added to bank parameter.
				bank.addAccount(new GIC(fullName, number, balance, yearsOfInv, interest));
				valid = true;
			}
			else
			{
				//If there has been an error with the amount of semicolons placed compared to the type of account, the user will have to re-enter the information.
				System.out.println("Input Error! (Chequing & GIC Require 4 semicolons | Savings Requires 3 semicolons ) Please re-enter:");
				stringIn = input.next();
			}
		}
		if(valid)
			System.out.println("\nAccount has been Successfully Added!\n\n");
	}
	
	/**
	 * This method is used to remove accounts from the bank parameter that has been passed form the main method.
	 * @param bank - Instance of a bank that has been passed through by the main method.
	 */
	static void closeAccount(Bank bank)
	{
		String closeInput;
		input = new Scanner(System.in);
		int option;
		boolean menu = true;
		
		//A list of accounts will be displayed
		displayListAccounts(bank.getAllAccounts());
		//The user must decide if they wish to remove an account by the accounts, Account Number or the by the account holders Full name.
		System.out.println("|----------------------------------------------------------------------------------------------------------|");
		System.out.println("| 1 - Close Account by Account Number | 2 - Close Account by Account Name (LastName, FirstName) | 0 - Home |");
		System.out.println("|----------------------------------------------------------------------------------------------------------|");
		System.out.print("|>>>");
		do{
			option = menuChoice(false,null,null);
			switch(option){
			//Case 0 will exit
			case 0:
				menu = false;
				break;
			case 1:
				//Case 1 allows the user to close an account based on the account number
				System.out.println("Enter the Account Number for Closure (type exit to go back): ");
				while(menu)
				{
					//User input
					closeInput = input.next();
					closeInput.trim();
					//This phase will begin comparing the user input to the Account Number on record
					for(int i=0;i<bank.getAllAccounts().length;i++)
					{
						if(bank.getAccNumber(i).compareTo(closeInput) == 0)
						{
							System.out.println(bank.removeAccount(bank.getAccNumber(i)));
							i= i + bank.getAllAccounts().length;
							menu=false;
							option = 1;
							pressEnter();
							clearScreen();
							//Display list of accounts
							displayListAccounts(bank.getAllAccounts());
							break;
						}	
					}		
					if(menu)
						System.out.print("Input Error! Enter Details as Seen on Screen! Re-Enter: ");
					else
						break;
				}
				break;
			case 2:
				//Case 2 allows the user to close the account based on the account holder full name.
				System.out.println("Enter the Account Name for Closure (type exit to go back): ");
				while(menu)
				{
					//Changes input in this case to allow the full name to be entered
					//User input
					closeInput = input.useDelimiter("\n").next();
					closeInput.trim();
					//This phase will determine if any accounts have been found matching the account holder full name that the user inputed.
					Account[] temp = new Account[bank.searchByAccountName(closeInput).length];
					if(temp.length > 1)
					{
						//If there is more then one account that has been found, the application requests the user to use the account number instead
						System.out.println("Too Many Accounts with the Same Name Found! Search By Account Number Instead!");
						menu = false;
						option = 0;
					}
					else if(temp.length == 1)
					{
						//If only one account was found, then that account is removed from the bank.
						temp[0] = bank.searchByAccountName(closeInput)[0];
						System.out.println(bank.removeAccount(temp[0].getAccountNumber()));
						menu=false;	
						System.out.println("\nAccount Successfully Closed!\n");
						pressEnter();
						clearScreen();
						//Display list of accounts
						displayListAccounts(bank.getAllAccounts());
					}
					else
						System.out.print("Input Error! Enter Details as Seen on Screen! Type exit to go Back! Re-Enter: ");
				}
				break;
			default:
				System.out.print("Input Error! Enter 1 or 2! Type exit to go Back! Re-Enter: ");
				menu = true;
				break;
			}
		}while(menu);
	}
	
	/**
	 * This method will deposit a user inputed amount into a user selected account.
	 * @param bank - Instance of a bank that has been passed through by the main method.
	 */
	static void depositMoney(Bank bank)
	{
		double amount;
		int menu;

		clearScreen();
		System.out.println("Select an Account to Deposit Money into...\n\n");
		System.out.println(bank.getBankName());
		//List Accounts
		displayListAccounts(bank.getAllAccounts());
		System.out.println("|----------|");
		System.out.println("| 0 - Home |");
		System.out.println("|----------|");
		System.out.print("|>>>>>>");
		//User Input
		menu = menuChoiceAcc(false,bank.getAllAccounts(),false);
		if(menu != 0)
		{
			//If not exiting
			menu = menu - 1;
			System.out.println("---"+bank.getAccById(menu).getFullName()+" : "+bank.getAccById(menu).getAccType()+"---");
			System.out.println("Selected!");
			pressEnter();
			clearScreen();
			displayAccount(bank.getAccById(menu));
			System.out.print("Input the Amount to Deposit: $");
			//Enter amount for deposit
			amount = menuChoiceD(false,"Amount",null);
			//Deposit amount
			bank.deposit(menu,amount);
			pressEnter();
			clearScreen();
			//Display account details
			displayAccount(bank.getAccById(menu));
		}
	}
	
	/**
	 * This method will withdraw a user inputed amount from a user selected account. If the amount exceeds the amount 
	 * that, that account currently holds then there will be no withdrawal and the user will be informed their selection is incorrect.
	 * This is done within the banks withdraw method.
	 * @param bank - Instance of a bank that has been passed through by the main method.
	 */
	static void withdrawMoney(Bank bank)
	{
		double amount;
		int menu;

		clearScreen();
		System.out.println("Select an Account to Withdraw From...\n\n");
		System.out.println(bank.getBankName());
		displayListAccounts(bank.getAllAccounts());
		System.out.println("|----------|");
		System.out.println("| 0 - Home |");
		System.out.println("|----------|");
		System.out.print("|>>>>>>");
		//User input
		menu = menuChoiceAcc(false,bank.getAllAccounts(),false);
		if(menu != 0)
		{
			//If not exiting
			menu = menu - 1;
			System.out.println("---"+bank.getAccById(menu).getFullName()+" : "+bank.getAccById(menu).getAccType()+"---");
			System.out.println("Selected!");
			pressEnter();
			clearScreen();
			displayAccount(bank.getAccById(menu));
			System.out.print("Input the Amount to Withdraw: $");
			//User input for withdrawal
			amount = menuChoiceD(false,"Amount",null);
			//User withdraws
			bank.withdraw(menu, amount);
			pressEnter();
			clearScreen();
			//Account details are displayed
			displayAccount(bank.getAccById(menu));

		}
	}
	
	/**
	 * This method will request the user to select an account in order to produce a Tax Statement of that account.
	 * Only Taxable accounts are applicable. i.e. - Savings and GIC accounts.
	 * @param bank - Instance of a bank that has been passed through by the main method.
	 */
	static void displayTaxStatement(Bank bank)
	{
		int menu;

		clearScreen();
		System.out.println("Select an Account to View their Tax Statement...\n\n");
		System.out.println(bank.getBankName());
		displayListAccounts(bank.getAllAccounts());
		System.out.println("|----------|");
		System.out.println("| 0 - Home |");
		System.out.println("|----------|");
		System.out.print("|>>>>>>");
		//User input
		menu = menuChoiceAcc(true,bank.getAllAccounts(),false);
		if(menu != 0)
		{
			//If not exiting
			menu = menu - 1;
			System.out.println("---"+bank.getAccById(menu).getFullName()+" : "+bank.getAccById(menu).getAccType()+"---");
			System.out.println("Selected!");
			pressEnter();
			clearScreen();
			//Display Statement
			displayStatement(bank.getAccById(menu));
		}
	}
	
	/**
	 * This method is used to initiate a search for a specific account based on the account's balance or the account holder's full name
	 * @param bank - Instance of a bank that has been passed through
	 */
	static public void searchAccounts(Bank bank)
	{
		int option;
		boolean menu = true;
		
		System.out.println("|---------------------------------------------------------------------------------------------------------------------------|");
		System.out.println("| 1 - Search for Accounts with Specific Balance | 2 - Search by Account Holder's Full Name (LastName, FirstName) | 0 - Home |");
		System.out.println("|---------------------------------------------------------------------------------------------------------------------------|");
		System.out.print("|>>>");
		while(menu){
			option = menuChoice(false,null,null);
			switch(option){
			//Case 0 will exit the program
				case 0:
					menu = false;
					break;
				case 1:
					double amount;
					//Case 1 allows the user to search for accounts with the same balance
					System.out.print("Enter the Balance to Search For: ");
					amount = menuChoiceD(false,null,null);
					Account[] temp = bank.search(amount);
					if(temp.length > 0)
					{
						System.out.println("Accounts Have Been Found!");
						pressEnter();
						clearScreen();
						//List accounts
						displayListAccounts(temp);
						//The user has an option to view more details of the accounts found
						System.out.println(">>>Select Account ID for Account Details or 0 for Home: ");
						option = menuChoiceAcc(false,temp,true);
						if(option != 0)
						{
							option = option - 1;
							System.out.println("---"+temp[option].getFullName()+" : "+temp[option].getAccType()+"---");
							System.out.println("Selected!");
							pressEnter();
							clearScreen();
							//Display account details
							displayAccount(temp[option]);
							menu = false;
							break;
						}
						else
							menu = false;
					}
					else
						System.out.println("No Accounts with the Balance of $"+amount+" Found!");
					break;
				case 2:
					String closeInput;
					input = new Scanner(System.in);
					//Case 2 allows the user to search for accounts based on the account holder's full name.
					System.out.println("Enter the Account Holder's Full Name: ");
					while(menu)
					{
						closeInput = input.useDelimiter("\n").next();
						closeInput.trim();
						//This phase will determine if any accounts have been found matching the account holder's full name that the user inputed.
						Account[] temp1 = bank.searchByAccountName(closeInput);
						if(temp1.length > 1)
						{
							System.out.println("Accounts Have Been Found!");
							pressEnter();
							clearScreen();
							//List accounts
							displayListAccounts(temp1);
							//The user can select one of the found accounts for more details
							System.out.print(">>>Select Account ID for Account Details or 0 for Home: ");
							option = menuChoiceAcc(false,temp1,true);
							if(option != 0)
							{
								option = option - 1;
								System.out.println("---"+temp1[option].getFullName()+" : "+temp1[option].getAccType()+"---");
								System.out.println("Selected!");
								pressEnter();
								clearScreen();
								//Display details of account
								displayAccount(temp1[option]);
								menu = false;
								break;
							}
							else
								menu = false;
						}
					}
					break;
				default:
					System.out.print("Input Error! Enter 1 or 2! Re-Enter: ");
					menu = true;
					break;
			}
		}
	}
	
	/**
	 * This method will request the user to select an account in order to obtain more details about the selected account.
	 * @param bank - Instance of a bank that has been passed through by the main method.
	 */
	static void displayAccounts(Bank bank)
	{
		int option;
		boolean menu = true;
		
		clearScreen();
		System.out.println("Select an Account for More Details by Selecting the ID or Searching by Account Number or Account Holder's Full Name...\n\n");
		System.out.println(bank.getBankName());
		//Display list of accounts
		displayListAccounts(bank.getAllAccounts());
		System.out.println("|--------------------------------------------------------------------------------------|");
		System.out.println("| 1 - Select Account by ID | 2 - Search for Account by Balance or Full Name | 0 - Home |");
		System.out.println("|--------------------------------------------------------------------------------------|");
		System.out.print("|>>>>>>");
		do{
			//User input
			option = menuChoice(false,null,null);
			switch(option){
			case 0:
				//Case 0 will exit
				menu = false;
				break;
			case 1:
				//Case 1 allows the user to display the account information based on the account id that is generated by the application
				System.out.println("Enter the Account ID: ");
				//User input
				option = menuChoiceAcc(false,bank.getAllAccounts(),true) - 1;
				System.out.println("---"+bank.getAccById(option).getFullName()+" : "+bank.getAccById(option).getAccType()+"---");
				System.out.println("Selected!");
				menu=false;
				pressEnter();
				clearScreen();
				//Display details of account
				displayAccount(bank.getAccById(option));
				break;
			case 2:
				//Case 2 allows the user to get more details of an account based on the account balance or Full Name
				searchAccounts(bank);
				menu = false;
				break;
			default:
				System.out.print("Input Error! Enter 1 or 2! Re-Enter: ");
				menu = true;
				break;
			}
		}while(menu);
	}

	
	/**
	 * This method will display the main menu.
	 * @param bankName - The Bank's Name
	 */
	static void displayMenu(String bankName)
	{
		System.out.println("       ----- Welcome to " + bankName + "Bank -----");
		System.out.println("|-------------------------------------------------------|");
		System.out.println("|1. Open an Account                                     |");
		System.out.println("|2. Close an Account                                    |");
		System.out.println("|3. Deposit Money                                       |");
		System.out.println("|4. Withdraw Money                     		        |");
		System.out.println("|5. Display a Specific Account                          |");
		System.out.println("|6. Display Accounts                                    |");
		System.out.println("|7. Display a Tax Statement      		        |");
		System.out.println("|8. Exit                         	                |");
		System.out.println("|-------------------------------------------------------|");
		System.out.print(">>>Input an Option: ");
	}
	
	/**
	 * This is the main method of the Financial Application. A menu is called and the user is able to chose from the menu options.
	 */
	public static void main(String[] args) {
		int menu;
		//Creates an instance of a Bank (bank)
		Bank bank = new Bank();
		//Bank Name
		String bankName = bank.getBankName();
		//Loads accounts into the bank
		loadBank(bank);
		
		do{
			//Displays the menu
			displayMenu(bankName);
			//user Input
			menu = menuChoice(false,null,null); 
			switch (menu) {
			//Case 1 will open a New Account
			case 1:
				System.out.println("After this step, the process MUST be completed!");
				System.out.print("Enter 0 NOW for Home or 1 to Continue: ");
				//user Input
				menu = menuChoice(false,null,null);
				switch(menu){
				//Case 0 will exit
					case 0:
						pressEnter();
						clearScreen();
						break;
				//Case 1 will continue
					case 1:
						clearScreen();
						openAccount(bank);
						pressEnter();
						clearScreen();
						break;
				}
				break;
			//Case 2 will remove/close an Account
			case 2:
				clearScreen();
				closeAccount(bank);
				pressEnter();
				clearScreen();
				break;
			//Case 3 will deposit money into a user selected account
			case 3:
				clearScreen();
				depositMoney(bank);
				pressEnter();
				clearScreen();
				break;
			//Case 4 will withdraw money from a user selected account
			case 4:
				clearScreen();
				withdrawMoney(bank);
				pressEnter();
				clearScreen();
				break;
			//Case 5 will display all accounts with the ability for the user to search for a specific account
			case 5:
				clearScreen();
				displayAccounts(bank);
				pressEnter();
				clearScreen();
				break;
			//Case 6 will only display all the accounts
			case 6:
				clearScreen();
				displayListAccounts(bank.getAllAccounts());
				pressEnter();
				clearScreen();
				break;
			//Case 7 will display the Tax Statement of a user selected account
			case 7:
				clearScreen();
				displayTaxStatement(bank);
				pressEnter();
				clearScreen();
				break;
			//Case 8 will stop the menu and close the program
			case 8:
				pressEnter();
				clearScreen();
				System.out.println("Thank you for using " + bankName + " Bank!\n Come Back Again!\n");
				pressEnter();
				clearScreen();
				break;
			//If input is incorrect, the case will switch to this method
			default:
				System.out.println();
				System.out.println("Input is Incorrect! Please re-enter your Selection!");
				pressEnter();
				clearScreen();
				break;
			}
		/* While menu does not equal 8, the loop will continue. If menu equals to 8, the loop will stop
		 * and the application will terminate. 
		 */
		} while (menu != 8);
	}

}
