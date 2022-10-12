/**
 * <h2>BankAccount class</h2>
 * This is the template for making and using the fetures of each account eg withdrawing.
 * This class has variables for the account number, password and balance, and methods to withdraw, deposit, check balance etc.
 * @author Minas Fakhori
 */


/**
 * OOP standards are that you have to use encapsulation, this is good as it stops other classes from accessing the private fields without permission.
 * You can controll if they have permission to read only or to write only, (using setters and getters).
 * This also makes the code more robust.
 * 
*/

public class BankAccount
{
    private int accNumber = 0;
    private int accPasswd = 0;
    private int balance = 0;
    private int overDraft = 0;
    private int dob;
    private int wdLimmit;
    
    /**
    *This constructor takes in a few parameters and sets them as class attributes. This is done because after the method runs the parameters disappears so this stores it in the class variable so it dosnt disappear. 
    *'this' is used to speacify that we want the class variables, to make java know what variable I am talking about.
    *To use this call this constructor and fill in the required values.
    * @see <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html">Constructors docs oracle</a>
    * @param a (int) this is the account number
    * @param?p (int) this is the account password
    * @param b (int) this is the balance
    * @param o (int) this is the overdraft
    * @param dob (int) this is the date of birth?
     
    */
    public BankAccount(int a, int p, int b , int o , int dob , int wdLimmit)
    {
        accNumber = a;
        accPasswd = p;
        balance = b;
        overDraft = o;
        this.dob = dob;
        this.wdLimmit = wdLimmit;
    }
    
    
    /**
    * This returns a boolean to see if the amount can be covered by the overDraft and the balance, and if amount is more than 0, it returns true and withdraws the money .
    * This also returns flase if the amount is less than 0.
    *@param amount (int) the amount the user puts in
    *@return true/false
    */
    // withdraw money from the account. Return true if successful, or 
    // false if the amount is negative, or less than the amount in the account 
    public boolean withdraw( int amount ) 
    { 
        Debug.trace( "BankAccount::withdraw: amount =" + amount ); 

        // CHANGE CODE HERE TO WITHDRAW MONEY FROM THE ACCOUNT
        if (amount < 0 || overDraft + balance < amount ) {
            return false;
        } else {
            balance = balance - amount;  // subtract amount from balance
            return true; 
        }
    }
    
    /**
    * This method returns a boolean to see if the amount is more than 0, if it is it returns true and depsit money .
    * This will returns flase if the amount is less than 0.
    *@param amount (int) the amount the user puts in
    *@return true/false
    */
    
    // deposit the amount of money into the account. Return true if successful,
    // or false if the amount is negative 
    public boolean deposit( int amount )
    { 
        Debug.trace( "LocalBank::deposit: amount = " + amount ); 
        // CHANGE CODE HERE TO DEPOSIT MONEY INTO THE ACCOUNT
        if (amount < 0) {
            return false;
        } else {
            balance = balance + amount;  // add amount to balance
            return true; 
        }
    }

    
    /**
     * This returns the balance when the method is called
     * @return balance 
    */
    // Return the current balance in the account
    public int getBalance() 
    { 
        Debug.trace( "LocalBank::getBalance" ); 

        // CHANGE CODE HERE TO RETURN THE BALANCE
        return balance;
    }
    
    /**
    * This will get the wdLimmit
    *  @return wdLimmit
    */
    public int getWdLimmit(){
        return wdLimmit;
    }
    
    /**
     * This will allow other methods to set the wdLimmit
     * param wdLimmit (int)
    */
    public void setWdLimmit(int wdLimmit){
        this.wdLimmit = wdLimmit;
    }
    
    
    
     /**
     * This returns the overdraft when the method is called
     * @return overdraft
    */
      public int getOverDraft() 
    { 
        Debug.trace( "LocalBank::getOverDraft" ); 

        // CHANGE CODE HERE TO RETURN THE BALANCE
        return overDraft;
    }
    
    /**
     * Getter - this method gets the account number 
     * return accNumber
    */
    public int getAccNumber(){
        return accNumber;
    }
    
     /**
     * Getter - this method gets the password
     * return accPasswd
    */
    public int getAccPasswd(){
        return accPasswd;
    }
    
     /**
     * Setter - this method sets the password
     * @param password (int)
    */
    public void setAccPasswd(int password){
        accPasswd = password;
    }
    
     /**
     * Getter - this method gets the dob
     * @return dob
    */
    public int getDob(){
        return dob;
    }
    
    
    
    
}
