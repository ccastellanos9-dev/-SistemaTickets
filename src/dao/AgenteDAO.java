package dao;

import conexion.Conexion;
import modelo.Agente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteDAO {

    public boolean insertar(Agente a) {
        String sql = "INSERT INTO AGENTES (nombre, email, especialidad) VALUES (?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getEspecialidad());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertar agente: " + e.getMessage());
            return false;
        }
    }

    public List<Agente> listar() {
        List<Agente> lista = new ArrayList<>();
        String sql = "SELECT * FROM AGENTES ORDER BY id_agente";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Agente(
                    rs.getInt("id_agente"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("especialidad"),
                    rs.getString("activo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error listar agentes: " + e.getMessage());
        }
        return lista;
    }

    public Agente buscarPorId(int id) {
        String sql = "SELECT * FROM AGENTES WHERE id_agente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Agente(
                    rs.getInt("id_agente"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("especialidad"),
                    rs.getString("activo")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error buscar agente: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Agente a) {
        String sql = "UPDATE AGENTES SET nombre=?, email=?, especialidad=?, activo=? WHERE id_agente=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getEspecialidad());
            ps.setString(4, a.getActivo());
            ps.setInt(5, a.getIdAgente());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error actualizar agente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM AGENTES WHERE id_agente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error eliminar agente: " + e.getMessage());
            return false;
        }
    }
}