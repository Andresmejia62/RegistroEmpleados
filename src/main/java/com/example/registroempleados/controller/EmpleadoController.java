package com.example.registroempleados.controller;

import com.example.registroempleados.connection.DatabaseConnection;
import com.example.registroempleados.model.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class EmpleadoController {

    @FXML private TableView<Empleado> tableEmpleados;
    @FXML private TableColumn<Empleado, String> colId;
    @FXML private TableColumn<Empleado, String> colNombres;
    @FXML private TableColumn<Empleado, String> colApellidos;
    @FXML private TableColumn<Empleado, String> colCedula;
    @FXML private TableColumn<Empleado, String> colCorreo;
    @FXML private TableColumn<Empleado, String> colTelefono;
    @FXML private TableColumn<Empleado, String> colCargo;
    @FXML private TableColumn<Empleado, String> colDepartamento;
    @FXML private TableColumn<Empleado, Number> colSalario;
    @FXML private TableColumn<Empleado, String> colFechaContratacion;
    @FXML private TableColumn<Empleado, String> colEstado;

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtCedula;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private ComboBox<String> cboCargo;
    @FXML private ComboBox<String> cboDepartamento;
    @FXML private TextField txtSalario;
    @FXML private DatePicker dpFechaContratacion;
    @FXML private ComboBox<String> cboEstado;
    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        cboCargo.getItems().setAll("Gerente", "Analista", "Soporte", "Auxiliar");
        cboDepartamento.getItems().setAll("TI", "Recursos Humanos", "Finanzas", "Marketing");
        cboEstado.getItems().setAll("Activo", "Inactivo");
        configurarTabla();
        tableEmpleados.setItems(listaEmpleados);
        limpiarFormulario();
        cargarEmpleados();
    }

    @FXML
    private void cargarEmpleados() {
        listaEmpleados.clear();

        String sql = "select * from empleado order by id";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombres = resultSet.getString("nombres");
                String apellidos = resultSet.getString("apellidos");
                String cedula = resultSet.getString("cedula");
                String correo = resultSet.getString("correo");
                String telefono = resultSet.getString("telefono");
                String cargo = resultSet.getString("cargo");
                String departamento = resultSet.getString("departamento");
                double salario = resultSet.getDouble("salario");
                String fechaContratacion = resultSet.getString("fecha_contratacion");
                String estado = resultSet.getBoolean("estado") ? "Activo" : "Inactivo";

                Empleado empleado = new Empleado(id, nombres, apellidos, cedula, correo,
                        telefono, cargo, departamento, salario, fechaContratacion, estado);
                listaEmpleados.add(empleado);
            }

            tableEmpleados.setItems(listaEmpleados);
            System.out.println("Empleados cargados: " + listaEmpleados.size());

        } catch (SQLException ex) {
            System.out.println("Error al cargar empleados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    @FXML
    private void guardarEmpleado(javafx.event.ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        String sql = "insert into empleado(nombres, apellidos, cedula, correo, telefono, cargo, "
                + "departamento, salario, fecha_contratacion, estado) values (?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, txtNombres.getText().trim());
            statement.setString(2, txtApellidos.getText().trim());
            statement.setString(3, txtCedula.getText().trim());
            statement.setString(4, txtCorreo.getText().trim());
            statement.setString(5, txtTelefono.getText().trim());
            statement.setString(6, cboCargo.getValue());
            statement.setString(7, cboDepartamento.getValue());
            statement.setDouble(8, Double.parseDouble(txtSalario.getText().trim()));
            statement.setTimestamp(9, Timestamp.valueOf(dpFechaContratacion.getValue().atStartOfDay()));
            statement.setBoolean(10, "Activo".equals(cboEstado.getValue()));

            statement.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado guardado", "Su registro ha sido guardado");
            limpiarFormulario();
            cargarEmpleados();

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar: " + e.getMessage());
        }
    }

    @FXML
    private void actualizarEmpleado(javafx.event.ActionEvent event) {
        Empleado seleccionado = tableEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione un empleado de la tabla para actualizar.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        String sql = "UPDATE empleado SET nombres = ?, apellidos = ?, cedula = ?, correo = ?, telefono = ?, "
                + "cargo = ?, departamento = ?, salario = ?, fecha_contratacion = ?, estado = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, txtNombres.getText().trim());
            statement.setString(2, txtApellidos.getText().trim());
            statement.setString(3, txtCedula.getText().trim());
            statement.setString(4, txtCorreo.getText().trim());
            statement.setString(5, txtTelefono.getText().trim());
            statement.setString(6, cboCargo.getValue());
            statement.setString(7, cboDepartamento.getValue());
            statement.setDouble(8, Double.parseDouble(txtSalario.getText().trim()));
            statement.setTimestamp(9, Timestamp.valueOf(dpFechaContratacion.getValue().atStartOfDay()));
            statement.setBoolean(10, "Activo".equals(cboEstado.getValue()));
            statement.setInt(11, Integer.parseInt(seleccionado.getId()));

            int filas = statement.executeUpdate();
            if (filas > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado actualizado", "Los datos han sido actualizados.");
                limpiarFormulario();
                cargarEmpleados();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validarCampos() {
        if (txtNombres.getText().isBlank() || txtApellidos.getText().isBlank()
                || txtCedula.getText().isBlank() || txtCorreo.getText().isBlank()
                || txtTelefono.getText().isBlank() || txtSalario.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Complete todos los campos.");
            return false;
        }
        if (cboCargo.getValue() == null || cboDepartamento.getValue() == null
                || cboEstado.getValue() == null || dpFechaContratacion.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
                    "Seleccione cargo, departamento, estado y fecha de contratación.");
            return false;
        }
        if (!txtCorreo.getText().trim().contains("@")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", "Ingrese un correo válido.");
            return false;
        }
        try {
            Double.parseDouble(txtSalario.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Salario inválido", "El salario debe ser un número.");
            return false;
        }
        return true;
    }

    @FXML
    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtSalario.clear();
        dpFechaContratacion.setValue(null);
        cboCargo.getSelectionModel().clearSelection();
        cboDepartamento.getSelectionModel().clearSelection();
        cboEstado.getSelectionModel().clearSelection();
    }
}