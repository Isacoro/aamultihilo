package com.isabel.hilos;

import com.isabel.hilos.util.Alertas;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DescargaController implements Initializable {

    public TextField tfUrl;
    public Label lbEstado;
    public ProgressBar pbProgreso;
    public Button btEliminar, btCancelar, btGuardar, btIniciar;
    private String urlText;
    private DescargaTask descargaTask;
    private File file;
    static Path rutaArchivo;

    private static final Logger logger = LogManager.getLogger(DescargaController.class);


    //Constructores
    public DescargaController(String urlText){
        this.urlText = urlText;
    }

    public DescargaController() {

    }
    public DescargaController(Path rutaArchivo){
        this.rutaArchivo = rutaArchivo;
    }

    public static File rutaArchivo(File filesAndDirectories) {
        return filesAndDirectories;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUrl.setText(urlText);
    }

    @FXML
    public void guardar(ActionEvent event) {
        tfUrl.setText(urlText);

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(tfUrl.getScene().getWindow());

        //Recojo ruta que ha elegido el usuario para guardar el archivo de descarga
        Path path = Paths.get(String.valueOf(file));
        rutaArchivo = path.toAbsolutePath();
        System.out.println(rutaArchivo);//Funciona. Tengo la ruta

        if (file == null)
            return;

        iniciar(event);

    }


    @FXML
    public void iniciar(ActionEvent event){
        File file = new File(String.valueOf(this.file));
        
        try {
            
            descargaTask = new DescargaTask(urlText, (file));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //Tarea y barra de progreso
                pbProgreso.progressProperty().unbind();

                //Vincula la barra de progreso con la tarea
                pbProgreso.progressProperty().bind(descargaTask.progressProperty());

                descargaTask.stateProperty().addListener((observableValue, viejoEstado, nuevoEstado) -> {
                    System.out.println(observableValue.toString());

                    if(nuevoEstado == Worker.State.SUCCEEDED){
                        Alertas.avisarInformacion("La descarga ha finalizado");
                    }
                });

                descargaTask.messageProperty().addListener((observableValue, viejoValor, nuevoValor) -> {
                    lbEstado.setText(nuevoValor);

                });

                new Thread (descargaTask).start();
        }
    

    @FXML
    public void cancelar(ActionEvent event){
        logger.trace("Descarga: " + tfUrl + " cancelada");
        if(descargaTask != null)
            descargaTask.cancel();
    }

    @FXML
    public void eliminar(ActionEvent event){
        cancelar(event);
      Parent panel = btEliminar.getParent().getParent().getParent();
      Parent descarga = btEliminar.getParent().getParent();

        VBox vBox = (VBox)panel;
        vBox.getChildren().remove(descarga);
    }
}
