package Coeur.Personne;

import java.util.EventListener;

public interface AdherentsListener extends EventListener{
	void ajouterAdherent(Adherent adherent);
	void supprimerAdherent(Adherent adherent);
}
