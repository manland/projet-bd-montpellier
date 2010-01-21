package Coeur.Cours;

import java.sql.Date;



public class CreneauCoursSquashCollectif {
	private int id_cours;
	private int jour;
	private int num_terrain_squash;
	private int heure_debut;
	private int heure_fin;
	private Date debut_trimestre;

	public CreneauCoursSquashCollectif(int id_cours, int num_terrain_squash, int jour, int heure_debut, int heure_fin, Date debut_trimestre){
		this.id_cours = id_cours;
		this.num_terrain_squash = num_terrain_squash;
		this.jour = jour;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
		this.debut_trimestre = debut_trimestre;
	}
	
	public int getIdCours(){
		return this.id_cours;
	}	
	
	public void setNumTerrainSquash(int num_terrain_squash){
		this.num_terrain_squash = num_terrain_squash;
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

	public int getJour() {
		return jour;
	}
	public void setJour(int jour) {
		this.jour = jour;
	}

	public Date getDebutTrimestre() {
		return debut_trimestre;
	}

	public void setDebutTrimestre(Date debut_trimestre) {
		this.debut_trimestre = debut_trimestre;
	}

	@Override
	public String toString() {
		return "CreneauCoursSquashCollectif [debut_trimestre="
				+ debut_trimestre + ", heure_debut=" + heure_debut
				+ ", heure_fin=" + heure_fin + ", id_cours=" + id_cours
				+ ", jour=" + jour + ", num_terrain_squash="
				+ num_terrain_squash + "]";
	}
	

}
