package Presentacion;

import Dominio.Categoria;
import Persistencia.CategoriaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CategoriaForm extends JInternalFrame {

    private JTextField txtId;
    private JTextField txtNombre;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tabla;
    private DefaultTableModel modelo;

    private CategoriaDAO categoriaDAO;

    public CategoriaForm() {
        super("Mantenimiento de Categorías", true, true, true, true);

        categoriaDAO = new CategoriaDAO();

        setSize(700, 500);
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
        txtNombre.setBounds(130, 60, 200, 25);
        add(txtNombre);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(400, 40, 120, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(400, 80, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(400, 120, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(400, 160, 120, 30);
        add(btnLimpiar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 230, 620, 180);
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
            Categoria categoria = new Categoria();
            categoria.setNombre(txtNombre.getText().trim());

            categoriaDAO.Crear(categoria);

            JOptionPane.showMessageDialog(this, "Categoría guardada correctamente");
            cargarTabla();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);

            ArrayList<Categoria> categorias = categoriaDAO.Buscar("");

            for (Categoria categoria : categorias) {
                modelo.addRow(new Object[]{
                        categoria.getId(),
                        categoria.getNombre()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void actualizar() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione una categoría de la tabla");
                return;
            }

            Categoria categoria = new Categoria();
            categoria.setId(Integer.parseInt(txtId.getText()));
            categoria.setNombre(txtNombre.getText().trim());

            categoriaDAO.Actualizar(categoria);

            JOptionPane.showMessageDialog(this, "Categoría actualizada");
            cargarTabla();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void eliminar() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione una categoría de la tabla");
                return;
            }

            Categoria categoria = new Categoria();
            categoria.setId(Integer.parseInt(txtId.getText()));

            categoriaDAO.Eliminar(categoria);

            JOptionPane.showMessageDialog(this, "Categoría eliminada");
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
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        tabla.clearSelection();
    }
}