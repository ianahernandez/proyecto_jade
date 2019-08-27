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
import javax.swing.JOptionPane;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

	public void cerrarSesion() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
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
		tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tablaProductos.setGridColor(new Color(102, 205, 170));
		tablaProductos.setSelectionBackground(new Color(102, 205, 170));
		tablaProductos.setShowVerticalLines(false);
		tablaProductos.setSize(new Dimension(300, 300));
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(tablaProductos);
        tablaProductos.setColumnSelectionAllowed(false);
        tablaProductos.setRowSelectionAllowed(true);
        tablaProductos.setRowHeight(30);
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaProductos.getTableHeader().setOpaque(false);
        tablaProductos.getTableHeader().setBackground(new Color(102, 205, 170));
        tablaProductos.getTableHeader().setForeground(new Color(255, 255, 255));
        
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
		lblBuscar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblBuscar.setForeground(new Color(255, 255, 255));
		ImageIcon buscar = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/search.png")).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		lblBuscar.setIcon(buscar);
        lblBuscar.setBounds(147, 13, 142, 20);
		panel_1.add(lblBuscar);
		
		textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		textField.setBounds(294, 11, 245, 28);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblOBuscaPor = new JLabel("Busca por categor\u00EDa:");
		lblOBuscaPor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOBuscaPor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblOBuscaPor.setForeground(new Color(255, 255, 255));
		lblOBuscaPor.setIcon(buscar);
		lblOBuscaPor.setBounds(142, 52, 147, 20);
		panel_1.add(lblOBuscaPor);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setBounds(294, 50, 245, 28);
		panel_1.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(
                new String[] {"Todo","Dulces", "Enlatados", "Salsas", "Reposteria","Telefonía"}));
		
		JLabel lblProductosDisponibles = new JLabel("Productos disponibles:");
		lblProductosDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductosDisponibles.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblProductosDisponibles.setBounds(10, 167, 674, 21);
		panel.add(lblProductosDisponibles);
		
		JButton btnCerrarSesin = new JButton("Cerrar sesi\u00F3n");
		btnCerrarSesin.setFocusable(false);
		btnCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrarSesion();
			}
		});
		btnCerrarSesin.setBackground(new Color(255, 255, 255));
		btnCerrarSesin.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 12));
		btnCerrarSesin.setBorder(new LineBorder(new Color(102, 205, 170), 2));
		btnCerrarSesin.setContentAreaFilled(false);
		btnCerrarSesin.setOpaque(true);
		btnCerrarSesin.setBounds(465, 11, 99, 23);
		panel.add(btnCerrarSesin);
        
        //Renderizar tabla para que admita imagenes y botones
        tablaProductos.setDefaultRenderer(Object.class, new Render());
        
        //Llenar la tablar con todos los productos
        cargarProductos(listaProductos());
        
        //Ajuste de ancho de las columnas
        TableColumnModel columnModel = tablaProductos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(350);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(100);
        
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
						System.out.println("Agregar el producto "+ tablaProductos.getValueAt(row, 0));
						agente.agregarCarrito((String) tablaProductos.getValueAt(row, 0));
					}
					
				}
				
			}
		});
        
        textField.addKeyListener(new KeyListener(){

            @Override
            public void keyPressed(KeyEvent e){

                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                	buscarPorNombre(textField.getText());
                }       
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }
        }
    );;
        
        comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedItem().toString().equals("Todo"))
					cargarProductos(listaProductos());
				else
				productosEncontrados((String)comboBox.getSelectedItem().toString());
			}
		});
        
        Carrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agente.abrirCarrito();
			}
		});
        
        Usuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agente.abrirPerfil();
			}
		});

	}
	
	//Modelo Default para llenar la tabla
    private DefaultTableModel modelo = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo","Imagen","Producto", "Categoria","Precio","Agregar"
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
        public void AgregarFila(String codigo, String nombre, float precio, String categoria,String url) {
        	JButton btnAgregar = new JButton("");
        	btnAgregar.setIcon(new ImageIcon(Principal.class.getResource("/img/anadir-btn.png")));
        	btnAgregar.setOpaque(true);
        	btnAgregar.setContentAreaFilled(false);
        	btnAgregar.setBackground(Color.WHITE);
        	JLabel imagen = new JLabel("");
    		ImageIcon imagenProducto = new ImageIcon(new ImageIcon(Principal.class.getResource(url)).getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT));
    		imagen.setIcon(imagenProducto);
    		imagen.setBounds(543, 11, 30, 30);
            Object[] fila = new Object[6];
            fila[0] = codigo;
            fila[1] = imagen;
            fila[2] = nombre;
            fila[3] = categoria;
            fila[4] =  String.valueOf(precio);
            fila[5] = btnAgregar;
            modelo.addRow(fila);
            tablaProductos.setModel(modelo);
        }
        
     //Cargar productos   
        public void cargarProductos(ArrayList<Producto> productos) {	 
   		 Limpiar();
   		 for(Producto producto:  productos) {
   			 AgregarFila(producto.getCodigo(),producto.getNombre(),producto.getPrecio(),producto.getCategoria(),producto.getImagen());
   		 }		 
   	 }
   	 
   	 //Lista con todos los productos disponibles en la tienda
   	 public ArrayList<Producto> listaProductos(){    	
       	ArrayList<Producto> products = new ArrayList<Producto>();
       	Producto producto1 = new Producto("A123","Nutella Ferrero Chocolate Hazelnut Spread 26.5oz (750 g)", (float) 8.5 ,"Dulces", "/img/products/A123.png");
       	Producto producto2 = new Producto("A124","Hershey's Chocolate Syrup 24 oz (680 g)", (float) 4.14 ,"Dulces","/img/products/A124.png");
       	Producto producto3 = new Producto("A125","Oreo Thins Sandwich Cookies, 10.1 oz (287 g)", (float) 3.96 ,"Dulces","/img/products/A125.png");
       	Producto producto4 = new Producto("A126","M&M's Sharing Size Peanut Butter Milk Chocolate Candy 9.6 oz (272.2 g)", (float) 3.62 ,"Dulces","/img/products/A126.png");
       	Producto producto5 = new Producto("A127","Chocolates dandy, 18 unidades ", (float) 5.5 ,"Dulces","/img/products/A127.jpg");
       	
       	products.add(producto1);
       	products.add(producto2);
       	products.add(producto3);
       	products.add(producto4);
       	products.add(producto5);
       	return products;
   	    	
   	 }

   	 //Cargar productos buscados   
        public void productosEncontrados(String categoria) {
        ArrayList<Producto> productos = agente.productosCategoria(listaProductos(), categoria);
        if(productos.size()==0)
        	JOptionPane.showMessageDialog(this, "No hay productos en esta categoría.", "Error", JOptionPane.ERROR_MESSAGE);
        else {
	        Limpiar();
	   		cargarProductos(productos);
	      }
   	 }
        
      //Cargar productos buscados por nombre
        public void buscarPorNombre(String nombre) {
        ArrayList<Producto> productos = agente.productosNombre(listaProductos(), nombre);
        if(productos.size()==0)
        	JOptionPane.showMessageDialog(this, "No hay productos para su búsqueda.", "Error", JOptionPane.ERROR_MESSAGE);
        else {
	        Limpiar();
	   		cargarProductos(productos);
	      }
   	 }
        
        //limpiar modelo para hacer busquedas
        public void Limpiar() {
            if (modelo.getRowCount() > 0) {
              int cant = modelo.getRowCount();
                  for (int i = 0; i < cant; i++) {
                      modelo.removeRow(0);
                  }
              }
          }
   }
