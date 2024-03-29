package agentes;

import java.util.Date;

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
import jade.proto.AchieveREInitiator;
import jade.proto.AchieveREResponder;

@SuppressWarnings({ "serial" })
public class Usuario extends Agent {

	private vistas.Principal gui;
	private vistas.Perfil guiPerfil;
	private String contrasenna = "123";
	private String categoria1, categoria2;

	protected void setup() {
		gui = new vistas.Principal(this);
		gui.setVisible(true);
		gui.setVisible(false);
		guiPerfil = new vistas.Perfil(this);

		// Registrar agente como "usuario"
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("usuario");
		sd.setName(this.getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// Agregar comportamiento ContractNetResponder (Gestion de usurio)
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP));

		// Agregar comportamiento AchieveREResponder (Para autenticar usuario,
		// contrase�a)
		template = MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		addBehaviour(new AchieveREResponder(this, template) {

			protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {

				if (request.getContent().equals("perfil")) {
					guiPerfil.setVisible(true);
				} else if (!request.getContent().equals(contrasenna)) {
					throw new RefuseException("Contrase�a incorrecta");
				}

				ACLMessage agree = request.createReply();
				agree.setPerformative(ACLMessage.AGREE);
				return agree;
			}

			protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response)
					throws FailureException {
				try {
					mostrarPantalla(request.getContent());
					abrirCarrito(true);
					ACLMessage inform = request.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					return inform;
				} catch (FIPAException fe) {
					throw new FailureException(fe.getMessage());
				}
			}
		});

		System.out.println(this.getLocalName() + " iniciado");
	}

	void mostrarPantalla(String papel) throws FIPAException {
		if (!papel.equals("perfil")) {
			gui.getComboBox().setSelectedIndex(0);
			gui.setVisible(true);
		}			
	}

	protected void takeDown() {
		// Eliminar vista
		gui.dispose();
		// Eliminar agente del registro
		try {
			DFService.deregister(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.getLocalName() + " finalizado");
	}

	public void agregarCarrito(String codigo) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID("carrito" + this.getLocalName(), AID.ISLOCALNAME));
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent(codigo);

		// Agregar comportamiento AchieveREInitiator (Agregar un producto al carrito)
		addBehaviour(new AchieveREInitiator(this, msg) {
			protected void handleInform(ACLMessage inform) {
				System.out.println(
						inform.getSender().getLocalName() + " ha agregado el producto " + codigo + " al carrito");
			}

			protected void handleRefuse(ACLMessage refuse) {
				System.out.println(refuse.getSender().getLocalName() + ": " + refuse.getContent());
			}

			protected void handleFailure(ACLMessage failure) {
				if (failure.getSender().equals(myAgent.getAMS())) {
					// Mensaje de la plataforma JADE: El destinatario no existe
					System.out.println("El carrito no est� disponible");
				} else {
					System.out.println(failure.getSender().getLocalName() + ": " + failure.getContent());
				}
			}
		});
	}

	public void abrirCarrito(boolean inicio) {
		String content = "abrir";
		if (inicio)
			content = "abrirInico";
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID("carrito" + this.getLocalName(), AID.ISLOCALNAME));
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent(content);
		addBehaviour(new AchieveREInitiator(this, msg) {
			protected void handleInform(ACLMessage inform) {
				System.out.println(inform.getSender().getLocalName() + " carrito abierto");
			}

			protected void handleRefuse(ACLMessage refuse) {
				System.out.println(refuse.getSender().getLocalName() + ": " + refuse.getContent());
			}

			protected void handleFailure(ACLMessage failure) {
				if (failure.getSender().equals(myAgent.getAMS())) {
					// Mensaje de la plataforma JADE: El destinatario no existe
					System.out.println("El usuario no se encuentra registrado");
				} else {
					System.out.println(failure.getSender().getLocalName() + ": " + failure.getContent());
				}
			}
		});
	}

	public void abrirPerfil() {
		guiPerfil.setVisible(true);
	}

	public void guardarPreferenciaProducto(String nombre) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID("carrito" + this.getLocalName(), AID.ISLOCALNAME));
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent("PREF-" + nombre);
		addBehaviour(new AchieveREInitiator(this, msg) {
			protected void handleInform(ACLMessage inform) {
				System.out.println(inform.getSender().getLocalName() + "Se ha agregado el producto " + nombre
						+ "a las preferencias");
			}

			protected void handleRefuse(ACLMessage refuse) {
				System.out.println(refuse.getSender().getLocalName() + ": " + refuse.getContent());
			}

			protected void handleFailure(ACLMessage failure) {
				if (failure.getSender().equals(myAgent.getAMS())) {
					// Mensaje de la plataforma JADE: El destinatario no existe
					System.out.println("El carrito no esta disponible");
				} else {
					System.out.println(failure.getSender().getLocalName() + ": " + failure.getContent());
				}
			}
		});
	}
	
	public void guardarPreferenciaCategoria(String categoria, boolean perfil) {
		String tipo = "CATEGPRINC-";
		if(perfil)
			tipo = "CATEGPERF-";
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID("carrito" + this.getLocalName(), AID.ISLOCALNAME));
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent(tipo + categoria);
		addBehaviour(new AchieveREInitiator(this, msg) {
			protected void handleInform(ACLMessage inform) {
				System.out.println(inform.getSender().getLocalName() + "Se ha agregado la categoria" + categoria
						+ "a las preferencias");
			}

			protected void handleRefuse(ACLMessage refuse) {
				System.out.println(refuse.getSender().getLocalName() + ": " + refuse.getContent());
			}

			protected void handleFailure(ACLMessage failure) {
				if (failure.getSender().equals(myAgent.getAMS())) {
					// Mensaje de la plataforma JADE: El destinatario no existe
					System.out.println("El carrito no esta disponible");
				} else {
					System.out.println(failure.getSender().getLocalName() + ": " + failure.getContent());
				}
			}
		});
	}

	public String getCategoria1() {
		
		return categoria1;
	}
	
	public String getCategoria2() {
		return categoria2;
	}

	public String getContrasenna() {
		return contrasenna;
	}

	public void setContrasenna(String contrasenna) {
		this.contrasenna = contrasenna;
	}
	
	

}
