import java.util.ArrayList;

/**
 * <h2>Bank class</h2> 
 * This is like a middle man between bank accounts and model. This also has the list of bank and the accounts we are logged in to.
 * @author Minas Fakhori
 */

// Bank class - simple implementation of a bank, with a list of bank accounts, and current account that we are logged in to.

 public class Bank{
    //Instance variables containing the bank information
    private ArrayList<BankAccount> accounts = new ArrayList<>(); // arraylist to hold the bank accounts
    BankAccount account = null; // currently logged in account ('null' if no-one is logged in)

    // Constructor method - this provides a couple of example bank accounts to work with
    public Bank()
    {
        Debug.trace( "Bank::<constructor>");        
    }

    // a method to create new BankAccounts - this is known as a 'factory method' and is a more
    // flexible way to do it than just using the 'new' keyword directly.
    
    /** 
     * This is a method to create new BankAccounts, using the factory method rather than using the new keyword directly
     * @param accNumber (int) BankAccount account number
     * @param pinPsswd (int)  BankAccount acount password
     * @param balance (int)  BankAccount the balance for that account 
     * @param overDraft (int) BankAccount the overdraft limmit for that account 
     * @param dob (int) BankAccount the date of birth for the person that owns that bank account 
     * @param wdLimmit (int) How many withdraws can a user make per day.
     * @return accNumber, accPasswd, balance , dob, wdLimmit
    */
    public BankAccount makeBankAccount(int accNumber, int accPasswd, int balance, int overDraft, int dob, int wdLimmit) 
    {
        return new BankAccount(accNumber, accPasswd, balance, overDraft ,dob,wdLimmit );
    }
    
    // a method to add a new bank account to the bank - it returns true if it succeeds
    // or false if it fails (it never fails, in this implementation)
    
    /** 
     * a method to add a new bank account to the bank - it returns true if it succeeds or false if it fails (it never fails, in this implementation)
     * @param a BankAccount
     * @return true
    */
    public boolean addBankAccount(BankAccount a)
    {
        accounts.add(a);
        //Debug.trace( "Bank::addBankAccount: added " + a.accNumber +" "+ a.accPasswd +" £"+ a.balance + " £" + a.overDraft);
        return true;            
    }
    
    /**
     * a variant of addBankAccount which makes the account and adds it all in one go.
     * Using the same name for this method is called 'method overloading' - two methods
     * can have the same name if they take different argument combinations 
    */
    
    public boolean addBankAccount(int accNumber, int accPasswd, int balance, int overDraft, int dob, int wdLimmit){
        return addBankAccount(makeBankAccount(accNumber, accPasswd, balance, overDraft, dob , wdLimmit));
    }    
    
    
    /* Check whether the current saved account and password correspond to 
     an actual bank account, and if so login to it (by setting 'account' to it)
     and return true. Otherwise, reset the account to null and return false
    */
     /**
     * @param newAccNumber checks if the account number is the same as is the same as the actual account 
     * @param newAccPasswd checks if the password is the same as is the same as the actual password for that account
     * @return true/false
    */
    public boolean login(int newAccNumber, int newAccPasswd) { 
        Debug.trace( "Bank::login: accNumber = " + newAccNumber);       
        logout(); // logout of any previous account

        // search the array to find a bank account with matching account and password.
        // If you find it, store it in the variable currentAccount and return true.
        // If you don't find it, reset everything and return false
        
        for (BankAccount b: accounts) {
            if (b.getAccNumber() == newAccNumber && b.getAccPasswd() == newAccPasswd) {
                // found the right account
                Debug.trace( "Bank::login: logged in, accNumber = " + newAccNumber ); 
                account = b;
                return true;
            }
        } 
         account = null;
        return false;
    }
    
    
   
    /**
     * This method checks if the user input is the same as the actual password and the date of birth before you can your password.
     * To use this method you call it in the model and give it two paramiters both int and make it do something if it returns true or false
     * It loops through the accounts that are available  to find your account, this could be a bad way to do it because if two people have the same password and birth date
     * But that chances of that are low 
     * It uses a simple if statments to check if both the account password and the date of birth are correct.
     * @param newAccPasswd (int) checks if the password for that account is the same as the number the user gives.
     * @param dob (int) checks if the date of birth is the same as what the user has entered (<= this is done in the model)
     * @return true/false
     */
    
    
    public boolean changePassAuth(int newAccPasswd , int dob) { 
        for (BankAccount b: accounts) {
            if (b.getAccPasswd() == newAccPasswd && b.getDob() == dob) {
                account = b;
                return true;
            }
              
            
    }
     return false; 
    }
    
    /**
     * This method takes the current password and changes it with another password it calls the method from account (from BankAccount class).
     * You use this in the model and call the method and pass the new int password.
     * @param newPass (int) the new password
     */
     public void newPass(int newPass) { 
        account.setAccPasswd(newPass);
    }
        
        
      
    /**
     * This method resets bank to be null - logged out state. This is done by calling the account (from BankAccount class) method logout.
     */
    // Reset the bank to a 'logged out' state
    public void logout() 
    {
        if (loggedIn())
        {
            Debug.trace( "Bank::logout: logging out, accNumber = " + account.getAccNumber());
            account = null;
        }
    }
    
    
    /**
     * This method simply checks wether your logged in or not using a simpel if statment, to check if it is equals to null
     * @return true/false
     */
    // test whether the bank is logged in to an account or not
    public boolean loggedIn()
    {
        if (account == null){
            return false;
        }else {
            return true;
        }
    }   
    
    
    /**
     * This deposits amount into acount if the loggedIn method returns true, it calls the account (from BankAccount class) method depsoit.
     * @param amount (int) The amount you want to deposit 
     * @return true/false
     */
    // try to deposit money into the account (by calling the deposit method on the 
    // BankAccount object)
    public boolean deposit(int amount) 
    {
        if (loggedIn()) {
            return account.deposit(amount);   
        } else {
            return false;
        }
    }
    
    
    
    
     /**
     * This withdraws amount from acount if the loggedIn method returns true, it calls the account (from BankAccount class) method withdraw.
     * @param amount (int) The amount you want to withdraw 
     * @return true/false
     */
    // try to withdraw money into the account (by calling the withdraw method on the 
    // BankAccount object)
    public boolean withdraw(int amount) {
        if (loggedIn()) { 
        return account.withdraw(amount);
        } else {
            return false;
        }
    }
    
    
    /**
     * This gets the balance if the loggedIn method returns true, it calls the account (from BankAccount class) method getBalance.
     * @return balance/-1 balance the account balance. -1 means an error. ?
     */
    // get the account balance (by calling the balance method on the 
    // BankAccount object)
    public int getBalance() {
        if (loggedIn()) {
            return account.getBalance();   
        } else {
            return -1; // use -1 as an indicator of an error
        }
    }
    
      /**
     * This gets the overdraft if the loggedIn method returns true, it calls the account(from BankAccount class) method getOverDraft.
     * @return overdraft/-1 balance the account overdraft limmit. -1 means an error. ?
     */
     public int getOverDraft() {
        if (loggedIn()) {
            return account.getOverDraft();   
        } else {
            return -1; // use -1 as an indicator of an error
        }
    }
    
    
    /**
    * This gets the wdLimmit from another getter from the account class
    * @return account.getWdLimmit()
    */
    public int getWdLimmit(){
        return account.getWdLimmit();
    }
    
    
     /**
    * This sets the wdLimmit from another setter from the account class
    * @param account.setWdLimmit()
    */
    public void setWdLimmit(int wdLimmit){
        account.setWdLimmit(wdLimmit);
    }
}