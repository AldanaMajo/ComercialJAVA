package Presentacion;

import Dominio.Marca;
import Persistencia.MarcaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarcaForm extends JDialog {

    // Ya no existe la variable txtId aquí arriba
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tablaMarcas;
    private JScrollPane scrollTabla;

    private DefaultTableModel modeloTabla;
    private MarcaDAO mDAO;

    // Esta variable guardará el ID de forma invisible para el usuario
    private int idSeleccionado = 0;

    public MarcaForm(JFrame homeForm) {
        setContentPane(panelPrincipal);
        setModal(true);
        setTitle("Gestión de Marcas");
        setSize(600, 450);
        setLocationRelativeTo(homeForm);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mDAO = new MarcaDAO();

        String[] columnas = {"ID", "Nombre de Marca"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tablaMarcas.setModel(modeloTabla);

        listar();

        // EVENTO: GUARDAR
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText().trim();
                    if (nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                        return;
                    }
                    Marca m = new Marca(0, nombre, (byte) 1);
                    mDAO.crear(m);
                    JOptionPane.showMessageDialog(null, "¡Marca guardada exitosamente!");
                    limpiarCampos();
                    listar();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage());
                }
            }
        });

        // EVENTO: MODIFICAR
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validamos usando nuestra variable oculta en vez de la caja de texto
                    if (idSeleccionado == 0) {
                        JOptionPane.showMessageDialog(null, "Selecciona una marca de la tabla para modificar");
                        return;
                    }
                    String nombre = txtNombre.getText().trim();

                    Marca m = new Marca(idSeleccionado, nombre, (byte) 1);
                    if (mDAO.actualizar(m)) {
                        JOptionPane.showMessageDialog(null, "¡Marca actualizada con éxito!");
                        limpiarCampos();
                        listar();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar: " + ex.getMessage());
                }
            }
        });

        // EVENTO: ELIMINAR
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validamos usando nuestra variable oculta
                    if (idSeleccionado == 0) {
                        JOptionPane.showMessageDialog(null, "Selecciona una marca para eliminar");
                        return;
                    }
                    int confirmar = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar esta marca?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        if (mDAO.eliminar(idSeleccionado)) {
                            JOptionPane.showMessageDialog(null, "Marca eliminada correctamente.");
                            limpiarCampos();
                            listar();
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
                }
            }
        });

        // EVENTO: LIMPIAR
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // EVENTO: CLIC EN LA TABLA
        tablaMarcas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaMarcas.getSelectedRow();
                if (fila >= 0) {
                    // Guardamos el ID en la variable oculta en lugar de ponerlo en una caja
                    idSeleccionado = Integer.parseInt(tablaMarcas.getValueAt(fila, 0).toString());
                    txtNombre.setText(tablaMarcas.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    private void listar() {
        modeloTabla.setRowCount(0);
        try {
            ArrayList<Marca> lista = mDAO.obtenerTodos();
            for (Marca m : lista) {
                Object[] fila = new Object[3];
                fila[0] = m.getIdMarca();
                fila[1] = m.getNombre();
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        idSeleccionado = 0; // Reseteamos la variable oculta
        txtNombre.setText("");
        tablaMarcas.clearSelection();
    }
}