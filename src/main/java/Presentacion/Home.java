package Presentacion;

import javax.swing.*;
import Dominio.Usuario;

public class Home extends JFrame {

    private Usuario Login;

    public Usuario getLogin() {
        return Login;
    }

    public void setLogin(Usuario login) {
        this.Login = login;
    }

    public Home() {
        setTitle("Comercial Cruz JAVA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        crearMenu();
    }

    private void crearMenu() {

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // PERFIL
        JMenu menuPerfil = new JMenu("Perfil");
        menuBar.add(menuPerfil);

        JMenuItem itemSalir = new JMenuItem("Salir");
        menuPerfil.add(itemSalir);

        itemSalir.addActionListener(e -> System.exit(0));

        // PRODUCTO
        JMenu menuProducto = new JMenu("Producto");
        menuBar.add(menuProducto);

        // Producto
        JMenuItem itemProducto = new JMenuItem("Producto");
        menuProducto.add(itemProducto);

        // Marca
        JMenuItem itemMarca = new JMenuItem("Marca");
        menuProducto.add(itemMarca);

        // Categoría
        JMenuItem itemCategoria = new JMenuItem("Categoria");
        menuProducto.add(itemCategoria);

        itemCategoria.addActionListener(e -> {
            CategoriaForm categoriaForm = new CategoriaForm();
            categoriaForm.setVisible(true);
        });
    }
}