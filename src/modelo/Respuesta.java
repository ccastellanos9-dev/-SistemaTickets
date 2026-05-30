package modelo;

import java.util.Date;

public class Respuesta {
    private int    idRespuesta;
    private int    idTicket;
    private int    idAgente;
    private String mensaje;
    private Date   fecha;

    public Respuesta() {}
    public Respuesta(int idRespuesta, int idTicket, int idAgente, String mensaje, Date fecha) {
        this.idRespuesta = idRespuesta;
        this.idTicket    = idTicket;
        this.idAgente    = idAgente;
        this.mensaje     = mensaje;
        this.fecha       = fecha;
    }

    public int    getIdRespuesta()          { return idRespuesta; }
    public void   setIdRespuesta(int id)    { this.idRespuesta = id; }
    public int    getIdTicket()             { return idTicket; }
    public void   setIdTicket(int id)       { this.idTicket = id; }
    public int    getIdAgente()             { return idAgente; }
    public void   setIdAgente(int id)       { this.idAgente = id; }
    public String getMensaje()              { return mensaje; }
    public void   setMensaje(String m)      { this.mensaje = m; }
    public Date   getFecha()                { return fecha; }
    public void   setFecha(Date f)          { this.fecha = f; }
}