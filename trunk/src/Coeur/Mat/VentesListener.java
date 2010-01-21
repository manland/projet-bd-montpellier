package Coeur.Mat;

import java.util.EventListener;

public interface VentesListener extends EventListener {
	void ajouterVente(Vente vente);
	void supprimerVente(Vente vente);
}
