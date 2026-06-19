package Persistencia;

import Dominio.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductoDAO {

    private ConeccionBD conexion;
    private PreparedStatement ps;
    private ResultSet rs;

    public ProductoDAO() {
        conexion = ConeccionBD.getInstance();
    }

    // 1. CREAR UN NUEVO PRODUCTO
    public Producto crear(Producto producto) throws SQLException {
        Producto resultado = null;

        ps = conexion.connect().prepareStatement(
                "INSERT INTO Producto (IdCategoria, IdMarca, Nombre, Descripcion, Precio, Stock) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, producto.getIdCategoria());
        ps.setInt(2, producto.getIdMarca());
        ps.setString(3, producto.getNombre());
        ps.setString(4, producto.getDescripcion());
        ps.setDouble(5, producto.getPrecio());
        ps.setInt(6, producto.getStock());

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

    // 2. OBTENER UN PRODUCTO POR SU ID
    public Producto obtenerPorId(int id) throws SQLException {
        Producto producto = null;

        ps = conexion.connect().prepareStatement(
                "SELECT * FROM Producto WHERE Id = ?"
        );

        ps.setInt(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            producto = new Producto();
            producto.setId(rs.getInt("Id"));
            producto.setIdCategoria(rs.getInt("IdCategoria"));
            producto.setIdMarca(rs.getInt("IdMarca"));
            producto.setNombre(rs.getString("Nombre"));
            producto.setDescripcion(rs.getString("Descripcion"));
            producto.setPrecio(rs.getDouble("Precio"));
            producto.setStock(rs.getInt("Stock"));
        }

        return producto;
    }

    // 3. OBTENER TODOS LOS PRODUCTOS
    public ArrayList<Producto> obtenerTodos() throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();

        ps = conexion.connect().prepareStatement(
                "SELECT * FROM Producto"
        );

        rs = ps.executeQuery();

        while (rs.next()) {
            Producto producto = new Producto();

            producto.setId(rs.getInt("Id"));
            producto.setIdCategoria(rs.getInt("IdCategoria"));
            producto.setIdMarca(rs.getInt("IdMarca"));
            producto.setNombre(rs.getString("Nombre"));
            producto.setDescripcion(rs.getString("Descripcion"));
            producto.setPrecio(rs.getDouble("Precio"));
            producto.setStock(rs.getInt("Stock"));

            lista.add(producto);
        }

        return lista;
    }

    // 4. ACTUALIZAR UN PRODUCTO
    public boolean actualizar(Producto producto) throws SQLException {
        ps = conexion.connect().prepareStatement(
                "UPDATE Producto SET " +
                        "IdCategoria = ?, " +
                        "IdMarca = ?, " +
                        "Nombre = ?, " +
                        "Descripcion = ?, " +
                        "Precio = ?, " +
                        "Stock = ? " +
                        "WHERE Id = ?"
        );

        ps.setInt(1, producto.getIdCategoria());
        ps.setInt(2, producto.getIdMarca());
        ps.setString(3, producto.getNombre());
        ps.setString(4, producto.getDescripcion());
        ps.setDouble(5, producto.getPrecio());
        ps.setInt(6, producto.getStock());
        ps.setInt(7, producto.getId());

        return ps.executeUpdate() > 0;
    }

    // 5. ELIMINAR UN PRODUCTO
    public boolean eliminar(int id) throws SQLException {
        ps = conexion.connect().prepareStatement(
                "DELETE FROM Producto WHERE Id = ?"
        );

        ps.setInt(1, id);

        return ps.executeUpdate() > 0;
    }
}