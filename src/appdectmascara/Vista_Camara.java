/*
 
 */
package appdectmascara;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;


/**
 *
 * @author Luis Alfredo Villera
 */
public class Vista_Camara extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form Vista_Camara
     */
    static VideoCapture webCam;
    public BufferedImage buff;
   Detecta miDetector = new Detecta();
    Mat imagenDeWebCam = new Mat();
    AppDectMascara c=new  AppDectMascara();
   
    
     
    public Vista_Camara() {
         
      
        initComponents();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new Thread((Runnable) this).start();
       
    }

    
    public void iniciarCamara(){
    
      webCam = new VideoCapture();
    }
    
 public void timer(){
 
 }
    
    public void apagarcam(){
        try {
            
            webCam.release();
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Vista_Camara.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
  
    
    
    
   
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (buff == null) {
            return;
        }

        g.drawImage(buff, 10, 20, buff.getWidth(), buff.getHeight(), null);

    }

   
     
    @Override
    public void run() {
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         
        try{
        webCam.open(0);
        
        
       
        
        if (webCam.isOpened()) {
             
            while (true) {
                webCam.read(imagenDeWebCam);
                if (!imagenDeWebCam.empty()) {
                    // Invocamos la rutina de opencv que Mdetecta rostros sobre la imagen obtenida por la webcam
                    imagenDeWebCam = miDetector.Mdetecta(imagenDeWebCam);
                    // Muestra la imagen
                    MatToBufferedImage(imagenDeWebCam);
                    this.repaint();
                } else {
                    //System.out.println("No se capturó nada");
                    break;
                }
            }
        }else{
           // System.out.println("hay un error de lectura de imagen");
        }
        
        webCam.release(); // Se libera el recurso de la webcam
        }catch(NullPointerException npe){
           
            
            c.alCamara("la camara se iniciara en segundos");
        }
    
    }

    
    
    void MatToBufferedImage(Mat mat) {

        int altura = mat.width();
        int largura = mat.height();
        int canais = mat.channels(); //RGB

        byte[] source = new byte[altura * largura * canais];
        mat.get(0, 0, source);
        buff = new BufferedImage(altura, largura, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] saida = ((DataBufferByte) buff.getRaster().getDataBuffer()).getData();
        System.arraycopy(source, 0, saida, 0, source.length);

    }

    /**
     
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
