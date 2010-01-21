package Coeur.Gym;

import java.util.EventListener;

public interface CoursGymsListener  extends EventListener{
	void ajouterCoursGym(CoursGym cours_gym);
	void supprimerCoursGym(CoursGym cours_gym);
}