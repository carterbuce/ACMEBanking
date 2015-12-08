import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/** ATM_GUI.java
 * @author Carter Buce | cmb9400
 * 
 * Version:
 * 		$Id: ATM_GUI.java,v 1.15 2015/11/09 05:07:55 cmb9400 Exp $
 * 
 * Revisions:
 * 		$Log: ATM_GUI.java,v $
 * 		Revision 1.15  2015/11/09 05:07:55  cmb9400
 * 		resized font
 *
 * 		Revision 1.14  2015/11/08 22:21:17  cmb9400
 * 		changed  font size
 *
 * 		Revision 1.13  2015/11/08 03:07:08  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.12  2015/11/07 23:29:10  cmb9400
 * 		separated constructor and run methods
 *
 * 		Revision 1.11  2015/11/07 22:16:52  cmb9400
 * 		connected all the guis and handlers
 *
 * 		Revision 1.10  2015/11/06 20:45:21  cmb9400
 * 		updated main method
 *
 * 		Revision 1.9  2015/11/06 19:58:26  cmb9400
 * 		did a thing
 *
 * 		Revision 1.8  2015/11/06 04:18:53  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.7  2015/11/05 22:36:48  cmb9400
 * 		*** empty log message ***
 *
 * 		Revision 1.6  2015/11/05 19:49:52  cmb9400
 * 		changed textfield to textarea
 *
 * 		Revision 1.5  2015/11/05 17:56:33  cmb9400
 * 		slight modification to gui divider
 *
 * 		Revision 1.4  2015/11/05 16:52:47  cmb9400
 * 		gui design now (probably) finalized
 *
 * 		Revision 1.3  2015/11/05 04:14:12  cmb9400
 * 		improved sizing of buttons
 *
 * 		Revision 1.2  2015/11/05 03:56:36  cmb9400
 * 		gui created
 *
 * 		Revision 1.1  2015/11/02 04:39:08  cmb9400
 * 		*** empty log message ***
 *
 * 
 */

@SuppressWarnings("serial")
public class ATM_GUI extends JFrame {
	private JTextArea displayField;
	
	static class NButton extends JButton{
		private int number;
		public NButton(String name, int number){
			super(name);
			this.number = number;
		}
		public int getNumber(){
			return number;
		}
	}
	
	
	/**
	 * set the display to a string
	 * @param s string to set display to
	 */
	public void setDisplay(String s){
		displayField.setText(s);
	}
	
	
	/**
	 * get the current display
	 * @return the display string
	 */
	public String getDisplay(){
		return displayField.getText();
	}
	
	
	/**
	 * create the ATM GUI
	 * @param listener the actionlistener to use for all the events
	 */
	public ATM_GUI(ActionListener listener){
		
		Color buttonColor = new Color(173, 173, 173);
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		
		
		//panel for the number pad buttons
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4,3, 10, 10));
		
		for(int i = 1; i < 10; i++){ //add buttons 1-9
			NButton button = new NButton("" + i, i);
			button.setBackground(buttonColor);
			button.addActionListener(listener);
			panel1.add(button);
		}
		
		JButton button = new JButton("");
		button.setBackground(new Color(100,100,100));
		panel1.add(button);
		
		NButton nbutton = new NButton("0", 0);
		nbutton.setBackground(buttonColor);
		nbutton.addActionListener(listener);
		panel1.add(nbutton);
		
		button = new JButton("");
		button.setBackground(new Color(100,100,100));
		panel1.add(button);
		
		panel1.setBorder(new EmptyBorder(5,5,5,5));
		
		
		
		//panel for the buttons below the number pad
		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.setLayout(new GridLayout(1, 4, 8, 5));
		
		JButton ok = new JButton("OK");
		ok.setBackground(buttonColor);
		ok.setBorder(new LineBorder(new Color(56, 224, 0), 1));
		ok.addActionListener(listener);
		panel2.add(ok);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBackground(buttonColor);
		cancel.setBorder(new LineBorder(new Color(224, 0, 0), 1));
		cancel.addActionListener(listener);
		panel2.add(cancel);
		
		JButton clear = new JButton("Clear");
		clear.setBackground(buttonColor);
		clear.setBorder(new LineBorder(new Color(224, 224, 0), 1));
		clear.addActionListener(listener);
		panel2.add(clear);
		
		JButton close = new JButton("Close");
		close.setBackground(buttonColor);
		close.setBorder(new LineBorder(new Color(130, 0, 0), 1));
		close.addActionListener(listener);
		panel2.add(close);
		
		panel2.setBorder(new EmptyBorder(5,5,5,5));
		
		
		
		//panel containing both the number pad and the buttons below it
		panel1.setPreferredSize(new Dimension(225, 50));
		panel2.setPreferredSize(new Dimension(225, 300));
		JSplitPane splitPaneButtons = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel1, panel2);
		splitPaneButtons.setDividerSize(5);
		JPanel buttons = new JPanel(new BorderLayout());
		buttons.setBorder(new EmptyBorder(5,5,5,5));
		buttons.add(splitPaneButtons, BorderLayout.CENTER);
		splitPaneButtons.setDividerLocation(250);
		splitPaneButtons.setEnabled(false); // stops from resizing
		
		
		
		//panel containing the display field
		JPanel display = new JPanel(new BorderLayout());
		displayField = new JTextArea("");
		displayField.setEditable(false);
		displayField.setBackground(new Color(161, 212, 144));
        displayField.setFont(new Font("monospaced", Font.PLAIN, 24));
		display.add(displayField);
		display.setBorder(new EmptyBorder(5,5,5,5));
		
		
		container.add(buttons, BorderLayout.EAST);
		container.add(display, BorderLayout.CENTER);
		
		
	}
	
	public void run(){
		setTitle("Carter Buce | cmb9400 | ATM" + System.identityHashCode(this));
		setSize(600, 350);
		setResizable(false);
		setLocation(100, 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}


	
	
	
}
