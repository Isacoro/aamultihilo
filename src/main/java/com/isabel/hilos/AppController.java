package com.isabel.hilos;

import com.isabel.hilos.util.Alertas;
import com.isabel.hilos.util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AppController {

    public TextField tfUrl;
    public VBox panel;
    private List<DescargaController> descargas;
    public Button btVerDescargas;
    public TextArea taMostrar;
    private ArrayList<DescargaController> downloads;

    private static final Logger logger = LogManager.getLogger(AppController.class);


    public AppController(){
        descargas = new ArrayList<>();
    }


    @FXML
    public void iniciarDescarga(ActionEvent event) {
        //Recoge lo que se escribe en la caja de texto de descarga
        String urlText = tfUrl.getText();

        //Limpio caja de texto para más descargas
        tfUrl.clear();

        try {
            //Carga de la pantalla de descarga
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUI("hilos.fxml"));
            DescargaController descargaController = new DescargaController(urlText);
            loader.setController(descargaController);
            VBox hilo = loader.load();

            panel.getChildren().add(hilo);

            descargas.add(descargaController);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    public void verDescargas(ActionEvent event){
       taMostrar.setText("");
       leerLog();
    }

    public void leerLog(){

        try{
            String path = "multidescargas.log";
            File archivo = new File(path);
            BufferedReader lee = new BufferedReader(new FileReader(archivo));
            String linea = lee.readLine();

            while (linea != null){
                taMostrar.appendText(linea + "\n");
                linea = lee.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


