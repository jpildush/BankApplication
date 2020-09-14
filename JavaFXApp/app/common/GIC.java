package app.common;
/**
 * <h1>Assignment 2 - GIC</h1>
 * The GIC class would create an instance of an GIC account.
 * This class would implement many methods from the Account class.
 * This class extends the Account class.
 * <p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */

import java.math.BigDecimal;

public class GIC extends Account implements Taxable{
	private static final long serialVersionUID = 1L;
	private int yearsOfInv;
	private BigDecimal interestIncome,taxOnInterest,annInterestRate;
	protected BigDecimal accBal,balanceAtMaturity;
	
	/**
	 * Default constructor for the GIC class
	 */
	public GIC(){
		this("","",0,1,1.25);
	}
	
	/**
	 * 5 parameter constructor
	 * @param fullName - account holder's full name
	 * @param accNum - account number
	 * @param accBal - account balance
	 * @param yearsOfInv - years of investment
	 * @param annIntRate - annual interest rate
	 */
	public GIC(String fullName,String accNum,double accBal,int yearsOfInv,double annIntRate)
	{
		super(fullName,accNum,accBal);
		
		if(accBal >= 0)
			this.accBal = new BigDecimal(accBal);
		else
			this.accBal = new BigDecimal(0);
		if(yearsOfInv >= 0)
			this.yearsOfInv = yearsOfInv;
		else
			this.yearsOfInv = 0;
		if(annIntRate >= 0)
			this.annInterestRate = new BigDecimal(annIntRate);
		else
			this.annInterestRate = new BigDecimal(annIntRate);
		this.interestIncome = new BigDecimal(0);
		this.taxOnInterest = new BigDecimal(0);
		this.balanceAtMaturity = new BigDecimal(0);
		this.calculateTax(15);
	}

	/**
	 * Override of the equals method
	 * @param obj - the source object used to determine equality
	 * @return boolean - true if equal, false if unequal
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj instanceof GIC)
		{
			GIC temp = (GIC)obj; 
			if (obj != null 
					&& super.equals(temp) 
					&& this.yearsOfInv == temp.yearsOfInv 
					&& this.taxOnInterest.compareTo(temp.taxOnInterest) == 0 
					&& this.annInterestRate.compareTo(temp.annInterestRate) == 0
					&& this.interestIncome.compareTo(temp.interestIncome) == 0
					&& this.balanceAtMaturity.compareTo(temp.balanceAtMaturity) == 0)				
				return true;
			else
				return false;
		}
		else 
			return false;		
	}
	
	/**
	 * Override toString method
	 * @return String - will return the accounts toString method 
	 */
	@Override
	public String toString() 
	{
		StringBuffer tempString = new StringBuffer(super.toString());
					 tempString.append("Type: GIC\n")
					 		   .append("Annual Interest Rate: ").append(super.financeFormat.format(this.annInterestRate)).append("%\n")
					 		   .append("Period of Investment: ").append(this.yearsOfInv).append("years\n")
					 		   .append("Interest Income at Maturity: ").append(super.financeFormat.format(this.interestIncome)).append("\n")
					 		   .append("Balance at Maturity: ").append(super.financeFormat.format(this.getBalance())).append("\n\n\n");
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
		hash = 31 * hash + this.yearsOfInv;
        return hash;
    }
	
	/**
	 * Override of the super's deposit method. This is done because a deposit is not allowed in the GIC account
	 * @param amount - the amount for deposit
	 */
	@Override
	public void deposit (double amount){
		return;
	}
	
	/**
	 * Override of the super's withdraw method. This is done because a withdrawal is not allowed in the GIC account
	 * @param amount - the amount for withdrawal
	 * @return boolean - will always return false to signify that a withdrawal failed
	 */
	@Override
	public boolean withdraw(double amount){
		return false;
	}
	
	/**
	 * Override of the super's getBalance method. This is done so that the Balance at Maturity may be returned instead
	 * @return double - Balance at Maturity
	 */
	@Override
	public double getBalance() {
		return this.balanceAtMaturity.doubleValue();
	}
	
	/**
	 * Override of the calculateTax method. This is done so that the appropriate taxes may be calculated and applied for this account
	 * @param taxRate - this is the tax rate that is being used
	 */
	@Override
	public void calculateTax(double taxRate) {
		BigDecimal tRate = new BigDecimal(taxRate).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP);
		BigDecimal intRate = this.annInterestRate.divide(new BigDecimal(100)).add(new BigDecimal(1)).pow(this.yearsOfInv);
		
		this.balanceAtMaturity = super.accBal.multiply(intRate);		
		this.interestIncome = this.balanceAtMaturity.subtract(this.accBal);
		this.taxOnInterest = this.interestIncome.multiply(tRate);
	}

	/**
	 * Override of the getTaxAmount method. This is done so that this accounts taxed amount will be returned (taxOnInterest)
	 * @return double - the amount of tax
	 */
	@Override
	public double getTaxAmount() {
		return Double.parseDouble(super.financeFormat.format(this.taxOnInterest.doubleValue()));
	}

	/**
	 * Override of the createTaxStatement method so that the appropriate tax statement will be created for this account
	 * @return String - the visual tax statement
	 */
	@Override
	public String createTaxStatement() 
	{
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
	 * Override of the getAccType method. This will return the appropriate account type
	 * @return String - this will return GIC
	 */
	@Override
	public String getAccType(){
		return "GIC";
	}

}
