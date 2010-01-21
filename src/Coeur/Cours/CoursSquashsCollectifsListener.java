package Coeur.Cours;

import java.util.EventListener;

public interface CoursSquashsCollectifsListener extends EventListener {
	void ajouterCoursSquashCollectif(CoursSquashCollectif cours_squash_collectif);
	void supprimerCoursSquashCollectif(CoursSquashCollectif cours_squash_collectif);
}
