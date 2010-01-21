package Coeur.Mat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class MaterielsVentes {
	private ArrayList<MaterielVente> materiels_ventes;
	protected final EventListenerList listeners = new EventListenerList();
	
	public MaterielsVentes() throws SQLException
	{
		materiels_ventes = new ArrayList<MaterielVente>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".materielVente";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				String type_materiel = resultat.getString("type_materiel");
				int quantite = resultat.getInt("quantite");
				int prix_base = resultat.getInt("prix_base");
				String description = resultat.getString("description");
				MaterielVente materiel_vente = new MaterielVente(type_materiel, description, quantite, prix_base);
				materiels_ventes.add(materiel_vente);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addMaterielsVentesListener(MaterielsVentesListener listener) {
        listeners.add(MaterielsVentesListener.class, listener);
    }
	
	public void removeMaterielsVentesListener(MaterielsVentesListener listener){
		 listeners.remove(MaterielsVentesListener.class, listener);
	}
	
	public MaterielsVentesListener[] getMaterielsVentesListener()
	{
		return listeners.getListeners(MaterielsVentesListener.class);
	}
	
	protected void fireAjouterMaterielVente(MaterielVente materiel_vente) {
        for(MaterielsVentesListener listener : getMaterielsVentesListener()) {
            listener.ajouterMaterielVente(materiel_vente);
        }
    }
	
	protected void fireSupprimerMaterielVente(MaterielVente materiel_vente) {
        for(MaterielsVentesListener listener : getMaterielsVentesListener()) {
            listener.supprimerMaterielVente(materiel_vente);
        }
    }
	
	public void supprimerMaterielVente(String type_materiel) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".materielVente WHERE type_materiel=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_materiel);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				MaterielVente materiel_vente_a_supprimer=null;
				for(MaterielVente materiel_vente : materiels_ventes)
					if(materiel_vente.getTypeMateriel().equals(type_materiel))
						materiel_vente_a_supprimer = materiel_vente;
				materiels_ventes.remove(materiel_vente_a_supprimer);
				fireSupprimerMaterielVente(materiel_vente_a_supprimer);
			}
			else
				throw new SQLException("MaterielVente non supprimé dans la Dabatase type_materiel :"+type_materiel);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierMaterielVente(String type_materiel, String description, int quantite, int prix_base) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".materielVente SET quantite=?,prix_base=?, description=? WHERE type_materiel=? ";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(3, description);
			pstmt.setString(4, type_materiel);
			pstmt.setInt(1, quantite);
			pstmt.setInt(2, prix_base);
			if(pstmt.executeUpdate()>0)
			{
				MaterielVente materiel_vente_a_modifier = null;
				for(MaterielVente materiel_vente : materiels_ventes)
					if(materiel_vente.getTypeMateriel().equals(type_materiel))
						materiel_vente_a_modifier = materiel_vente;
				if(materiel_vente_a_modifier != null)
				{
					materiels_ventes.remove(materiel_vente_a_modifier);
					fireSupprimerMaterielVente(materiel_vente_a_modifier);
					materiel_vente_a_modifier.setQuantite(quantite);
					materiel_vente_a_modifier.setPrixBase(prix_base);
					materiel_vente_a_modifier.setDescription(description);
					materiels_ventes.add(materiel_vente_a_modifier);
					fireAjouterMaterielVente(materiel_vente_a_modifier);
				}		
			}
			else
				throw new SQLException("MaterielVente non modifié dans la Dabatase type :"+type_materiel);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterMaterielVente(String type_materiel, String description, int quantite, int prix_base) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".materielVente VALUES(?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_materiel);
			pstmt.setString(2, description);
			pstmt.setInt(3, quantite);
			pstmt.setInt(4, prix_base);
			if(pstmt.executeUpdate()> 0)
			{
				MaterielVente materiel_vente = new MaterielVente(type_materiel,description, quantite, prix_base);
				materiels_ventes.add(materiel_vente);
				fireAjouterMaterielVente(materiel_vente);
			}
			else
				throw new SQLException("MaterielVente non ajouté dans la Dabatase au type :"+type_materiel);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	public ArrayList<MaterielVente> getMaterielsVentes() {
		return materiels_ventes;
	}

	public String toString() {
		String string;
		string = "MaterielsVentes = \n";
		for(MaterielVente a : materiels_ventes)
			string += a+"\n";
		return string;
	}


}
