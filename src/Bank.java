import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.TreeMap;
import java.util.Map;
/** Bank.java
 * @author Carter Buce | cmb9400 
 *
 * Version:
 *		$Id: Bank.java,v 1.18 2015/11/09 23:31:02 cmb9400 Exp $
 *
 * Revisions:
 *		$Log: Bank.java,v $
 *		Revision 1.18  2015/11/09 23:31:02  cmb9400
 *		changed atm accessing methods to static
 *
 *		Revision 1.17  2015/11/08 03:07:08  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.16  2015/11/07 23:29:34  cmb9400
 *		separated constructor and run methods
 *
 *		Revision 1.15  2015/11/07 22:16:52  cmb9400
 *		connected all the guis and handlers
 *
 *		Revision 1.14  2015/11/06 04:37:16  cmb9400
 *		wrote methods to interact with an ATM
 *
 *		Revision 1.13  2015/11/05 03:56:45  cmb9400
 *		works perfectly now
 *
 *		Revision 1.12  2015/11/02 04:39:07  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.11  2015/11/02 04:33:02  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.10  2015/11/02 01:16:04  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.9  2015/11/01 21:47:14  cmb9400
 *		removed debug prints
 *
 *		Revision 1.8  2015/11/01 19:54:24  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.7  2015/11/01 17:28:46  cmb9400
 *		able to read and write, but problems with writing still
 *
 *		Revision 1.6  2015/11/01 05:32:04  cmb9400
 *		added debug statements
 *
 *		Revision 1.5  2015/11/01 05:04:49  cmb9400
 *		wrote close method
 *
 *		Revision 1.4  2015/11/01 04:29:25  cmb9400
 *		wrote processaccounts method
 *
 *		Revision 1.3  2015/11/01 01:44:43  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.2  2015/10/31 18:34:38  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.1  2015/10/30 20:55:05  cmb9400
 *		Initial commit.
 *
 */

public class Bank {
	
	private static Map<Integer, Account> accountMap = new TreeMap<Integer, Account>();
	private static String filepath;
	private static BankGUI gui;
	
