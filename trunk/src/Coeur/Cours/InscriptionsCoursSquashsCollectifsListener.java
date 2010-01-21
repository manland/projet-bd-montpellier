package Coeur.Cours;

import java.util.EventListener;

public interface InscriptionsCoursSquashsCollectifsListener extends EventListener {
	void ajouterInscriptionCoursSquashCollectif(InscriptionCoursSquashCollectif inscription_cours_squash_collectif);
	void supprimerInscriptionCoursSquashCollectif(InscriptionCoursSquashCollectif inscription_cours_squash_collectif);
}
