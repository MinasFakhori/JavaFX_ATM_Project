import javafx.stage.Stage;
import javafx.stage.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import javafx.scene.media.AudioClip;

/**
 * <h2>Model class</h2>
 * @author Minas Fakhori 
 * 
 */


/** 
 * The model represents all the actual content and functionality of the app
 * For the ATM, it keeps track of the information shown in the display
 * (the title and two message boxes), and the interaction with the bank, executes
 * commands provided by the controller and tells the view to update when
 * something changes. <br>
 * To use this, you call the methods in the controller  
 */
public class Model{
    // the ATM model is always in one of six states - waiting for an account number, 
    // waiting for a password, waiting for new password, waiting for date of birth or logged in and processing account requests. 
    // We use string values to represent each state:
    // (the word 'final' tells Java we won't ever change the values of these variables)
    //I also made all states private so other classes can't access it without permission.
    private final String ACCOUNT_NO = "account_no";
    private final String PASSWORD = "password";
    private final String LOGGED_IN = "logged_in";
    private final String CHECK_PASS = "check_pass";
    private final String NEW_PASSWORD = "new_password";
    private final String CHECK_DOB = "date_of_birth";
    private final String DEPOSIT = "deposit";
    private final String WITHDRAW = "withdraw";

    // variables representing the ATM model
    private String state = ACCOUNT_NO;      // the state it is currently in
    private int  number = 0;                // current number displayed in GUI (as a number, not a string)

    /**
     * So other classes can view number if the method is called but not change it. 
     * To get the number you just call the method 'model.getNumber()'. 
     * What a geter does is access the private variable (becuase it is in the same class) and then return it so other classes can just call that method
     * rather than having direct access.
     * @return number
     */
    public int getNumber(){ 
        return number;
    }
    
   

    private Bank  bank = null;              // The ATM talks to a bank, represented by the Bank object.
    private int accNumber = -1;             // Account number typed in    
    private int accPasswd = -1;             // Password typed in
    private int dob = -1;                  //Date of birth typed in, if -1 that means there is an issue. This is done to make dubugging eaier/ 
    private int newPass = -1;               // The new password the user types in.
    private int depositeAmount = -1;
    private int withdrawAmount = -1;
    // These five are what are shown on the View display

    private String display1 = null;         // The contents of the Message 1 box (a single line)
    private String display2 = null;         // The contents of the Message 2 box (may be multiple lines)

    private String historyFile; //This string is used to make the file names in the withdraw history according to the account number

    
    // This sets the big heading for both windows
    private String title ="Bank ATM"; 

    /**
     * This gets the title for other classes to access 
     * @return get the title 
     */
    public String getTitle(){
        return title;
    }

    /**
     * This sets title so other classes can change the title by calling the method and giving it a parameter (which is what you want the title to be).
     * eg 'setTitle("Hello")'
     * A setter works by taking in a parameter, that update the value to what the parameter is. 
     * I had to do this because the variable was a private variable.
     * this keyword means the class variable not the method variable, so java knows what variable we are talking about
     * @param title what you want the title to be.
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
    @return display1 this returns display1   
     */
    public String getDisplay1(){
        return display1;
    }

    /**
    @return display2 this returns display2   
     */  
    public String getDisplay2(){
        return display2;
    }

    /**
     * @param display1 what you want disply1 to be
     */
    public void setDisplay1(String display1){
        this.display1 = display1;
    }

    /**
     * @param display1 what you want disply2 to be
     */
    public void setDisplay2(String display2){
        this.display2 = display2;
    }

    private String cTitle ="Currency Converter"; //This is the title for the second window
    /**
     * @returns cTitle this is what you want the cTitle to be
     */
    public String getCTitle(){
        return cTitle;
    }
    
