package Coeur.Gym;

import java.util.EventListener;

public interface CreneauxCoursGymListener extends EventListener{
	void ajouterCreneauCoursGym(CreneauCoursGym creneau_cours_gym);
	void supprimerCreneauCoursGym(CreneauCoursGym creneau_cours_gym);
}