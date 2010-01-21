package Coeur.Cours;

import java.sql.Date;

public class CreneauCoursSquashIndividuel {

	private int id_cours;
	private int num_terrain_squash;
	private Date date_cours;
	private int heure_debut;
	private int heure_fin;

	public CreneauCoursSquashIndividuel(int id_cours, int num_terrain_squash, Date date_cours, int heure_debut, int heure_fin){
		this.id_cours = id_cours;
		this.num_terrain_squash = num_terrain_squash;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
		this.date_cours = date_cours;
	}
	
	public int getIdCours(){
		return this.id_cours;
	}	
	public void setIdCours(int id_cours){
		this.id_cours = id_cours;
	}
	
	public int getNumTerrainSquash(){
		return this.num_terrain_squash;
	}
	
	public void setHeureDebut(int heure_debut){
		this.heure_debut= heure_debut;
	}
	public int getHeureDebut(){
		return this.heure_debut;
	}
	
	public void setHeureFin(int heure_debut){
		this.heure_debut= heure_debut;
	}
	public int getHeureFin(){
		return this.heure_fin;
	}
	
	public Date getDateCours(){
		return this.date_cours;
	}
	public void setDateCours(Date date_cours){
		this.date_cours = date_cours;
	}

	@Override
	public String toString() {
		return "CreneauCoursSquashIndividuel [date_cours=" + date_cours
				+ ", heure_debut=" + heure_debut + ", heure_fin=" + heure_fin
				+ ", id_cours=" + id_cours + ", num_terrain_squash="
				+ num_terrain_squash + "]";
	}
	
	
}
