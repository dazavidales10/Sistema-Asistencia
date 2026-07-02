package Modelo;

public class Usuario {

    private int id;
    private String nombre;
    private String identificacion;
    private String tipoDocumento;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id,
                   String nombre,
                   String identificacion,
                   String tipoDocumento,
                   String password,
                   String rol) {

        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.tipoDocumento = tipoDocumento;
        this.password = password;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}