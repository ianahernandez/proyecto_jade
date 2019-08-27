package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import modelos.Producto;
import utilidades.Render;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Cart extends JFrame{
	
	private agentes.Carrito agente;
	
	private JFrame Carrito;
	private final JPanel panel = new JPanel();
	
	@SuppressWarnings("serial")
	private DefaultTableModel modelo = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo","Imagen", "Producto", "Precio", "Cantidad", "Total", "Eliminar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
	private JTextField Total;
	private JTextField Subtotal;
	private JTable tablaCarrito;


	/**
	 * Create the application.
	 */
	public Cart(agentes.Carrito carrito) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		agente = carrito;
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	System.out.println("Sesion terminada");
            	//agente.doDelete();
            }
        });
		initialize();
	}

	public void cerrarCarrito() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cart.class.getResource("/img/logo.png")));
		setTitle("Mi Carrito - "+ agente.getLocalName());
		setResizable(false);
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(null);
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(0, 0, 594, 421);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblMiCarrito = new JLabel("Mi Carrito");
		lblMiCarrito.setForeground(new Color(255, 255, 255));
		lblMiCarrito.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblMiCarrito.setBounds(432, 11, 101, 28);
		panel.add(lblMiCarrito);
		
		JLabel label_1 = new JLabel("");
		ImageIcon carrito = new ImageIcon(new ImageIcon(Cart.class.getResource("/img/cart-logo.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		label_1.setIcon(carrito);
		label_1.setBounds(543, 11, 30, 30);
		panel.add(label_1);
		
		JButton btnAtras = new JButton("< Atr\u00E1s");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrarCarrito();
			}
		});
		btnAtras.setForeground(new Color(255, 255, 255));
		btnAtras.setBackground(new Color(102, 205, 170));
		btnAtras.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		btnAtras.setBounds(10, 11, 77, 28);
		btnAtras.setContentAreaFilled(false);
		btnAtras.setOpaque(true);
		panel.add(btnAtras);
		
		JPanel central = new JPanel();
		central.setBackground(new Color(245, 245, 245));
		central.setBounds(0, 63, 594, 358);
		panel.add(central);
		central.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 27, 550, 210);
		central.add(scrollPane);
		
		tablaCarrito = new JTable();

		tablaCarrito.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tablaCarrito.setGridColor(new Color(102, 205, 170));
		tablaCarrito.setSelectionBackground(new Color(102, 205, 170));
		tablaCarrito.setShowVerticalLines(false);
		tablaCarrito.setSize(new Dimension(300, 300));
		tablaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaCarrito.setRowSelectionAllowed(true);
		tablaCarrito.setRowHeight(30);
		tablaCarrito.setColumnSelectionAllowed(false);
		tablaCarrito.setModel(modelo);
		scrollPane.setViewportView(tablaCarrito);
		tablaCarrito.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
		tablaCarrito.getTableHeader().setOpaque(false);
		tablaCarrito.getTableHeader().setBackground(new Color(102, 205, 170));
		tablaCarrito.getTableHeader().setForeground(new Color(255, 255, 255));
		
		JButton btnLimpiar = new JButton("Limpiar Carrito");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Limpiar();
			}
		});
		btnLimpiar.setBorder(new LineBorder(new Color(102, 205, 170), 2));
		btnLimpiar.setContentAreaFilled(false);
		btnLimpiar.setOpaque(true);
		btnLimpiar.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 12));
		btnLimpiar.setBounds(22, 312, 109, 35);
		central.add(btnLimpiar);
		
		JButton btnProcesarPago = new JButton("Procesar Pago");
		btnProcesarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcesarPago();
			}
		});
		btnProcesarPago.setForeground(new Color(255, 255, 255));
		btnProcesarPago.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		btnProcesarPago.setBackground(new Color(102, 205, 170));
		btnProcesarPago.setBounds(439, 312, 133, 35);
		btnProcesarPago.setContentAreaFilled(false);
		btnProcesarPago.setOpaque(true);
		central.add(btnProcesarPago);
		
		Total = new JTextField();
		Total.setBounds(439, 279, 133, 20);
		central.add(Total);
		Total.setColumns(10);
		
		Subtotal = new JTextField();
		Subtotal.setBounds(439, 248, 133, 20);
		central.add(Subtotal);
		Subtotal.setColumns(10);
		
		JLabel lblSubtotalBs = new JLabel("Subtotal Bs.");
		lblSubtotalBs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtotalBs.setBounds(357, 251, 72, 14);
		central.add(lblSubtotalBs);
		
		JLabel lblTotalBs = new JLabel("Total Bs.");
		lblTotalBs.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblTotalBs.setBounds(368, 282, 61, 14);
		central.add(lblTotalBs);
		//Renderizar tabla para que admita imagenes y botones
		tablaCarrito.setDefaultRenderer(Object.class, new Render());
		
		//Evento que indica cuando han cambiado los valores en la tabla
		modelo.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
				
				//Calculo de nuevo total y subtotal
				
				float total = 0;
				for(int i=0; i<modelo.getRowCount(); i++) {
					total = total + (float)modelo.getValueAt(i, 5);
				}
				Subtotal.setText(String.valueOf(Math.round(total*100)/100d));
				Total.setText(String.valueOf(Math.round(total*100)/100d));
			}
			
		});
			
		//Ajuste de ancho de las columnas
        TableColumnModel columnModel = tablaCarrito.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(100);
        
      //Accion del boton agregarAlCarrito
        tablaCarrito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				int column = tablaCarrito.getColumnModel().getColumnIndexAtX(ev.getX());
				int row = ev.getY()/tablaCarrito.getRowHeight();
				
				if(row < tablaCarrito.getRowCount() && row >= 0 && column < tablaCarrito.getColumnCount() && column >= 0) {
					Object value = tablaCarrito.getValueAt(row, column);
					
					if(value instanceof JButton) {
						((JButton)value).doClick();
						JButton boton = (JButton)value;
						ArrayList<Producto> actualizar = agente.getProductos();
						if ((int)tablaCarrito.getValueAt(row, 4) == 1) {
							System.out.println("Eliminando producto con codigo "+ actualizar.get(row).getCodigo() +" del carrito.");
							actualizar.remove(row);
							agente.setProductos(actualizar);
							modelo.removeRow(row);
						} else {
							Object item = modelo.getDataVector().get(row);
							int cant = (int)((Vector)item).get(4) - 1;
							((Vector)item).set(4, cant);
							float precio = (float)((Vector)item).get(3);
							((Vector)item).set(5, cant*precio);
							modelo.fireTableDataChanged();
							tablaCarrito.setModel(modelo);
							System.out.println("Producto "+ ((Vector)item).get(0).toString() +": Cantidad actualizada en el carrito.");
						}
						
					}
					
				}
				
			}
		});
        
		cargarProductos(agente.getProductos());
	}
	       
	public void Limpiar() {
		if (modelo.getRowCount() > 0) {
			int cant = modelo.getRowCount();
	        for (int i = 0; i < cant; i++) {
	            modelo.removeRow(0);
	        }
	        agente.setProductos(new ArrayList<Producto>());
	        System.out.println("Carrito vaciado.");
	    }
		else JOptionPane.showMessageDialog(this, "El carrito ya está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
	    Subtotal.setText("0");
	    Total.setText("0");
	}
            
	public void ProcesarPago() {
		if (modelo.getRowCount() > 0)
			new Payment(this);
		else JOptionPane.showMessageDialog(this, "Ingrese productos al carrito para realizar un pago.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
    //Agregar nueva fila en la tabla
	public void AgregarFila(String codigo, String nombre, float precio, String categoria,String url) {
		JButton btnEliminar = new JButton("");
		btnEliminar.setIcon(new ImageIcon(Cart.class.getResource("/img/eliminar.png")));
		btnEliminar.setOpaque(true);
		btnEliminar.setContentAreaFilled(false);
		btnEliminar.setBackground(Color.WHITE);
		JLabel imagen = new JLabel("");
		ImageIcon imagenProducto = new ImageIcon(new ImageIcon(Cart.class.getResource(url)).getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT));
		imagen.setIcon(imagenProducto);
		imagen.setBounds(543, 11, 30, 30);
        Object[] fila = new Object[7];
        fila[0] = codigo;
        fila[1] = imagen;
        fila[2] = nombre;
        fila[3] = precio;
        fila[4] = 1;
        fila[5] = precio;
        fila[6] = btnEliminar;
        modelo.addRow(fila);
        tablaCarrito.setModel(modelo);
    }
	
	//Cargar productos en la tabla  
	public void cargarProductos(ArrayList<Producto> productos) {	 	 
		 for(Producto producto:  productos) {
			 AgregarFila(producto.getCodigo(),producto.getNombre(),producto.getPrecio(),producto.getCategoria(),producto.getImagen());
		 }		 
	 }

	public JTable getTablaCarrito() {
		return tablaCarrito;
	}

	public void setTablaCarrito(JTable tablaCarrito) {
		this.tablaCarrito = tablaCarrito;
	}
	
	//Incrementa la cantidad de un producto que ya esta añadido al carrito
	public void incrementar(String codigo) {
		for(Object item: modelo.getDataVector()) {
			if(((Vector)item).get(0).toString().equals(codigo))
			{
				int cant = (int)((Vector)item).get(4) + 1;
				((Vector)item).set(4, cant);
				float precio = (float)((Vector)item).get(3);
				((Vector)item).set(5, cant*precio);
				modelo.fireTableDataChanged();
				tablaCarrito.setModel(modelo);
				
			}			
		}
	}
    
    
}
