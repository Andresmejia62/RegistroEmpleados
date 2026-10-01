package com.example.registroempleados;

import com.example.registroempleados.connection.DatabaseConnection;

import java.sql.Connection;

public class DBTester {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()){
            if (conn != null && !conn.isClosed()){
                System.out.println("Conexion correcta: " + conn.getMetaData().getURL());
            } else {
                System.out.println("Conexion null o cerrada");
            }
        } catch (Exception e){
            System.out.println("Conexion fallida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

