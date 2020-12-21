package com.isabel.hilos.util;

import javafx.scene.control.Alert;

public class Alertas {

    public static void avisarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.show();
    }
}
