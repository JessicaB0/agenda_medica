/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

/**
 *
 * @author jessi
 */
public class Html {
    
    public static String esc(String texto){
        if (texto == null){
            return "";
        }
        
        StringBuilder sb = new StringBuilder(texto.length() + 16);
        
        for (char c : texto.toCharArray()){
            switch (c) {
                case '&' :
                    sb.append("&am;");
                    break;
                     case '<':
                    sb.append("&lt;");
                    break;
                     case '>':
                    sb.append("&gt;");
                    break;
                     case '"':
                    sb.append("&qout;");
                    break;
                     case '\'':
                    sb.append("&#39;");
                    break;
                    default:
                        sb.append(c);
            }
        }
        return sb.toString();
    }
}
