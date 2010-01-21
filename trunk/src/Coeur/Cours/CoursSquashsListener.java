package Coeur.Cours;

import java.util.EventListener;

public interface CoursSquashsListener extends EventListener {
	void ajouterCoursSquash(CoursSquash cours_squash);
	void supprimerCoursSquash(CoursSquash cours_squash);
}
