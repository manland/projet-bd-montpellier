package Coeur.Personne;
import java.util.EventListener;

public interface PersonnelsSportifsListener extends EventListener {
		void ajouterPersonnelSportif(PersonnelSportif personnel_sportif);
		void supprimerPersonnelSportif(PersonnelSportif personnel_sportif);
}