    /**
    *@param changes what number is, from another class
    */
     public void setNumber(int number){
        this.number = number;
    }
    
     

    
    //ArrayList for the withdraw history.
    //ArrayList are a generic type (made up out of other types you need to import them) and they use <> and class types so thats why I had to write Interger not int.
    // I decide to go for arraylist rather than array because they dont have a set limmit fixed amount of things they can hold unlike array.
    private ArrayList<Integer> wdHistory = new ArrayList<>();

    
    // The other parts of the model-view-controller setup
    public View view;
    public Controller controller;
    public CC cc;

    
    // Model constructor - we pass it a Bank object representing the bank we want to talk to
    public Model(Bank b){
        Debug.trace("Model::<constructor>");          
        bank = b;
    }


    /**
     * This is to start up the ATM or resetting after an error or logout 
     * set state to ACCOUNT_NO, number to zero, and display message 
     * @param message standard instruction message
     */ 
    public void initialise(String message) {
        setState(ACCOUNT_NO);
        number = 0;
        display1 = message; 
        display2 =  "Enter your account number\n" +
        "Followed by \"Ent\"\n If you want to know what each button does click on the ?";
    }

    /** use this method to change state - mainly so we print a debugging message whenever the state changes 
     * @param newState the state it is going to next
     */
    public void setState(String newState) {
        if ( !state.equals(newState) ){
            String oldState = state;
            state = newState;
            Debug.trace("Model::setState: changed state from "+ oldState + " to " + newState);
        }
    }

    /**
     * These methods are called by the Controller to change the Model when particular buttons are pressed on the GUI 
     * process a number key (the key is specified by the label argument)
     */
    public void processNumber(String label){
        // a little magic to turn the first char of the label into an int
        // and update the number variable with it
        char c = label.charAt(0);
        number = number * 10 + c-'0';           // Build number 

        // show the new number in the display
        display1 = "" + number; 
        display();  // update the GUI   
    }

    /**
     *process the Clear button - reset the number (and number display string) and gets the start of the current state
     */
    public void processClear(){
        // clear the number stored in the model
        number = 0;
        display1 = "";
        display();  // update the GUI
    }

    /**
     * This method deletes the last digit by dividing by 10 and because it is an int the decimal disappears.
     * If the only number is 0 I dont want it to display anything, I did this because after you all the number 0 would apper which made it look elegant 
     */
    public void del(){
        number = number /10; // divdes number by 10 to remove the last digit
        if (number == 0){  //if statment to see if number is 0 and not display anything.
            display1 = " "; // makes it an empty display
            display();  //updates the display
        }else{
            display1 = "" + number; // if it is not 0 I want the new number to be displayed. I had to use the "" to conver it to a String.
            display(); //updates the display
        }
    }

    
    
