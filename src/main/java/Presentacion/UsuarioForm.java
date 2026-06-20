package Presentacion;

import Dominio.Usuario;
import Persistencia.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class UsuarioForm extends JInternalFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtLogin;
    private JTextField txtPassword;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tabla;
    private JComboBox<String> cbEstatus;

    private DefaultTableModel modelo;

    private UsuarioDAO usuarioDAO;

    public UsuarioForm() {
        super("Mantenimiento de Usuarios", true, true, true, true);

        usuarioDAO = new UsuarioDAO();

        setSize(750, 550);
        setLayout(null);
        setVisible(true);

        JLabel lblId = new JLabel("");
        lblId.setBounds(30, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 150, 25);
        txtId.setVisible(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 60, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 60, 150, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(30, 100, 100, 25);
        add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(130, 100, 150, 25);
        add(txtApellido);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(30, 140, 100, 25);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setBounds(130, 140, 150, 25);
        add(txtLogin);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 180, 100, 25);
        add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setBounds(130, 180, 150, 25);
        add(txtPassword);

        JLabel lblEstatus = new JLabel("Estatus:");
        lblEstatus.setBounds(30, 220, 100, 25);
        add(lblEstatus);

        cbEstatus = new JComboBox<>();
        cbEstatus.addItem("ACTIVO");
        cbEstatus.addItem("INACTIVO");
        cbEstatus.setBounds(130, 220, 150, 25);
        add(cbEstatus);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 50, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 90, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 130, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(350, 170, 120, 30);
        add(btnLimpiar);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Login");
        modelo.addColumn("Estatus");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 300, 650, 150);
        add(scroll);

        cargarTabla();

        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                seleccionar();
            }
        });
    }

    private void guardar() {
        try {
            Usuario usuario = new Usuario();

            usuario.setNombre(txtNombre.getText().trim());
            usuario.setApellido(txtApellido.getText().trim());
            usuario.setLogin(txtLogin.getText().trim());
            usuario.setPassword(txtPassword.getText().trim());

            if (cbEstatus.getSelectedItem().equals("ACTIVO")) {
                usuario.setEstatus((byte) 1);
            } else {
                usuario.setEstatus((byte) 2);
            }

            usuarioDAO.crear(usuario);

            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente");
            cargarTabla();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);

            ArrayList<Usuario> usuarios = usuarioDAO.obtenerTodos();

            for (Usuario usuario : usuarios) {
                modelo.addRow(new Object[]{
                        usuario.getIdUsuario(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getLogin(),
                        usuario.getStrEstatus()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void actualizar() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
                return;
            }

            Usuario usuario = new Usuario();

            usuario.setIdUsuario(Integer.parseInt(txtId.getText()));
            usuario.setNombre(txtNombre.getText().trim());
            usuario.setApellido(txtApellido.getText().trim());
            usuario.setLogin(txtLogin.getText().trim());
            usuario.setPassword(txtPassword.getText().trim());

            if (cbEstatus.getSelectedItem().equals("ACTIVO")) {
                usuario.setEstatus((byte) 1);
            } else {
                usuario.setEstatus((byte) 2);
            }

            usuarioDAO.actualizar(usuario);

            JOptionPane.showMessageDialog(this, "Usuario actualizado");
            cargarTabla();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void eliminar() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
                return;
            }

            int id = Integer.parseInt(txtId.getText());
            usuarioDAO.eliminar(id);

            JOptionPane.showMessageDialog(this, "Usuario eliminado");
            cargarTabla();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void seleccionar() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) return;

        txtId.setText(tabla.getValueAt(fila, 0).toString());
        txtNombre.setText(tabla.getValueAt(fila, 1).toString());
        txtApellido.setText(tabla.getValueAt(fila, 2).toString());
        txtLogin.setText(tabla.getValueAt(fila, 3).toString());
        cbEstatus.setSelectedItem(tabla.getValueAt(fila, 4).toString());
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtLogin.setText("");
        txtPassword.setText("");
        cbEstatus.setSelectedIndex(0);
        tabla.clearSelection();
    }
}