package Coeur.Casier;

public class ReservationCasierAnnee extends Casier {

	private int num_adherent;
	private int annee;
	
	public ReservationCasierAnnee(int num_casier, int num_adherent, int annee) {
		super(num_casier);
		this.num_adherent = num_adherent;
		this.annee = annee;
		// TODO Auto-generated constructor stub
	}
	
	public int getAnnee(){
		return this.annee;
	}
	public int getNumAdherent(){
		return this.num_adherent;
	}
	
	public void setNumAdherent(int num_adherent){
		this.num_adherent = num_adherent;
	}

	@Override
	public String toString() {
		return "ReservationCasierAnnee [annee=" + annee + ", num_adherent="
				+ num_adherent + "] =====>"+super.toString();
	}

}
