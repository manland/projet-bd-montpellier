package Coeur.Forfait;

import java.sql.Date;

public class HistoriqueSouscription {

	private String type_forfait;
	private int num_adherent;
	private Date date_debut;
	private Date date_fin;
	
	public HistoriqueSouscription(String type_forfait, int num_adherent, Date date_debut, Date date_fin){
		this.type_forfait = type_forfait;
		this.num_adherent = num_adherent;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
	}
	
	public void setDateFin(Date date_fin){
		this.date_fin = date_fin;
	}
	public Date getDateFin(){
		return this.date_fin;
	}
	
	public void setDateDebut(Date date_debut){
		this.date_debut = date_debut;
	}
	public Date getDateDebut(){
		return this.date_debut;
	}
	
	public int getNumAdherent(){
		return this.num_adherent;
	}
	
	public String getTypeForfait(){
		return this.type_forfait;
	}

	@Override
	public String toString() {
		return "HistoriqueSouscription [date_debut=" + date_debut + ", date_fin="
				+ date_fin + ", num_adherent=" + num_adherent
				+ ", type_forfait=" + type_forfait + "]";
	}

}
