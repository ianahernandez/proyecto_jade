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
import jade.proto.ContractNetResponder;

@SuppressWarnings({"serial"})
public class Usuario extends Agent{
	
	private vistas.Principal gui;
	public String contrasenna ="123";
	
	
	protected void setup() {
		gui = new vistas.Principal(this);
	    gui.setVisible(true);
        gui.setVisible(false);

        // Registrar agente como "persona"
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

        // Agregar comportamiento ContractNetResponder (Venta de libros)
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP)
        );

        // Agregar comportamiento AchieveREResponder (Para definir un papel asignado por el planificador)
        template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
        );
        addBehaviour(new AchieveREResponder(this, template) {
            protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
                if(!request.getContent().equals(contrasenna)) {
                    throw new RefuseException("Contraseña incorrecta");
                }
                ACLMessage agree = request.createReply();
                agree.setPerformative(ACLMessage.AGREE);
                return agree;
            }

            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
                try {
                    mostrarPantalla(request.getContent());
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
        gui.setVisible(true);
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

}