    /**
    * This the the enter button.
    * The enter button is mainly used to change states/ 
    */
    public void processEnter(){
        // Enter was pressed - what we do depends what state the ATM is already in
        switch ( state ){
            case ACCOUNT_NO:
                // we were waiting for a complete account number - save the number we have
                // reset the tyed in number to 0 and change to the state where we are expecting 
                // a password
                accNumber = number; //makes number be account number 
                number = 0; //after that it sets number to 0 so you can use it again
                setState(PASSWORD); //changes state to password
                display1 = "";
                display2 = "Now enter your password\n" +
                "Followed by \"Ent\"";
                break;
            case PASSWORD:
                // we were waiting for a password - save the number we have as the password
                // and then cotnact the bank with accumber and accPasswd to try and login to
                // an account
                accPasswd = number;
                number = 0;
                display1 = "";
                // This check the account/password combination. If it's ok go into the LOGGED_IN
                // state, otherwise go back to the start (by re-initialsing)
                if ( bank.login(accNumber, accPasswd) ){
                    setState(LOGGED_IN); //this changes state to logged in
                    historyFile = "file/" + accNumber + ".txt"; //This makes the historyFile file nammed according to the account name.
                    display2 = "You have sucessfully logged in"; // Tells you that you logged in
                } else {
                    initialise("Unknown account/password"); //Tells you the username or password is wrong
                }
                break;
            
            // This is called if you want to change the password, checkpass what the user entered is the same as the password and then 
            //changes state to check for the date of birth
            case CHECK_PASS:
                display(); 
                accPasswd = number; // makes number accPasswd 
                number = 0; // resets the number to 0 to check something else
                setState(CHECK_DOB); // this changes state to check the dob
                display1 = ""; 
                display2 = "For added securty please enter you date of birth (DDMMYYYY) \nFollowed by enter. ";
                break;

            case CHECK_DOB:
                dob = number;
                number = 0;
                display1 = "";
                // This checks if the password and dob is true if it is it sets a new sate and gives instuctions.
                if ( bank.changePassAuth(accPasswd, dob) ){
                    display2 =  "Now enter your new password and press enter\nThe password must meet the requirements below:\n*No common passwords\n*min 5 charaters\n*max 9 charaters"; //What the password can have and what it cant have.
                    display1  = "";
                    number = 0;
                    display();
                    setState(NEW_PASSWORD); //changes state to new password
                }else{
                    display2 = "You entered either the password or the date of birth incorrectly\n Please try again"; //if it is wrong it returns to the perivous state and tells the user to try again
                    setState(CHECK_PASS); 
                }
                break; 

            case NEW_PASSWORD: //This state is to set the new password and to make sure it meets all the requirements
                display1="";
                newPass = number; 
                //if the number is smaller than those two digits is sets newPassword
                if (newPass >= 10000 && newPass <= 999999999){ 
                    number =0;
                    bank.newPass(newPass);
                    display2 = "Your password has been change sucessfully"; //displays the password chaned
                    display();
                    setState(LOGGED_IN);   // goes back to the logged in state 
                }else{
                    number = 0;
                    display2 = "This did not work because you didnt meet the requirements\n please try again\nThe password much meet the requirements below:\n*No common passwords\n*min 5 charaters\n*max 9 charaters";
                    setState(NEW_PASSWORD); // still on new password state because the user didnt meet the rquirements 
                }
                break;
                case DEPOSIT: //This changes to this state when you want to deposit
                    depositeAmount = number; // makes number depositAmount 
                    number = 0; // resets the number to 0 to check something else
                    display1 = ""; //updates display1
                    //Checks if deposite is not 0
                    if (depositeAmount != 0){ 
                        bank.deposit( depositeAmount ); //if it is not this calls the bank method to depsit money
                        number = 0; // makes number 0
                        display1 = ""; //updates display1
                        display2 = "You have successfully deposited: £" + depositeAmount  +"\n" + "Now you have £" + bank.getBalance();
                    }else{
                        display2 = "Amount cannot be 0, please try again. \n" + "You still have £" + bank.getBalance()+ "\nPlease try again or click EXT to exit"; 
                        setState(DEPOSIT);
                    }
                setState(LOGGED_IN); //goes back to the login state
                break;
                //The withdraw state has a bunch of if statment to see if it meets the right criteria to withdraw money
                case WITHDRAW:
                withdrawAmount = number;
                number = 0;
                display1 ="";
                // This checks if the amount is less than 1000
                if(withdrawAmount < 1000){
                    // This checks if the amount of withdraws you have left today.
                    if(bank.getWdLimmit()!= 0){
                    // This checks if the number can be withdrawn from the total amount
                        if ( bank.withdraw( withdrawAmount ) ){
                    // This checks if the number is not 0 and if it is you can withdraw money
                            if (withdrawAmount != 0){
                                wdHistory.add(withdrawAmount); //Adds number to the wdhistory array
                                display2 =   "You have successfully withdrawn: £" + withdrawAmount +"\n" + "Now you have £" + bank.getBalance(); // Displays the you withdrawn money and gives you your balance
                                bank.setWdLimmit(bank.getWdLimmit()-1); //Takes away one from the withdraw limmit.
                                 // This tells you how namy withdraws you have 
                                if (bank.getWdLimmit() == 0){
                                display2 += "\nThis was the last withdraw you could make today";
                                }else{
                                    display2 += "\nYou are allowed " + bank.getWdLimmit() + " more withdraws today "; 
                                }   
                            }else{
                            display2 = "Amount cannot be 0, please try again. \n" + "You still have £" + bank.getBalance() +"\nPlease try again or click EXT to exit"; //If the number is 0 it gives and error
                            setState(WITHDRAW);
                            }
                        }else{
                            display2 =   "You do not have sufficient funds\nPlease try again or click EXT to exit"; // if you dont have money it gives an error
                            setState(WITHDRAW);
                        }
                    }else{
                        display2 = "You used all your withdraws today";
                    }
                }else{
                   display2 = "You can withdraw more than £1000 at a time \nPlease try again or click EXT to exit";
                   setState(WITHDRAW);
                }
                display();
            break;       
            case LOGGED_IN:   
            default: 
                // do nothing in any other state (ie logged in)
        } 

        display();  // update the GUI
    }
    
   

    
    

    
    /**
    * Withdraw method this checks if we are logged in by checking if the state is equals to account number or password I did this because there are other states
    * and when changing to other states I didnt want the state to logout. Until you press logout.
    * This then switches states to the withdraw 
    */ 
    public void processWithdraw(){
        // checks if state is equals to LOGGED_IN state
         if (!(state.equals(ACCOUNT_NO) || state.equals(PASSWORD))){
            display2 = "You currently have £" + bank.getBalance() + "\nHow much would you like to withdraw?\nFollowed by ENT";
            display();
            setState(WITHDRAW);
            number = 0;
            display1 = "";           
        }else {
            initialise("You are not logged in"); // if your not logged in it gives and error
        }
        display();  // update the GUI
    }

    
    /**
     * Deposit button - check we are logged in and then switches states
     */

