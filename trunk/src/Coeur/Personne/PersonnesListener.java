package Coeur.Personne;

import java.util.EventListener;

public interface PersonnesListener  extends EventListener{
	void ajouterPersonne(Personne personne);
	void supprimerPersonne(Personne personne);
}
