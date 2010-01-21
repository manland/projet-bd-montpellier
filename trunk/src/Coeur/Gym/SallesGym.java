package Coeur.Gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class SallesGym {
	private ArrayList<SalleGym> salles_gym;
	protected final EventListenerList listeners = new EventListenerList();
	
	public SallesGym() throws SQLException
	{
		salles_gym = new ArrayList<SalleGym>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".salleGym";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_salle = resultat.getInt("id_salle");
				int quantite = resultat.getInt("capacite");
				SalleGym salle_gym = new SalleGym(id_salle, quantite);
				salles_gym.add(salle_gym);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addSallesGymListener(SallesGymListener listener) {
        listeners.add(SallesGymListener.class, listener);
    }
	
	public void removeSallesGymListener(SallesGymListener listener){
		 listeners.remove(SallesGymListener.class, listener);
	}
	
	public SallesGymListener[] getSallesGymListener()
	{
		return listeners.getListeners(SallesGymListener.class);
	}
	
	protected void fireAjouterSalleGym(SalleGym salle_gym) {
        for(SallesGymListener listener : getSallesGymListener()) {
            listener.ajouterSalleGym(salle_gym);
        }
    }
	
	protected void fireSupprimerSalleGym(SalleGym salle_gym) {
        for(SallesGymListener listener : getSallesGymListener()) {
            listener.supprimerSalleGym(salle_gym);
        }
    }
	
	public void supprimerSalleGym(int id_salle) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".salleGym WHERE id_salle=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_salle);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				SalleGym salle_gym_a_supprimer=null;
				for(SalleGym salle_gym : salles_gym)
					if(salle_gym.getIdSalle() == id_salle)
						salle_gym_a_supprimer = salle_gym;
				salles_gym.remove(salle_gym_a_supprimer);
				fireSupprimerSalleGym(salle_gym_a_supprimer);
			}
			else
				throw new SQLException("Salle gym non supprimé dans la Dabatase numero cours :"+id_salle);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierSalleGym(int id_salle, int quantite) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".salleGym SET quantite=? WHERE id_salle=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, quantite);
			pstmt.setInt(2, id_salle);
			if(pstmt.executeUpdate()>0)
			{
				SalleGym salle_gym_a_modifier = null;
				for(SalleGym salle_gym : salles_gym)
					if(salle_gym.getIdSalle() == id_salle)
						salle_gym_a_modifier = salle_gym;
				if(salle_gym_a_modifier != null)
				{
					salles_gym.remove(salle_gym_a_modifier);
					fireSupprimerSalleGym(salle_gym_a_modifier);
					
					salle_gym_a_modifier.setQuantite(quantite);
					salles_gym.add(salle_gym_a_modifier);
					fireAjouterSalleGym(salle_gym_a_modifier);
				}		
			}
			else
				throw new SQLException("SalleGym non modifié dans la Dabatase identifiant :"+id_salle);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterSalleGym(int quantite) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int id_salle = maximumIdSalle() + 1;
			String sql = "INSERT INTO "+SQL.getProprietaire()+".salleGym VALUES(?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_salle);
			pstmt.setInt(2, quantite);
			if(pstmt.executeUpdate()> 0)
			{
				SalleGym salle_gym = new SalleGym(id_salle, quantite);
				salles_gym.add(salle_gym);
				fireAjouterSalleGym(salle_gym);
			}
			else
				throw new SQLException("SalleGym non ajouté dans la Dabatase numero :"+id_salle);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int maximumIdSalle() {
		// TODO Auto-generated method stub
		int maximum =0;
		for(SalleGym a : salles_gym)
		{
			if(a.getIdSalle() > maximum)
				maximum = a.getIdSalle();
		}
		return maximum;
	}

	public ArrayList<SalleGym> getSallesGym() {
		return salles_gym;
	}

	public String toString() {
		String string;
		string = "SallesGym = \n";
		for(SalleGym a : salles_gym)
			string += a+"\n";
		return string;
	}


}
