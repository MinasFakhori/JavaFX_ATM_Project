// The View class creates and manages the GUI for the application.
// It doesn't know anything about the ATM itself, it just displays
// the current state of the Model, (title, output1 and output2), 
// and handles user input from the buttonsand handles user input

// We import lots of JavaFX libraries (we may not use them all, but it
// saves us having to thinkabout them if we add new code)
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import javafx.scene.Group;
import javafx.collections.ObservableList;


/**
 * <h2>View Class</h2> 
 * @author Minas Fakhori <br>
 * This class creates and manages how the first window will look like. <br>
 * This has no knowlage of the bank.
 */
public class View{
    private int H = 540;         // Height of window pixels 
    private int W = 650;         // Width  of window pixels 

    // variables for components of the user interface
    Label      title;         // Title area (not the window title)
    TextField  message;       // Message area, where numbers appear
    TextArea   reply;         // Reply area where results or info are shown
    ScrollPane scrollPane;    // scrollbars around the TextArea object  
    GridPane   grid;          // main layout grid
    TilePane   buttonPane;    // tiled area for buttons
   
    
   
    

    // The other parts of the model-view-controller setup
    Model model;
    Controller controller;
    CC cc;
    
    
    
    /**String is a class type in java but java gives a shortcut where you don't have to write the new keyword,
    however, if you want it to take parameter you have to call it like a class.
    Character is also a class type unlike char which is a primative type, which calls a method called toChars that
    converts unicode into UTF-16 format.*/
     private String emoji = new String(Character.toChars(0x1f44b)); // This makes an emoji. 
    
    /**
     * I had to use getter because I wanted to access this variable in other classes, and it was private to follow with OOP(encapsulation) and to be more secure.
    */
     public String getEmoji(){
         return emoji;
     }
     
    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public View(){
        Debug.trace("View::<constructor>");
    }

    /** start is called from Main, to start the GUI up
    * Note that it is important to create controls etc here and
    * not in the constructor (or as initialisations to instance variables),
    * because we need things to be initialised in the right order
    * @throws java.io.IOException 
    */
    public void start(Stage window) throws java.io.IOException {
        Debug.trace("View::start");

        // create the user interface component objects
        // The ATM is a vertical grid of four components -
        // label, two text boxes, and a tiled panel
        // of buttons

        // layout objects
        grid = new GridPane();          //This creates a grid object 
        grid.setId("Layout");           // assign an id to be used in css file
        buttonPane = new TilePane();    //This creates a tilePane object
        buttonPane.setId("Buttons");    // assign an id to be used in css file
        
        // controls
        title  = new Label();           // Message bar at the top for the title
        grid.add( title, 0, 0);         // Add to GUI at the top
        title.setId("Label");           //This is to link the css to the title
        
        
      
        message  = new TextField();     // text field for numbers and error messages 
        message.setEditable(false);     // Read only (user can't type in)
        grid.add( message, 0, 1);       // Add to GUI on second row                      

        reply  = new TextArea();        // multi-line text area for instructions
        reply.setEditable(false);       // Read only (user can't type in)
        scrollPane  = new ScrollPane(); // create a scrolling window
        scrollPane.setContent( reply ); // put the text area 'inside' the scrolling window
        grid.add( scrollPane, 0, 2);    // add the scrolling window to GUI on third row

        // Buttons - these are laid out on a tiled pane, then
        // the whole pane is added to the main grid as the fourth row

        // Button labels - empty strings are for blank spaces
        // The number of button per row should match what is set in 
        // the css file
        
        String labels[][] = {
                {"7",    "8",  "9",  "",  "Dep",  "W/D"},
                {"4",    "5",  "6",  "",  "Bal",  "W/H"},
                {"1",    "2",  "3",  "",  "MOD",  "CC"},
                {"DEL",  "0",  "CLR",   "","?","CP"},
                {"EXT",  "",  "",   "Ent","","Logout"}};
                
        
        // loop through the array, making a Button object for each label 
        // (and an empty text label for each blank space) and adding them to the buttonPane
        // The number of button per row is set in the css file, not the array.
        for ( String[] row: labels ) {
            for (String label: row) {
                if ( label.length() >= 1 ) {
                    // non-empty string - make a button
                    Button b = new Button( label );
                    //if it finds Ent in the loop, it gives it, I didd this to be able to use in the css.
                    if (label.equals("Ent")){  
                        b.setId("enter");
                    }else if(label.equals("Logout") || label.equals("EXT")){
                        b.setId("logout");
                    }
                    b.setOnAction( this::buttonClicked ); // set the method to call when pressed
                    buttonPane.getChildren().add( b );    // and add to tiled pane
                } else {
                    // empty string - add an empty text element as a spacer
                    buttonPane.getChildren().add( new Text() ); 
    
                }
            }
        }
        
        grid.add(buttonPane,0,3); // add the tiled pane of buttons to the grid
        
        

        

        // add the complete GUI to the window and display it
        Scene scene = new Scene(grid, W, H);   
        scene.getStylesheets().add("atm.css"); // tell the app to use our css file
        window.setScene(scene);
        window.show();
    }

    // This is how the View talks to the Controller
    // This method is called when a button is pressed
    // It fetches the label on the button and passes it to the controller's process method
    public void buttonClicked(ActionEvent event) {
        // this line asks the event to provide the actual Button object that was clicked
        Button b = ((Button) event.getSource());
        if ( controller != null ){          
            String label = b.getText();   // get the button label
            Debug.trace( "View::buttonClicked: label = "+ label );
            try
            {
                // Try setting a breakpoint here
            controller.process( label );
            }
            catch (java.io.IOException ioe)
            {
                ioe.printStackTrace();
            }  // Pass it to the controller's process method
        }
    }
    
   

    // This is how the Model talks to the View
    // This method gets called BY THE MODEL, whenever the model changes
    // It fetches th title, display1 and display2 variables from the model
    // and displays them in the GUI
    public void update(){        
        if (model != null) {
            Debug.trace( "View::update" );
            String message1 = model.getTitle();        // get the new title from the model
            title.setText(message1);              // set the message text to be the title
            String message2 = model.getDisplay1(); // get the new message1 from the model
            message.setText( message2 );          // add it as text of GUI control output1
            String message3 = model.getDisplay2();     // get the new message2 from the model
            reply.setText( message3 );            // add it as text of GUI control output2
        }
    }
}
