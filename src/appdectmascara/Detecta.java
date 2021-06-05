/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdectmascara;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Luis Alfredo Villera
 */
class Detecta {

    AppDectMascara gu = new AppDectMascara();

   
    
    private CascadeClassifier clasificador_Tapabocas;
    private CascadeClassifier clasificador_Rostros;
    private CascadeClassifier clasificador_Ojos;
    static int var2 = 12;

    public Detecta() {
          ////File nos trae la ruta completa de los archivos
        File xml1 = new File("Tapabocas.xml");
        File xml2 = new File("cara_frontal.xml");
        File xml3 = new File("ojos.xml");
        // System.out.println(" " + xml1.getAbsolutePath());
        //System.setProperty("java.library.path", "C:\\Users\\Luis Alfredo Villera\\Documents\\NetBeansProjects\\AppDectMascara\\src\\appdectmascara");
        // System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);// libreria de opencv para leer archivos en la pc

        //Se leen los archivos Haar que le permite a OpenCV detectar lo que deseamos y los colocamosen clasificadores
        clasificador_Tapabocas = new CascadeClassifier(xml1.getAbsolutePath());
        clasificador_Rostros = new CascadeClassifier(xml2.getAbsolutePath());
        clasificador_Ojos = new CascadeClassifier(xml3.getAbsolutePath());

        if (clasificador_Tapabocas.empty()) {
            //System.out.println("Error de lectura.");
            return;
        } else {
            //System.out.println("Detector de tapabocas leido.");
        }
        if (clasificador_Rostros.empty()) {
            //System.out.println("Error de lectura.");
            return;
        } else {
            //System.out.println("Detector de rostros leido.");
        }
        if (clasificador_Ojos.empty()) {
            //System.out.println("Error de lectura.");
            return;
        } else {
            //System.out.println("Detector de Ojos leido.");
        }

    }

    public void clasifica() {
      
    }

    //cambiar valor de variables 
    public void CambioValorVariable(int vari2) {
        var2 = vari2;
    }

    // con este metodoprocesamos la imagen y dibujamos los rectanculos , ademas obtenemos la cantidad de ellos que se generan
    public Mat Mdetecta(Mat frameDeEntrada) {
        Mat mRgba = new Mat();
        Mat mGrey = new Mat();
        MatOfRect tapabocas = new MatOfRect();
        MatOfRect rostros = new MatOfRect();
        MatOfRect ojos = new MatOfRect();
        frameDeEntrada.copyTo(mRgba);
        frameDeEntrada.copyTo(mGrey);
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        //System.out.println("hajhashjlsafu" +var2);
        clasificador_Tapabocas.detectMultiScale(mGrey, tapabocas,1.5, var2);
        clasificador_Rostros.detectMultiScale(mGrey, rostros, 1.5, 3);
        clasificador_Ojos.detectMultiScale(mGrey, ojos, 1.5, 8);

        for (Rect rect : tapabocas.toArray()) {

            //Se dibuja un rectángulo donde se ha encontrado 
            Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255));

        }
        for (Rect rect : rostros.toArray()) {

            //Se dibuja un rectángulo donde se ha encontrado 
            Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 255));

        }

        for (Rect rect : ojos.toArray()) {

            //Se dibuja un rectángulo donde se ha encontrado 
            Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 0));

        }

        int can_Tapabocas = tapabocas.toArray().length;
        int can_Rostro = rostros.toArray().length;
        int can_Ojos = ojos.toArray().length;
        gu.pintar_Alertas(can_Tapabocas, can_Rostro, can_Ojos);
        return mRgba;
    }
}
