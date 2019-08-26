package agentes;
import java.util.ArrayList;
import java.util.Date;

import jade.core.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

@SuppressWarnings("serial")
public class Tienda extends Agent{
	
	 private vistas.Tienda gui;

    protected void setup() {
        gui = new vistas.Tienda(this);
        gui.setVisible(true);
    }
    
    protected void takeDown() {
        gui.dispose();
        System.out.print(this.getLocalName() + " finalizado");
    }

    public ArrayList<String> buscarUsuario() {
        ArrayList<String> usuarios = new ArrayList<String>();

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("usuario");
        dfd.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(this, dfd);
            for(int i = 0; i < result.length; i++) {
                usuarios.add(result[i].getName().getLocalName());
            }
        } catch(FIPAException fe) {
            fe.printStackTrace();
        }

        return usuarios;
    }
    
    public void autenticar(String usuario, String contrasenna){
    	
    	
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(usuario, AID.ISLOCALNAME));
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
        msg.setContent(contrasenna);

        // Agregar comportamiento AchieveREInitiator (Autenticar persona)
        addBehaviour(new AchieveREInitiator(this, msg) {
            protected void handleInform(ACLMessage inform) {
                System.out.println(inform.getSender().getLocalName() + " ha iniciado sesión");
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
	    

}
