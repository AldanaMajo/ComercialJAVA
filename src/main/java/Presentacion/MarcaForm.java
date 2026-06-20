package Presentacion;

import Dominio.Marca;
import Persistencia.MarcaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarcaForm extends JInternalFrame {

    private JTextField txtNombre;

    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaMarcas;
    private JScrollPane scrollTabla;

    private DefaultTableModel modeloTabla;
    private MarcaDAO mDAO;

    private int idSeleccionado = 0;

    public MarcaForm() {
        super("Gestión de Marcas", true, true, true, true);

        setSize(600, 450);
        setLayout(null);
        setVisible(true);

        mDAO = new MarcaDAO();

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 40, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(110, 40, 200, 25);
        add(txtNombre);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 30, 120, 30);
        add(btnGuardar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(350, 70, 120, 30);
        add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 110, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(350, 150, 120, 30);
        add(btnLimpiar);

        String[] columnas = {"ID", "Nombre de Marca"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tablaMarcas = new JTable(modeloTabla);

        scrollTabla = new JScrollPane(tablaMarcas);
        scrollTabla.setBounds(30, 220, 520, 150);
        add(scrollTabla);

        listar();

        btnGuardar.addActionListener(e -> guardar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tablaMarcas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaMarcas.getSelectedRow();
                if (fila >= 0) {
                    idSeleccionado = Integer.parseInt(tablaMarcas.getValueAt(fila, 0).toString());
                    txtNombre.setText(tablaMarcas.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                return;
            }

            Marca m = new Marca(0, nombre, (byte) 1);
            mDAO.crear(m);

            JOptionPane.showMessageDialog(this, "¡Marca guardada exitosamente!");
            limpiarCampos();
            listar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void modificar() {
        try {
            if (idSeleccionado == 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una marca de la tabla para modificar");
                return;
            }

            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                return;
            }

            Marca m = new Marca(idSeleccionado, nombre, (byte) 1);

            if (mDAO.actualizar(m)) {
                JOptionPane.showMessageDialog(this, "¡Marca actualizada con éxito!");
                limpiarCampos();
                listar();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage());
        }
    }

    private void eliminar() {
        try {
            if (idSeleccionado == 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una marca para eliminar");
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas eliminar esta marca?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                if (mDAO.eliminar(idSeleccionado)) {
                    JOptionPane.showMessageDialog(this, "Marca eliminada correctamente.");
                    limpiarCampos();
                    listar();
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void listar() {
        modeloTabla.setRowCount(0);

        try {
            ArrayList<Marca> lista = mDAO.obtenerTodos();

            for (Marca m : lista) {
                Object[] fila = new Object[2];
                fila[0] = m.getIdMarca();
                fila[1] = m.getNombre();
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        idSeleccionado = 0;
        txtNombre.setText("");
        tablaMarcas.clearSelection();
    }
}