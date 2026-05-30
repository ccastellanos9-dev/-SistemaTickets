package dao;

import conexion.Conexion;
import modelo.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public boolean insertar(Categoria c) {
        String sql = "INSERT INTO CATEGORIAS (nombre, prioridad) VALUES (?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getPrioridad());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertar categoria: " + e.getMessage());
            return false;
        }
    }

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORIAS ORDER BY id_categoria";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("prioridad")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error listar categorias: " + e.getMessage());
        }
        return lista;
    }

    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM CATEGORIAS WHERE id_categoria = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("prioridad")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error buscar categoria: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Categoria c) {
        String sql = "UPDATE CATEGORIAS SET nombre=?, prioridad=? WHERE id_categoria=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getPrioridad());
            ps.setInt(3, c.getIdCategoria());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error actualizar categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM CATEGORIAS WHERE id_categoria = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error eliminar categoria: " + e.getMessage());
            return false;
        }
    }
}