/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

public class Usuario {

    private String usuario;
    private String password;
    private String nombres;
    private String rol;
    private boolean activo;
    private int id;

    public Usuario() {

    }

    public Usuario(
            String usuario,
            String password,
            String nombre,
            String rol) {
        this.usuario = usuario;
        this.password = password;
        this.nombres = nombre;
        this.rol = rol;
        this.activo = true;
    }

    public Usuario(
            int id,
            String usuario,
            String password,
            String nombre,
            String rol,
            boolean activo) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.nombres = nombre;
        this.rol = rol;
        this.activo = activo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
