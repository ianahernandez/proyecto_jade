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
import java.awt.Toolkit;
import javax.swing.SwingConstants;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/logo.png")));
		setResizable(false);
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
        setBounds(100, 100, 700, 450);
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 434, 261);
        setContentPane(panel);
		panel.setLayout(null);
		

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setLocation(10, 194);
        scrollPane.setSize(674, 216);
        getContentPane().add(scrollPane);
		
        //Definicion y configuraciones básicas de la tabla
		tablaProductos = new JTable();	
		tablaProductos.setSize(new Dimension(300, 300));
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(tablaProductos);
        tablaProductos.setColumnSelectionAllowed(false);
        tablaProductos.setRowSelectionAllowed(true);
        tablaProductos.setRowHeight(30);
        
        JLabel lblSmartstore = new JLabel("SmartStore");
        lblSmartstore.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
        ImageIcon logo = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/logo.png")).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
		lblSmartstore.setIcon(logo);
        lblSmartstore.setBounds(10, 11, 155, 45);
        panel.add(lblSmartstore);
        
        JButton Usuario = new JButton("");
        Usuario.setBackground(Color.WHITE);
        ImageIcon imgUsuario = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/user.png")).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
		Usuario.setIcon(imgUsuario);
        Usuario.setBounds(582, 11, 46, 45);
        Usuario.setContentAreaFilled(false);
        Usuario.setOpaque(true);
		panel.add(Usuario);
		
		JButton Carrito = new JButton("");
		ImageIcon imgCarrito = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/cart.png")).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		Carrito.setIcon(imgCarrito);
        Carrito.setOpaque(true);
		Carrito.setContentAreaFilled(false);
		Carrito.setBackground(Color.WHITE);
		Carrito.setBounds(638, 11, 46, 45);
		panel.add(Carrito);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 205, 170));
		panel_1.setBounds(0, 65, 694, 91);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblBuscar = new JLabel("Busca un producto:");
		lblBuscar.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 14));
		lblBuscar.setForeground(new Color(255, 255, 255));
		lblBuscar.setBounds(160, 13, 117, 20);
		panel_1.add(lblBuscar);
		
		textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textField.setBounds(287, 11, 245, 28);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblOBuscaPor = new JLabel("Busca por categor\u00EDa:");
		lblOBuscaPor.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 14));
		lblOBuscaPor.setForeground(new Color(255, 255, 255));
		lblOBuscaPor.setBounds(160, 52, 122, 21);
		panel_1.add(lblOBuscaPor);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setBounds(287, 50, 245, 28);
		panel_1.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(
                new String[] {"Dulces", "Enlatados", "Salsas", "Reposteria","Telefonia"}));
		
		JLabel lblProductosDisponibles = new JLabel("Productos disponibles:");
		lblProductosDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductosDisponibles.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblProductosDisponibles.setBounds(10, 167, 674, 21);
		panel.add(lblProductosDisponibles);
        
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
        	JButton btnAgregar = new JButton("");
        	btnAgregar.setIcon(new ImageIcon(Principal.class.getResource("/img/anadir-btn.png")));
        	btnAgregar.setOpaque(true);
        	btnAgregar.setContentAreaFilled(false);
        	btnAgregar.setBackground(Color.WHITE);
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
