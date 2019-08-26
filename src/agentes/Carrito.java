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

@SuppressWarnings("serial")
public class Carrito extends Agent{
	
	private vistas.Cart gui;
	
	private String[] categoriasPreferidas = {};
	private ArrayList<Producto> productosPreferidos = new ArrayList<Producto>();
	private ArrayList<Producto> productos = new ArrayList<Producto>();
	
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
                MessageTemplate.MatchPerformative(ACLMessage.CFP)
        );

        // Agregar comportamiento AchieveREResponder (Para agregar al carrito)
        template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
        );
        addBehaviour(new AchieveREResponder(this, template) {
        	
            protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
            	Producto producto = new Producto();
            	producto = buscarItem(listaProductos(),(String)request.getContent());
                if(buscarItem(productos,(String)request.getContent()) != null) {
                	//Aumentar cantidad
                    throw new RefuseException("El producto ya está en el carrito, se incrementa la cantidad");
                }  
                else {
                	productos.add(producto);
                }
                
                ACLMessage agree = request.createReply();
                agree.setPerformative(ACLMessage.AGREE);
                return agree;
            }

            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {        	
				ACLMessage inform = request.createReply();
				inform.setPerformative(ACLMessage.INFORM);
				return inform;
            }
            
        });
        
        
	}	
	
	public Producto buscarItem(ArrayList<Producto> listaProductos, String codigo) {
		for(Producto producto: listaProductos) {
			if(producto.getCodigo()==codigo || producto.getCodigo().equals(codigo)) {
				return producto;
			}			
		}
		return null;
	}
	
	public ArrayList<Producto> listaProductos(){
    	
    	ArrayList<Producto> products = new ArrayList<Producto>();
    	
    	Producto producto1 = new Producto("A123","Nutella Ferrero Chocolate Hazelnut Spread 26.5oz (750 g)", (float) 8.5 ,"Dulces", "/img/products/A123.png");
    	Producto producto2 = new Producto("A124","Hershey's Chocolate Syrup 24 oz (680 g)", (float) 4.14 ,"Dulces","/img/products/A124.png");
    	Producto producto3 = new Producto("A125","Oreo Thins Sandwich Cookies, 10.1 oz (287 g)", (float) 3.96 ,"Dulces","/img/products/A125.png");
    	Producto producto4 = new Producto("A126","M&M's Sharing Size Peanut Butter Milk Chocolate Candy 9.6 oz (272.2 g)", (float) 3.62 ,"Dulces","/img/products/A126.png");	
    	products.add(producto1);
    	products.add(producto2);
    	products.add(producto3);
    	products.add(producto4);
    	return products;
    }
	
}
