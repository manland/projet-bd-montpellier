package Coeur.Casier;

import java.sql.Date;

public class ReservationCasierSeance extends Casier {
	
	private Date date_location;
	private int heure_debut;
	private int heure_fin;
	private int id_personne;
	private int prix_paye;
	
	public ReservationCasierSeance(int num_casier, Date date, int heure_debut, int heure_fin, int id_personne, int prix_paye){
		super(num_casier);
		this.date_location = date;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
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
	
	public void setHeureFin(int heure_fin){
		this.heure_fin = heure_fin;
	}
	
	public int getHeureFin(){
		return heure_fin;
	}
	
	public void setHeureDebut(int heure_debut){
		this.heure_debut = heure_debut;
	}
	
	public int getHeureDebut(){
		return heure_debut;
	}
	
	public Date getDateLocation(){
		return this.date_location;
	}
	
	public void setDateLocation(Date date){
		this.date_location = date;
	}

	@Override
	public String toString() {
		return "ReservationCasierSeance [date=" + date_location + ", heure_debut="
				+ heure_debut + ", heure_fin=" + heure_fin + ", id_personne="
				+ id_personne + ", prix_paye=" + prix_paye + "] =====> "+super.toString();
	}
	

}
