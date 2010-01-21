package Coeur.TerrainSquash;

import java.sql.Date;

public class ReservationTerrainSquash {
	
	private int num_terrain_squash;
	private Date date_location;
	private int heure_debut;
	private int heure_fin;
	private int minute_debut;
	private int minute_fin;
	private int id_personne;
	private int prix_paye;
	
	public ReservationTerrainSquash(int num_terrain_squash, Date date, int heure_debut, int heure_fin, int minute_debut, int minute_fin, int id_personne, int prix_paye){
		this.num_terrain_squash = num_terrain_squash;
		this.date_location = date;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
		this.minute_debut = minute_debut;
		this.minute_fin = minute_fin;
		this.id_personne = id_personne;
		this.prix_paye = prix_paye;
	}
	
	public void setPrixPaye(int prix_paye){
		this.prix_paye = prix_paye;
	}
	
	public int getPrixPaye(){
		return this.prix_paye;
	}
	
	public void setIdPersonne(int id_personne){
		this.id_personne = id_personne;
	}
	
	public int getIdPersonne(){
		return id_personne;
	}
	
	public int getNumTerrainSquash(){
		return this.num_terrain_squash;
	}
	
	public int getHeureFin(){
		return heure_fin;
	}
	
	public int getHeureDebut(){
		return heure_debut;
	}
	
	public int getMinuteDebut() {
		return minute_debut;
	}

	public int getMinuteFin() {
		return minute_fin;
	}

	public Date getDateLocation(){
		return this.date_location;
	}

	@Override
	public String toString() {
		return "ReservationTerrainSquash [date_location=" + date_location
				+ ", heure_debut=" + heure_debut + ", heure_fin=" + heure_fin
				+ ", id_personne=" + id_personne + ", minute_debut="
				+ minute_debut + ", minute_fin=" + minute_fin
				+ ", num_terrain_squash=" + num_terrain_squash + ", prix_paye="
				+ prix_paye + "]";
	}

	

}
