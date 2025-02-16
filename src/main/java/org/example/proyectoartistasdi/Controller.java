package org.example.proyectoartistasdi;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

public class Controller {

    public Label lblTitulo;
    public Button btnInfCliente;
    public Button btnInfArtista;
    public Button btnCerrar;

    public void btnCrearInfCliente(ActionEvent actionEvent) {
        try {

            Connection connection = ConexionDB.obtenerConexion();
            InputStream inputStream = getClass().getResourceAsStream("ClientesReport.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            System.err.println("El archivo no fue encontrado");        }

    }

    public void btnCrearInfArtista(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("InformeArtistasView.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Informe de artistas");

            InformeArtistas informeArtistas = loader.getController();
            informeArtistas.listarArtistas();

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnSalirApp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida");
        alert.setHeaderText("¿Está seguro de que desea salir?");

        // Mostrar la alerta y esperar la respuesta
        ButtonType respuesta = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (respuesta == ButtonType.OK) {
            // Si el usuario confirma la salida, cerrar la aplicación
            System.exit(0);
        }
    }
}