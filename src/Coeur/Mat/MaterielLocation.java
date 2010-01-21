package Coeur.Mat;

import java.sql.Date;

public class MaterielLocation extends Materiel {

	private int num_materiel;
	private Date date_achat;
	private String etat;
	
	public MaterielLocation(int num_materiel, String type_materiel, Date date_achat, String etat,String description, int prix_base){
		super(type_materiel, prix_base, description);
		this.num_materiel = num_materiel;
		this.date_achat = date_achat;
		this.etat = etat;
	}
	
	public Date getDateAchat(){
		return this.date_achat;
	}
	public void setDateAchat(Date date_achat){
		this.date_achat = date_achat;
	}
	
	public int getNumMateriel(){
		return this.num_materiel;
	}

	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}

	@Override
	public String toString() {
		return "MaterielLocation [date_achat=" + date_achat + ", etat=" + etat
				+ ", num_materiel=" + num_materiel + "] ======>"+super.toString();
	}
	
	
	
}
