package Modelo;

public class Usuario {

    private int id;
    private String identificacion;
    private String tipoDocumento;
    private String rol;
    private String password;
    private String nombre;

    
    public Usuario() {
    }

    
    public Usuario(int id,String identificacion,String tipoDocumento,String rol,String password,String nombre) {
        this.id = id;
        this.identificacion = identificacion;
        this.tipoDocumento = tipoDocumento;
        this.rol = rol;
        this.password = password;
        this.nombre = nombre;
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
}
