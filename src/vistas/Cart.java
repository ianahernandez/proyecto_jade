package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;

public class Cart {

	private JFrame Carrito;
	private final JPanel panel = new JPanel();
	
	@SuppressWarnings("serial")
	private DefaultTableModel modelo = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Imagen", "Producto", "Precio", "Cantidad", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
	private JTextField Total;
	private JTextField Subtotal;
	private JTable tablaCarrito;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cart window = new Cart();
					window.Carrito.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cart() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Carrito = new JFrame();
		Carrito.setIconImage(Toolkit.getDefaultToolkit().getImage(Cart.class.getResource("/img/logo.png")));
		Carrito.setTitle("Mi Carrito");
		Carrito.setResizable(false);
		Carrito.setBounds(100, 100, 600, 450);
		Carrito.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Carrito.getContentPane().setLayout(null);
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(0, 0, 594, 421);
		Carrito.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblMiCarrito = new JLabel("Mi Carrito");
		lblMiCarrito.setForeground(new Color(255, 255, 255));
		lblMiCarrito.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblMiCarrito.setBounds(432, 11, 101, 28);
		panel.add(lblMiCarrito);
		
		JLabel label_1 = new JLabel("");
		ImageIcon carrito = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/cart-logo.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		label_1.setIcon(carrito);
		label_1.setBounds(543, 11, 30, 30);
		panel.add(label_1);
		
		JButton btnAtras = new JButton("< Atr\u00E1s");
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
		scrollPane.setBounds(22, 27, 550, 217);
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
				if (modelo.getRowCount() > 0) {
			            int cant = modelo.getRowCount();
			            for (int i = 0; i < cant; i++) {
			                modelo.removeRow(0);
			            }
			        }
			        Subtotal.setText("0");
			        Total.setText("0");
			}
		});
		btnLimpiar.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 12));
		btnLimpiar.setBounds(22, 322, 109, 25);
		central.add(btnLimpiar);
		
		JButton btnProcesarPago = new JButton("Procesar Pago");
		btnProcesarPago.setForeground(new Color(255, 255, 255));
		btnProcesarPago.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		btnProcesarPago.setBackground(new Color(102, 205, 170));
		btnProcesarPago.setBounds(439, 322, 133, 25);
		btnProcesarPago.setContentAreaFilled(false);
		btnProcesarPago.setOpaque(true);
		central.add(btnProcesarPago);
		
		Total = new JTextField();
		Total.setBounds(439, 291, 133, 20);
		central.add(Total);
		Total.setColumns(10);
		
		Subtotal = new JTextField();
		Subtotal.setBounds(439, 260, 133, 20);
		central.add(Subtotal);
		Subtotal.setColumns(10);
		
		JLabel lblSubtotalBs = new JLabel("Subtotal Bs.");
		lblSubtotalBs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSubtotalBs.setBounds(357, 263, 72, 14);
		central.add(lblSubtotalBs);
		
		JLabel lblTotalBs = new JLabel("Total Bs.");
		lblTotalBs.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblTotalBs.setBounds(368, 294, 61, 14);
		central.add(lblTotalBs);
	}
}
