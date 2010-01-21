package Coeur.Forfait;

public class Forfait {
	
	private String type_forfait;
	private int prix_mois;
	private int prix_trimestre;
	private int prix_semestre;
	private int prix_annee;
	
	public Forfait(String type_forfait, int prix_mois, int prix_trimestre, int prix_semestre, int prix_annee){
		this.type_forfait = type_forfait;
		this.prix_mois = prix_mois;
		this.prix_trimestre = prix_trimestre;
		this.prix_semestre = prix_semestre;
		this.prix_annee = prix_annee;
	}
	
	public void setPrixMois(int prix_mois){
		this.prix_mois = prix_mois;
	}
	public int getPrixMois(){
		return this.prix_mois;
	}
	
	public void setPrixTrimestre(int prix_trimestre){
		this.prix_trimestre = prix_trimestre;
	}
	public int getPrixTrimestre(){
		return this.prix_trimestre;
	}
	
	public void setPrixSemestre(int prix_semestre){
		this.prix_semestre = prix_semestre;
	}
	public int getPrixSemestre(){
		return this.prix_semestre;
	}
	
	public void setPrixAnnee(int prix_annee){
		this.prix_annee = prix_annee;
	}
	public int getPrixAnnee(){
		return this.prix_annee;
	}
	
	public String getTypeForfait(){
		return this.type_forfait;
	}

	@Override
	public String toString() {
		return "Forfait [prix_annee=" + prix_annee + ", prix_mois=" + prix_mois
				+ ", prix_semestre=" + prix_semestre + ", prix_trimestre="
				+ prix_trimestre + ", type_forfait=" + type_forfait + "]";
	}

}
