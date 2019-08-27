package modelos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Producto {
	private String codigo;
	private String nombre;
	private float precio;
	private String categoria = "Todo";
	private String  imagen;
	private JButton agregarCarrito;
	
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Producto(String codigo, String nombre, float precio, String categoria, String  imagen) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.imagen = imagen;
		this.categoria = categoria;
	}
	
	public Producto(String codigo, String nombre, float precio, String categoria, String  imagen, JButton agregarCarrito) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.imagen = imagen;
		this.categoria = categoria;
		this.agregarCarrito = agregarCarrito;
		this.agregarCarrito.setText("");
		agregarCarrito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	System.out.println(nombre);
            }
        });
//		agregarCarrito.setOnMouseReleased(new EventHandler() {
//
//			@Override
//			public void handle(Event arg0) {
//				// TODO Auto-generated method stub
//				System.out.println(nombre);
//			}
//			
//		});
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String  getImagen() {
		return imagen;
	}

	public void setImagen(String  imagen) {
		this.imagen = imagen;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public JButton getAgregarCarrito() {
		return agregarCarrito;
	}

	public void setAgregarCarrito(JButton agregarCarrito) {
		
		this.agregarCarrito = agregarCarrito;
	}
	
}
