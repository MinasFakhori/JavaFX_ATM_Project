import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.image.Image;

/**
 * <h2> CC (currency converter) class </h2>
 * @author Minas Fakhori
 * This class creates the seconds window for the currency conversion
 * This creates and manages the GUI and extends View so I don't have to reassign all the variables that I assigned in view 
*/

public class CC extends View{
    /**
    * This method has everything to do with this window its similar to the view     
    */
    public void  ccWindow() {
        int H = 540;         // Height of window pixels 
        int W = 650;         // Width  of window pixels 
   
        // layout objects
        grid = new GridPane();          // Creates a new instance of GridPane (layout)
        grid.setId("Layout");           // assign an id to be used in css file
        buttonPane = new TilePane();     // Creates a new instance of buttonPane (buttons)
        buttonPane.setId("Buttons");    // assign an id to be used in css file
        
        
        // controls
        title  = new Label();           // Message bar at the top for the title
        grid.add( title, 0, 0);         // Add to GUI at the top
        
        
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
                {"7",    "8",  "9",  "",  "",  ""},
                {"4",    "5",  "6",  "",  "$",  "€"},
                {"1",    "2",  "3",  "",  "BTC",  "ETH"},
                {"DEL",  "0",  "CLR",   "","",""}};
                
    
        // loop through the array, making a Button object for each label 
        // (and an empty text label for each blank space) and adding them to the buttonPane
        // The number of button per row is set in the css file, not the array.
        for ( String[] row: labels ) {
            for (String label: row) {
                if ( label.length() >= 1 ) {
                    // non-empty string - make a button
                    Button b = new Button( label );
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
        Stage window2 = new Stage();
        window2.initModality(Modality.APPLICATION_MODAL); // This makes the other windows in the programm freeze unitl this is dismissed, I did this because
                                                          // Most atms dont have much ram so to save system resources I freeze the other windows. 
        window2.setTitle("Currency Converter"); //This sets the title of the app
        window2.getIcons().add(new Image("img/btc.png")); // This sets the icon of the app
        Scene scene = new Scene(grid, W, H);   
        scene.getStylesheets().add("atm.css"); // tell the app to use our css file
        window2.setScene(scene); 
        window2.show(); // This is done to show the window so when the method is called it runs all the code and shows this window
    }


    // This is how the View talks to the Controller
    // This method is called when a button is pressed
    // It fetches the label on the button and passes it to the controller's process method
    
   

    // This is how the Model talks to the View
    // This method gets called BY THE MODEL, whenever the model changes
    // It fetches th title, display1 and display2 variables from the model
    // and displays them in the GUI
    public void update()
    {        
        if (model != null) {
            Debug.trace( "View::update" );
            String message1 = model.getCTitle();        // get the new title from the model
            title.setText(message1);              // set the message text to be the title
            String message2 = model.getDisplay1();     // get the new message1 from the model
            message.setText( message2);          // add it as text of GUI control output1
            String message3 = model.getDisplay2();     // get the new message2 from the model
            reply.setText( message3 );            // add it as text of GUI control output2
        }
    }
    
 
}
