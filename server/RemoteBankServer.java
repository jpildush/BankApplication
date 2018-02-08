package BankApp.server;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.rmi.RemoteException;

import BankApp.common.*;

/**
 * <h1>RemoteBankServer</h1>
 * This Class is the Remote Bank, Server side. The server side initiates an RMI object and binds
 * that object to a specific port number. This method will also initiate user account and profile information.
 * <p>
 * @author Joseph Pildush
 * @since 04-10-2017
 * </p>
 */
class RemoteBankServer {
	/**
	 * This is the main method that initiates the server
	 */
	public static void main(String[] args) {
		try {
			   System.out.println( "...Remote Bank Server is Running!" );

			   RemoteBankImpl bank = new RemoteBankImpl("Seneca@York");

			   loadBank(bank);
			   System.out.println("Binding remote bank object to rmi registry" );

			   // for localhost only
			   java.rmi.registry.Registry registry =
			        java.rmi.registry.LocateRegistry.createRegistry(5678);


		       registry.rebind("bank",bank);

			   System.out.println( "These remote objects are waiting for clients..." );
		      }catch( Exception e ) {System.out.println( "Error: " + e );}

		      System.out.println( "... The thread is now waiting" );
		      /* a separate thread is started after a remote object has been created */
	}

	/**
	 * This method will convert the string to hex - meant to be used for password encryption
	 * @param string - The password that is being hashed
	 * @return string - Will return a string of the encrypted password
	 */
	private static String toHex(String string) {
	    try {
			return String.format("%x", new BigInteger(1, string.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    return "";
	}

	/**
	 * This method will load 6 accounts into the bank parameter along with 2 profiles for those accounts.
	 * @param bank - An instance of a Bank that has been passed into this method from the main method
	 */
	private static void loadBank(RemoteBankImpl bank)
	{
		//Default password for the default 2 profiles
		String pass = toHex("password");

		//Loading the bank accounts for the two users
		try {
			bank.addAccount(new Chequing("Doe, John","145632",10000,0.20,5));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			bank.addAccount(new Chequing("Ryan, Mary","1456321",6000,0.20,5));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			bank.addAccount(new Savings("Doe, John","145633",10000,0.15));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			bank.addAccount(new Savings("Ryan, Mary","1456322",9000,0.25));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			bank.addAccount(new GIC("Doe, John","145634",6000,2,1.50));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			bank.addAccount(new GIC("Ryan, Mary","1456323",15000,4,2.50));
		} catch (RemoteException e) {
			e.printStackTrace();
		}


		//Load Profiles of the two users
		try {
			bank.addProfile("Ryan, Mary", pass);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			bank.addProfile("Doe, John", pass);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		//Setting the default password back to blank
		pass = "";

	}

}
