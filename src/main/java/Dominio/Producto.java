package Dominio;

public class Producto {

    private int id;
    private int idCategoria;
    private int idMarca;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;


    public Producto(){

    }


    public Producto(int id, int idCategoria, int idMarca,
                    String nombre, String descripcion,
                    double precio, int stock){

        this.id = id;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;

    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getIdCategoria() {
        return idCategoria;
    }


    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }


    public int getIdMarca() {
        return idMarca;
    }


    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public double getPrecio() {
        return precio;
    }


    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public int getStock() {
        return stock;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }
}
