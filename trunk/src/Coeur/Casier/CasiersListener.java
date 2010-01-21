package Coeur.Casier;

import java.util.EventListener;

public interface CasiersListener extends EventListener{
	void ajouterCasier(Casier casier);
	void supprimerCasier(Casier casier);

}
