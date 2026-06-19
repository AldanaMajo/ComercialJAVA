package Dominio;

public class Categoria {
    private int Id;
    private String Nombre;

    public Categoria() {

    }

    public Categoria(int Id, String Nombre){
        this.Id = Id;
        this.Nombre = Nombre;
    }
    public int getId() {
        return Id;
    }
    public void setId(int id)
    {
        this.Id = id;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre( String Nombre){
        this.Nombre =Nombre;
    }
}
