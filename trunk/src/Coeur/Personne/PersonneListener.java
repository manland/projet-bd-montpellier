package Coeur.Personne;
import java.sql.Date;
import java.util.EventListener;


public interface PersonneListener extends EventListener{
	void nomPersonneChange(Personne personne, String nouveau_nom);
	void prenomPersonneChange(Personne personne, String nouveau_prenom);
	void datePersonneChange(Personne personne, Date nouvelle_date_naissance);
	void adressePersonneChange(Personne personne, String nouvelle_adresse);

}
