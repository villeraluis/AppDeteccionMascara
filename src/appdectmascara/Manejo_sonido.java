/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdectmascara;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;

public class Manejo_sonido {

    public Manejo_sonido()  {
        
       
    }
    
    public void sonido(){
    
     try {
            InputStream direccion = getClass().getResourceAsStream("audioAlerta.mp3");
            Player player;
            BufferedInputStream bis = new BufferedInputStream(direccion);

            player = new Player(bis);

            player.play();

        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }
    
    
    
    
    
     
}
