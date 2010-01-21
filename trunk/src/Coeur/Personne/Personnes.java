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


public class Personnes {

	private ArrayList<Personne> personnes;
	protected final EventListenerList listeners = new EventListenerList();
	
	public Personnes() throws SQLException
	{
		personnes = new ArrayList<Personne>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".personne";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_personne = resultat.getInt("id_personne");
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				Date date = resultat.getDate("dateDeNaissance");
				String adresse = resultat.getString("adresse");
				Personne personne = new Personne(id_personne, nom, prenom, date, adresse);
				personnes.add(personne);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}
	} 
	
	protected void fireAjouterPersonne(Personne personne) {
        for(PersonnesListener listener : getPersonnesListener()) {
            listener.ajouterPersonne(personne);
        }
    }
	
	protected void fireSupprimerPersonne(Personne personne) {
        for(PersonnesListener listener : getPersonnesListener()) {
            listener.supprimerPersonne(personne);
        }
    }
	
	public void addPersonnesListener(PersonnesListener listener) {
        listeners.add(PersonnesListener.class, listener);
    }
	
	public void removePersonnesListener(PersonnesListener listener){
		 listeners.remove(PersonnesListener.class, listener);
	}
	
	public PersonnesListener[] getPersonnesListener()
	{
		return listeners.getListeners(PersonnesListener.class);
	}
	
	public void modifierPersonne(int id_personne, String nom, String prenom, Date date, String adresse) throws Exception
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".personne SET nom =?, prenom=?, datedenaissance=?, adresse=? WHERE id_personne=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(5, id_personne);
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setDate(3, date);
			pstmt.setString(4, adresse);
			if(pstmt.executeUpdate()>0)
			{
				Personne personne_a_modifier = null;
				for(Personne personne : personnes)
					if(personne.getIdPersonne() == id_personne)
						personne_a_modifier = personne;
				if(personne_a_modifier != null)
				{
					personnes.remove(personne_a_modifier);
					fireSupprimerPersonne(personne_a_modifier);

					personne_a_modifier.setNom(nom);
					personne_a_modifier.setPrenom(prenom);
					personne_a_modifier.setDate(date);
					personne_a_modifier.setAdresse(adresse);
					personnes.add(personne_a_modifier);
					fireAjouterPersonne(personne_a_modifier);
				}		
			}
			else
				throw new Exception("Personne non modifié dans la Dabatase identifiant :"+id_personne);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int maximumIdPersonne() {
		// TODO Auto-generated method stub
		int maximum = 0;
		for(Personne p : personnes)
		{
			if(p.getIdPersonne() > maximum)
				maximum = p.getIdPersonne();
		}
		return maximum;
	}

	public void supprimerPersonne(int id_personne) throws Exception
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".adherent WHERE id_personne=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_personne);
			pstmt.executeUpdate();
			sql = "DELETE FROM "+SQL.getProprietaire()+".personnelAdministratif WHERE id_personne=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_personne);
			pstmt.executeUpdate();
			sql = "DELETE FROM "+SQL.getProprietaire()+".personnelSportif WHERE id_personne=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_personne);
			pstmt.executeUpdate();
			sql = "DELETE FROM "+SQL.getProprietaire()+".personne WHERE id_personne=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_personne);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				Personne personne_a_supprimer=null;
				for(Personne personne : personnes)
					if(personne.getIdPersonne() == id_personne)
						personne_a_supprimer = personne;
				personnes.remove(personne_a_supprimer);
				fireSupprimerPersonne(personne_a_supprimer);
			}
			else
				throw new Exception("Personne non supprimé dans la Dabatase identifiant :"+id_personne);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterPersonne(String nom, String prenom, Date date, String adresse) throws Exception
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int id_personne = maximumIdPersonne() + 1; // plus au ID auquel on ajoute un pour le suivant Id libre
			String sql = "INSERT INTO "+SQL.getProprietaire()+".personne VALUES (?, ?, ?, ?, ?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_personne);
			pstmt.setString(2, nom);
			pstmt.setString(3, prenom);
			pstmt.setDate(4, date);
			pstmt.setString(5, adresse);
			if(pstmt.executeUpdate()> 0)
			{
				Personne personne = new Personne(id_personne, nom, prenom, date, adresse);
				personnes.add(personne);
				fireAjouterPersonne(personne);
			}
			else
				throw new Exception("Personne non ajouté dans la Dabatase identifiant :"+id_personne);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	@Override
	public String toString() {
		String string;
		string = "Personnes = \n";
		for(Personne e : personnes)
			string += e+"\n";
		return string;
	}

	public ArrayList<Personne> getPersonnes() {
		return personnes;
	}

	public void setPersonnes(ArrayList<Personne> personnes) {
		this.personnes = personnes;
	}
}
