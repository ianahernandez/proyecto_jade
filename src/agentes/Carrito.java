package agentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jade.core.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import modelos.Producto;
import modelos.ProductoDAO;

@SuppressWarnings("serial")
public class Carrito extends Agent {

	private vistas.Cart gui;
	private ProductoDAO productoDAO = new ProductoDAO();

	private ArrayList<String> categoriasPreferidas = new ArrayList<String>();
	private ArrayList<String> categoriasPreferidasPerfil = new ArrayList<String>();
	private ArrayList<String> categoriasPreferidasPrincipal = new ArrayList<String>();
	private ArrayList<String> preferencias = new ArrayList<String>();
	private ArrayList<Producto> productos = new ArrayList<Producto>();

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	@Override
	protected void setup() {
		gui = new vistas.Cart(this);
		gui.setVisible(true);
		gui.setVisible(false);

		// Registrar agente como "carrito"
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("carrito");
		sd.setName(this.getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// Agregar comportamiento ContractNetResponder (Gestion de carrito)
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP));

		// Agregar comportamiento AchieveREResponder (Para agregar al carrito)
		template = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		addBehaviour(new AchieveREResponder(this, template) {

			protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
				// Abrir el carrito
				if (request.getContent().contains("abrir")) {
					if (request.getContent().equals("abrir")) {
						gui.setVisible(true);
					} else {
						// cargar preferencias
						if((!preferencias.isEmpty() || !categoriasPreferidasPrincipal.isEmpty() || !categoriasPreferidasPerfil.isEmpty()) && productos.isEmpty()) {
							gui.Limpiar();
							sugerirCompras();
							gui.setVisible(true);
						}	
					}
				}
				// Agregar preferencias
				else if (request.getContent().contains("PREF")) {
					String preferencia = request.getContent().split("-")[1];
					preferencias.add(preferencia);
				}
				else if (request.getContent().contains("CATEGPRINC")) {
					String categoria = request.getContent().split("-")[1];
					System.out.println("Agregando en principal");
					categoriasPreferidasPrincipal.add(categoria);
				}
				else if (request.getContent().contains("CATEGPERF")) {
					String categoria = request.getContent().split("-")[1];
					System.out.println("Agregando en perfil");
					categoriasPreferidasPerfil.add(categoria);
				}
				// Agregar al carrito
				else {
					Producto producto = new Producto();
					producto = productoDAO.buscarItem(productoDAO.listaProductos(), (String) request.getContent());
					if (productoDAO.buscarItem(productos, (String) request.getContent()) != null) {
						// Aumentar cantidad
						gui.incrementar(producto.getCodigo());
						throw new RefuseException("El producto ya está en el carrito, se incrementa la cantidad");

					} else {
						productos.add(producto);
						gui.AgregarFila(producto.getCodigo(), producto.getNombre(), producto.getPrecio(),
								producto.getCategoria(), producto.getImagen());
						// gui.cargarProductos(productos);
					}
				}

				ACLMessage agree = request.createReply();
				agree.setPerformative(ACLMessage.AGREE);
				return agree;
			}

			protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response)
					throws FailureException {
				ACLMessage inform = request.createReply();
				inform.setPerformative(ACLMessage.INFORM);
				return inform;
			}

		});

	}

	public void sugerirCompras() {
		actualizarcategoriasPreferidas();
		System.out.println("Sugiriendo");
		ArrayList<Producto> products = new ArrayList<Producto>();
		if(!preferencias.isEmpty()) {
			String prod = "";
			int mayor = 0;
			ArrayList<String> categ = new ArrayList<String>();
	        Set<String> quipu = new HashSet<String>(preferencias);
	        for (String key : quipu) {       	
	        	int f = Collections.frequency(preferencias, key);
	        	if(f > mayor) {
	        		prod = key;
	        		mayor = f;        		
	        	}    	
	        }
	        Producto producto = new Producto();
	        producto = mejorOferta(productoDAO.listaProductos(), prod);
			if (producto != null) {
				
				if (productoDAO.buscarItem(productos, producto.getCodigo()) != null) {
					// Aumentar cantidad
					gui.incrementar(producto.getCodigo());

				} else {
					productos.add(producto);
					gui.AgregarFila(producto.getCodigo(), producto.getNombre(), producto.getPrecio(),
							producto.getCategoria(), producto.getImagen());
					// gui.cargarProductos(productos);
				}
			}
		}
		
		if(!categoriasPreferidas.isEmpty())
		for (String c : categoriasPreferidas) {
			ArrayList<Producto> res = new ArrayList<Producto>();
			res = productoDAO.productosCategoria(productoDAO.listaProductos(), c);
			
			Producto producto = new Producto();
			producto = res.get((int) Math.floor(Math.random()*res.size()));
			if (producto != null) {
				
				if (productoDAO.buscarItem(productos, producto.getCodigo()) != null) {
					// Aumentar cantidad
					gui.incrementar(producto.getCodigo());

				} else {
					productos.add(producto);
					gui.AgregarFila(producto.getCodigo(), producto.getNombre(), producto.getPrecio(),
							producto.getCategoria(), producto.getImagen());
					// gui.cargarProductos(productos);
				}
			}
		}
	}

	public Producto mejorOferta(ArrayList<Producto> listaProductos, String nombre) {
		ArrayList<Producto> products = new ArrayList<Producto>();
		for (Producto producto : listaProductos) {
			if (producto.getNombre().toLowerCase().matches(".*" + nombre.toLowerCase() + ".*")) {
				products.add(producto);
			}
		}
		if (!products.isEmpty()) {
			Producto producto = products.get(0);
			for (Producto p : products) {
				if (p.getPrecio() < producto.getPrecio())
					producto = p;
			}
			return producto;
		}
		return null;
	}
	
	public void actualizarcategoriasPreferidas() {
		categoriasPreferidas = new ArrayList<String>();
		
		//Agregar las preferencias del perfil
		if(!categoriasPreferidasPerfil.isEmpty()) 
			categoriasPreferidas.add(categoriasPreferidasPerfil.get(categoriasPreferidasPerfil.size()-1));
		if(categoriasPreferidasPerfil.size()>=2)
			categoriasPreferidas.add(categoriasPreferidasPerfil.get(categoriasPreferidasPerfil.size()-2));
		
		//Agregar lo mas buscado
		
		
		String categoria = "";
		int mayor = 0;
		ArrayList<String> categ = new ArrayList<String>();
		categ.add("Dulces");
		categ.add("Enlatados");
		categ.add("Salsas");
		categ.add("Reposteria");
		categ.add("Telefonía");
        Set<String> quipu = new HashSet<String>(categ);
        for (String key : quipu) {       	
        	int f = Collections.frequency(categoriasPreferidasPrincipal, key);
        	if(f > mayor) {
        		categoria = key;
        		mayor = f;        		
        	}    	
        }
        System.out.println("La categoria mas buscada es: "+categoria);
        if(mayor!=0)
        	categoriasPreferidas.add(categoria);
	}

}
