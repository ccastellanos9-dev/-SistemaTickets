package dao;

import conexion.Conexion;
import modelo.Respuesta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RespuestaDAO {

    public boolean insertar(Respuesta r) {
        String sql = "INSERT INTO RESPUESTAS (id_ticket, id_agente, mensaje) VALUES (?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getIdTicket());
            ps.setInt(2, r.getIdAgente());
            ps.setString(3, r.getMensaje());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertar respuesta: " + e.getMessage());
            return false;
        }
    }

    public List<Respuesta> listarPorTicket(int idTicket) {
        List<Respuesta> lista = new ArrayList<>();
        String sql = "SELECT * FROM RESPUESTAS WHERE id_ticket = ? ORDER BY fecha";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTicket);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Respuesta(
                    rs.getInt("id_respuesta"),
                    rs.getInt("id_ticket"),
                    rs.getInt("id_agente"),
                    rs.getString("mensaje"),
                    rs.getDate("fecha")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error listar respuestas: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Respuesta r) {
        String sql = "UPDATE RESPUESTAS SET mensaje=? WHERE id_respuesta=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getMensaje());
            ps.setInt(2, r.getIdRespuesta());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error actualizar respuesta: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM RESPUESTAS WHERE id_respuesta = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error eliminar respuesta: " + e.getMessage());
            return false;
        }
    }
}