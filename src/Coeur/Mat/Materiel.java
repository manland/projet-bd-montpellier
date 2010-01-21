package Coeur.Mat;

public abstract class Materiel {
	
	private String type_materiel;
	private int prix_base;
	private String description;
	
	public Materiel(){
		this.prix_base = 0;
		this.type_materiel = "";
		this.description = "";
	}
	
	public Materiel(String type_materiel, int prix_base, String description){
		this.type_materiel = type_materiel;
		this.prix_base = prix_base;
		this.description = description;
	}

	public String getTypeMateriel(){
		return this.type_materiel;
	}
	public void setTypeMateriel(String type_materiel){
		this.type_materiel = type_materiel;
	}
	
	public int getPrixBase(){
		return this.prix_base;
	}
	public void setPrixBase(int prix_base){
		this.prix_base = prix_base;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Materiel [prix_base=" + prix_base + ", type_materiel="
				+ type_materiel + ", description = "+description+"]";
	}
	
}
