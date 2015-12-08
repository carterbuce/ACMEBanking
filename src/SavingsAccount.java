/** SavingsAccount.java
 * @author Carter Buce | cmb9400
 * 
 * Version:
 * 		$Id: SavingsAccount.java,v 1.2 2015/11/02 04:40:06 cmb9400 Exp $
 * 
 * Revisions:
 * 		$Log: SavingsAccount.java,v $
 * 		Revision 1.2  2015/11/02 04:40:06  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.1  2015/11/02 04:39:05  cmb9400
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

public class SavingsAccount extends Account {
	
	public SavingsAccount(int id, int pin, double bal){
		super(id, pin, bal);
		setAcctType('s');
		setMin(200);
	}

	
	/**
	 * apply monthly interest. If balance is below minimum balance, pentaly
	 * otherwise, give montly interest
	 */
	@Override
	public double applyInterest() {
		if(getBalance() < getMin()){ //penalty
			if (getBalance() > 10){
				withdraw(10);
				return -10;
			}
			else{			
				double change =  Math.round((getBalance() * 0.10) * 100.0) / 100.0; //10% of balance, round to 2 decimal places
				withdraw(change);
				return -change;
			}
		}
		else{ //give interest
			double change =  Math.round((getBalance() * (0.005 / 12)) * 100.0) / 100.0; //0.005/12 interest rate
			deposit(change);
			return change;
		}
		
	}
	
}
