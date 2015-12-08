import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ATM {

	private enum ATM_State{ //the state that the ATM is in (which screen to show)
		LOGINID, LOGINPIN, TRANSACTION, DEPOSIT, DEPOSITNOTIFICATION, WITHDRAW, WITHDRAWALNOTIFICATION, BALANCE
	}
	
	private int accountID; //the account id that the ATM is logged into
	private ATM_GUI gui;
	private ATM_State state;
	private String currentInput = "";
	
	
	/**
	 * create a new ATM and its corresponding GUI
	 */
	public ATM(){
		gui = new ATM_GUI(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
							handleButton(e.getActionCommand());
					}
				}
			);
		gui.run();
		loginMenu();
	}
	
	
	/**
	 * handle a button pressed
	 * @param s a button command string
	 */
	public void handleButton(String s) {
		if(s.equals("Close")){
			gui.setVisible(false); //hide the window until the Bank program exits
		}
		else if(s.equals("Clear")){
			currentInput = "";
			refreshState();
		}
		else if(s.equals("OK")){
			advance();
		}
		else if(s.equals("Cancel")){
			back();
		}
		else{ //a number was pressed
			handleNumber(s);
		}	
	}
	
	
	/**
	 * handle a number button being pressed
	 * @param s the number that was pressed
	 */
	public void handleNumber(String s){
		int numPressed = Integer.parseInt(s);
		if(currentInput.length() < 7){ //prevents overflow exception when parsing input as int
			currentInput += numPressed;
			if(state == ATM_State.LOGINPIN){
				gui.setDisplay(gui.getDisplay() + "*");		
			}
			else{
				gui.setDisplay(gui.getDisplay() + numPressed);
			}
		}
	}
	
	
	/**
	 * display the account login menu
	 */
	public void loginMenu(){
		state = ATM_State.LOGINID;
		gui.setDisplay("Enter your account ID: \n");
	}
	
	
	/**
	 * display the failed account login notification
	 */
	public void failedLogin(){
		state = ATM_State.LOGINID;
		gui.setDisplay("ID does not exist. \n"
				+ "Enter your account ID: \n");
	}
	
	
	/**
	 * display the account pin menu
	 */
	public void loginPin(){
		state = ATM_State.LOGINPIN;
		gui.setDisplay("Enter your account PIN: \n");
	}
	
	
	/**
	 * display a failed pin notification
	 */
	public void failedPin(){
		state = ATM_State.LOGINPIN;
		gui.setDisplay("Login failed. \n"
				+ "Enter your account PIN: \n");
	}
	
	
	/**
	 * display the transaction menu
	 */
	public void transactionMenu(){
		state = ATM_State.TRANSACTION;
		gui.setDisplay("Transaction menu\n"
				+ "1. Balance\n"
				+ "2. Deposit\n"
				+ "3. Withdraw\n"
				+ "4. Log out\n"
				+ "Enter choice: ");
	}
	
	/**
	 * display the deposit menu
	 */
	public void depositMenu(){
		state = ATM_State.DEPOSIT;
		gui.setDisplay("Amount to deposit: \n$");	
	}
	
	
	/**
	 * display successful deposit.
	 */
	public void successfulDeposit(){
		state = ATM_State.DEPOSITNOTIFICATION;
		gui.setDisplay("Successful deposit.");
	}
	
	
	
	/**
	 * display the withdrawal menu 
	 */
	public void withdrawalMenu(){
		state = ATM_State.WITHDRAW;
		gui.setDisplay("Amount to withdraw: \n$");
	}
	
	
	/**
	 * display that the withdrawal failed
	 */
	public void failedWithdraw(){
		state = ATM_State.WITHDRAWALNOTIFICATION;
		gui.setDisplay("Invalid withdrawal amount.");
	}
	
	
	/**
	 * display that the withdrawal succeeded
	 */
	public void succeededWithdraw(){
		state = ATM_State.WITHDRAWALNOTIFICATION;
		gui.setDisplay("Withdrawal success.");
	}
	
	
	/**
	 * display the balance menu
	 */
	public void balanceMenu(){
		state = ATM_State.BALANCE;
		double balance = Bank.getBalance(accountID); 
		gui.setDisplay("Current balance: \n$" + balance);	
	}
	
	
	/**
	 * refresh the menu screen depending on which state the ATM is in
	 * for use after clearing input
	 */
	public void refreshState(){
		switch(state){
		case LOGINID:
			loginMenu();
			break;
		case LOGINPIN:
			loginPin();
			break;
		case TRANSACTION:
			transactionMenu();
			break;
		case DEPOSIT:
			depositMenu();
			break;
		case DEPOSITNOTIFICATION:
			depositMenu();
			break;
		case WITHDRAW:
			withdrawalMenu();
			break;
		case BALANCE:
			balanceMenu();
			break;
		case WITHDRAWALNOTIFICATION:
			balanceMenu();
			break;
		}
	}
	
	
	/**
	 * go back one menu
	 * called when the user presses cancel  
	 */
	public void back(){
		switch(state){
		case LOGINID:
			break; //do nothing, already at top menu
		case LOGINPIN:
			loginMenu();
			break;
		case TRANSACTION:
			break; //do nothing, user must enter the choice to log out
		case DEPOSIT:
			transactionMenu();
			break;
		case DEPOSITNOTIFICATION:
			break; //do nothing, user must press ok
		case WITHDRAW:
			transactionMenu();
			break;
		case BALANCE:
			break; //do nothing, user must press ok
		case WITHDRAWALNOTIFICATION:
			break; //do onthing, user must press ok
		}
		
		currentInput = "";
	}
	
	
	/**
	 * submit current input and go forward
	 * called when the user presses OK
	 */
	public void advance(){
		switch(state){
		
		case LOGINID: 
			if(currentInput.length() > 0){ //not empty 
				if(Bank.accountExists(Integer.parseInt(currentInput))){ //shouldn't need to check if int since input can only be int
					accountID = Integer.parseInt(currentInput);
					loginPin();
				}
				else{
					failedLogin();
				}
			}
			else{
				refreshState();
			}
			break;
			
		case LOGINPIN:
			if(currentInput.length() > 0){
				if(Bank.login(accountID, Integer.parseInt(currentInput))){
					transactionMenu();
				}
				else{
					failedPin();
				}
			}
			break;
			
		case TRANSACTION:
			transactionMenu();
			if(currentInput.equals("1")){
				balanceMenu();
			}
			else if(currentInput.equals("2")){
				depositMenu();
			}
			else if(currentInput.equals("3")){
				withdrawalMenu();
			}
			else if(currentInput.equals("4")){
				accountID = 0;
				loginMenu();
			}
			else{
				refreshState();
			}
			break;
			
		case DEPOSIT:
			if(currentInput.length() > 0){
				Bank.deposit(accountID, Integer.parseInt(currentInput));
				successfulDeposit();
			}
			
			break;
			
		case DEPOSITNOTIFICATION: 
			transactionMenu(); 
			break;
			
		case WITHDRAW:
			if(currentInput.length() > 0){
				if(Bank.withdraw(accountID, Integer.parseInt(currentInput))){
					succeededWithdraw();
				}
				else{
					failedWithdraw();
				}
			}
			break;
			
		case BALANCE: 
			transactionMenu();
			break;
			
		case WITHDRAWALNOTIFICATION: 
			transactionMenu(); 
			break;
		}
		
		currentInput = "";
	}
	
	
	
	

}
