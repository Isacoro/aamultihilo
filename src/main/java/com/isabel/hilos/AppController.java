package com.isabel.hilos;

import com.isabel.hilos.util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AppController {

    public TextField tfUrl;
    public VBox panel;
    private List<DescargaController> descargas;
    public Button btVerDescargas;

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
        btVerDescargas.setText(String.valueOf(DescargaController.rutaArchivo));
        FileChooser archivo = new FileChooser();
        archivo.showOpenDialog(btVerDescargas.getScene().getWindow());
        //No he conseguido coger aquí la ruta exacta
    }
}


