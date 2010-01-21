package Coeur.Mat;

import java.util.EventListener;

public interface MaterielsVentesListener extends EventListener {
	void ajouterMaterielVente(MaterielVente materiel_vente);
	void supprimerMaterielVente(MaterielVente materiel_vente);
}
