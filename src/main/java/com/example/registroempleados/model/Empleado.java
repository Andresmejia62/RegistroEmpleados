package com.example.registroempleados.model;

import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Empleado {
    @FXML
    private String id;
    @FXML
    private String nombres;
    @FXML
    private String apellidos;
    @FXML
    private String cedula;
    @FXML
    private String correo;
    @FXML
    private String telefono;
    @FXML
    private String cargo;
    @FXML
    private String departamento;
    @FXML
    private double salario;
    @FXML
    private LocalDate fecha_contratacion;
    @FXML
    private boolean estado;

    public Empleado() {
    }

    public Empleado(int id, String nombres, String apellidos, String cedula, String correo,
                    String telefono, String cargo, String departamento, double salario,
                    String fechaContratacion, String estado) {
        this.id = String.valueOf(id);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.fecha_contratacion = parseFecha(fechaContratacion);
        this.estado = "Activo".equalsIgnoreCase(estado);
    }

    private static LocalDate parseFecha(String fechaContratacion) {
        if (fechaContratacion == null || fechaContratacion.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(fechaContratacion);
        } catch (DateTimeParseException ignored) {
            try {
                return LocalDateTime.parse(fechaContratacion, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .toLocalDate();
            } catch (DateTimeParseException ex) {
                return null;
            }
        }
    }

    public Empleado(String id, String nombres, String apellidos, String cedula, String correo, String telefono, String cargo, String departamento, double salario, LocalDate fecha_contratacion, boolean estado) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.fecha_contratacion = fecha_contratacion;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalDate getFecha_contratacion() {
        return fecha_contratacion;
    }

    public LocalDate getFechaContratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(LocalDate fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fecha_contratacion = fechaContratacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getEstado() {
        return estado ? "Activo" : "Inactivo";
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
