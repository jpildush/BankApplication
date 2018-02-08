package BankApp.common;

/**
 * <h1>UserProfile</h1>
 * This Class is intended for the creation of a user's profile (name and password)
 * @author Joseph Pildush
 * @since 04-10-2017
 */
public class UserProfile {
	private String userName;
	private String passHex;

	/**
	 * This constructor will set the user's name and password
	 * @param user - the desired user name
	 * @param pass - the desired password (in hex format)
	 */
	public UserProfile(String user,String pass)
	{
		userName = user;
		passHex = pass;
	}

	/**
	 * This method is meant for getting the user's user name.
	 * @return String - will return the user's user name
	 */
	public String getName(){
		 return userName;
	}

	/**
	 *  This method is used for getting the user's password.
	 * @return String - the user's password
	 */
	public String getPass() {
	    return passHex;
	}

	/**
	 * This method is used to change the user's password
	 * @param newPass - the new password that is being passed through
	 */
	public void changePass(String newPass){
		 passHex = newPass;
	}
}
