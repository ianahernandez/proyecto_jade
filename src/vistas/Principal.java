package vistas;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.ImageIcon;

import modelos.Producto;
import utilidades.Render;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.Font;


import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings({"serial", "unused"})
public class Principal extends JFrame{
	private agentes.Usuario agente;
	private JTextField textField;
	private JTable tablaProductos;
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Principal(agentes.Usuario usuario) {
		agente = usuario;
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Sesion terminada");
            }
        });
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		setTitle(agente.getLocalName() );
        setBounds(100, 100, 735, 462);
        JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
        setContentPane(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(47, 67, 330, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setBounds(10, 74, 46, 14);
		panel.add(lblBuscar);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(515, 67, 194, 28);
		comboBox.setModel(new DefaultComboBoxModel(
                new String[] {"Dulces", "Enlatados", "Salsas", "Reposteria","Telefonia"}));
		panel.add(comboBox);
		
		JLabel label = new JLabel("Categorias");
		label.setBounds(445, 74, 70, 14);
		panel.add(label);
		

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLocation(10, 125);
        scrollPane.setSize(699, 190);
        getContentPane().add(scrollPane);
		
        //Definicion y configuraciones básicas de la tabla
		tablaProductos = new JTable();	
		tablaProductos.setSize(new Dimension(300, 300));
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(tablaProductos);
        tablaProductos.setColumnSelectionAllowed(false);
        tablaProductos.setRowSelectionAllowed(true);
        tablaProductos.setRowHeight(30);
        
        //Renderizar tabla para que admita imagenes y botones
        tablaProductos.setDefaultRenderer(Object.class, new Render());
        
        //Llenar la tablar con todos los productos
        cargarProductos();
        
        //Ajuste de ancho de las columnas
        TableColumnModel columnModel = tablaProductos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(350);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        
        //Accion del boton agregarAlCarrito
        tablaProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				int column = tablaProductos.getColumnModel().getColumnIndexAtX(ev.getX());
				int row = ev.getY()/tablaProductos.getRowHeight();
				
				if(row < tablaProductos.getRowCount() && row >= 0 && column < tablaProductos.getColumnCount() && column >= 0) {
					Object value = tablaProductos.getValueAt(row, column);
					
					if(value instanceof JButton) {
						((JButton)value).doClick();
						JButton boton = (JButton)value;
						System.out.println("Agregar el producto "+ tablaProductos.getValueAt(row, 1));
					}
					
				}
				
			}
		});

	}
	
	//Modelo Default para llenar la tabla
    private DefaultTableModel modelo = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Imagen","Producto", "Categoria","Precio","Agregar"
            }
        ) {
            /*Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };*/
            boolean[] canEdit = new boolean [] {
                false, false
            };

            /*public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }*/

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        
        
        //Agregar nueva fila
        public void AgregarFila(String nombre, float precio, String categoria,String url) {
        	JButton btnAgregar = new JButton("Agregar");
        	JLabel imagen = new JLabel("");
    		ImageIcon imagenProducto = new ImageIcon(new ImageIcon(Principal.class.getResource(url)).getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT));
    		imagen.setIcon(imagenProducto);
    		imagen.setBounds(543, 11, 30, 30);
            Object[] fila = new Object[5];
            fila[0] = imagen;
            fila[1] = nombre;
            fila[2] = categoria;
            fila[3] = precio;
            fila[4] = btnAgregar;
            modelo.addRow(fila);
            tablaProductos.setModel(modelo);
        }
        
     //Cargar todos los productos   
	 public void cargarProductos() {
		 AgregarFila("Hershey's Chocolate Syrup 24 oz (680 g)", (float) 4.14 ,"Dulces","/img/products/A123.png");
		 AgregarFila("Oreo Thins Sandwich Cookies, 10.1 oz (287 g)", (float) 3.96 ,"Dulces","/img/products/A124.png");
		 AgregarFila("M&M's Sharing Size Peanut Butter Milk Chocolate Candy 9.6 oz (272.2 g)", (float) 3.62 ,"Dulces","/img/products/A125.png");
		 AgregarFila("Nutella Ferrero Chocolate Hazelnut Spread 26.5oz (750 g)", (float) 8.5 ,"Dulces","/img/products/A126.png");
		 
	 }
}
