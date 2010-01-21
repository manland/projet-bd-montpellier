package Coeur.Forfait;

import java.util.EventListener;

public interface ForfaitsListener extends EventListener{
	void ajouterForfait(Forfait forfait);
	void supprimerForfait(Forfait forfait);
}