    public void processDeposit(){
        //checks if you are loggin in to deposite money
        if (!(state.equals(ACCOUNT_NO)|| state.equals(PASSWORD) )) {
            display2 = "You currently have £" + bank.getBalance() + "\nHow much would you like to deposit?\nFollowed by ENT";
            display();
            setState(DEPOSIT);
        } else {
            initialise("You are not logged in");
        } 
        display();  // update the GUI
    }


    
    /**
     * Balance button - check we are logged in and if so access the current balance and the overdraft limmit
     */
    public void processBalance(){
        if(!(state.equals(ACCOUNT_NO) || state.equals(PASSWORD))) {
            number = 0;
            display2 = "Your balance is: £" + bank.getBalance() + "\nYour overdraft limit is £" + bank.getOverDraft(); //calls a getter from the bank to get the balance and the overdraft
        } else {
            initialise("You are not logged in");
        }
        display();  // update the GUI
    }

    /**
     * Finish button - check we are logged in and if so log out
     */ 
    public void processFinish(){
        if (!(state.equals(ACCOUNT_NO) || state.equals(PASSWORD))) {
            // go back to the log in state
            processClear(); // clears the screen
            clearWd(); // this clears the wdHistory so the other accounts dont get the withdraws from the other account
            setState(ACCOUNT_NO); // changes state back to the start
            number = 0;
            display2 = "Welcome: Enter your account number";
            bank.logout(); // calls the logout method from the bank

        } else {
            initialise("You are not logged in"); //if your not logged in display an error
        }
        display();  // update the GUI
    }

    /**
     * Any other key results in an error message and a reset of the GUI, this is the default in the controller 
     */ 
    public void processUnknownKey(String action){
        // unknown button, or invalid for this state - reset everything
        Debug.trace("Model::processUnknownKey: unknown button \"" + action + "\", re-initialising");
        // go back to initial state
        initialise("Invalid command");
        display();
    }

