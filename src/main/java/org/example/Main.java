package org.example;

import Presentacion.LoginForm;
import Presentacion.Home;
import javax.swing.*;
import Presentacion.UsuarioForm;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // Utiliza el hilo de despacho de eventos (Event Dispatch Thread - EDT) para asegurar
            // que todas las operaciones relacionadas con la interfaz gráfica de usuario (Swing)
            // se realicen de forma segura y sin bloqueos.
            Home homeForm = new Home();

            //LoginForm loginForm = new LoginForm(homeForm);
            //loginForm.setVisible(true);
            UsuarioForm Usuario = new UsuarioForm();
            Usuario.setVisible(true);

            //if(homeForm.getLogin() != null){
              //  homeForm.setVisible(true);
           // } // Hace visible la ventana de inicio de sesión, solicitando al usuario que ingrese sus credenciales.
        });
    }
}
