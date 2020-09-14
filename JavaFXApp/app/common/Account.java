package app.common;
  /**
 * <h1>Assignment 2 - Account</h1>
 * The Account class would create an instance of an Account to be used by a bank.
 * There are multitude of methods designed for this class to be utilized efficiently 
 * <p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	//Private variables for the class created
	private String name,fname,lname;
	private String accNum;
	protected BigDecimal accBal;
	DecimalFormat financeFormat;

	/**
	 * Default constructor which would set the private variables within the class to a default state
	 */
	public Account(){
		this(null,null,0);
	}

	/**
	 * A 3-Argument Constructor which takes in a the account holders Name, Account Number and Account Balance and sets it to the current object
	 * @param name - account holder's name
	 * @param accNum - account number
	 * @param accBal - account balance
	 */
	public Account(String name,String accNum,double accBal)
	{
		financeFormat = new DecimalFormat("#,###.##");
		//If name or account number is null, the objects name and account number will be set to null
		if(name==null){
			this.fname="";
			this.lname="";
			this.name="";
		}
		//Otherwise trim spaces and pass name onto this objects Name
		else
			this.name=name.trim();
		//Will set this object's accNum to an empty string if the accNum is null 
		if(accNum==null)
			this.accNum="";
		//Otherwise will trim the accNum and pass it into this object's accNum
		else
			this.accNum=accNum.trim();
		//Setting this object's accBal to incoming accBal if the incoming accBal is 0 or greater
		if(accBal>=0)
			this.accBal=new BigDecimal(accBal);
		//Otherwise will set this object's accBal to 0
		else
			this.accBal=new BigDecimal(0);

		//Checks that name is not an empty string and that name contains a coma
		if(!name.equals("") && name.contains(","))
		{
			//Splits the string by the comas
			String[] temp = name.split(",");

			//If the split array if size of 2, continue and set the name
			if(temp.length == 2)
			{
				this.fname = temp[1].trim();
				this.lname = temp[0].trim();
			}
			//Otherwise sets the name to empty string
			else
			{
				this.name = "";
				this.lname = "";
				this.fname = "";
			}
		}
		//Otherwise sets the name to empty strings
		else{
			this.name = "";
			this.fname = "";
			this.lname = "";
		}
	}
	
	/**
	 * Getter method for this object's Full name
	 * @return String - will return the account holder's full name
	 */
	public String getFullName(){
		return this.name;
	}
	/**
	 * Getter method for this object's First name
	 * @return String - will return the holder's first name
	 */
	public String getFirstName(){
		return this.fname;
	}
	/**
	 * Getter method for this object's  Last name
	 * @return String - will return the holder's last name
	 */
	public String getLastName(){
		return this.lname;
	}
	/**
	 * Getter method for this object's Account number
	 * @return String - will return the account's, Account number
	 */
	public String getAccountNumber(){
		return this.accNum;
	}
	/**
	 * Getter method for this object's Account balance
	 * @return double - will return the account's balance
	 */
	public double getBalance(){
		return this.accBal.doubleValue();
	}

	/**An override of the toString() function so that when the class gets called - System.out.println(Account) -
	*it will print out the entire contents of the class.
	*If the name or the account number is set to null, a blank space will be printed instead.
	*@return String - will return the the override toString method
	*/
	@Override
	public String toString() 
	{
		StringBuffer tempString = new StringBuffer("\nName: ");
					 tempString.append((this.lname == null ? "" + (this.fname == null ? "" : this.fname) 
																					 : this.lname + (this.fname == null ? "" : ", " + this.fname)))
							   .append("\nNumber: ").append((this.accNum != null ? this.accNum: ""))
							   .append("\nCurrent Balance: $").append(this.financeFormat.format(this.accBal)).append("\n");
		return tempString.toString();
	}
	
	/**
	 * Calculates the Hash Code for the Account Objects
	 */
	@Override
	public 	int hashCode( )
	{
        int hash = 18;
		double bal = accBal.doubleValue();
        hash = 31 * hash + this.name.hashCode();
        hash = 31 * hash + this.fname.hashCode();
        hash = 31 * hash + this.lname.hashCode();
        hash = 31 * hash + this.accNum.hashCode();
        hash = 31 * hash + (int)(Double.doubleToLongBits(bal) ^ (Double.doubleToLongBits(bal) >>> 32));
        return hash;
	}
	
	/**
	 * This will compare the objects Name, Account Number and Account Balance with the
	 * Name, Account Number and Account Balance from source(src).
	 * @param obj - the source object that is being checked for equality
	 * @return boolean - true if equal, false if unequal
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Account)
		{
			Account src=(Account)obj;
			if(src!=null && this.name.equals(src.name) && this.accNum.equals(src.accNum) && this.accBal.compareTo(src.accBal) == 0)
				return true;
			else
				return false;
		}
		return false;
	}
	
	/**
	 * Account method which takes out a certain amount of money from this Account object (from the accBal)
	 * @param amount - the user inputed amount for withdrawal
	 * @return boolean - true if withdrawal has occurred , false if the withdrawal has been denied
	 */
	public boolean withdraw(double amount)
	{
		//Ensures that the amount argument is greater then 0
		if(amount > 0)
		{
			//Converts the amount to a BigDecimal
			BigDecimal amnt = new BigDecimal(amount);
			//Will continue to withdraw if the amount is less than the current balance in this object's accBal
			if (amnt.compareTo(this.accBal) == -1) 
			{
				this.accBal = this.accBal.subtract(amnt);
				System.out.println(">>>>$"+amount+" - Successfully Withdrawn!");
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * This Account method allows for the addition of finances to this object's Account Balance (accBal)
	 * @param amount - the user inputed amount that is being used for deposit
	 */
	public void deposit(double amount)
	{
		//Ensures that the amount in this methods argument is greater then 0, otherwise the finances will not be added to the account.
		if (amount > 0) 
		{
			this.accBal = this.accBal.add(new BigDecimal(amount));
			System.out.println(">>>>$"+amount+" - Successfully Deposited!");
		}

	}
	
	/**
	 * A base method that is used when attempting to figure out the type of account that has been created.
	 * The classes Chequing, Savings and GIC override this method
	 * @return String - will return which type of account has been created (CHQ,SAV,GIC)
	 */
	public String getAccType(){
		return null;
	}
}
