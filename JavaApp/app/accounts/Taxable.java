  /**
 * <h1>Assignment 1 - FinancialApp - Taxable</h1>
 * The Taxable class would create a few methods that would be used by accounts that are taxed
 * <p>
 * @author Joseph Pildush
 * @since 03-10-2017
 */

package app.accounts;

public interface Taxable 
{
	/**
	 * This method will calculate the tax for the specific account
	 * @param taxRate
	 */
	void calculateTax(double taxRate);
	
	/**
	 * This method will get the taxed amount 
	 * @return double
	 */
    double getTaxAmount();
    
    /**
     * This method will create a visual Tax Statement for the account
     * @return String
     */
    String createTaxStatement();
}
