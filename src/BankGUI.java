import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/** BankGUI.java
 * @author Carter Buce | cmb9400 
 *
 * Version:
 *		$Id: BankGUI.java,v 1.16 2015/11/08 05:21:23 cmb9400 Exp $
 *
 * Revisions:
 *		$Log: BankGUI.java,v $
 *		Revision 1.16  2015/11/08 05:21:23  cmb9400
 *		bold font
 *
 *		Revision 1.15  2015/11/08 03:18:27  cmb9400
 *		set font size, changed window size
 *
 *		Revision 1.14  2015/11/08 03:07:08  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.13  2015/11/07 23:29:09  cmb9400
 *		separated constructor and run methods
 *
 *		Revision 1.12  2015/11/07 22:16:51  cmb9400
 *		connected all the guis and handlers
 *
 *		Revision 1.11  2015/11/06 20:45:20  cmb9400
 *		updated main method
 *
 *		Revision 1.10  2015/11/05 22:05:00  cmb9400
 *		display field not editable
 *
 *		Revision 1.9  2015/11/05 21:49:04  cmb9400
 *		named buttons
 *
 *		Revision 1.8  2015/11/05 19:49:51  cmb9400
 *		changed textfield to textarea
 *
 *		Revision 1.7  2015/11/05 18:41:45  cmb9400
 *		changed colors
 *
 *		Revision 1.6  2015/11/05 17:56:33  cmb9400
 *		slight modification to gui divider
 *
 *		Revision 1.5  2015/11/05 17:53:23  cmb9400
 *		added scroll bar
 *
 *		Revision 1.4  2015/11/05 16:52:47  cmb9400
 *		gui design now (probably) finalized
 *
 *		Revision 1.3  2015/11/05 04:34:21  cmb9400
 *		*** empty log message ***
 *
 *		Revision 1.2  2015/11/05 03:56:59  cmb9400
 *		begin creating GUI
 *
 *		Revision 1.1  2015/10/30 20:55:05  cmb9400
 *		Initial commit.
 *
 */


public class BankGUI extends JFrame {
	private static final long serialVersionUID = -5881404881207662990L;
	private static JTextArea displayField;

	/**
	 * set the display to a string
	 * @param s string to set display to
	 */
	public void setDisplay(String s){
		displayField.setText(s);
	}
	
	/**
	 * create a new bank GUI
	 * @param listener the listener to use for the buttons
	 */
	public BankGUI(ActionListener listener){
		Color buttonColor = new Color(173, 173, 173);
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		
		//panel for the buttons
		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.setLayout(new GridLayout(1, 3, 8, 5));
		JButton refresh = new JButton("Refresh Account List");
		refresh.addActionListener(listener);
		refresh.setBackground(buttonColor);
		panel2.add(refresh);
		
		JButton launch = new JButton("Launch an ATM");
		launch.setBackground(buttonColor);
		launch.addActionListener(listener);
		panel2.add(launch);
		
		JButton close = new JButton("Close");
		close.setBackground(buttonColor);
		close.addActionListener(listener);
		panel2.add(close);
		
		panel2.setBorder(new EmptyBorder(5,5,5,5));
		
		
		//panel for the display
		JPanel display = new JPanel(new BorderLayout());
		displayField = new JTextArea("");
		displayField.setEditable(false);
		displayField.setBackground(new Color(144, 195, 212));
        displayField.setFont(new Font("monospaced", Font.BOLD, 18));
		JScrollPane scroll = new JScrollPane(displayField);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		display.add(scroll);
		display.setBorder(new EmptyBorder(5,5,5,5));

		
		
		//panel containing both the number pad and the buttons below it	
		JSplitPane splitPaneButtons = new JSplitPane(JSplitPane.VERTICAL_SPLIT, display, panel2);
		splitPaneButtons.setDividerSize(0);
		JPanel window = new JPanel(new BorderLayout());
		window.setBorder(new EmptyBorder(5,5,5,5));
		window.add(splitPaneButtons, BorderLayout.CENTER);
		splitPaneButtons.setDividerLocation(350);
		splitPaneButtons.setEnabled(false); // stops from resizing
		

		container.add(window, BorderLayout.CENTER);
		
		
	}

	public void run(){
		this.addWindowListener( //display the bank data when this closes
			new WindowAdapter(){
			    public void windowClosing(WindowEvent e)
			    {
			        Bank.close();
			    }
			}
		);
		
		
		setTitle("Carter Buce | cmb9400");
		setSize(600, 450);
		setResizable(false);
		setLocation(100, 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
