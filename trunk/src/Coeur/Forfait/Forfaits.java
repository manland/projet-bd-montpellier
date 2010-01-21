package Coeur.Forfait;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class Forfaits {
	private ArrayList<Forfait> forfaits;
	protected final EventListenerList listeners = new EventListenerList();
	
	public Forfaits() throws SQLException
	{
		forfaits = new ArrayList<Forfait>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".forfait";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat =  statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				String type_forfait = resultat.getString("type_forfait");
				int prix_mois = resultat.getInt("prix_mois");
				int prix_trimestre = resultat.getInt("prix_trimestre");
				int prix_semestre = resultat.getInt("prix_semestre");
				int prix_annee = resultat.getInt("prix_an");
				Forfait forfait = new Forfait(type_forfait, prix_mois, prix_trimestre, prix_semestre, prix_annee);
				forfaits.add(forfait);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addForfaitsListener(ForfaitsListener listener) {
        listeners.add(ForfaitsListener.class, listener);
    }
	
	public void removeForfaitsListener(ForfaitsListener listener){
		 listeners.remove(ForfaitsListener.class, listener);
	}
	
	public ForfaitsListener[] getForfaitsListener()
	{
		return listeners.getListeners(ForfaitsListener.class);
	}
	
	protected void fireAjouterForfait(Forfait forfait) {
        for(ForfaitsListener listener : getForfaitsListener()) {
            listener.ajouterForfait(forfait);
        }
    }
	
	protected void fireSupprimerForfait(Forfait location) {
        for(ForfaitsListener listener : getForfaitsListener()) {
            listener.supprimerForfait(location);
        }
    }
	
	public void supprimerForfait(String type_forfait) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".forfait WHERE type_forfait=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_forfait);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				Forfait forfait_a_supprimer=null;
				for(Forfait forfait : forfaits)
					if(forfait.getTypeForfait().equals(type_forfait))
						forfait_a_supprimer = forfait;
				forfaits.remove(forfait_a_supprimer);
				fireSupprimerForfait(forfait_a_supprimer);
			}
			else
				throw new SQLException("Forfait non supprim� dans la Dabatase type_forfait:"+type_forfait);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierForfait(String type_forfait, int prix_mois, int prix_trimestre, int prix_semestre, int prix_annee) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".forfait SET prix_mois=?,prix_trimestre=?,prix_semestre=?,prix_annee=? WHERE type_forfait=? ";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(5, type_forfait);
			pstmt.setInt(1, prix_mois);
			pstmt.setInt(2, prix_trimestre);
			pstmt.setInt(3, prix_semestre);
			pstmt.setInt(4, prix_annee);
			if(pstmt.executeUpdate()>0)
			{
				Forfait forfait_a_modifier = null;
				for(Forfait forfait : forfaits)
					if(forfait.getTypeForfait().equals(type_forfait))
						forfait_a_modifier = forfait;
				if(forfait_a_modifier != null)
				{
					forfaits.remove(forfait_a_modifier);
					fireSupprimerForfait(forfait_a_modifier);
					forfait_a_modifier.setPrixMois(prix_mois);
					forfait_a_modifier.setPrixTrimestre(prix_trimestre);
					forfait_a_modifier.setPrixSemestre(prix_semestre);
					forfait_a_modifier.setPrixAnnee(prix_annee);
					forfaits.add(forfait_a_modifier);
					fireAjouterForfait(forfait_a_modifier);
				}		
			}
			else
				throw new SQLException("Forfait non modifi� dans la Dabatase type_forfait :"+type_forfait);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterForfait(String type_forfait, int prix_mois, int prix_trimestre, int prix_semestre, int prix_annee) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".forfait VALUES(?,?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_forfait);
			pstmt.setInt(2, prix_mois);
			pstmt.setInt(3, prix_trimestre);
			pstmt.setInt(4, prix_semestre);
			pstmt.setInt(5, prix_annee);
			if(pstmt.executeUpdate()> 0)
			{
				Forfait forfait = new Forfait(type_forfait, prix_mois, prix_trimestre, prix_semestre, prix_annee);
				forfaits.add(forfait);
				fireAjouterForfait(forfait);
			}
			else
				throw new SQLException("Forfait non ajout� dans la Dabatase au type_forfait:"+type_forfait);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	public ArrayList<Forfait> getForfaits() {
		return forfaits;
	}

	public String toString() {
		String string;
		string = "Forfaits = \n";
		for(Forfait a : forfaits)
			string += a+"\n";
		return string;
	}

}

