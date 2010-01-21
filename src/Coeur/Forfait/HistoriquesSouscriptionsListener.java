package Coeur.Forfait;

import java.util.EventListener;

public interface HistoriquesSouscriptionsListener extends EventListener {
	void ajouterHistoriqueSouscription(HistoriqueSouscription interface_souscription);
	void supprimerHistoriqueSouscription(HistoriqueSouscription interface_souscription);

}
