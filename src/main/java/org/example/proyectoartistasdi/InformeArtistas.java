package org.example.proyectoartistasdi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class InformeArtistas {


    public ListView<String> artistasList;
    public Button btnCerrar;
    public Button btnGenerarInforme;

    public void listarArtistas() {
        ObservableList<String> artistas = FXCollections.observableArrayList();
        try {
            Connection conectar = ConexionDB.obtenerConexion();
            String sql = "select ArtistId,Name from artists";
            PreparedStatement ps = conectar.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idArtista = rs.getInt("ArtistId");
                String artista = rs.getString("Name");
                artistas.add(idArtista + " - " + artista);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la conexion");

        }
        artistasList.setItems(artistas);

    }

    public void generarinformeArtista() {
        String artista = artistasList.getSelectionModel().getSelectedItem();


        String [] partes = artista.split(" - ");
        int idArtista = Integer.parseInt(partes[0]);
        String nombreArtista =partes[1];


        Connection conectar;
        InputStream inputStream;

        try{
            conectar = ConexionDB.obtenerConexion();
            inputStream = getClass().getResourceAsStream("ArtistasReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            Map<String, Object> parametro = new HashMap<>();
            parametro.put("ARTIST_ID", idArtista);
            parametro.put("NOMBRE_ARTISTA",nombreArtista);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, conectar);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void btnCerrar(ActionEvent event) {
        Stage titStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        titStage.close();
    }

}
