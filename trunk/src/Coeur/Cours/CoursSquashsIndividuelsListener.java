package Coeur.Cours;

import java.util.EventListener;

public interface CoursSquashsIndividuelsListener extends EventListener {
	void ajouterCoursSquashIndividuel(CoursSquashIndividuel cours_squash_individuel);
	void supprimerCoursSquashIndividuel(CoursSquashIndividuel cours_squash_individuel);
}
