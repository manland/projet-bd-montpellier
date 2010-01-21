package Coeur.Gym;

import java.util.EventListener;


public interface SallesGymListener extends EventListener{
	void ajouterSalleGym(SalleGym salle_gym);
	void supprimerSalleGym(SalleGym salle_gym);
}
