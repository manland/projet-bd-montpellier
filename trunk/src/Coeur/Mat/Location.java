package Coeur.Mat;

import java.sql.Date;
import java.util.ArrayList;

public class Location {
	private int num_location;
	private Date date_location;
	private int heure_debut;
	private int heure_fin;
	private ArrayList<TypeMaterielLoue> materiel;
	private int id_personne;
	private int prix_paye;
	
	public Location(int num_location, Date date_location, int heure_debut, int heure_fin, ArrayList<TypeMaterielLoue> materiel, int id_personne, int prix_paye){
		this.num_location = num_location;
		this.date_location = date_location;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
		this.materiel = materiel;
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
		return this.id_personne;
	}
	
	public void setHeureFin(int heure_fin){
		this.heure_fin = heure_fin;
	}
	public int getHeureFin(){
		return this.heure_fin;
	}
	
	public void setDateLocation(Date date_location){
		this.date_location = date_location;
	}
	public Date getDateLocation(){
		return this.date_location;
	}
	
	public void setHeureDebut(int heure_debut){
		this.heure_debut = heure_debut;
	}
	public int getHeureDebut(){
		return this.heure_debut;
	}
	
	public void setMateriel(ArrayList<TypeMaterielLoue> materiel){
		this.materiel = materiel;
	}
	public ArrayList<TypeMaterielLoue> getMateriel(){
		return this.materiel;
	}
	
	public int getNumLocation(){
		return this.num_location;
	}
	
	public String afficheMateriel(){
		String string = "\n";
		for(TypeMaterielLoue t : materiel){
			string += t.toString()+"\n";
		}
		return string;
	}


	@Override
	public String toString() {
		return "Location [num_location +"+num_location+", date_location=" + date_location + ", heure_debut="
				+ heure_debut + ", heure_fin=" + heure_fin + ", id_personne="
				+ id_personne + ", materiel=" + afficheMateriel()
				+ ", prix_paye=" + prix_paye + "]";
	}

}
