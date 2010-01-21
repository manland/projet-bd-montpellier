package Coeur.Personne;

import java.util.EventListener;

public interface PersonnelsAdministratifsListener extends EventListener{
	void ajouterPersonnelAdministratif(PersonnelAdministratif personnel_administratif);
	void supprimerPersonnelAdministratif(PersonnelAdministratif personnel_administratif);
}