	/**
	 * @param args one required arg, one optional
	 * usage: java Bank bankFile batchFile 
	 * If second argument is present, it will stay on command line and run the bankbatch processor
	 * otherwise it will open the Bank GUI
	 * 
	 * When the Bank closes (in either GUI or batch mode), it will print out to standard output 
	 * (System.out) the status of all the accounts that are present in the bank.
	 */
	public static void main(String[] args)
	{
		//if incorrect # or bankfile can't be opened, print to system error
		//Usage: java Bank bankFile [batchFile]
		filepath = args[0];
		if(args.length > 2 || args.length == 0){
			System.err.println("Usage: java Bank bankFile [batchFile]");
		}
		else{
			if(args.length == 2){ //batch mode
				processAccounts(args[0]);
				BankBatch.processBatch(accountMap, args[1]);
				close();
			}
			
			else if(args.length == 1){ // gui mode
				processAccounts(args[0]);
				gui = new BankGUI(
					new ActionListener(){
						public void actionPerformed(ActionEvent e) {
								handleButton(e.getActionCommand());
						}
					}
				);
				gui.run();
				gui.setDisplay(listAccounts()); //start the GUI with accounts listed
			}
		}
		
	}
	
	
	/**
	 * handle a button from the Bank GUI
	 * @param command the button command
	 */
	private static void handleButton(String command){
		if(command.equals("Launch an ATM")){
			new ATM();
		}
		else if(command.equals("Refresh Account List")){
			gui.setDisplay(listAccounts());
		}
		else if(command.equals("Close")){
			close();
			System.exit(1);
		}
				
	}
	
	
	public static String listAccounts(){
		String fmt = "%11s%10d%4s%9s";
		DecimalFormat df = new DecimalFormat("#.00");
		String result = "";
		result += ("Acount Type   Account      Balance   ");
		result += ("\n-----------   -------   ----------");
		for(Account curr : accountMap.values()){
			char type = curr.getAcctType();
			String accountType= "";
				if(type == 'c') accountType = "CD";
				if(type == 's') accountType = "Saving";
				if(type == 'x') accountType = "Checking";
			int id = curr.getId();
			double balance = curr.getBalance();
			
			result = result + "\n" + String.format(fmt, accountType, id, "$", df.format(balance)); //write the account (without the pin) to the console
		}
		
		return result;
	}
	
	
	/**
	 * reads accounts from the file into the account arraylist
	 * @param filepath path to the account file
	 */
	private static void processAccounts(String filepath){
		BufferedReader accountReader;
		File accountFile = new File(filepath);
		if(accountFile.isDirectory()){
			System.err.println("Usage: java Bank bankFile [batchFile]");
			System.exit(1);
		}
		
		if(accountFile.exists()){
			try{
				accountReader = new BufferedReader(new FileReader(accountFile));

				String line = accountReader.readLine();
				while(line != null && line != ""){//in case there are extra empty lines
					String[] s = line.split(" "); // parts of an account line: type, id, pin, balance
					if(s.length != 4) break; //incorrect format, shouldn't happen
					int id = Integer.parseInt(s[1]);
					int pin = Integer.parseInt(s[2]);
					double balance = Double.parseDouble(s[3]);
					
					switch(s[0]){ //account type
						case "x": 
							accountMap.put(id, new CheckingAccount(id, pin, balance));
							break;
						
						case "c":
							accountMap.put(id, new CD_Account(id, pin, balance));
							break;
							
						case "s":
							accountMap.put(id, new SavingsAccount(id, pin, balance));
							break;
					}
					line = accountReader.readLine();
				}
				accountReader.close();
			}
			catch(FileNotFoundException e){
				//this shouldn't happen since we check to see if it exists
			}
			catch (IOException e) {
			}
			
		}
	}
	
	
	/**
	 * closes the bank, writing the accounts to both the console and the file
	 */
	public static void close(){
		try {
			BufferedWriter accountWriter = new BufferedWriter(new FileWriter(filepath));
			String fmt = "%11s%10d%4s%9s";
			DecimalFormat df = new DecimalFormat("#.00"); //forces to have 2 trailing numbers
			
			System.out.println("\n========= Final Bank Data ==========\n");
			System.out.println("Acount Type   Account      Balance   ");
			System.out.println("-----------   -------   ----------");
			
			int count = 0;
			for(Account curr : accountMap.values()){
				char type = curr.getAcctType();
				String accountType= "";
					if(type == 'c') accountType = "CD";
					if(type == 's') accountType = "Saving";
					if(type == 'x') accountType = "Checking";
				int id = curr.getId();
				double balance = curr.getBalance();
				
				accountWriter.write(curr.toString()); //write the account to the file
				count++;
				if(count < accountMap.size()) accountWriter.newLine(); //prevents the last write from creating a blank line after
				System.out.printf(fmt, accountType, id, "$", df.format(balance)); //write the account (without the pin) to the console
				System.out.println();
			}
			
			System.out.println("\n====================================");
			accountWriter.close();
		} 
		catch (IOException e) {
		}	
	}
	
	
	//------------------------------------------------------------------------------------------------------
	//      the following is code for the bank to interact with the ATM class
	//------------------------------------------------------------------------------------------------------
	

	/**
	 * determine if an entered account number corresponds to an account in the bank
	 * @param id the account number
	 * @return if the account exists
	 */
	public static boolean accountExists(int id){
		return accountMap.containsKey(id);
	}
	

	/**
	 * determine if the entered pin is the correct pin for a given account
	 * @param id the account id
	 * @param pin the entered pin
	 * @return if the pin matches that of the account's
	 */
	public static boolean login(int id, int pin){
		return accountMap.get(id).login(pin);
	}
	
	
	//the below methods do not need to determine if the account is logged in, because the
	//ATM class will only let the GUI use these methods if the login method returned true
	/**
	 * withdraw money from an account
	 * @param id the account id
	 * @param amount the amount to withdraw
	 * @return if the amount could be withdrawn
	 */
	public static boolean withdraw(int id, int amount){
		return accountMap.get(id).withdraw(amount);
	}
	
	
	/**
	 * deposit money into an account
	 * @param id the account id
	 * @param amount the amount to deposit
	 * @return if the amount could be deposited (always true)
	 */
	public static boolean deposit(int id, int amount){
		return accountMap.get(id).deposit(amount);
	}
	
	
	/**
	 * get the balance of an account
	 * @param id the account id
	 * @return the balance of the account
	 */
	public static double getBalance(int id){
		return accountMap.get(id).getBalance();
	}
	
	
	
	
}
