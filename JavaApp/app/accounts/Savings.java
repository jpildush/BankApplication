package app.accounts;
/**
* <h1>Assignment 1 - FinancialApp - Savings</h1>
* The Savings class would create an Account of type Savings.
* This class extends account and is taxable
* <p>
* @author Joseph Pildush
* @since 03-10-2017
*/

import java.math.BigDecimal;

public class Savings extends Account implements Taxable{
	private BigDecimal interestIncome;
	private BigDecimal taxOnInterest;
	private BigDecimal annInterestRate;
	
	/**
	 * Default Constructor for Savings
	 */
	public Savings (){
		this("","",0,0.10);
	}
	
	/**
	 * Savings constructor which takes in 4 parameters
	 * @param fullName - the full name of the account holder
	 * @param accNum - the account number
	 * @param accBal - the account balance
	 * @param annIntRate - the annual interest rate
	 */
	public Savings(String fullName,String accNum,double accBal,double annIntRate)
	{
		super(fullName,accNum,accBal);
		
		this.interestIncome = new BigDecimal(0);
		this.taxOnInterest = new BigDecimal(0);
		if(annIntRate >= 0)
			this.annInterestRate = new BigDecimal(annIntRate);
		else
			this.annInterestRate = new BigDecimal(0.10);
		this.calculateTax(15);
	}
	
	/**
	 * An override of the equals method which will take in an object (obj) and determine if This is the same as the source object
	 * @param obj - the source object
	 * @return boolean - will true if equal, false if unequal
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj instanceof Savings)
		{
			Savings temp = (Savings)obj; 
			if (obj != null 
					&&super.equals(temp) 
					&& this.interestIncome.compareTo(temp.interestIncome) == 0 
					&& this.taxOnInterest.compareTo(temp.taxOnInterest) == 0 
					&& this.annInterestRate.compareTo(temp.annInterestRate) == 0)			
				return true;
		}
		else 
			return false;
		return false;
		
	}
	
	/**
	 * This is the override of the toString method
	 * @return String
	 */
	@Override
	public String toString() 
	{
		StringBuffer tempString = new StringBuffer (super.toString());
		tempString.append("Type: SAV\n")
				  .append("Interest Rate: ").append(super.financeFormat.format(annInterestRate)).append("%\n")
				  .append("Interest Income: $").append(super.financeFormat.format(this.interestIncome))
				  .append("\nFinal Balance: $").append(super.financeFormat.format(this.getBalance())).append("\n\n\n");
		return tempString.toString();
	}
	
	/**
	 * Override of the hashCode method
	 * @return int
	 */
	@Override
    public int hashCode() 
	{
		int hash = super.hashCode();
		hash = 31 * hash + (int)(Double.doubleToLongBits(interestIncome.doubleValue())
									^ (Double.doubleToLongBits(interestIncome.doubleValue()) >>> 32));
		hash = 31 * hash + (int)(Double.doubleToLongBits(taxOnInterest.doubleValue())
									^ (Double.doubleToLongBits(taxOnInterest.doubleValue()) >>> 32));
		hash = 31 * hash + (int)(Double.doubleToLongBits(annInterestRate.doubleValue()) 
									^ (Double.doubleToLongBits(annInterestRate.doubleValue()) >>> 32));
        return hash;
    }
	
	/**
	 * This override of the getBalance method is meant to tax the balance.
	 * @return double - the balance
	 */
	@Override
	public double getBalance() 
	{
		this.calculateTax(15);
		BigDecimal accBal = new BigDecimal(super.getBalance());
		BigDecimal sumBal = new BigDecimal(accBal.add(this.interestIncome).doubleValue());
		return sumBal.doubleValue();
	}
	
	/**
	 * This is override of the calculateTax method for savings
	 * @param taxRate - the tax rate  
	 */
	@Override
	public void calculateTax(double taxRate) 
	{
		BigDecimal tRate = new BigDecimal(taxRate);
		BigDecimal accBal = new BigDecimal(super.getBalance());
		BigDecimal intRate = this.annInterestRate.divide(new BigDecimal(100));
		
		this.interestIncome = accBal.multiply(intRate);
		
		if (this.interestIncome.doubleValue() > 50) 
		{
			intRate = tRate.divide(new BigDecimal(100));
			this.taxOnInterest = this.interestIncome.multiply(intRate);
		}
	}
	
	/**
	 * The taxed amount
	 * @return double
	 */
	@Override
	public double getTaxAmount(){
		return taxOnInterest.doubleValue();
	}
	
	/**
	 * Will create the tax statement for this account for the user to see
	 * @return String
	 */
	@Override
	public String createTaxStatement() 
	{
		this.calculateTax(15);
		
		StringBuffer tempString = new StringBuffer("");
		tempString.append("---").append(super.getFullName()).append(" : ").append(this.getAccType()).append("---\n\n\n")
				  .append(super.toString())
				  .append("Tax rate: 15%\n")
				  .append("Account Number: ").append(super.getAccountNumber())
				  .append("\nInterest Income: $").append(super.financeFormat.format(this.interestIncome))
				  .append("\nAmount of tax: $").append(super.financeFormat.format(this.taxOnInterest)).append("\n");
		return tempString.toString();
	}
	
	/**
	 * This will show the specific account type of SAV
	 * @return String - the return will be SAV
	 */
	@Override
	public String getAccType(){
		return "SAV";
	}
	
}
