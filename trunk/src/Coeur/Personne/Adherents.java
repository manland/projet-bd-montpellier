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

public class Adherents {
	private ArrayList<Adherent> adherents;
	protected final EventListenerList listeners = new EventListenerList();
	
	public Adherents() throws SQLException
	{
		adherents = new ArrayList<Adherent>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".adherent";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_personne = resultat.getInt("id_personne");
				int num_adherent = resultat.getInt("num_adherent");
				Adherent adherent = new Adherent(num_adherent, id_personne);
				adherents.add(adherent);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addAdherentsListener(AdherentsListener listener) {
        listeners.add(AdherentsListener.class, listener);
    }
	
	public void removeAdherentsListener(AdherentsListener listener){
		 listeners.remove(AdherentsListener.class, listener);
	}
	
	public AdherentsListener[] getAdherentsListener()
	{
		return listeners.getListeners(AdherentsListener.class);
	}
	
	protected void fireAjouterAdherent(Adherent adherent) {
        for(AdherentsListener listener : getAdherentsListener()) {
            listener.ajouterAdherent(adherent);
        }
    }
	
	protected void fireSupprimerAdherent(Adherent adherent) {
        for(AdherentsListener listener : getAdherentsListener()) {
            listener.supprimerAdherent(adherent);
        }
    }
	
	public void supprimerAdherent(int num_adherent) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".adherent WHERE num_adherent=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_adherent);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				Adherent adherent_a_supprimer=null;
				for(Adherent adherent : adherents)
					if(adherent.getNumAdherent() == num_adherent)
						adherent_a_supprimer = adherent;
				adherents.remove(adherent_a_supprimer);
				fireSupprimerAdherent(adherent_a_supprimer);
			}
			else
				throw new SQLException("Adherent non supprimé dans la Dabatase numero adherent :"+num_adherent);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierAdherent(int id_personne, String nom, String prenom, Date date, String adresse) throws SQLException
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
				Adherent adherent_a_modifier = null;
				for(Adherent adherent : adherents)
					if(adherent.getIdPersonne() == id_personne)
						adherent_a_modifier = adherent;
				if(adherent_a_modifier != null)
				{
					adherents.remove(adherent_a_modifier);
					fireSupprimerAdherent(adherent_a_modifier);
					
					adherent_a_modifier.setNom(nom);
					adherent_a_modifier.setPrenom(prenom);
					adherent_a_modifier.setDate(date);
					adherent_a_modifier.setAdresse(adresse);
					adherents.add(adherent_a_modifier);
					fireAjouterAdherent(adherent_a_modifier);
				}		
			}
			else
				throw new SQLException("Personne(via adherent) non modifié dans la Dabatase identifiant :"+id_personne);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterAdherent(int id_personne) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int num_adherent = maximumNumAdherent() + 1;
			String sql = "INSERT INTO "+SQL.getProprietaire()+".adherent VALUES(?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_adherent);
			pstmt.setInt(2, id_personne);
			if(pstmt.executeUpdate()> 0)
			{
				Adherent adherent = new Adherent(num_adherent, id_personne);
				adherents.add(adherent);
				fireAjouterAdherent(adherent);
			}
			else
				throw new SQLException("Adherent non ajouté dans la Dabatase identifiant :"+id_personne+" au numero :"+num_adherent);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int maximumNumAdherent() {
		// TODO Auto-generated method stub
		int maximum =0;
		for(Adherent a : adherents)
		{
			if(a.getNumAdherent() > maximum)
				maximum = a.getNumAdherent();
		}
		return maximum;
	}

	public ArrayList<Adherent> getAdherents() {
		return adherents;
	}

	public String toString() {
		String string;
		string = "Adherents = \n";
		for(Adherent a : adherents)
			string += a+"\n";
		return string;
	}

}
