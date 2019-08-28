package agentes;

import java.util.ArrayList;

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

	private String[] categoriasPreferidas = {};
	private ArrayList<String> preferencias = new ArrayList<String>();
	private ArrayList<Producto> productosPreferidos = new ArrayList<Producto>();
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
						if(!preferencias.isEmpty() && productos.isEmpty()) {
							gui.Limpiar();
							sugerirCompras();
							gui.setVisible(true);
						}	
					}
				}
				// Agregar preferencias
				else if (request.getContent().contains("PREF")) {
					String preferencia = request.getContent().split("-")[1];
//            		Producto p = new Producto();
//            		String codigo = request.getContent().split("-")[1];
//            		p = buscarItem(listaProductos(),codigo);
					preferencias.add(preferencia);
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
		System.out.println("Sugiriendo");
		ArrayList<Producto> products = new ArrayList<Producto>();
		for (String p : preferencias) {
			System.out.println("Preferencia: " + p);
			Producto producto = new Producto();
			producto = mejorOferta(productoDAO.listaProductos(), p);
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

}
