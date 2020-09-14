package app.common;

import java.rmi.*;

/**
 * <h1>Assignment 2 - RemoteBank</h1>
 * This Remote Bank Interface gives information of the used methods within the Remote Bank Implementation file.
 * 
 * </p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */
public interface RemoteBank extends Remote{	
	//Adding an Account
	public boolean addAccount(Account src) throws RemoteException;
	
	//Removal of an Account
	public Account removeAccount(String num) throws RemoteException;
	
	//Search for accounts with a specific Balance
	public Account [] search(double bal,Account[] account) throws RemoteException;
	
	//Search for accounts with a specific user name
	public Account[] searchByAccountName(String accountName) throws RemoteException;
	
	//Get the Banks Name
	public String getBankName() throws RemoteException;
	
	//Get the accounts, Accounts number
	public String getAccNumber(int id) throws RemoteException;
	
	//Get the Account Holder's Full Name
	public String getAccName(int id) throws RemoteException;

	//Get the ID of a specific account number
	public int getAccById(String num) throws RemoteException;
	
	//This method will cross reference the user name with the userProfile list
	public int checkForProfile(String user) throws RemoteException;
	
	//This method will add a new profile to the array list userProfile
	public void addProfile(String user,String pass) throws RemoteException;
	
	//This method will return the password for the desired user
	public boolean checkPass(int user,String pass) throws RemoteException;
	
	//This method will change the password for the desired user id
	public void changePass(int id,String newPass) throws RemoteException;
}
