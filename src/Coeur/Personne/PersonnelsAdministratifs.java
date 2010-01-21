package Coeur.Personne;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class PersonnelsAdministratifs {
	private ArrayList<PersonnelAdministratif> personnels_administratifs;
	protected final EventListenerList listeners = new EventListenerList();
	
	public PersonnelsAdministratifs() throws SQLException
	{
		personnels_administratifs = new ArrayList<PersonnelAdministratif>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".personnelAdministratif";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int num_employe = resultat.getInt("num_employe");
				String affectation = resultat.getString("affectation");
				int id_personne = resultat.getInt("id_personne");
				PersonnelAdministratif personnel_administratif= new PersonnelAdministratif(num_employe,affectation, id_personne);
				personnels_administratifs.add(personnel_administratif);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addPersonnelsAdministratifsListener(PersonnelsAdministratifsListener listener) {
        listeners.add(PersonnelsAdministratifsListener.class, listener);
    }
	
	public void removePersonnelsAdministratifsListener(PersonnelsAdministratifsListener listener){
		 listeners.remove(PersonnelsAdministratifsListener.class, listener);
	}
	
	public PersonnelsAdministratifsListener[] getPersonnelsAdministratifsListener()
	{
		return listeners.getListeners(PersonnelsAdministratifsListener.class);
	}
	
	protected void fireAjouterPersonnelsAdministratifs(PersonnelAdministratif personnel_administratif) {
        for(PersonnelsAdministratifsListener listener : getPersonnelsAdministratifsListener()) {
            listener.ajouterPersonnelAdministratif(personnel_administratif);
        }
    }
	
	protected void fireSupprimerPersonnelsAdministratifs(PersonnelAdministratif personnel_administratif) {
        for(PersonnelsAdministratifsListener listener : getPersonnelsAdministratifsListener()) {
            listener.supprimerPersonnelAdministratif(personnel_administratif);
        }
    }
	
	public void supprimerPersonnelAdministratif(int num_employe) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".personnelAdministratif WHERE num_employe=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_employe);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				PersonnelAdministratif personnel_administratif_a_supprimer=null;
				for(PersonnelAdministratif personnel_administratif: personnels_administratifs)
					if(personnel_administratif.getNumEmploye() == num_employe)
						personnel_administratif_a_supprimer = personnel_administratif;
				personnels_administratifs.remove(personnel_administratif_a_supprimer);
				fireSupprimerPersonnelsAdministratifs(personnel_administratif_a_supprimer);
			}
			else
				throw new SQLException("PersonnelAdministratif non supprimé dans la Dabatase numero adherent :"+num_employe);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierPersonnelAdministratif(int id_personne, String nom, String prenom, Date date, String adresse, String affectation) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql2 = "UPDATE "+SQL.getProprietaire()+".personnelAdministratif SET affectation=? WHERE id_personne=?";
			pstmt = c.prepareStatement(sql2);
			pstmt.setString(1, affectation);
			pstmt.setInt(2, id_personne);
			if(!(pstmt.executeUpdate()>0))
				throw new SQLException("PersonnelAdministratif non modifié dans la Dabatase identifiant :"+id_personne);

			
			String sql ="UPDATE "+SQL.getProprietaire()+".personne SET nom =?, prenom=?, datedenaissance=?, adresse=? WHERE id_personne=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(5, id_personne);
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setDate(3, date);
			pstmt.setString(4, adresse);
			if(pstmt.executeUpdate()>0)
			{
				PersonnelAdministratif personnel_administratif_a_modifier = null;
				for(PersonnelAdministratif personnel_administratif: personnels_administratifs)
					if(personnel_administratif.getIdPersonne() == id_personne)
						personnel_administratif_a_modifier = personnel_administratif;
				if(personnel_administratif_a_modifier != null)
				{
					personnels_administratifs.remove(personnel_administratif_a_modifier);
					fireSupprimerPersonnelsAdministratifs(personnel_administratif_a_modifier);

					personnel_administratif_a_modifier.setNom(nom);
					personnel_administratif_a_modifier.setPrenom(prenom);
					personnel_administratif_a_modifier.setDate(date);
					personnel_administratif_a_modifier.setAdresse(adresse);
					personnel_administratif_a_modifier.setAffectation(affectation);
					personnels_administratifs.add(personnel_administratif_a_modifier);
					fireAjouterPersonnelsAdministratifs(personnel_administratif_a_modifier);
				}		
			}
			else
				throw new SQLException("Personne(via PersonnelAdministratif) non modifié dans la Dabatase identifiant :"+id_personne);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterPersonnelAdministratif(int id_personne, String affectation) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int num_employe = maximumNumEmploye() + 1;
			String sql = "INSERT INTO "+SQL.getProprietaire()+".personnelAdministratif VALUES(?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_employe);
			pstmt.setInt(2, id_personne);
			pstmt.setString(3, affectation);
			if(pstmt.executeUpdate()> 0)
			{
				PersonnelAdministratif personnel_administratif= new PersonnelAdministratif(num_employe, affectation, id_personne);
				personnels_administratifs.add(personnel_administratif);
				fireAjouterPersonnelsAdministratifs(personnel_administratif);
			}
			else
				throw new SQLException("PersonnelAdministratif non ajouté dans la Dabatase identifiant :"+id_personne+" au numero :"+num_employe);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int maximumNumEmploye() {
		// TODO Auto-generated method stub
		int maximum = 0;
		for(PersonnelAdministratif p : personnels_administratifs)
		{
			if(p.getNumEmploye() > maximum)
				maximum = p.getNumEmploye();
		}
		return maximum;
	}
	public ArrayList<PersonnelAdministratif> getPersonnelsAdministratifs() {
		return personnels_administratifs;
	}

	public String toString() {
		String string;
		string = "Personnels Administratifs = \n";
		for(PersonnelAdministratif p : personnels_administratifs)
			string += p+"\n";
		return string;
	}
}
