module com.example.registroempleados {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.registroempleados to javafx.fxml;
    exports com.example.registroempleados;
}