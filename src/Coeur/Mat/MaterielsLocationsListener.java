package Coeur.Mat;

import java.util.EventListener;

public interface MaterielsLocationsListener extends EventListener {
	void ajouterMaterielLocation(MaterielLocation materiel_location);
	void supprimerMaterielLocation(MaterielLocation materiel_location);
}
