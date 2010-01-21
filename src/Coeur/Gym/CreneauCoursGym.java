package Coeur.Gym;

import java.sql.Date;

public class CreneauCoursGym {
	private int id_salle;
	private String type_cours;
	private Date date_cours;
	private int heure_debut;
	private int heure_fin;
	private Date debut_trimestre;
	
	public CreneauCoursGym(int id_salle, String type_cours, Date date_cours, int heure_debut, int heure_fin, Date debut_trimestre){
		this.id_salle = id_salle;
		this.type_cours = type_cours;
		this.date_cours = date_cours;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
		this.debut_trimestre = debut_trimestre;
	}

	public String getTypeCours() {
		return type_cours;
	}

	public void setTypeCours(String type_cours) {
		this.type_cours = type_cours;
	}

	public int getHeureFin() {
		return heure_fin;
	}

	public void setHeureFin(int heure_fin) {
		this.heure_fin = heure_fin;
	}

	public int getIdSalle() {
		return id_salle;
	}

	public Date getDateCours() {
		return date_cours;
	}

	public int getHeureDebut() {
		return heure_debut;
	}


	public Date getDebutTrimestre() {
		return this.debut_trimestre;
	}

	public void setDebutTrimestre(Date debut_trimestre) {
		this.debut_trimestre = debut_trimestre;
	}
	@Override
	public String toString() {
		return "CreneauCoursGym [date_cours=" + date_cours + ", heure_debut="
				+ heure_debut + ", heure_fin=" + heure_fin + ", id_salle="
				+ id_salle + ", type_cours=" + type_cours + "]";
	}
	
	
	
}
