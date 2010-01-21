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

public class PersonnelsSportifs {
	private ArrayList<PersonnelSportif> personnels_sportifs;
	protected final EventListenerList listeners = new EventListenerList();
	
	public PersonnelsSportifs() throws SQLException
	{
		personnels_sportifs = new ArrayList<PersonnelSportif>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".personnelSportif";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_prof = resultat.getInt("id_prof");
				String type_sport = resultat.getString("type_sport");
				int id_personne = resultat.getInt("id_personne");
				PersonnelSportif personnel_sportif= new PersonnelSportif(id_prof ,type_sport, id_personne);
				personnels_sportifs.add(personnel_sportif);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addPersonnelsSportifsListener(PersonnelsSportifsListener listener) {
        listeners.add(PersonnelsSportifsListener.class, listener);
    }
	
	public void removePersonnelsSportifsListener(PersonnelsSportifsListener listener){
		 listeners.remove(PersonnelsSportifsListener.class, listener);
	}
	
	public PersonnelsSportifsListener[] getPersonnelsSportifsListener()
	{
		return listeners.getListeners(PersonnelsSportifsListener.class);
	}
	
	protected void fireAjouterPersonnelsSportifs(PersonnelSportif personnel_sportif) {
        for(PersonnelsSportifsListener listener : getPersonnelsSportifsListener()) {
            listener.ajouterPersonnelSportif(personnel_sportif);
        }
    }
	
	protected void fireSupprimerPersonnelsSportifs(PersonnelSportif personnel_sportif) {
        for(PersonnelsSportifsListener listener : getPersonnelsSportifsListener()) {
            listener.supprimerPersonnelSportif(personnel_sportif);
        }
    }
	
	public void supprimerPersonnelSportif(int id_prof) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".personnelSportif WHERE id_prof=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_prof);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				PersonnelSportif personnel_sportif_a_supprimer=null;
				for(PersonnelSportif personnel_sportif: personnels_sportifs)
					if(personnel_sportif.getIdProf() == id_prof)
						personnel_sportif_a_supprimer = personnel_sportif;
				personnels_sportifs.remove(personnel_sportif_a_supprimer);
				fireSupprimerPersonnelsSportifs(personnel_sportif_a_supprimer);
			}
			else
				throw new SQLException("PersonnelSportifnon supprimé dans la Dabatase numero adherent :"+id_prof);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierPersonnelSportif(int id_personne, String nom, String prenom, Date date, String adresse, String type_sport) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql2 = "UPDATE "+SQL.getProprietaire()+".personnelSportif SET type_sport=? WHERE id_personne=?";
			pstmt = c.prepareStatement(sql2);
			pstmt.setString(1, type_sport);
			pstmt.setInt(2, id_personne);
			if(!(pstmt.executeUpdate()>0))
				throw new SQLException("PersonnelSportif non modifié dans la Dabatase identifiant :"+id_personne);

			
			String sql ="UPDATE "+SQL.getProprietaire()+".personne SET nom =?, prenom=?, datedenaissance=?, adresse=? WHERE id_personne=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(5, id_personne);
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setDate(3, date);
			pstmt.setString(4, adresse);
			if(pstmt.executeUpdate()>0)
			{
				PersonnelSportif personnel_sportif_a_modifier = null;
				for(PersonnelSportif personnel_sportif: personnels_sportifs)
					if(personnel_sportif.getIdPersonne() == id_personne)
						personnel_sportif_a_modifier = personnel_sportif;
				if(personnel_sportif_a_modifier != null)
				{
					personnels_sportifs.remove(personnel_sportif_a_modifier);
					fireSupprimerPersonnelsSportifs(personnel_sportif_a_modifier);
					personnel_sportif_a_modifier.setNom(nom);
					personnel_sportif_a_modifier.setPrenom(prenom);
					personnel_sportif_a_modifier.setDate(date);
					personnel_sportif_a_modifier.setAdresse(adresse);
					personnel_sportif_a_modifier.setTypeSport(type_sport);

					personnels_sportifs.add(personnel_sportif_a_modifier);
					fireAjouterPersonnelsSportifs(personnel_sportif_a_modifier);
				}		
			}
			else
				throw new SQLException("Personne(via PersonnelSportif) non modifié dans la Dabatase identifiant :"+id_personne);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterPersonnelSportif(int id_personne, String type_sport) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int id_prof = maximumIdProf() + 1;
			String sql = "INSERT INTO "+SQL.getProprietaire()+".personnelSportif VALUES(?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_prof);
			pstmt.setInt(2, id_personne);
			pstmt.setString(3, type_sport);
			if(pstmt.executeUpdate()> 0)
			{
				PersonnelSportif personnel_sportif= new PersonnelSportif(id_prof, type_sport, id_personne);
				personnels_sportifs.add(personnel_sportif);
				fireAjouterPersonnelsSportifs(personnel_sportif);
			}
			else
				throw new SQLException("PersonnelSportif non ajouté dans la Dabatase identifiant :"+id_personne+" au numero :"+id_prof);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int maximumIdProf() {
		// TODO Auto-generated method stub
		int maximum = 0;
		for(PersonnelSportif p : personnels_sportifs)
		{
			if(p.getIdProf() > maximum)
				maximum = p.getIdProf();
		}
		return maximum;
	}

	public ArrayList<PersonnelSportif> getPersonnelSportifs() {
		return personnels_sportifs;
	}

	public String toString() {
		String string;
		string = "Personnels Sportifs = \n";
		for(PersonnelSportif p : personnels_sportifs)
			string += p+"\n";
		return string;
	}
}
