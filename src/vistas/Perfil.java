package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Perfil extends JFrame {

	private JTextField correo;
	private JPasswordField contrasenna;
	private JTextField nombre;
	private agentes.Usuario agente;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Perfil(agentes.Usuario usuario) {
		agente = usuario;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 setTitle("Perfil de usuario");
		 setIconImage(Toolkit.getDefaultToolkit().getImage(Perfil.class.getResource("/img/logo.png")));
		 getContentPane().setBackground(new Color(245, 245, 245));
		 setResizable(false);
		 setBounds(100, 100, 600, 450);
		 getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(0, 0, 300, 421);
		 getContentPane().add(panel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Perfil.class.getResource("/img/logo.png")));
		label.setBounds(85, 123, 128, 128);
		panel.add(label);
		
		JLabel label_1 = new JLabel("SmartStore");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Segoe UI", Font.BOLD, 24));
		label_1.setBounds(10, 262, 280, 32);
		panel.add(label_1);
		
		JButton button = new JButton("< Atr\u00E1s");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setOpaque(true);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		button.setContentAreaFilled(false);
		button.setBackground(new Color(102, 205, 170));
		button.setBounds(10, 11, 77, 28);
		panel.add(button);
		
		JLabel lblPerfil = new JLabel("Perfil");
		lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerfil.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblPerfil.setBounds(329, 11, 238, 29);
		 getContentPane().add(lblPerfil);
		
		JButton btnGuardar = new JButton("Guardar cambios");
		btnGuardar.setOpaque(true);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		btnGuardar.setContentAreaFilled(false);
		btnGuardar.setBackground(new Color(102, 205, 170));
		btnGuardar.setBounds(408, 370, 159, 29);
		btnGuardar.setContentAreaFilled(false);
		btnGuardar.setOpaque(true);
		 getContentPane().add(btnGuardar);
		
		JLabel lblCorreo = new JLabel("Correo electr\u00F3nico");
		lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblCorreo.setBounds(329, 108, 165, 20);
		 getContentPane().add(lblCorreo);
		
		correo = new JTextField();
		correo.setToolTipText("");
		correo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		correo.setColumns(10);
		correo.setBounds(329, 130, 238, 29);
		 getContentPane().add(correo);
		
		JLabel lblContrase = new JLabel("Contrase\u00F1a");
		lblContrase.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblContrase.setBounds(329, 170, 121, 20);
		 getContentPane().add(lblContrase);
		
		contrasenna = new JPasswordField();
		contrasenna.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		contrasenna.setColumns(10);
		contrasenna.setBounds(329, 192, 238, 29);
		 getContentPane().add(contrasenna);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNombre.setBounds(329, 46, 165, 20);
		 getContentPane().add(lblNombre);
		
		nombre = new JTextField();
		nombre.setToolTipText("");
		nombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		nombre.setColumns(10);
		nombre.setBounds(329, 68, 238, 29);
		 getContentPane().add(nombre);
		
		JLabel lblCategoraPreferida = new JLabel("Categor\u00EDa Preferida 1");
		lblCategoraPreferida.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblCategoraPreferida.setBounds(329, 232, 165, 20);
		 getContentPane().add(lblCategoraPreferida);
		
		JComboBox categoria1 = new JComboBox();
		categoria1.setBounds(329, 255, 238, 29);
		 getContentPane().add(categoria1);
		
		JLabel lblCategoraPreferida_1 = new JLabel("Categor\u00EDa Preferida 2");
		lblCategoraPreferida_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblCategoraPreferida_1.setBounds(329, 295, 165, 20);
		 getContentPane().add(lblCategoraPreferida_1);
		
		JComboBox categoria2 = new JComboBox();
		categoria2.setBounds(329, 318, 238, 29);
		 getContentPane().add(categoria2);
		 
		nombre.setText(agente.getLocalName());
	}
}
