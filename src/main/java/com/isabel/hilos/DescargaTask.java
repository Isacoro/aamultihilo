package com.isabel.hilos;

import javafx.concurrent.Task;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class DescargaTask extends Task <Integer>{

    private URL url;
    private File file;

    public DescargaTask(String urlText, File file) throws MalformedURLException {
        this.url = new URL(urlText);
        this.file = file;
    }



    @Override
    protected Integer call() throws Exception {
        updateMessage("Conectando al servidor...");

        //Hago conexión para calcular el tamaño del fichero a descargar
        URLConnection urlConnection = url.openConnection();
        double fileTamano = urlConnection.getContentLength();

        //Va descargando el fichero
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);


        byte dataBuffer[] = new byte[1024];
        int bytesLeidos;
        int totalLeido = 0;
        double descargaProgreso = 0;

        while ((bytesLeidos = in.read(dataBuffer, 0, 1024)) != -1) {

            //El progreso se irá pintando... Total leído / Tamaño del archivo
            descargaProgreso =((double) totalLeido / fileTamano);

            //Va descargando
            updateProgress(descargaProgreso, 1);

            DecimalFormat formato = new DecimalFormat("0");
            updateMessage("Tamaño descarga  " + fileTamano * 100 + "Mb.    Descargado  " + descargaProgreso * 100 + "%");

            DescargaController descargaController = new DescargaController();

                fileOutputStream.write(dataBuffer, 0, bytesLeidos);

                totalLeido += bytesLeidos;

                //Si cancelamos la descarga
            if(isCancelled()){
                return null;
            }
        }

        //Descarga completa
        updateProgress(1, 1);
        updateMessage("100%");

        return null;
    }
}
