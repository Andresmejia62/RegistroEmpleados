module com.example.registroempleados {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.registroempleados to javafx.fxml;
    opens com.example.registroempleados.controller to javafx.fxml;
    opens com.example.registroempleados.model to javafx.base;

    exports com.example.registroempleados;
    exports com.example.registroempleados.controller;
}