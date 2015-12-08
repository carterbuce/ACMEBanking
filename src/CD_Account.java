/** CD_Account.java
 * @author Carter Buce | cmb9400
 * 
 * Version:
 * 		$Id: CD_Account.java,v 1.2 2015/11/02 04:40:07 cmb9400 Exp $
 * 
 * Revisions:
 * 		$Log: CD_Account.java,v $
 * 		Revision 1.2  2015/11/02 04:40:07  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.1  2015/11/02 04:39:07  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.3  2015/11/01 04:28:59  cmb9400
 * 		fixed constructors to take double as balance
 *
 * 		Revision 1.2  2015/11/01 01:44:27  cmb9400
 * 		fixed not using superclass' variables
 *
 * 		Revision 1.1  2015/10/31 22:20:35  cmb9400
 * 		defined abstract methods
 *
 * 
 */

public class CD_Account extends Account {
	
	
	public CD_Account(int id, int pin, double bal){
		super(id, pin, bal);
		setMin(500);
		setAcctType('c');
	}

	
	/**
	 * apply the monthly interest
	 */
	@Override
	public double applyInterest() {
			double change =  Math.round((getBalance() * (0.05 / 12)) * 100.0) / 100.0; // 0.05/12 interest rate
			deposit(change);
			return change;
	}
	

	/**
	 * always returns false; cannot withdraw from a CD account
	 */
	@Override
	public boolean withdraw (double amount){
		return false; //cannot withdraw from a cd account
	}

}
