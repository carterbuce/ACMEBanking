/** CheckingAccount.java
 * @author Carter Buce | cmb9400
 * 
 * Version:
 * 		$Id: CheckingAccount.java,v 1.2 2015/11/02 04:40:07 cmb9400 Exp $
 * 
 * Revisions:
 * 		$Log: CheckingAccount.java,v $
 * 		Revision 1.2  2015/11/02 04:40:07  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.1  2015/11/02 04:39:06  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.4  2015/11/02 04:25:38  cmb9400
 * 		fixed apply interest
 *
 * 		Revision 1.3  2015/11/01 04:29:00  cmb9400
 * 		fixed constructors to take double as balance
 *
 * 		Revision 1.2  2015/11/01 01:44:27  cmb9400
 * 		fixed not using superclass' variables
 *
 * 		Revision 1.1  2015/10/31 22:20:36  cmb9400
 * 		defined abstract methods
 *
 * 
 */


public class CheckingAccount extends Account {
	
	public CheckingAccount(int id, int pin, double bal){
		super(id, pin, bal);
		setAcctType('x');
		setMin(50);
	}

	
	/**
	 * apply montly interest
	 * checkings accounts have no interest, only penalties for having less than minimum
	 */
	@Override
	public double applyInterest() { //only penalty with a checking account
		if(getBalance() < getMinimum()){
			if (getBalance() > 5){
				withdraw(5);
				return -5;
			}
			else{
				double change =  Math.round((getBalance() * 0.10) * 100.0) / 100.0; //10% of balance, round to 2 decimal places
				withdraw(change);
				return -change;
			}
		}
		return 0;
	}
	
	
	
	
}
