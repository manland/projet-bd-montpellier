package Coeur.Cours;

import java.sql.SQLException;

public class InscriptionCoursSquashCollectif {
	private int id_cours;
	private int num_adherent;
	private CoursSquashCollectif cours_squash_collectif;
	
	public InscriptionCoursSquashCollectif(int id_cours, int num_adherent) throws SQLException{
		cours_squash_collectif = new CoursSquashCollectif(id_cours);
		this.id_cours = id_cours;
		this.num_adherent = num_adherent;
	}
	
	public int getIdCours(){
		return this.id_cours;
	}
	
	public int getNumAdherent(){
		return this.num_adherent;
	}
	
	public CoursSquashCollectif getCoursSquashCollectif(){
		return this.cours_squash_collectif;
	}

	@Override
	public String toString() {
		return "InscriptionCoursSquashCollectif [id_cours=" + id_cours
				+ ", num_adherent=" + num_adherent + "]";
	}
	
	

}
