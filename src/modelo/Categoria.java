package modelo;

public class Categoria {
    private int    idCategoria;
    private String nombre;
    private String prioridad;

    public Categoria() {}
    public Categoria(int idCategoria, String nombre, String prioridad) {
        this.idCategoria = idCategoria;
        this.nombre      = nombre;
        this.prioridad   = prioridad;
    }

    public int    getIdCategoria()          { return idCategoria; }
    public void   setIdCategoria(int id)    { this.idCategoria = id; }
    public String getNombre()               { return nombre; }
    public void   setNombre(String n)       { this.nombre = n; }
    public String getPrioridad()            { return prioridad; }
    public void   setPrioridad(String p)    { this.prioridad = p; }

    @Override
    public String toString() { return nombre; }
}