import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

/** BankBatch.java
 * @author Carter Buce | cmb9400 
 *
 * Version:
 *		$Id: BankBatch.java,v 1.5 2015/11/02 04:39:06 cmb9400 Exp $
 *
 * Revisions:
 *		$Log: BankBatch.java,v $
 *		Revision 1.5  2015/11/02 04:39:06  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.4  2015/11/02 04:33:11  cmb9400
 *		all methods now work
 *
 *		Revision 1.3  2015/11/02 01:16:31  cmb9400
 *		defined various methods
 *
 *		Revision 1.2  2015/11/01 01:44:36  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.1  2015/10/30 20:55:04  cmb9400
 *		Initial commit.
 *
 */

public class BankBatch {
	private static ArrayList<String> commands = new ArrayList<String>();
	private static Map<Integer, Account> accountMap;
	
	/**
	 * process the commands from a bank onto the accounts
	 * @param accountMap map of accounts
	 * @param batchFile filename of batchfile to run commands in
	 */
	public static void processBatch(Map<Integer, Account> accounts, String batchFile) {
		accountMap = accounts;
		printInitial();
		getCommands(batchFile);
		applyCommands();
	}
	
	
	/**
	 * print the initial bank data
	 * @param accountMap map of accounts
	 */
	private static void printInitial(){
		String fmt = "%11s%10d%4s%9s";
		DecimalFormat df = new DecimalFormat("#.00");
		
		System.out.println("======== Initial Bank Data =========\n");
		System.out.println("Acount Type   Account      Balance   ");
		System.out.println("-----------   -------   ----------");
		
		for(Account curr : accountMap.values()){
			char type = curr.getAcctType();
			String accountType= "";
				if(type == 'c') accountType = "CD";
				if(type == 's') accountType = "Saving";
				if(type == 'x') accountType = "Checking";
			int id = curr.getId();
			double balance = curr.getBalance();
			
			System.out.printf(fmt, accountType, id, "$", df.format(balance)); //write the account (without the pin) to the console
			System.out.println();
		}
		
		System.out.println("\n====================================\n");
	} 
	
	
	/**
	 * get commands by line out of the file and store them in the command arraylist
	 * @param filepath path to batchfile
	 */
	private static void getCommands(String filepath){
		BufferedReader commandReader;
		File commandFile = new File(filepath);
		if(commandFile.isDirectory()){
			System.err.println("Usage: java Bank bankFile [batchFile]");
			System.exit(1);
		}
		
		if(commandFile.exists()){
			try{
				commandReader = new BufferedReader(new FileReader(filepath));

				String line = commandReader.readLine();
				while(line != null && line != ""){//in case there are extra empty lines
					commands.add(line);
					line = commandReader.readLine();
				}
				
				commandReader.close();
			}
			catch(FileNotFoundException e){
				//this shouldn't happen since we check to see if it exists
				System.err.println("Usage: java Bank bankFile [batchFile]");
				System.exit(1);
			}
			catch (IOException e) {
			}
			
		}
		else{
			System.err.println("Usage: java Bank bankFile [batchFile]");
			System.exit(1);
		}
	}
	
	
	/**
	 * read the commands from the arraylist, and execute them
	 */
	private static void applyCommands(){
		for(String s : commands){
			String[] parts = s.split(" ");
			
			switch(parts[0]){ //by the type of command
				
				case "o": //open an account
					open(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
					break;
					
				case "d": //deposit
					deposit(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
					break;
					
				case "w": //withdraw
					withdraw(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
					break;
				
				case "a": //apply interest to every account
					applyInterest();
					break;
					
				case "c": //close an account
					close(Integer.parseInt(parts[1]));
					break;
			}
		}
	}
	
	
	/**
	 * open an account
	 * @param type the type of account
	 * @param id the id it will have
	 * @param pin the account pin
	 * @param amount the opening balance
	 */
	public static void open(String type, int id, int pin, double amount){
		//cd accounts can't be opened with less than $500
		String fmt = "%4d%5s%4s%3s%-16s%1s%10s";
		DecimalFormat df = new DecimalFormat("#.00");
		
		
		if(accountMap.containsKey(id) || (type.equals("c") && amount < 500.0) ){ //account exists and cd accounts start with lesss than 500 (the minimum)
			System.out.printf(fmt, id, "o", type, "", "Open: Failed", "", "");
			System.out.println();	
		}
		else{
			System.out.printf(fmt, id, "o", type, "", "Open: Success", "$", df.format(amount));
			System.out.println();
			
			switch(type){
			case "s":
				accountMap.put(id, new SavingsAccount(id, pin, amount));
				break;
				
			case "x": //deposit
				accountMap.put(id,  new CheckingAccount(id, pin, amount));
				break;
				
			case "c": //withdraw
				accountMap.put(id,  new CD_Account(id, pin, amount));
				break;
			}
		}
	}
	
	
	/**
	 * deposit into an account
	 * @param id account to deposit into
	 * @param amount amount to deposit
	 */
	public static void deposit(int id, double amount){
		String fmt = "%4d%5s%8s%10s%5s%1s%10s";
		DecimalFormat df = new DecimalFormat("#.00");
		
		if(accountMap.containsKey(id)){
			Account tmp = accountMap.get(id);
			tmp.deposit(amount);
			System.out.printf(fmt, id, "d", "$", df.format(amount), "", "$", df.format(tmp.getBalance()));
			System.out.println();
		}
		else{
			System.out.printf(fmt, id, "d", "$", df.format(amount), "", "Failed", "");
			System.out.println();
		}
	}
	
	
	/**
	 * withdraw from an account
	 * @param id the account to deposit into
	 * @param amount the amount to withdraw
	 */
	public static void withdraw(int id, double amount){
		String fmt = "%4d%5s%8s%10s%5s%1s%10s";
		DecimalFormat df = new DecimalFormat("#.00");
		
		if(accountMap.containsKey(id)){
			Account tmp = accountMap.get(id);
			if(tmp.getAcctType() != 'c'){
				tmp.withdraw(amount);
				System.out.printf(fmt, id, "w", "$", df.format(amount), "", "$", df.format(tmp.getBalance()));
				System.out.println();
			}
			else{ //cannot withdraw from a CD account
				System.out.printf(fmt, id, "w", "$", amount, "", "Failed", "");
				System.out.println();
			}
		}
		else{
			System.out.printf(fmt, id, "w", "$", amount, "", "Failed", "");
			System.out.println();
		}
	}
	
	
	/**
	 * execute the interest functions on each of the accounts
	 */
	public static void applyInterest(){
		String fmt = "%4d%5s%1s%9s%2s%1s%10s";
		DecimalFormat df = new DecimalFormat("0.00");
		
		System.out.println("\n========= Interest Report ==========");
		System.out.println("Account  Adjustment  New Balance");
		System.out.println("-------  ----------  -----------");
		for(Account curr : accountMap.values()){
			System.out.printf(fmt, curr.getId(), "", "$", df.format(curr.applyInterest()), "", "$", df.format(curr.getBalance()));
			System.out.println();
		}
		System.out.println("====================================\n");
	}
	
	
	/**
	 * close the accounts, removing them from the map
	 * @param id the account id to remove from the map
	 */
	public static void close(int id){
		String fmt = "%4d%5s%7s%-15s%2s%10s";
		DecimalFormat df = new DecimalFormat("#.00");
		
		
		if(accountMap.containsKey(id)){ //if the account exists
			Account removed = accountMap.remove(id);
			System.out.printf(fmt, removed.getId(), "c", "", "Closed: Success", "$", df.format(removed.getBalance()));
			System.out.println();
		}
		else{
			System.out.printf(fmt, id, "c", "", "Closed: Failed", "", "");
			System.out.println();
		}
	}
	
	
	
}