    /**
     * This is where the Model talks to the View, by calling the View's update method
     * The view will call back to the model to get new information to display on the screen
     * Everytime the screen updates it checks for two things
     * 1) is if the ccB (change currency) button is clicked and if it is update the cc window. <br>
     * 2) The second thing it checks for is if intOverflow is happing by calling a method.
     */
    public void display(){
        Debug.trace("Model::display");
        view.update(); // this updates the view in window 1
        // This checks if the button is click then updates the second window (cc). I did this to save system resources so it dosnt work on the background even though I don't need it.
        if(controller.getCcB() == true){
            cc.update();
        }
        intOverflow(); //calls the intOverflow method
    }

    
    /**
     * This is a way to play audio in java.
     * AudioClip is a class that you import that plays sound, this is mainly used for sound rather than music with minimal latency, if I was going to play
     * music I would use MediaPlayer because you dont want to play the music mulitple times simultaneously, but I want to play a sound everytime you click a
     * button simulatanusly thats why I used AudioClip.
    */
    public void audio(){
        AudioClip atmSound = new AudioClip(new File("sound/noise.mp3").toURI().toString()); //Makes an object of AudioClip called atmSound
                                                                                            //This takes in a file through making another object of File
                                                                                            //and adding where my file is located. 
                                                                                            // .toURL() the path name into actual URL I did this becuase
                                                                                            //AudioClip can only play urls strings and then converted it 
                                                                                            //into a string so AudioClip can play it
                                                                        
        atmSound.play(); // This method tells AudioClip to play the sound
    }

    /**
    * This stops an interger overflow from happing by checking if the number is bigger or smaller than another number.
    * Before you could withdraw a negative number so you are adding money. This is a big vulnerability and can be really dangerous if a bad actor was using this system.
    * It clears the screen and the number after it reaches a certain limmit.
    * An int can hold 2,147,483,647 but because this checks it everytime the screen is update rather than before it is updated I had to remove a digit just
    * in case, but to still make use of an int I used the maximum number with that removed digit
    * int can hold 10 digits and what I made the limmit can only hold 9 digits
    */
    public void intOverflow(){
        // This checks if number is bigger or equals to 999999999 or smalle than 0 if it is it clears the screen
        if(number >= 999999999 || number < 0){
            processClear();
            display();
        }
    }

    
    /**
     * This writes the withdraw history to a file the location is historyFile which is each accounts file according to there file number
     * @throws IOException incase the file is not found this has subclasses like FileNotFoundException which is what catches the error if the file is not foud
     * An exception is when a there is a problem while running the program instead of the program stopping it solves the problem, in this case, if the file 
     * can't be created or opened it throws an exception.
     */
    public void file() throws IOException{
        //FileWriter is a way to write to a file, you make an object of the class and give in the file you want to edit.
        //historyFile is the location where it adds the file and its dynamic according to what account is logged in to.
        //If the file dosn't exist FileWriter creats a file for you. 
        FileWriter writer = new FileWriter(historyFile); 
        // This loops through the arraylist every iteam in array eachHistory and 
        for (int eachHistory : wdHistory){
            // to write a file you use the .write method and give in what you want to write
            //System.lineSeparator() this addeds a new line everytime something is written to the file
            writer.write(eachHistory + System.lineSeparator());
        } 
        
        //You have to close the file so it saves that changes and dosnt take up unnecessary system resource 
        writer.close();
    }

    
    /**
     * This gets the history from the arraylist this is similar to how you write files, and add the total spend 
    */
    public void showHistory(){
        if(!(state.equals(ACCOUNT_NO) || state.equals(PASSWORD))){
        int totalSpend = 0; //this adds you all that you spend in the arraylist 
        display2 = "This is your statment\nTo view your full history go to file/your account name";
        for (int eachHistory :  wdHistory){
            // Everytime it loops through it adds eachHistory.
            //IMPORTANT: use += so the display dosn't get overwritten, original this read it from a file but you had to close the program for it to refresh,so I used this instead
            display2 += "\n£"+ eachHistory;
            display();
            //This adds up the total spend
            totalSpend += eachHistory;
        }
         //This says the current account balance in the mini statment 
        display2 += "\nYou spend a total of £" + totalSpend + "\nYour balance is £" +bank.getBalance();
        display();
        
       
    }else{
        display2 = "Please login";
        display();
    }
    }

