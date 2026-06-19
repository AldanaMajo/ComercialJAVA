package Persistencia;

import Dominio.Categoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class CategoriaDAO {

    private ConeccionBD conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public CategoriaDAO() {
        conn = ConeccionBD.getInstance();
    }

    public Categoria Crear(Categoria categoria) throws SQLException {

        Categoria res = null;

        try {

            ps = conn.connect().prepareStatement(
                    "INSERT INTO Categoria (Nombre) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, categoria.getNombre());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {

                ResultSet generatedKeys = ps.getGeneratedKeys();

                if (generatedKeys.next()) {

                    int IdGenerado = generatedKeys.getInt(1);
                    res = getById(IdGenerado);

                } else {
                    throw new SQLException("No se obtuvo el ID generado.");
                }
            }

            ps.close();

        } catch (SQLException ex) {

            throw new SQLException("Error al crear la categoría: " + ex.getMessage(), ex);

        } finally {

            ps = null;
            conn.disconnect();
        }

        return res;
    }

    public boolean Actualizar(Categoria categoria) throws SQLException {

        boolean res = false;

        try {

            ps = conn.connect().prepareStatement(
                    "UPDATE Categoria SET Nombre = ? WHERE Id = ?"
            );

            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getId());

            if (ps.executeUpdate() > 0) {
                res = true;
            }

            ps.close();

        } catch (SQLException ex) {

            throw new SQLException("Error al actualizar la categoría: " + ex.getMessage(), ex);

        } finally {

            ps = null;
            conn.disconnect();
        }

        return res;
    }

    public boolean Eliminar(Categoria categoria) throws SQLException {

        boolean res = false;

        try {

            ps = conn.connect().prepareStatement(
                    "DELETE FROM Categoria WHERE Id = ?"
            );

            ps.setInt(1, categoria.getId());

            if (ps.executeUpdate() > 0) {
                res = true;
            }

            ps.close();

        } catch (SQLException ex) {

            throw new SQLException("Error al eliminar la categoría: " + ex.getMessage(), ex);

        } finally {

            ps = null;
            conn.disconnect();
        }

        return res;
    }

    public ArrayList<Categoria> Buscar(String nombre) throws SQLException {

        ArrayList<Categoria> categorias = new ArrayList<>();

        try {

            ps = conn.connect().prepareStatement(
                    "SELECT Id, Nombre FROM Categoria WHERE Nombre LIKE ?"
            );

            ps.setString(1, "%" + nombre + "%");

            rs = ps.executeQuery();

            while (rs.next()) {

                Categoria categoria = new Categoria();

                categoria.setId(rs.getInt("Id"));
                categoria.setNombre(rs.getString("Nombre"));

                categorias.add(categoria);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {

            throw new SQLException("Error al buscar categorías: " + ex.getMessage(), ex);

        } finally {

            rs = null;
            ps = null;
            conn.disconnect();
        }

        return categorias;
    }

    public Categoria getById(int Id) throws SQLException {

        Categoria categoria = null;

        try {

            ps = conn.connect().prepareStatement(
                    "SELECT Id, Nombre FROM Categoria WHERE Id = ?"
            );

            ps.setInt(1, Id);

            rs = ps.executeQuery();

            if (rs.next()) {

                categoria = new Categoria();

                categoria.setId(rs.getInt("Id"));
                categoria.setNombre(rs.getString("Nombre"));
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {

            throw new SQLException("Error al obtener la categoría: " + ex.getMessage(), ex);

        } finally {

            rs = null;
            ps = null;
            conn.disconnect();
        }

        return categoria;
    }
}