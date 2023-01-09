/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlintegritychecker;

import java.io.IOException;
import java.util.NoSuchElementException;

public class TH implements Runnable {

    @Override
    public void run() {
        while (!Main.q.isEmpty()) {

            String tempLink = (String) Main.q.remove();                 //take URL from the queue q and put it in templink
            try {
                if (Main.checkValidity(tempLink)) {
                    Main.extractLinks1(tempLink, Main.q2);             //extract links in each url to q2 in case if needed in bigger depth
                }
            }
            
            catch (IOException ex) {
            }
        }
        
    }

}
