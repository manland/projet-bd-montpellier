package Coeur.Gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class CoursGym {
	private String type_cours;
	private int id_prof;
	
	public CoursGym(String type_cours) throws SQLException{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursGym WHERE type_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_cours);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				this.id_prof = resultat.getInt("id_prof");
				this.type_cours = type_cours;
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}
	}
	
	public CoursGym(String type_cours, int id_prof){
		this.type_cours = type_cours;
		this.id_prof = id_prof;
	}
	
	public String getTypeCours(){
		return this.type_cours;
	}
	
	public int getIdProf(){
		return this.id_prof;
	}
	public void setIdProf(int id_prof){
		this.id_prof=id_prof;
	}

	@Override
	public String toString() {
		return "CoursGym [id_prof=" + id_prof + ", type_cours=" + type_cours
				+ "]";
	}
	
	
}
