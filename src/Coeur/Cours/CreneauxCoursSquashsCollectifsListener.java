package Coeur.Cours;

import java.util.EventListener;



public interface CreneauxCoursSquashsCollectifsListener extends EventListener {
	void ajouterCreneauCoursSquashCollectif(CreneauCoursSquashCollectif creneau_cours_squash_collectif);
	void supprimerCreneauCoursSquashCollectif(CreneauCoursSquashCollectif creneau_cours_squash_collectif);
}
