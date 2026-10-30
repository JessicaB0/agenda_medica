/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author jessi
 */
public class PruebaBCrypt {
    public static void main(String[] args) {
        String password = "admin123";
        
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        
        
        System.out.println("HASH GENERADO:");
        System.out.println(hash);
        
        boolean correcta = BCrypt.checkpw(password, hash);
        
        System.out.println("CONTRASEÑA CORRECTA: "+ correcta);
    }
    
}
