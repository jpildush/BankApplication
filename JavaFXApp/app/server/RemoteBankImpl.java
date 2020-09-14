package app.server;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import app.common.*;

/**
 * <h1>Assgnment 2 - RemoteBankImpl</h1>
 * This RemoteBankImpl class would implement the 
 * methods for the RemoteBank class.
 * <p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */

public class RemoteBankImpl extends UnicastRemoteObject
							implements RemoteBank
{
	private ArrayList<Account> acc;
	private ArrayList<UserProfile> userProfile;
	private String bankName;
	private static final long serialVersionUID = 1L;

	/**
	 * Bank String Argument Constructor which Initializes the Bank Name and Creates a List of Accounts
	 * @param name
	 * @throws RemoteException
	 */
	protected RemoteBankImpl(String name) throws RemoteException {
		//Confirms that the String Argument being passed in, is NOT null
	     if(name!=null)
	     {
	       this.bankName=name;
	       this.acc = new ArrayList<Account>();
	       this.userProfile =new ArrayList<UserProfile>(); 
	     }
	     else
	     {
	       //If the String Argument IS null
	       this.bankName="";
	       this.acc=new ArrayList<Account>();
	       this.userProfile =new ArrayList<UserProfile>(); 
	     }
	}
 	
 	/**
 	 * Adding an Account (src) to the current Bank.
 	 * @param src - source object that is being passed into the account
 	 * @return boolean - true if account has been added, false if account has not been added
 	 * @throws RemoteException
 	 */
	public boolean addAccount(Account src) throws RemoteException {
		if(src!=null){
	      for(int i=0;i<this.acc.size();i++){
	        //Confirms that the source Account (src) is not within the current Banks account list.
	        if(src==this.acc.get(i))
	          return false;
	      }
	      //Source Account (src) is added to the current Banks accounts list.
	      if(src instanceof Chequing)
	    	  this.acc.add((Chequing)src);
		  else if(src instanceof Savings)
		   	  this.acc.add((Savings)src);
		  else if(src instanceof GIC)
			  this.acc.add((GIC)src);
	      return true;
	    }
	    return false;
	}

	/**
	 * Removing Account from Bank
	 * @param num - the account number that is being used to track down the account that needs removing
	 * @return Account - will return an instance of the account that has been removed
	 * @throws RemoteException
	 */
	public Account removeAccount(String num) throws RemoteException {
		Account ret=null;
	    //Confirms that the source string num is not null
	    if(num!=null)
	    {
	      for(int i=0;i<this.acc.size();i++)
	      {
	        /*Attempts to Obtain the correct Account within the current bank (acc) based on the source
	         *accounts, account number (num). If found, that account will be removed from the account list
	         *of the current bank
	         */
	        if(this.acc.get(i)!=null && this.acc.get(i).getAccountNumber().equals(num)){
	          ret=new Account(this.acc.get(i).getFullName(),this.acc.get(i).getAccountNumber(),this.acc.get(i).getBalance());
	          this.acc.remove(this.acc.get(i));
	        }
	      }
	      return ret;
	    }
	    return ret;
	}
	
	/**
	 * Searching through the current Banks Accounts for Certain Balance (bal)
	 * @param bal - the balance that is being used to track down all accounts with that balance
	 * @return Account[] - will return an array of all accounts that has been found with the same balance (bal)
	 * @throws RemoteException
	 */
	public Account[] search(double bal,Account[] account) throws RemoteException {
		int countin_=0;
		Account [] tempArray = new Account[countin_];
	    for(int i=0;i<account.length;i++)
	    {
	    	if(Double.compare(account[i].getBalance(),bal) == 0)
	    	{
    			tempArray = Arrays.copyOf(tempArray,tempArray.length + 1);
	    		tempArray[countin_] = account[i];
	    		countin_++;		
	    	}
	    }
	    return tempArray;
	}

	/**
     * This method will search through the account list (acc) for accounts with the same account holder's name (accountName)
     * @param accountName - the desired account holder's full name
     * @return Account[] - will return an array of accounts with the desired account holder's full name
     * @throws RemoteException
     */
	public Account[] searchByAccountName(String accountName) throws RemoteException {
		int countin_=0;
		Account [] tempArray = new Account[countin_];
	    for(int i=0;i<this.acc.size();i++)
	    {
	    	if(this.acc.get(i).getFullName().compareToIgnoreCase(accountName.trim()) == 0)
	    	{
	    		tempArray = Arrays.copyOf(tempArray,tempArray.length + 1);
    			tempArray[countin_] = acc.get(i);
	    		countin_++;
	    	}
	    }   
	    return tempArray;
	}

	/**
     * This method is used to return the Bank's name
     * @return String - the bank name
     * @throws RemoteException
     */
	public String getBankName() throws RemoteException {
		  return this.bankName;
	}

	/**
     * This method is used to return an account's Account Number
     * @param id - the id of the account within the array list (acc)
     * @return String - will return the account number
     * @throws RemoteException
     */
	public String getAccNumber(int id) throws RemoteException {
		  return acc.get(id).getAccountNumber().trim();
	}

	/**
     * This method is used to return an account holder's full name
     * @param id - the id of the account within the array list (acc)
     * @return  String - will return the account holder's full name
     * @throws RemoteException
     */
	public String getAccName(int id) throws RemoteException {
		  return acc.get(id).getFullName();
	}

	/**
     * Get the ID of a specific account number
     * @param number - the account number being searched for
     * @return int - will return the ID of the account with the proper account number
     * @throws RemoteException
     */
	public int getAccById(String number) throws RemoteException {
		for(int i=0;i < acc.size();i++)  
		{
			if(acc.get(i).getAccountNumber().equals(number))
				return i;
		}
		return -1;
	}
	
	/**
	 * This method will cross reference the user name with the userProfile list
	 * @param user - the desired user name 
	 * @return int - THe ID of the desired user
	 */
	public int checkForProfile(String user) throws RemoteException
	{

		if(userProfile.size() > 0)
		{
			for(int i = 0; i < userProfile.size();i++){
				if(userProfile.get(i).getName().equalsIgnoreCase(user))
					return i;
			}
			return -1;
		}
		else
			return -1;
	}
	
	/**
	 * This method will add a new profile to the array list userProfile
	 * @param user - the user name that is being set
	 * @param pass - the password that is being set
	 * @throws RemoteException
	 */
	public void addProfile(String user,String pass) throws RemoteException{
		userProfile.add(new UserProfile(user,pass));
	}
	
	/**
	 * This method will return the password for the desired user
	 * @param user - the user name that is being used for checking the password
	 * @param pass - the password that is being set
	 * @return boolean - True if password match | False is password do NOT match
	 * @throws RemoteException
	 */
	public boolean checkPass(int user,String pass) throws RemoteException
	{
		for(int i = 0; i < userProfile.size();i++){
			if(userProfile.get(user).getPass().equals(pass))
				return true;		
		}
		return false;
	}
	
	/**
	 * This method will change the password for the desired user id
	 * @param id - the user id for the change of password
	 * @param newPass - the new password that is being set
	 * @throws RemoteException
	 */
	public void changePass(int id, String newPass) throws RemoteException{
		//for(int i = 0; i < userProfile.size();i++)
		//{
			//if(userProfile.get(i).getName().equals(user))
			//{
				userProfile.get(id).changePass(newPass);
				//break;
			//}
		//}
	}
	
}
