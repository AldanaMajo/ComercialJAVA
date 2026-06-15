package Dominio;

import java.time.LocalDateTime;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String login;
    private String password;
    private byte estatus;
    private LocalDateTime fechaRegistro;

    public Usuario(){

    }

    public Usuario(int idUsuario, String nombre, String apellido,
                   String login, String password,
                   byte estatus, LocalDateTime fechaRegistro){

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.login = login;
        this.password = password;
        this.estatus = estatus;
        this.fechaRegistro = fechaRegistro;

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getEstatus() {
        return estatus;
    }

    public void setEstatus(byte estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getStrEstatus(){

        String estado = "";

        switch(estatus){

            case 1:
                estado = "ACTIVO";
                break;

            case 2:
                estado = "INACTIVO";
                break;

        }

        return estado;
    }
}
