243 OOP: Project 02 README
===============================
(please use the RETURN key to make multiple lines; don't assume autowrap.)

0. Author Information
---------------------

CS Username: 	cmb9400		Name:  		Carter Buce

1. Problem Analysis
---------

Summarize the analysis work you did. 
What new information did it reveal?  How did this help you?
How much time did you spend on problem analysis?

	To analyze the problem, I looked at code written in the MVC pattern to get a better hold
	of how to make the GUI. I also thought about different ways to create the account objects
	and how to store them for easy access by the bank model.

2. Design
---------

Explain the design you developed to use and why. What are the major 
components? What connects to what? How much time did you spend on design?
Make sure to clearly show how your design uses the MVC model by
separating the UI "view" code from the back-end game "model".

	My design is based off of MVC,but I don't think the components are strictly independent. 
	The Bank class is the main class, holding the accounts and reading/writing the accounts
	to the files. If provided with a bank batch file, it sends it to BankBatch.java to process.
	Otherwise, it creates a new BankGUI instance using an actionlistener made in the Bank class.
	Then, if "create an atm" is selected in the bankGUI, the Bank class will create a new ATM,
	which creates an ATM_GUI with an actionlistener, so the ATM is the controller for it. The
	ATM class can then interact with the Bank model, and depending on what the Bank model returns,
	the ATM class can then change the display of the ATM_GUI

3. Implementation and Testing
-------------------

Describe your implementation efforts here; this is the coding and testing.

What did you put into code first? The Bank class and the BankBatch class
How did you test it? putting in various batch files trying to modify and create accounts and 
    seeing if it is written correctly to the file
How well does the solution work? It works as expected 
Does it completely solve the problem? Yes
Is anything missing? No
How could you do it differently? Making the Model, view, and controller more independent
How could you improve it? optimizing the code to make it faster or more readable

How much total time would you estimate you worked on the project? 30 hours 
If you had to do this again, what would you do differently? 

	I would use observer/observable because I'm still not exactly sure how to make it work in MVC, 
	so I would like to experiment more with it.

4. Development Process
-----------------------------

Describe your development process here; this is the overall process.

How much problem analysis did you do before your initial coding effort? 1-2 hours
How much design work and thought did you do before your initial coding effort? 2-3 hours
How many times did you have to go back to assess the problem? not many

What did you learn about software development?

	It is very simple to create a program if you are already supplied with examples of how it should
	work and all of the requirements are already written down. I could imagine that this project
	would be much harder if there were extra requirements given each time you submitted a working copy.
	I also learned a lot about MVC, and that there are many different ways to create a program using
	the pattern
