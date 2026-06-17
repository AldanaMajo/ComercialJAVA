package Presentacion;

import Dominio.Usuario;
import Persistencia.UsuarioDAO;

import javax.swing.*;
import java.sql.SQLException;

public class LoginForm extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public LoginForm() {

        setTitle("Login");
        setSize(350, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 30, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 150, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(30, 70, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 150, 25);
        add(txtPassword);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(120, 110, 120, 30);
        add(btnIngresar);

        btnIngresar.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {

        try {

            UsuarioDAO dao = new UsuarioDAO();

            Usuario usuario = dao.login(
                    txtUsuario.getText(),
                    String.valueOf(txtPassword.getPassword())
            );

            if (usuario != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido " + usuario.getNombre()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos"
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm();
            login.setVisible(true);
        });
    }
}