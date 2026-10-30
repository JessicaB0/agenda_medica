/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;
import java.sql.Connection;



public class PruebaConexion {

    public static void main(String[] args) {

        Connection conexion = Conexion.conectar();

        if (conexion != null) {

            System.out.println("===================");
            System.out.println("CONEXION EXITOSA");
            System.out.println("===================");

            try {
                conexion.close();
                System.out.println("Conexion cerrada correctamente.");
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexion:");
                e.printStackTrace();
            }

        } else {

            System.out.println("===================");
            System.out.println("ERROR DE CONEXION");
            System.out.println("===================");

        }
    }
}
