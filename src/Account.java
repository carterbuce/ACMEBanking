import java.text.DecimalFormat;

/** Account.java
 * @author Carter Buce | cmb9400
 * 
 * Version:
 * 		$Id: Account.java,v 1.8 2015/11/06 04:18:37 cmb9400 Exp $
 * 
 * Revisions:
 * 		$Log: Account.java,v $
 * 		Revision 1.8  2015/11/06 04:18:37  cmb9400
 * 		removed unnecessary methods
 *
 * 		Revision 1.7  2015/11/02 01:16:16  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.6  2015/11/01 21:43:31  cmb9400
 * 		removed static from variable declaration
 *
 * 		Revision 1.5  2015/11/01 19:54:17  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.4  2015/11/01 05:03:45  cmb9400
 * 		fixed more methods
 *
 * 		Revision 1.3  2015/11/01 01:44:08  cmb9400
 * 		optimized the class to have more methods
 *
 * 		Revision 1.2  2015/10/31 22:20:42  cmb9400
 * 		defined account methods
 *
 * 		Revision 1.1  2015/10/31 18:30:28  cmb9400
 * 		Initial Commit.
 *
 * 
 */


public abstract class Account {
	
	private double balance;
	private double minimum;
	private int pin;
	private int id;
	private char acctType;
	
	
	public Account(int id, int pin, double bal){
		this.balance = bal;
		this.pin = pin;
		this.id = id;
	}
	
	
	/**
	 * set the account type
	 * @param c the account type
	 */
	public void setAcctType(char c){
		acctType = c;
	}
	
	
	/**
	 * get the account type
	 * @return 
	 * @return the account type character
	 */
	public char getAcctType(){
		return acctType;
	}
	
	
	/**
	 * get the account pin
	 * @return the account pin
	 */
	public int getPin(){
		return pin;
	}
	
	
	/**
	 * set the minimum balance
	 * @param i the minimum balance
	 */
	public void setMin(int i){
		minimum = i;
	}
	
	
	/**
	 * checks if the entered pin is equivalent to the account's pin
	 * @param enteredPin the pin the user at the ATM / bank entered
	 * @return true if the user entered the account's pin
	 */
	public boolean login(int enteredPin){
		return enteredPin == pin; 
	}
	
	
	/**
	 * get the id of the account 
	 * @return the account's id
	 */
	public int getId(){
		return id;
	}
	
	
	/**
	 * deposit money into the account's balance
	 * @param amount the amount to deposit
	 * @return true if the balance was deposited
	 */
	public boolean deposit(double amount){
		balance += amount;
		return true;
	}
	
	
	/**
	 * withdraw money from the account
	 * @param amount the amount to withdraw
	 * @return false if the balance is too low to withdraw from
	 */
	public boolean withdraw(double amount){
		if(amount >= balance - minimum){
			return false;
		}
		else{
			balance -= amount;
			return true;
		}
	}
	
	
	/**
	 * get balance of the account
	 * @return amount of money in the account
	 */
	public double getBalance(){
		return balance;
	}
	
	
	/**
	 * get minimum balance of the account
	 * @return minimum balance allowed
	 */
	public double getMin(){
		return minimum;
	}
	
	
	/**
	 * get minimum balance of the account
	 * @return minimum balance
	 */
	public double getMinimum(){
		return minimum;
	}
	
	
	/**
	 * apply interest earnings/penalties to the account
	 * @return the amount that was added/deducted 
	 */
	public abstract double applyInterest();
	
	
	/**
	 * make the account into a string so it can be stored into a data file
	 * @return string representation of the account
	 */
	public String toString(){
		DecimalFormat df = new DecimalFormat("#.00");
		return (acctType + " " + id + " " + pin + " " + df.format(balance));
	}
	
}
