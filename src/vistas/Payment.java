package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Payment extends JFrame {

	private JTextField nroTarjeta;
	private JTextField nombre;
	private JPasswordField csv;
	private Cart cart;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the application.
	 */
	public Payment(Cart ventana) {
		cart = ventana;
		setVisible(true);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Procesar Pago");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Payment.class.getResource("/img/logo.png")));
		setResizable(false);
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(null);
		ImageIcon img = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/payment.png")).getImage().getScaledInstance(320, 140, Image.SCALE_DEFAULT));
		ImageIcon visa = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/visa.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		ImageIcon master = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/mastercard.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		ImageIcon amex = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/american-express.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		
		JLabel pago4 = new JLabel("");
		ImageIcon online = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/payment-online.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		pago4.setIcon(online);
		pago4.setBounds(521, 334, 50, 50);
		getContentPane().add(pago4);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(0, 39, 285, 42);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblDetallesDeTarjeta = new JLabel("Detalles de la tarjeta");
		lblDetallesDeTarjeta.setForeground(new Color(255, 255, 255));
		lblDetallesDeTarjeta.setFont(new Font("Segoe UI", Font.BOLD, 16));
		ImageIcon tarjeta = new ImageIcon(new ImageIcon(Principal.class.getResource("/img/credit-card.png")).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		lblDetallesDeTarjeta.setIcon(tarjeta);
		lblDetallesDeTarjeta.setBounds(10, 0, 265, 42);
		panel.add(lblDetallesDeTarjeta);
		
		JLabel lblNmeroDeTarjeta = new JLabel("N\u00FAmero de tarjeta");
		lblNmeroDeTarjeta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNmeroDeTarjeta.setBounds(10, 110, 165, 20);
		getContentPane().add(lblNmeroDeTarjeta);
		
		nroTarjeta = new JTextField();
		nroTarjeta.setToolTipText("");
		nroTarjeta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		nroTarjeta.setColumns(10);
		nroTarjeta.setBounds(10, 132, 254, 29);
		getContentPane().add(nroTarjeta);
		
		JLabel lblNombreDelTitular = new JLabel("Nombre del titular");
		lblNombreDelTitular.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNombreDelTitular.setBounds(10, 172, 165, 20);
		getContentPane().add(lblNombreDelTitular);
		
		nombre = new JTextField();
		nombre.setToolTipText("");
		nombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		nombre.setColumns(10);
		nombre.setBounds(10, 194, 254, 29);
		getContentPane().add(nombre);
		
		JLabel lblFechaDeVencimiento = new JLabel("Fecha de vencimiento");
		lblFechaDeVencimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblFechaDeVencimiento.setBounds(10, 234, 137, 20);
		getContentPane().add(lblFechaDeVencimiento);
		
		JSpinner fecha = new JSpinner();
		fecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date vencimiento = cal.getTime();
		SpinnerDateModel dmodel = new SpinnerDateModel(new Date(), vencimiento, null, Calendar.MONTH);
		fecha.setModel(dmodel);
		JSpinner.DateEditor de_fecha = new JSpinner.DateEditor(fecha,"MM/yyyy");
	    fecha.setEditor(de_fecha);
		fecha.setBounds(10, 257, 137, 29);
		getContentPane().add(fecha);
		
		JLabel lblCsvNo = new JLabel("CSV No.");
		lblCsvNo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblCsvNo.setBounds(170, 234, 94, 20);
		getContentPane().add(lblCsvNo);
		
		csv = new JPasswordField();
		csv.setColumns(10);
		csv.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		csv.setBounds(170, 257, 94, 29);
		getContentPane().add(csv);
		
		JButton btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pagar();
			}
		});
		btnProcesar.setContentAreaFilled(false);
		btnProcesar.setOpaque(true);
		btnProcesar.setForeground(Color.WHITE);
		btnProcesar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnProcesar.setBackground(new Color(102, 205, 170));
		btnProcesar.setBounds(145, 327, 117, 32);
		getContentPane().add(btnProcesar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBorder(new LineBorder(new Color(102, 205, 170), 2));
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.setOpaque(true);
		btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnCancelar.setBounds(10, 327, 117, 32);
		getContentPane().add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 205, 170));
		panel_1.setBounds(284, 0, 310, 421);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("SmartStore");
		label.setBounds(0, 47, 309, 32);
		panel_1.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(255, 255, 255));
		label.setFont(new Font("Segoe UI", Font.BOLD, 24));
		
		JLabel lblProcesarPago = new JLabel("Procesar pago");
		lblProcesarPago.setBounds(0, 84, 309, 25);
		panel_1.add(lblProcesarPago);
		lblProcesarPago.setHorizontalAlignment(SwingConstants.CENTER);
		lblProcesarPago.setForeground(new Color(255, 255, 255));
		lblProcesarPago.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
		
		JLabel imagen = new JLabel("");
		imagen.setBounds(-9, 149, 318, 140);
		panel_1.add(imagen);
		imagen.setIcon(img);
		
		JLabel pago1 = new JLabel("");
		pago1.setBounds(20, 334, 50, 50);
		panel_1.add(pago1);
		pago1.setIcon(visa);
		
		JLabel pago2 = new JLabel("");
		pago2.setBounds(92, 334, 50, 50);
		panel_1.add(pago2);
		pago2.setIcon(master);
		
		JLabel pago3 = new JLabel("");
		pago3.setBounds(165, 334, 50, 50);
		panel_1.add(pago3);
		pago3.setIcon(amex);
	}
	
	public void Pagar() {
		if (!nombre.getText().equals("") && !nroTarjeta.getText().equals("") && csv.getPassword().length==3) {
			JOptionPane.showMessageDialog(this, "Su pago ha sido procesado exitosamente.");
			cart.Limpiar();
			dispose();
		}
		else JOptionPane.showMessageDialog(this, "Por favor complete los datos para continuar.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
