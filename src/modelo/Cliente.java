package modelo;

public class Cliente {
    private int    idCliente;
    private String nombre;
    private String email;
    private String telefono;

    public Cliente() {}
    public Cliente(int idCliente, String nombre, String email, String telefono) {
        this.idCliente = idCliente;
        this.nombre    = nombre;
        this.email     = email;
        this.telefono  = telefono;
    }

    public int    getIdCliente()          { return idCliente; }
    public void   setIdCliente(int id)    { this.idCliente = id; }
    public String getNombre()             { return nombre; }
    public void   setNombre(String n)     { this.nombre = n; }
    public String getEmail()              { return email; }
    public void   setEmail(String e)      { this.email = e; }
    public String getTelefono()           { return telefono; }
    public void   setTelefono(String t)   { this.telefono = t; }

    @Override
    public String toString() { return nombre; }
}