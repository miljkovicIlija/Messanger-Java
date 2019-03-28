
package mesanger2.pkg0;

import java.io.IOException;
import javax.swing.JFrame;


public class Mesanger20 {

    public static void main(String[] args)throws IOException, ClassNotFoundException {
        Server app=new Server(); 
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        app.start();
        
    }
    
}
