import jade.core.Runtime; 
import jade.core.Profile; 
import jade.core.ProfileImpl; 
import jade.wrapper.*;

import javax.swing.UIManager;

public class Main {
	
	public static void main(String[] args) {
        try {
            // Usar look & feel nativo en las vistas
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        // Plataforma JADE
        Runtime runtime = Runtime.instance();

        // Perfil predetermiando (localhost:1099)
        Profile profile = new ProfileImpl();

        // Contenedor principal
        AgentContainer mainContainer = runtime.createMainContainer(profile);

        // Crear agentes
        try {
            // RMA (Jade Boot GUI)
            AgentController ac = mainContainer.createNewAgent("rma",
                    "jade.tools.rma.rma", null);
            ac.start();

            // Tienda
            ac = mainContainer.createNewAgent("Tienda",
                    "agentes.Tienda", null);
            ac.start();

            // 4 personas
            String[] usuarios = {"Ana", "Andres", "Otniel", "Alvaro","Estefania"};
            for(int i = 0; i < usuarios.length; i++) {
                ac = mainContainer.createNewAgent(usuarios[i],
                        "agentes.Usuario", null);
                ac.start();
            }
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
