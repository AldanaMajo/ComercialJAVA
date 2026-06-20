package Presentacion;

import javax.swing.*;
import Dominio.Usuario;

public class Home extends JFrame {

    private Usuario Login;
    private JDesktopPane escritorio;

    public Usuario getLogin() {
        return Login;
    }

    public void setLogin(Usuario login) {
        this.Login = login;
    }

    public Home() {
        setTitle("Comercial Cruz JAVA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        escritorio = new JDesktopPane();
        setContentPane(escritorio);

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

        JMenuItem itemProducto = new JMenuItem("Producto");
        menuProducto.add(itemProducto);
        itemProducto.addActionListener(e -> abrirFormulario(new ProductoForm()));

        JMenuItem itemMarca = new JMenuItem("Marca");
        menuProducto.add(itemMarca);
        itemMarca.addActionListener(e -> abrirFormulario(new MarcaForm()));

        JMenuItem itemCategoria = new JMenuItem("Categoria");
        menuProducto.add(itemCategoria);
        itemCategoria.addActionListener(e -> abrirFormulario(new CategoriaForm()));

        // MANTENIMIENTO
        JMenu menuMantenimiento = new JMenu("Mantenimiento");
        menuBar.add(menuMantenimiento);

        JMenuItem itemUsuario = new JMenuItem("Usuario");
        menuMantenimiento.add(itemUsuario);
        itemUsuario.addActionListener(e -> abrirFormulario(new UsuarioForm()));
    }

    private void abrirFormulario(JInternalFrame formulario) {
        escritorio.add(formulario);
        formulario.setVisible(true);

        try {
            formulario.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}