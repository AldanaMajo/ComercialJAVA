package Persistencia;

import Dominio.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO {

    private ConeccionBD conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    public UsuarioDAO() {
        conexion = ConeccionBD.getInstance();
    }

    public Usuario crear(Usuario usuario) throws SQLException {

        Usuario resultado = null;

        ps = conexion.connect().prepareStatement(
                "INSERT INTO Usuario " +
                        "(Nombre, Apellido, Login, Password, Estatus) " +
                        "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getApellido());
        ps.setString(3, usuario.getLogin());
        ps.setString(4, usuario.getPassword());
        ps.setByte(5, usuario.getEstatus());

        int filas = ps.executeUpdate();

        if (filas > 0) {

            rs = ps.getGeneratedKeys();

            if (rs.next()) {

                int idGenerado = rs.getInt(1);

                resultado = obtenerPorId(idGenerado);
            }
        }

        return resultado;
    }

    public Usuario obtenerPorId(int idUsuario) throws SQLException {

        Usuario usuario = null;

        ps = conexion.connect().prepareStatement(
                "SELECT * FROM Usuario WHERE IdUsuario = ?"
        );

        ps.setInt(1, idUsuario);

        rs = ps.executeQuery();

        if (rs.next()) {

            usuario = new Usuario();

            usuario.setIdUsuario(rs.getInt("IdUsuario"));
            usuario.setNombre(rs.getString("Nombre"));
            usuario.setApellido(rs.getString("Apellido"));
            usuario.setLogin(rs.getString("Login"));
            usuario.setPassword(rs.getString("Password"));
            usuario.setEstatus(rs.getByte("Estatus"));

            if (rs.getTimestamp("FechaRegistro") != null) {
                usuario.setFechaRegistro(
                        rs.getTimestamp("FechaRegistro")
                                .toLocalDateTime()
                );
            }
        }

        return usuario;
    }

    public ArrayList<Usuario> obtenerTodos() throws SQLException {

        ArrayList<Usuario> lista = new ArrayList<>();

        ps = conexion.connect().prepareStatement(
                "SELECT * FROM Usuario"
        );

        rs = ps.executeQuery();

        while (rs.next()) {

            Usuario usuario = new Usuario();

            usuario.setIdUsuario(rs.getInt("IdUsuario"));
            usuario.setNombre(rs.getString("Nombre"));
            usuario.setApellido(rs.getString("Apellido"));
            usuario.setLogin(rs.getString("Login"));
            usuario.setPassword(rs.getString("Password"));
            usuario.setEstatus(rs.getByte("Estatus"));

            lista.add(usuario);
        }

        return lista;
    }

    public boolean actualizar(Usuario usuario) throws SQLException {

        ps = conexion.connect().prepareStatement(
                "UPDATE Usuario SET " +
                        "Nombre = ?, " +
                        "Apellido = ?, " +
                        "Login = ?, " +
                        "Password = ?, " +
                        "Estatus = ? " +
                        "WHERE IdUsuario = ?"
        );

        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getApellido());
        ps.setString(3, usuario.getLogin());
        ps.setString(4, usuario.getPassword());
        ps.setByte(5, usuario.getEstatus());
        ps.setInt(6, usuario.getIdUsuario());

        return ps.executeUpdate() > 0;
    }

    public boolean eliminar(int idUsuario) throws SQLException {

        ps = conexion.connect().prepareStatement(
                "DELETE FROM Usuario WHERE IdUsuario = ?"
        );

        ps.setInt(1, idUsuario);

        return ps.executeUpdate() > 0;
    }

    public Usuario login(String login, String password) throws SQLException {

        Usuario usuario = null;

        ps = conexion.connect().prepareStatement(
                "SELECT * FROM Usuario " +
                        "WHERE Login = ? " +
                        "AND Password = ? " +
                        "AND Estatus = 1"
        );

        ps.setString(1, login);
        ps.setString(2, password);

        rs = ps.executeQuery();

        if (rs.next()) {

            usuario = new Usuario();

            usuario.setIdUsuario(rs.getInt("IdUsuario"));
            usuario.setNombre(rs.getString("Nombre"));
            usuario.setApellido(rs.getString("Apellido"));
            usuario.setLogin(rs.getString("Login"));
            usuario.setPassword(rs.getString("Password"));
            usuario.setEstatus(rs.getByte("Estatus"));
        }

        return usuario;
    }
}
