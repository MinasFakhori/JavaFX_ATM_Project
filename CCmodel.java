import javafx.stage.Stage;
import javafx.stage.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.media.AudioClip;

/**
 * <h2> CCmodel class (currency converter model)</h2>
 * @author?Minas Fakhori
 * This class has everything to do with the second window and is the actual actions that happen when the methods are called.
*/
public class CCmodel {
    Model model; 
    
    /**
     * This method clears the display and sets a message to the user so they know what to do.
    */
    public void newDisplay(){
        model.processClear();
        model.setDisplay2("Enter any number followed by the currency.");
        model.display();
    }
   
    /**
     * This method gets the price of ethereum (at the time I made this) and say what it would be in gbp. 
     * Two ways I can improve this is:
     * 1)By getting the live price of ethereum <br>
     * 2) By letting them conver the other way around
    */
    public void eth(){
        int gbp = 2500 * model.getNumber(); //2500 is the current price of eth and that is muliplied by the number the user puts in
        // If statment to see if it will cause an integer overflow 
        if(gbp >= Integer.MAX_VALUE || gbp < 0){
            model.setDisplay2("Number too big, try a smaller value");
            model.display();
        }else{
            //If the number dosn't cause a int overflow it checks if the number of eth this is done to get the right grammer
        if(model.getNumber() > 1){
        model.setDisplay2("" + model.getNumber() +" Ethereum's are equal to £" + gbp);
        }else{
        model.setDisplay2("" + model.getNumber() +" Ethereum is equal to £" +  gbp);
        }
        model.setNumber(0); // This sets the number to 0 again  
        model.setDisplay1(" "); //Makes the display1 empty of the next conversion  
        model.display();
        
        }
    }
    
    /**
     * The btc method is the same as the eth method but gets the price of Bitcoin (at the time I made this) and say what it would be in gbp.
     */
    public void btc(){
        int  gbp = 35000 * model.getNumber();
        if(gbp >= Integer.MAX_VALUE || gbp < 0){
            model.setDisplay2("Number too big, try a smaller value");
            model.display();
        }else{
            if(model.getNumber() > 1){
                model.setDisplay2("" + model.getNumber() +" Bitcoin's are equal to £" +  gbp);
            }else{
                model.setDisplay2("" + model.getNumber() +" Bitcoin is equal to £" +  gbp);
            }
        model.setNumber(0);
        model.setDisplay1(" ");
        model.display();
        }
    }
    
  
    /**
     * The usd method is the same as the eth method but gets the price of usd (at the time I made this) and say what it would be in gbp.
     */
    public void usd(){
        double  gbp = 0.76 * model.getNumber();
        if(model.getNumber() > 1){
            model.setDisplay2("$" + model.getNumber() +" are equal to £" +  gbp);
        }else{
         model.setDisplay2("$" + model.getNumber() +" is equal to £" +  gbp);
        }
        model.setNumber(0);
        model.setDisplay1(" ");
        model.display();
    }
    
    /**
     * The eur method is the same as the eth method but gets the price of eur (at the time I made this) and say what it would be in gbp.
     */
      public void eur(){
        double  gbp = 0.84 * model.getNumber();
        if(model.getNumber() > 1){
            model.setDisplay2("€" + model.getNumber() +" are equal to £" +  gbp);
        }else{
             model.setDisplay2("€" + model.getNumber() +" is equal to £" +  gbp);
        } 
        model.setNumber(0);
        model.setDisplay1(" ");
        model.display();
    }
    
    /**
     * The toTheMoon method adds a sounds that says to the moon this was the same as the other AudioClip I just changed the file 
    */
    public void toTheMoon(){
        AudioClip toTheMoon = new AudioClip(new File("sound/tothemoon.mp3").toURI().toString());
        toTheMoon.play();
    }
    
  
}
