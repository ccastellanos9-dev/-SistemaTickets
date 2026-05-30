package modelo;

import java.util.Date;

public class Ticket {
    private int    idTicket;
    private int    idCliente;
    private int    idAgente;
    private int    idCategoria;
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date   fechaCreacion;

    public Ticket() {}
    public Ticket(int idTicket, int idCliente, int idAgente, int idCategoria,
                  String titulo, String descripcion, String estado, String prioridad) {
        this.idTicket    = idTicket;
        this.idCliente   = idCliente;
        this.idAgente    = idAgente;
        this.idCategoria = idCategoria;
        this.titulo      = titulo;
        this.descripcion = descripcion;
        this.estado      = estado;
        this.prioridad   = prioridad;
    }

    public int    getIdTicket()              { return idTicket; }
    public void   setIdTicket(int id)        { this.idTicket = id; }
    public int    getIdCliente()             { return idCliente; }
    public void   setIdCliente(int id)       { this.idCliente = id; }
    public int    getIdAgente()              { return idAgente; }
    public void   setIdAgente(int id)        { this.idAgente = id; }
    public int    getIdCategoria()           { return idCategoria; }
    public void   setIdCategoria(int id)     { this.idCategoria = id; }
    public String getTitulo()                { return titulo; }
    public void   setTitulo(String t)        { this.titulo = t; }
    public String getDescripcion()           { return descripcion; }
    public void   setDescripcion(String d)   { this.descripcion = d; }
    public String getEstado()                { return estado; }
    public void   setEstado(String e)        { this.estado = e; }
    public String getPrioridad()             { return prioridad; }
    public void   setPrioridad(String p)     { this.prioridad = p; }
    public Date   getFechaCreacion()         { return fechaCreacion; }
    public void   setFechaCreacion(Date f)   { this.fechaCreacion = f; }
}