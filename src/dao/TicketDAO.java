package dao;

import conexion.Conexion;
import modelo.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    public boolean insertar(Ticket t) {
        String sql = "INSERT INTO TICKETS (id_cliente, id_agente, id_categoria, " +
                     "titulo, descripcion, prioridad) VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, t.getIdCliente());
            ps.setInt(2, t.getIdAgente());
            ps.setInt(3, t.getIdCategoria());
            ps.setString(4, t.getTitulo());
            ps.setString(5, t.getDescripcion());
            ps.setString(6, t.getPrioridad());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertar ticket: " + e.getMessage());
            return false;
        }
    }

    public List<Object[]> listarDetalle() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM VW_TICKETS_DETALLE ORDER BY id_ticket DESC";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_ticket"),
                    rs.getString("titulo"),
                    rs.getString("cliente"),
                    rs.getString("agente"),
                    rs.getString("categoria"),
                    rs.getString("estado"),
                    rs.getString("prioridad"),
                    rs.getDate("fecha_creacion")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error listar tickets: " + e.getMessage());
        }
        return lista;
    }

    public boolean cambiarEstado(int idTicket, String nuevoEstado) {
        String sql = "UPDATE TICKETS SET estado = ? WHERE id_ticket = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idTicket);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error cambiar estado: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Ticket t) {
        String sql = "UPDATE TICKETS SET titulo=?, descripcion=?, prioridad=?, " +
                     "id_agente=?, id_categoria=? WHERE id_ticket=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getTitulo());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getPrioridad());
            ps.setInt(4, t.getIdAgente());
            ps.setInt(5, t.getIdCategoria());
            ps.setInt(6, t.getIdTicket());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error actualizar ticket: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idTicket) {
        String sqlResp   = "DELETE FROM RESPUESTAS WHERE id_ticket = ?";
        String sqlTicket = "DELETE FROM TICKETS WHERE id_ticket = ?";
        try (Connection con = Conexion.getConexion()) {
            con.setAutoCommit(false);
            PreparedStatement ps1 = con.prepareStatement(sqlResp);
            ps1.setInt(1, idTicket);
            ps1.executeUpdate();
            PreparedStatement ps2 = con.prepareStatement(sqlTicket);
            ps2.setInt(1, idTicket);
            ps2.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Error eliminar ticket: " + e.getMessage());
            return false;
        }
    }
}