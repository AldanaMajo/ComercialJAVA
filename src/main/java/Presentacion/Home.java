package Presentacion;

import javax.swing.*;
import Dominio.Usuario;

public class Home extends JFrame {
    private Usuario Login;
    public Usuario getLogin(){
        return Login;
    }
    public void setLogin(Usuario login){
        this.Login = login;
    }

    public Home(){
        setTitle("Comercial Cruz JAVA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        crearMenu();
    }
    private void crearMenu(){

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menuPerfil = new JMenu("Perfil"); // Crea un nuevo menú llamado "Perfil".
        menuBar.add(menuPerfil); // Agrega el menú "Perfil" a la barra de menú.



        JMenuItem itemSalir = new JMenuItem("Salir"); // Crea un nuevo elemento de menú llamado "Salir".
        menuPerfil.add(itemSalir); // Agrega el elemento "Salir" al menú "Perfil".
        itemSalir.addActionListener(e -> System.exit(0)); // Agrega un ActionListener al elemento "Salir". Cuando se hace clic, termina la ejecución de la aplicación (cierra la JVM).

        JMenu menuMantenimiento = new JMenu("Mantenimientos"); // Crea un nuevo menú llamado "Mantenimientos".
        menuBar.add(menuMantenimiento); // Agrega el menú "Mantenimientos" a la barra de menú.


    }
}
