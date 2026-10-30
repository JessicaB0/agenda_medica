/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

/**
 *
 * @author jessi
 */
public class PruebaUsuario {
    public static void main(String[] args) {
        Usuario usuario = new Usuario(
        "Rosa",
        "1234",
        "Recepcionista",
        "RECEPCION");
        
        UsuarioDAO dao = new UsuarioDAO();
        boolean resultado = dao.insertar(usuario);
        
        if (resultado){
            System.out.println("======================0");
            System.out.println("USUARIO CREADO CORREECTAMENTE");
            System.out.println("Usuario: admin");
            System.out.println("Rol: ADMIN");
            System.out.println("======================0");
        }else{
            System.out.println("======================0");
            System.out.println("ERROR AL CREAR EL USUARIO");
            System.out.println("======================0");
        }
    }
}
