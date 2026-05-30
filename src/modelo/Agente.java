package modelo;

public class Agente {
    private int    idAgente;
    private String nombre;
    private String email;
    private String especialidad;
    private String activo;

    public Agente() {}
    public Agente(int idAgente, String nombre, String email, String especialidad, String activo) {
        this.idAgente     = idAgente;
        this.nombre       = nombre;
        this.email        = email;
        this.especialidad = especialidad;
        this.activo       = activo;
    }

    public int    getIdAgente()             { return idAgente; }
    public void   setIdAgente(int id)       { this.idAgente = id; }
    public String getNombre()               { return nombre; }
    public void   setNombre(String n)       { this.nombre = n; }
    public String getEmail()                { return email; }
    public void   setEmail(String e)        { this.email = e; }
    public String getEspecialidad()         { return especialidad; }
    public void   setEspecialidad(String e) { this.especialidad = e; }
    public String getActivo()               { return activo; }
    public void   setActivo(String a)       { this.activo = a; }

    @Override
    public String toString() { return nombre; }
}