import javafx.scene.control.Button;
import javafx.stage.Stage; 
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

/**
 * <h2>Controller class</h2>
 * This is the middle man between view and model.
 * This class gets input from view and calls a method in model.
 * @author Minas Fakhori
 */
// The ATM controller is quite simple - the process method is passed
// the label on the button that was pressed, and it calls different
// methods in the model depending what was pressed.
public class Controller{
    public Model model; //This gets the model class and makes a new instance of it
    public View  view;
    public CC cc;
    public CCmodel ccm;

    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public Controller()
    {
        Debug.trace("Controller::<constructor>");
    }

    // This is how the View talks to the Controller
    // AND how the Controller talks to the Model
    // This method is called by the View to respond to some user interface event
    // The controller's job is to decide what to do. In this case it uses a switch 
    // statement to select the right method in the Model
    
    
    private boolean mode = false; //This make mode have a boolean value that is true if the button is click, this changes the theme to be light or dark mode 
    private boolean ccB = false;
    private boolean help = false;
    
       public boolean getMode(){
        return mode;
    }
    
     public void setMode(boolean mode){
        this.mode = mode;
    }
    
    public boolean getHelp(){
        return help;
    }
    
     public void setHelp(boolean help){
        this.help = help;
    }
    
    public boolean getCcB(){
        return ccB;
    }
    
    /**
     * This is a massive switch statment that runs methods in the model and the cc model depending on what button is clicked
     * @param action
     * @throws java.io.IOException 
    */
    public void process( String action ) throws java.io.IOException{
        Debug.trace("Controller::process: action = " + action);
        switch (action) {
            case "1" :
            case "2": 
            case "3" :    
            case "4" :    
            case "5" :
            case "6" : 
            case "7" : 
            case "8" :      
            case "9" : 
            case "0" :
                model.audio();  //gets the audio() method from the model class
                model.processNumber(action); // gets the processNumber() method from model class and action is what you clicked.  
                break;
            case "CLR": 
                model.audio(); 
                model.processClear(); // method from model
                break;
            case "Ent":
                model.audio(); 
                model.processEnter();
                break;
            case "W/D":
                model.audio(); 
                model.processWithdraw();
                model.file();
                break; 
            case "Dep":
                model.audio(); 
                model.processDeposit();
                break;
            case "Bal":
                model.audio(); 
                model.processBalance();
                break; 
            case "DEL":
                model.audio(); 
                model.del();
                break;
            case "W/H":
                model.audio(); 
                model.showHistory();
                break;
            case "CC":
                ccB = true; // makes the button true that will update the display. 
                            //I did this because there where two diffrent windows, to save on system resources I stoped one from updating unitl it is clicked. 
                model.audio(); 
               cc.ccWindow(); //Opens a new window
               ccm.newDisplay(); //Makes it say diffent thigns on the display.
               break;
            case "ETH":
                ccm.eth(); // method from ccm (Currency converter model)
                model.audio();
                break;
             case "BTC":
                ccm.btc();
                ccm.toTheMoon(); // plays a special sound if btc is clicked
                break;
             case "$":
                 ccm.usd();
                 model.audio();
                 break;
              case "€":
                  ccm.eur();
                  model.audio();
                  break;
                case "MOD":
                        model.changeMode();
                    break;
                case "CP":
                    model.changePassword();
                    model.audio();
                    break;
                 case "?":
                      model.whatButton();
                     break;  
                 case "Logout":
                    model.audio(); 
                    model.processFinish();
                    break;
                  case "EXT":
                       model.audio(); 
                       model.resetState();
                       break;
                    default:
                model.processUnknownKey(action);  //This calles the processUnknownKey() to say that it didnt find the key that was clicked.
                break; 
        } 
        
        
    }
  
}

    