    /**
     * This method changes the mode of the display (light mode/ dark mode), if clicked once it sets the boolean is false so it changes to light mode and changes
     * boolean to true, which changes it to dark mode and changes the mode.
    */
    public void changeMode(){
        // if mode is false and it is click change to light mode
        if(!controller.getMode()){ 
            view.grid.setId("Layout1"); //the colour is changed in css so all this does is change the id
            view.title.setId("Label1"); // the colour is changed in css so all this does is change the id 
            display1 = "Light mode";
            display(); //updates the display
            controller.setMode(true); // makes the boolean true
        }else {
            normalMode(); // calls a method named normal mode that changes the css back and makes it dark
            controller.setMode(false); // makes boolean false
        }

    }

    public void normalMode(){
        // changes the css back and makes it dark mode.
        view.grid.setId("Layout");
        view.title.setId("Label");
        display1 = "Dark mode"; // displays the mode in screen1
        display(); // updates the display
    }  

    
    /**
     * This method changes state to allow to change password.
     * It checks if the user is logged in or is already in the CHECK_PASS state and then sets the state to CHECK_PASS.
    */
    public void changePassword() {
        if(!(state.equals(ACCOUNT_NO) || state.equals(PASSWORD))) {
            display2 = "To change your password, please enter your current pasword, followed by Ent";
            display();
            setState(CHECK_PASS); //Changes state to CHECK_PASS if state.equals LOGGED_IN or is already in CHECK_PASS (incase you click the button twice).
        }else{
            display2 = "Please login"; // If not it tells the use to login
            display();
        }
    }

    /**
     * This lets you know what each button does.
     * This uses the same trick as the mode changer where if you click it twice it goes to the current state.
     * This also uses the emoji that I made in the view 
    */
    public void whatButton(){  
        if(!controller.getHelp()){
            // If getHelp is false it tells the users what everything does and make setHelp true so when clicked again it resets the state
            display2 = "Hello there" + view.getEmoji() + " here I will explain what each button does!\n*DEL - Delete only one number\n*CLR - Clears all the numbers\n*? - Help\n*Dep - Deposit\n*W/D - Withdraw\n*Bal - Balance\n*W/H - Withdraw history\n*MOD - Mode either dark or light theme\n*CC - Currency converter\n*CP - Change Password\nEXT - goes to the pervious state"; 
            display();
            audio();
            controller.setHelp(true); 
        }else{
            resetState();
            controller.setHelp(false); // makes it false so when clicked again it displays the message
        }
    }

    /**
     * This gets you back to the previous state and exits out of the current state.
    */
    public void resetState(){
        switch (state){
            case ACCOUNT_NO:
                display2 =  "Enter your account number\n" +"Followed by \"Ent\"\n If you want to know what each button does click on the ?";
                display();
                setState(ACCOUNT_NO);
                break;
            case PASSWORD:
                display2 =  "Enter your account number\n" +"Followed by \"Ent\"\n If you want to know what each button does click on the ?";
                display();
                setState(ACCOUNT_NO);
                break;
            default: 
                display2 =  "Welcome to your account";
                display();
                setState(LOGGED_IN);
                break;
                
        }

    
    }
    
    
    /**ArrayList method to clear all the arrays I use this in the loggout process.*/
    public void clearWd(){
        wdHistory.removeAll(wdHistory);
    }
    
    public void ext(){
        resetState(); //calls the method resetState to get you to the start of the state.
        display();  // update the GUI
        number = 0;
        display1 = "";
    }
}