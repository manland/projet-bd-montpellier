package Coeur.Mat;

import java.sql.Date;
import java.util.ArrayList;

public class Vente {

	private int num_vente;
	private ArrayList<TypeMaterielAchete> materiel;
	private Date date_vente;
	private int id_personne;
	private int prix_paye;
	
	public Vente(int num_vente,ArrayList<TypeMaterielAchete> materiel, Date date_vente, int id_personne, int prix_paye){
		this.num_vente = num_vente;
		this.materiel = materiel;
		this.date_vente = date_vente;
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
	

	public Date getDateVente() {
		return this.date_vente;
	}
	public void setDateVente(Date date_vente){
		this.date_vente = date_vente;
	}
	
	public int getNumVente(){
		return this.num_vente;
	}
	
	public String afficheMateriel(){
		String string = "\n";
		for(TypeMaterielAchete t : materiel){
			string += t.toString()+"\n";
		}
		return string;
	}

	public ArrayList<TypeMaterielAchete> getMateriel() {
		return materiel;
	}
	public void setMateriel(ArrayList<TypeMaterielAchete> materiel) {
		this.materiel = materiel;
	}

	@Override
	public String toString() {
		return "Vente [date_vente=" + date_vente + ", id_personne="
				+ id_personne + ", num_vente=" + num_vente + ", prix_paye="
				+ prix_paye + afficheMateriel() +"]";
	}
}
