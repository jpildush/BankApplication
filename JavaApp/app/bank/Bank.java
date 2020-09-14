package app.bank;
/**
 * <h1>Assgnment 1 - FinancialApp - Bank</h1>
 * The Bank class would create an instance of a Bank
 * which would hold an instance of an Account List.
 * <p>
 * @author Joseph Pildush
 * @since 03-10-2017
 */

//Importing Libraries
 import java.util.*;
 import app.accounts.*;

//Bank Class
 public class Bank{
   private ArrayList<Account> acc;
   private String bankName;

/**
 * Bank Default Constructor
 */
   public Bank(){
     this("Seneca@York");
   }

/**
 * Bank String Argument Constructor which Initializes the Bank Name and Creates a List of Accounts
 * @param name
 */
   public Bank(String name)
   {
     //Confirms that the String Argument being passed in, is NOT null
     if(name!=null){
       this.bankName=name;
       this.acc=new ArrayList<Account>();
     }
     else{
       //If the String Argument IS null
       this.bankName="";
       this.acc=new ArrayList<Account>();
     }
   }

   	/**An override of the toString() function so that when the class gets called - System.out.println(Bank) -
     *it will print out the entire contents of the class, including the list of accounts of that bank.
     *@return String - Will return the override of the toString method
 	 */
 	@Override
 	public String toString() 
 	{
 		StringBuffer tempString = new StringBuffer("Bank: ");
 					 tempString.append(this.bankName).append(" - Number of Accounts: ").append(this.acc.size()).append("\n");
 		for(int i=0;i<this.acc.size();i++)
 			tempString.append("Account: ").append(i+1).append(", Account Information: ").append(this.acc.get(i)).append(",\n");
	    return tempString.toString();
 	}

 	/**
 	 * The equals method compares the current Banks Name with the selected object(the bank),
 	 * ignoring case as well as confirming that the selected .
 	 * @param obj - the object that is being checked for equality
 	 * @return boolean - True if Equal, False if Unequal 
 	 */
  public boolean equals(Object obj)
  {
	  boolean ret = false;
	  if(obj instanceof Bank)
	  {
      //Passes the obj into an instance of a bank.
      Bank src=(Bank)obj;
      //Confirmation that the newly created bank (src) is not null
      if(src!=null){
        /*Confirmation that the source object's bank name (src.bankName) is not null,
         *Confirmation that the current banks (bankName) name is the same as the object's bank name.
         *Ignoring case.
         */
        if(this.bankName.equalsIgnoreCase(src.bankName))
        {
          for(int i=0;i<this.acc.size();i++)
          {
            //Checking if All accounts within the current bank (acc) is identical to the source objects accounts (src.acc).
            if(this.acc.get(i)==src.acc.get(i))
              ret=true;
            else
            {
              ret = false;
              break;
            }
          }
          //Checking if current bank (acc) and source object (src.acc) have no accounts
          if(this.acc.size()==0 && src.acc.size()==0)
            ret=true;
          //If ret is true, the current bank has the same name (ignoring case) and the same accounts as the source object (src)
          if(ret==true)
            return ret;
        }
        else
          return false;
      }
      return false;
	  }
	  else
		  return false;
  }

/**
 * Adding an Account (src) to the current Bank.
 * @param src - source object that is being passed into the account
 * @return boolean - true if account has been added, false if account has not been added
 */
  public boolean addAccount(Account src){
    if(src!=null){
      for(int i=0;i<this.acc.size();i++){
        //Confirms that the source Account (src) is not within the current Banks account list.
        if(src==this.acc.get(i)){
          return false;
        }
      }
      //Source Account (src) is added to the current Banks accounts list.
      this.acc.add(src);
      return true;
    }
    return false;
  }

/**
 * Removing Account from Bank
 * @param num - the account number that is being used to track down the account that needs removing
 * @return Account - will return an instance of the account that has been removed
 */
  public Account removeAccount(String num){
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
 */
  public Account [] search(double bal)
  {
	  int countin_=0;
	  Account [] tempArray = new Account[countin_];
	    for(int i=0;i<this.acc.size();i++)
	    {
	    	if(this.acc.get(i).getBalance() == bal)
	    	{
    			tempArray = Arrays.copyOf(tempArray,tempArray.length + 1);
	    		tempArray[countin_] = acc.get(i);
	    		countin_++;		
	    	}
	    }
	    
	    return tempArray;
  }

/**
 * Get All Accounts from a Bank
 * @return Account[] - Will return an array of accounts that represent the account list (acc)
 */
  public Account [] getAllAccounts(){
    Account [] ret;
    if(this.acc.size()==0){
      //Creates a return account array size of 0 if the current banks account array list has no accounts
      ret=new Account[0];
      return ret;
    }
    else{
      //Creates an account array the size of the accounts, of the current banks account array list
      ret=new Account[this.acc.size()];
      for(int i=0;i<this.acc.size();i++){
        //Passes all existing accounts from the current banks account array list to the return account array
        if(this.acc.get(i)!=null)
          ret[i]=this.acc.get(i);
      }
      return ret;
    }
  }

  /**
   * This method will search through the account list (acc) for accounts with the same account holder's name (accountName)
   * @param accountName - the desired account holder's full name
   * @return Account[] - will return an array of accounts with the desired account holder's full name
   */
  public Account[] searchByAccountName(String accountName)
  {
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
   */
  public String getBankName()
  {
	  return this.bankName;
  }
  
  /**
   * This method is used to return an account's Account Number
   * @param id - the id of the account within the array list (acc)
   * @return String - will return the account number
   */
  public String getAccNumber(int id)
  {
	  return acc.get(id).getAccountNumber().trim();
  }
  
  /**
   * This method is used to return an account holder's full name
   * @param id - the id of the account within the array list (acc)
   * @return  String - will return the account holder's full name
   */
  public String getAccName(int id)
  {
	  return acc.get(id).getFullName();
  }
  
  /**
   * This method is used to call the deposit method of a specific account within the account list (acc)
   * @param id - the id of the account within the array list (acc)
   * @param amount - the desired amount for deposit
   */
  public void deposit(int id, double amount)
  {
	  acc.get(id).deposit(amount);
  }
  
  /**
   * This method is used to call the withdraw method of a specific account within the account list (acc)
   * @param id - the id of the account within the array list (acc)
   * @param amount - the desired amount for withdrawal
   */
  public void withdraw(int id, double amount)
  {
	  acc.get(id).withdraw(amount);
  }
  
  /**
   * This method is used for getting a specific account from the account list (acc)
   * @param id - the id of the account within the array list (acc)
   * @return Account - will return an account from the array list (acc)
   */
  public Account getAccById(int id)
  {
	  return acc.get(id);
  }
 }
