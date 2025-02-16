module org.example.proyectoartistasdi {
    requires javafx.controls;
    requires javafx.fxml;
    requires jasperreports;
    requires java.sql;



    opens org.example.proyectoartistasdi to javafx.fxml;
    exports org.example.proyectoartistasdi;
}