package Presentacion;

import Dominio.Usuario;
import Persistencia.UsuarioDAO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginForm extends JDialog {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnSalir;

    private UsuarioDAO usuarioDAO;
    private Home homeForm;

    public LoginForm(Home homeForm) {

        this.homeForm = homeForm;
        this.usuarioDAO = new UsuarioDAO();

        setTitle("Login");
        setSize(350, 220);
        setLayout(null);
        setModal(true);
        setLocationRelativeTo(homeForm);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 30, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 180, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(30, 70, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 180, 25);
        add(txtPassword);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(50, 120, 110, 30);
        add(btnIngresar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(180, 120, 110, 30);
        add(btnSalir);

        btnIngresar.addActionListener(e -> login());

        btnSalir.addActionListener(e -> System.exit(0));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void login() {

        try {

            String usuarioTxt = txtUsuario.getText().trim();
            String passwordTxt = String.valueOf(txtPassword.getPassword());

            if(usuarioTxt.isEmpty() || passwordTxt.isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar usuario y contraseña",
                        "Login",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Usuario usuario = usuarioDAO.login(
                    usuarioTxt,
                    passwordTxt
            );

            if(usuario != null){

                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido " + usuario.getNombre(),
                        "Login",
                        JOptionPane.INFORMATION_MESSAGE
                );

                homeForm.setLogin(usuario);
                dispose();

            }else{

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos",
                        "Login",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Sistema",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}