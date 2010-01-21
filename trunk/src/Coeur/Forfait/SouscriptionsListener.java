package Coeur.Forfait;

import java.util.EventListener;

public interface SouscriptionsListener extends EventListener {
	void ajouterSouscription(Souscription souscription);
	void supprimerSouscription(Souscription souscription);

}
