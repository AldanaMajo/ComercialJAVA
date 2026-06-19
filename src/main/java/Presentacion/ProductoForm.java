package Presentacion;


import Dominio.Producto;
import Persistencia.ProductoDAO;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;



public class ProductoForm extends JFrame {



    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtStock;


    private JComboBox<Integer> cbCategoria;
    private JComboBox<Integer> cbMarca;


    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;


    private JTable tabla;

    private DefaultTableModel modelo;


    private ProductoDAO productoDAO;


    public ProductoForm(){


        productoDAO = new ProductoDAO();



        setTitle("Mantenimiento de Productos");

        setSize(800,600);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JLabel id = new JLabel("");

        id.setBounds(30,20,100,25);

        add(id);


        txtId=new JTextField();

        txtId.setBounds(120,20,150,25);

        txtId.setVisible(false);

        add(txtId);





        JLabel nombre=new JLabel("Nombre:");

        nombre.setBounds(30,60,100,25);

        add(nombre);


        txtNombre=new JTextField();

        txtNombre.setBounds(120,60,150,25);

        add(txtNombre);





        JLabel descripcion=new JLabel("Descripcion:");

        descripcion.setBounds(30,100,100,25);

        add(descripcion);


        txtDescripcion=new JTextField();

        txtDescripcion.setBounds(120,100,150,25);

        add(txtDescripcion);





        JLabel categoria=new JLabel("Categoria:");

        categoria.setBounds(30,140,100,25);

        add(categoria);



        cbCategoria=new JComboBox<>();

        cbCategoria.addItem(1);

        cbCategoria.addItem(2);

        cbCategoria.setBounds(120,140,150,25);

        add(cbCategoria);





        JLabel marca=new JLabel("Marca:");

        marca.setBounds(30,180,100,25);

        add(marca);



        cbMarca=new JComboBox<>();

        cbMarca.addItem(1);

        cbMarca.addItem(2);

        cbMarca.setBounds(120,180,150,25);

        add(cbMarca);






        JLabel precio=new JLabel("Precio:");

        precio.setBounds(30,220,100,25);

        add(precio);



        txtPrecio=new JTextField();

        txtPrecio.setBounds(120,220,150,25);

        add(txtPrecio);






        JLabel stock=new JLabel("Stock:");

        stock.setBounds(30,260,100,25);

        add(stock);



        txtStock=new JTextField();

        txtStock.setBounds(120,260,150,25);

        add(txtStock);







        btnGuardar=new JButton("Guardar");

        btnGuardar.setBounds(350,60,120,30);

        add(btnGuardar);



        btnActualizar=new JButton("Actualizar");

        btnActualizar.setBounds(350,100,120,30);

        add(btnActualizar);



        btnEliminar=new JButton("Eliminar");

        btnEliminar.setBounds(350,140,120,30);

        add(btnEliminar);



        btnLimpiar=new JButton("Limpiar");

        btnLimpiar.setBounds(350,180,120,30);

        add(btnLimpiar);







        modelo=new DefaultTableModel();


        modelo.addColumn("ID");

        modelo.addColumn("Categoria");

        modelo.addColumn("Marca");

        modelo.addColumn("Nombre");

        modelo.addColumn("Descripcion");

        modelo.addColumn("Precio");

        modelo.addColumn("Stock");



        tabla=new JTable(modelo);



        JScrollPane scroll=new JScrollPane(tabla);

        scroll.setBounds(30,330,720,180);

        add(scroll);




        cargarTabla();




        btnGuardar.addActionListener(e->guardar());

        btnActualizar.addActionListener(e->actualizar());

        btnEliminar.addActionListener(e->eliminar());

        btnLimpiar.addActionListener(e->limpiar());



        tabla.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseClicked(java.awt.event.MouseEvent e){

                seleccionar();

            }

        });


    }






    private void guardar(){


        try{


            Producto p=new Producto();


            p.setIdCategoria(
                    (int)cbCategoria.getSelectedItem()
            );


            p.setIdMarca(
                    (int)cbMarca.getSelectedItem()
            );


            p.setNombre(
                    txtNombre.getText()
            );


            p.setDescripcion(
                    txtDescripcion.getText()
            );


            p.setPrecio(
                    Double.parseDouble(txtPrecio.getText())
            );


            p.setStock(
                    Integer.parseInt(txtStock.getText())
            );



            productoDAO.crear(p);



            JOptionPane.showMessageDialog(
                    this,
                    "Producto guardado"
            );


            cargarTabla();

            limpiar();



        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        }

    }






    private void cargarTabla(){


        try{


            modelo.setRowCount(0);



            ArrayList<Producto> lista =
                    productoDAO.obtenerTodos();



            for(Producto p:lista){


                modelo.addRow(new Object[]{


                        p.getId(),

                        p.getIdCategoria(),

                        p.getIdMarca(),

                        p.getNombre(),

                        p.getDescripcion(),

                        p.getPrecio(),

                        p.getStock()


                });


            }


        }catch(Exception e){


            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        }

    }





    private void actualizar(){


        try{


            Producto p=new Producto();


            p.setId(
                    Integer.parseInt(txtId.getText())
            );


            p.setIdCategoria(
                    (int)cbCategoria.getSelectedItem()
            );


            p.setIdMarca(
                    (int)cbMarca.getSelectedItem()
            );


            p.setNombre(txtNombre.getText());


            p.setDescripcion(txtDescripcion.getText());


            p.setPrecio(
                    Double.parseDouble(txtPrecio.getText())
            );


            p.setStock(
                    Integer.parseInt(txtStock.getText())
            );



            productoDAO.actualizar(p);


            cargarTabla();

            limpiar();



        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        }


    }





    private void eliminar(){


        try{


            productoDAO.eliminar(
                    Integer.parseInt(txtId.getText())
            );


            cargarTabla();

            limpiar();



        }catch(Exception e){


            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );


        }


    }







    private void seleccionar(){


        int fila=tabla.getSelectedRow();


        txtId.setText(
                tabla.getValueAt(fila,0).toString()
        );


        cbCategoria.setSelectedItem(
                tabla.getValueAt(fila,1)
        );


        cbMarca.setSelectedItem(
                tabla.getValueAt(fila,2)
        );


        txtNombre.setText(
                tabla.getValueAt(fila,3).toString()
        );


        txtDescripcion.setText(
                tabla.getValueAt(fila,4).toString()
        );


        txtPrecio.setText(
                tabla.getValueAt(fila,5).toString()
        );


        txtStock.setText(
                tabla.getValueAt(fila,6).toString()
        );

    }






    private void limpiar(){


        txtId.setText("");

        txtNombre.setText("");

        txtDescripcion.setText("");

        txtPrecio.setText("");

        txtStock.setText("");

    }




    public static void main(String[] args){


        new ProductoForm().setVisible(true);


    }



}