package Coeur.Casier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class Casiers {
	private ArrayList<Casier> casiers;
	protected final EventListenerList listeners = new EventListenerList();
	
	public Casiers() throws SQLException
	{
		casiers = new ArrayList<Casier>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".casier";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int num_casier = resultat.getInt("num_casier");
				Casier casier = new Casier(num_casier);
				casiers.add(casier);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCasiersListener(CasiersListener listener) {
        listeners.add(CasiersListener.class, listener);
    }
	
	public void removeCasiersListener(CasiersListener listener){
		 listeners.remove(CasiersListener.class, listener);
	}
	
	public CasiersListener[] getCasiersListener()
	{
		return listeners.getListeners(CasiersListener.class);
	}
	
	protected void fireAjouterCasier(Casier casier) {
        for(CasiersListener listener : getCasiersListener()) {
            listener.ajouterCasier(casier);
        }
    }
	
	protected void fireSupprimerCasier(Casier casier) {
        for(CasiersListener listener : getCasiersListener()) {
            listener.supprimerCasier(casier);
        }
    }
	
	public void supprimerCasier(int num_casier) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".casier WHERE num_casier=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_casier);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				Casier casier_a_supprimer=null;
				for(Casier casier : casiers)
					if(casier.getNumCasier() == num_casier)
						casier_a_supprimer = casier;
				casiers.remove(casier_a_supprimer);
				fireSupprimerCasier(casier_a_supprimer);
			}
			else
				throw new SQLException("Casier non supprimé dans la Dabatase numero casier :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCasier( ) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int num_casier = numCasier();
			if(num_casier > SQL.getNombreCasier())
				throw new SQLException("Impossibles de créer d'autres casiers");
			String sql = "INSERT INTO "+SQL.getProprietaire()+".casier VALUES(?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_casier);
			if(pstmt.executeUpdate()> 0)
			{
				Casier casier = new Casier(num_casier);
				casiers.add(casier);
				fireAjouterCasier(casier);
			}
			else
				throw new SQLException("Casier non ajouté dans la Dabatase au numero :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int numCasier() {
		// TODO Auto-generated method stub
		int maximum = SQL.getPremierCasierAnnee();
		boolean estLibre = false;
		while(!estLibre && maximum<=SQL.getDernierCasierSeance())
		{
			estLibre = true;
			for(Casier a : casiers)
			{
				if(a.getNumCasier() == maximum)
					estLibre = false;
			}
			if(!estLibre)
				maximum++;
		}
		return maximum;
	}

	public ArrayList<Casier> getCasiers() {
		return casiers;
	}

	public String toString() {
		String string;
		string = "Casiers = \n";
		for(Casier a : casiers)
			string += a+"\n";
		return string;
	}

}
