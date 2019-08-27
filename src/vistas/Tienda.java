package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class Tienda extends JFrame{
	
	private agentes.Tienda agente;
	

	//private JFrame frame;
	private JTextField txtUsuario;
	private JTextField txtContrasenna;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	
	
	public Tienda(agentes.Tienda tienda) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tienda.class.getResource("/img/logo.png")));
		setResizable(false);
		agente = tienda;
		
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
	private void initialize() {
		
		setTitle("SmartStore - Iniciar sesi\u00F3n" );
        setBounds(100, 100, 600, 450);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(0, 0, 434, 261);
        //contentPane = new JPanel();
       // contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
		panel.setLayout(null);
        //contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
//		frame = new JFrame();
//		frame.setBounds(100, 100, 450, 300);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtUsuario.setToolTipText("");
		txtUsuario.setBounds(332, 126, 222, 29);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtContrasenna = new JPasswordField();
		txtContrasenna.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtContrasenna.setBounds(332, 188, 222, 29);
		panel.add(txtContrasenna);
		txtContrasenna.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Correo electr\u00F3nico");
		lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUsuario.setBounds(332, 104, 165, 20);
		panel.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblContrasea.setBounds(332, 166, 121, 20);
		panel.add(lblContrasea);
		
		JButton btnIniciar = new JButton("Acceder");
		btnIniciar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		btnIniciar.setBackground(new Color(102, 205, 170));
		btnIniciar.setForeground(new Color(255, 255, 255));
		btnIniciar.setBounds(433, 266, 121, 29);
		btnIniciar.setContentAreaFilled(false);
		btnIniciar.setOpaque(true);
		panel.add(btnIniciar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 205, 170));
		panel_1.setBounds(0, 0, 300, 421);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setBounds(85, 97, 128, 128);
		logo.setIcon(new ImageIcon(Tienda.class.getResource("/img/logo.png")));
		panel_1.add(logo);
		
		JLabel lblSmartstore = new JLabel("SmartStore");
		lblSmartstore.setHorizontalAlignment(SwingConstants.CENTER);
		lblSmartstore.setForeground(new Color(255, 255, 255));
		lblSmartstore.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblSmartstore.setBounds(10, 236, 280, 32);
		panel_1.add(lblSmartstore);
		
		JLabel lblIniciaSesinPara = new JLabel("Inicia sesi\u00F3n para empezar a comprar");
		lblIniciaSesinPara.setHorizontalAlignment(SwingConstants.CENTER);
		lblIniciaSesinPara.setForeground(new Color(255, 255, 255));
		lblIniciaSesinPara.setFont(new Font("Segoe UI Semilight", Font.BOLD, 14));
		lblIniciaSesinPara.setBounds(10, 279, 280, 20);
		panel_1.add(lblIniciaSesinPara);
		
		JLabel lblIniciarSesin = new JLabel("Iniciar sesi\u00F3n");
		lblIniciarSesin.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblIniciarSesin.setHorizontalAlignment(SwingConstants.CENTER);
		lblIniciarSesin.setBounds(332, 34, 222, 29);
		panel.add(lblIniciarSesin);
		
		btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agente.autenticar(txtUsuario.getText(), txtContrasenna.getText());
            }
        });
		
		
	}
}
