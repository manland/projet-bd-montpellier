package Coeur.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class CoursSquash {

	private int id_cours;
	private int id_prof;
	private final String type_cours;
	
	public CoursSquash(int id_cours) throws SQLException{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursSquash WHERE id_cours=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, id_cours);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouvé un utilisateur
			{
				resultat.first();
				this.id_cours = id_cours;
				type_cours = resultat.getString("type_de_cours");
				id_prof = resultat.getInt("id_prof");
			}
			else
				throw new SQLException("Cours non trouvé dans la Dabatase identifiant :"+id_prof);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
	}
	
	public CoursSquash(int id_cours, String type_cours) throws SQLException{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursSquash WHERE id_cours=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, id_cours);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouvé un utilisateur
			{
				resultat.first();
				this.id_cours = id_cours;
				id_prof = resultat.getInt("id_prof");
				this.type_cours = type_cours;
			}
			else
				throw new SQLException("Cours non trouvé dans la Dabatase identifiant :"+id_prof);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
	}
	
	public CoursSquash(int id_cours, int id_prof, String type_cours){
		this.id_cours = id_cours;
		this.id_prof = id_prof;
		this.type_cours = type_cours;
	}
	
	public int getIdCours(){
		return id_cours;
	}
	
	public int getIdProf(){
		return id_prof;
	}
	public void setIdProf(int id_prof){
		this.id_prof = id_prof;
	}
	
	public String getTypeCours(){
		return this.type_cours;
	}

	@Override
	public String toString() {
		return "CoursSquash [id_cours=" + id_cours + ", id_prof=" + id_prof
				+ ", type_cours=" + type_cours + "]";
	}
}
