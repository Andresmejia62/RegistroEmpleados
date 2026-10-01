module com.example.registroempleados {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.registroempleados to javafx.fxml;
    exports com.example.registroempleados;
}