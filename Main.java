import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * <h1>Introduction to Programming CI401 assignment, ATM project</h1>
 * <h2>Main Class</h2>
 * @author Minas Fakhori
 
*/

public class Main extends Application{
 
    
    public static void main( String args[] ){
        
        // The main method only gets used when launching from the command line
        // launch initialises the system and then calls start
        // In BlueJ, BlueJ calls start itself
        launch(args);
    }

    
    /**
    * This is the thing that puts everything together.
    * It starts the window.
    * @param window this open the main GUI window 
    * @throws java.io.IOException this is done incase a failure in Input & Output, for this program it throws a failure if it dosn't finds window (output)
    */
    public void start(Stage window) throws java.io.IOException{
        // set up debugging and print initial debugging message
        Debug.set(true);             
        Debug.trace("atm starting"); 
        Debug.trace("Main::start"); 
        
        // Create a Bank object for this ATM
        Bank b = new Bank();
        
        
        /** b.addBankAccount is used to add bank accounts from the bank method.
        * To make a new account you need to type b.addBankAccount with the parameters it asks for. 
        * @param accountnumber
        * @param accountpassword
        * @param balance
        * @param overdraft 
        * @param dateOfBirth
        * @param withdraw limmit per day
        */
        // adding bank accounts
        b.addBankAccount(10001, 11111, 10000,20, 29042001 ,10);
        b.addBankAccount(10002, 22222, 50,0,14021999, 5);
        b.addBankAccount(11111,11111,10,10, 30052000,6);

        // This creates the Model, View, Controller, Popup, CC and CCmodel objects
        Model model = new Model(b);   // The model needs the Bank object to 'talk to' the bank, so you pass the bank object inside the model object.
        View  view  = new View(); 
        Controller controller  = new Controller();
        CC cc = new CC();
        CCmodel ccm = new CCmodel();
        

        // Link them together so they can talk to each other
        // Each one has instances variable for the other two
        model.view = view;
        model.controller = controller;
        model.cc = cc;
        
        controller.model = model;
        controller.view = view;
        controller.cc = cc;
        controller.ccm = ccm;
        
        view.model = model;
        view.controller = controller;
        
        
        cc.model = model;
        cc.controller = controller;
        
        ccm.model = model;

        // start up the GUI (view), and then tell the model to initialise and display itself
        view.start(window);
        model.initialise(""); 
        model.display();   

        // application is now running
        Debug.trace("atm running"); 
        
       window.setTitle("Bank ATM"); //This creates the top bar title for the window. 
       window.getIcons().add(new Image("img/atm.png")); //This puts the image in the top bar of the window.
       
    }
    
    
}
