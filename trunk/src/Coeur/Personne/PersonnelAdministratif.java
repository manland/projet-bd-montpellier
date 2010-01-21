package Coeur.Personne;

import java.sql.SQLException;

public class PersonnelAdministratif extends Personne {
	private int num_employe;
	private String affectation;
	
	public PersonnelAdministratif(int num_employe, String affectation, int id_personne) throws SQLException
	{
		super(id_personne);
		this.num_employe = num_employe;
		this.affectation = affectation;
	}
	
	@Override
	public String toString() {
		return "PersonnelsAdministratif [num_employe=" + num_employe+ ", affectation ="+affectation+ "] ====>"+super.toString();
	}
	
	public int getNumEmploye() {
		return num_employe;
	}
	
	public String getAffectation() {
		return affectation;
	}
	public void setAffectation(String affectation) {
		this.affectation = affectation;
	}

}
