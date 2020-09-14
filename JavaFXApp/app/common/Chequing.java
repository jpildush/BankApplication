package app.common;
/**
 * <h1>Assignment 2 - Chequing</h1>
 * The Chequing class would create an instance of a Chequing account.
 * The methods within this class uses many methods from the Account class.
 * This class extends the Account class
 * <p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class Chequing extends Account{
	private static final long serialVersionUID = 1L;
	private BigDecimal serviceCharge,totalCharge;
	private int maxNumTrans,transCount;
	private double[] transArray;
	
	/**
	 * Default constructor for Chequing
	 */
	public Chequing() {
		this("","",0,0.25,3);
	}
	
	/**
	 * 5 parameter constructor for Chequing.
	 * @param fullName - the account holder's full name
	 * @param accNum - account number
	 * @param accBal - account balance
	 * @param sCharge - service charge
	 * @param maxTrans - maximum allowed transactions
	 */
	public Chequing(String fullName,String accNum,double accBal,double sCharge,int maxTrans)
	{
		super(fullName,accNum,accBal);
		
		if(sCharge >= 0)
			this.serviceCharge = new BigDecimal(sCharge);
		else
			this.serviceCharge = new BigDecimal(0.25);
		
		if(maxTrans > 0)
			this.maxNumTrans = maxTrans;
		else
			this.maxNumTrans = 3;
		
		this.transArray = new double[this.maxNumTrans];
		this.totalCharge = new BigDecimal(0);
		this.transCount = 0;
	}
	
	/**
	 * Override of the equals method.
	 * @param obj - this is the source object that is being used to check for equality
	 * @return boolean - true is equal, false if unequal
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj instanceof Chequing)
		{
			Chequing temp = (Chequing)obj; 				
			if(this.transArray.length == temp.transArray.length)
			{
				for(int i=0;i<this.transArray.length;i++)
				{
					if(this.transArray[i] != temp.transArray[i])
						return false;
				}
			}
			else
				return false;
			
			if (obj != null 
					&&super.equals(temp) 
					&& this.serviceCharge.compareTo(temp.serviceCharge) == 0 
					&& this.totalCharge.compareTo(temp.totalCharge) == 0 
					&& this.maxNumTrans == temp.maxNumTrans
					&& this.transCount == temp.transCount)			
				return true;

		}
		else
			return false;
		
		return false;
	}
	
	/**
	 * Override of the toString method
	 * @return String - A string of this accounts display method
	 */
	@Override
	public String toString() 
	{
		DecimalFormat listTransFormat = new DecimalFormat("+#,##0.00;-#");
		StringBuffer tempString = new StringBuffer(super.toString());
				  tempString.append("Type: CHQ\n")
							.append("Service Charge: $").append(super.financeFormat.format(this.serviceCharge))
							.append("\nTotal Service Charges: $").append(super.financeFormat.format(this.totalCharge))
							.append("\nNumber of Transactions Allowed: ").append(this.maxNumTrans)
							.append("\nList of Transactions: ");
		for(int i = 0;i < transCount;i++)
		{
			tempString.append(listTransFormat.format(transArray[i]));
			if(i != (transCount-1))
				tempString.append(", ");
			else
				tempString.append("\n");
		}
		tempString.append("\nFinal Balance: $").append(super.financeFormat.format(this.getBalance())).append("\n\n\n");
		return tempString.toString();
	}
	
	/**
	 * override of the hashCode method
	 * @return int
	 */
	@Override
    public int hashCode() 
	{
		int hash = super.hashCode();
		hash = 31 * hash + (int)(Double.doubleToLongBits(this.serviceCharge.doubleValue())
														^ (Double.doubleToLongBits(this.serviceCharge.doubleValue()) >>> 32));
		hash = 31 * hash + (int)(Double.doubleToLongBits(this.totalCharge.doubleValue())
														^ (Double.doubleToLongBits(this.serviceCharge.doubleValue()) >>> 32));
		hash = 31 * hash + this.maxNumTrans;
		hash = 31 * hash + this.transCount;
		hash = 31 * hash + this.transArray.hashCode();
				
		return hash;
    }
	
	/**
	 * Override of the super's deposit method. This method will ensure that the given amount is greater then 0 and ensure that the user has 
	 * not exceeded their maximum allowed of transactions.
	 * @param amount - the user inputed amount for deposit
	 */
	@Override
	public void deposit (double amount) 
	{
		if(amount > 0)
		{
			if(this.transCount < this.maxNumTrans) 
			{
				amount = amount - serviceCharge.doubleValue();
				this.accBal = this.accBal.add(new BigDecimal(amount));
				this.totalCharge = this.totalCharge.add(serviceCharge);
				this.transArray[this.transCount++] = amount;
				System.out.println(">>>>$"+amount+" - Successfully Deposited!");
			}
			else
				System.out.println("Maximum Number of Transactions Exceeded!");
				
		}
	}
	
	/**
	 * Override of the super's withdraw method. This method will ensure that that given amount is greater then 0 and 
	 * ensure that the user has not exceeded their maximum allowed of transactions.
	 */
	@Override
	public boolean withdraw(double amount) {
		if (amount > 0)
		{
			double temp = this.accBal.doubleValue();
			temp = temp - amount - serviceCharge.doubleValue();
			if(temp >= 0)
			{
				if(this.transCount < this.maxNumTrans)
				{
					this.accBal = new BigDecimal(temp);
					this.totalCharge = this.totalCharge.add(serviceCharge);
					this.transArray[transCount++] = (-amount);
					System.out.println(">>>>$"+amount+" - Successfully Withdrawn!");
					return true;
				}
				else
					System.out.println("Maximum Number of Transactions Exceeded!");
			}
			else
				return false;
		}
		else
			return false;
		return false;
	}
	
	/**
	 * Override of the getBalance method. This method makes gives better access to the getBalance method for the main method
	 * @return double - the balance
	 */
	@Override
	public double getBalance(){
		return super.getBalance();
	}
	
	/**
	 * Override of the getAccType method so that it will return this accounts type
	 * @return String - will return CHQ
	 */
	@Override
	public String getAccType(){
		return "CHQ";
	}
	
	/**
	 * This method will return the total charges in this account
	 * @return String - a formatted version of the total charges
	 */
	public String getTotalServiceCharge(){
		return super.financeFormat.format(this.totalCharge);
	}
	
	/**
	 * This method will return the maximum of allowed transactions for this account
	 * @return int - the maximum of allowed transactions
	 */
	public int getMaxTransactions(){
		return this.maxNumTrans;
	}
	
	/**
	 *This method will return this account's current number of transactions 
	 * @return int - how many transactions were made
	 */
	public int getTransactionCount(){
		return this.transCount;
	}
}
