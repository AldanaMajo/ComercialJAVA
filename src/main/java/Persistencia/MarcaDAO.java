package Persistencia;

import Dominio.Marca;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MarcaDAO {

    private ConeccionBD conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    public MarcaDAO() {
        conexion = ConeccionBD.getInstance();
    }

    // 1. CREAR UNA NUEVA MARCA
    public Marca crear(Marca marca) throws SQLException {
        Marca resultado = null;

        ps = conexion.connect().prepareStatement(
                "INSERT INTO Marca (Nombre) VALUES ( ?)",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, marca.getNombre());


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

    // 2. OBTENER UNA MARCA POR SU ID
    public Marca obtenerPorId(int idMarca) throws SQLException {
        Marca marca = null;

        ps = conexion.connect().prepareStatement(
                "SELECT * FROM Marca WHERE Id = ?"
        );

        ps.setInt(1, idMarca);
        rs = ps.executeQuery();

        if (rs.next()) {
            marca = new Marca();
            marca.setIdMarca(rs.getInt("Id"));
            marca.setNombre(rs.getString("Nombre"));

        }

        return marca;
    }

    // 3. OBTENER TODAS LAS MARCAS (Para llenar tu JTable)
    public ArrayList<Marca> obtenerTodos() throws SQLException {
        ArrayList<Marca> lista = new ArrayList<>();

        ps = conexion.connect().prepareStatement(
                "SELECT * FROM Marca"
        );

        rs = ps.executeQuery();

        while (rs.next()) {
            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("Id"));
            marca.setNombre(rs.getString("Nombre"));


            lista.add(marca);
        }

        return lista;
    }

    // 4. ACTUALIZAR UNA MARCA
    public boolean actualizar(Marca marca) throws SQLException {
        ps = conexion.connect().prepareStatement(
                "UPDATE Marca SET Nombre = ?, WHERE Id = ?"
        );

        ps.setString(1, marca.getNombre());
        ps.setInt(3, marca.getIdMarca());

        return ps.executeUpdate() > 0;
    }

    // 5. ELIMINAR UNA MARCA
    public boolean eliminar(int idMarca) throws SQLException {
        ps = conexion.connect().prepareStatement(
                "DELETE FROM Marca WHERE Id = ?"
        );

        ps.setInt(1, idMarca);

        return ps.executeUpdate() > 0;
    }
}