package Coeur.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class PersonnelSportif extends Personne {
	private int id_prof;
	private String type_sport;
	
	public PersonnelSportif(int id_prof) throws SQLException{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".personnelSportif WHERE id_prof=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, id_prof);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouvé un utilisateur
			{
				resultat.first();
				Personne personne = new Personne(resultat.getInt("id_personne"));
				this.type_sport = resultat.getString("type_sport");
				this.id_personne = personne.id_personne;
				this.adresse = personne.adresse;
				this.nom = personne.nom;
				this.prenom = personne.prenom;
				this.date = personne.date;
				this.id_prof = id_prof;
			}
			else
				throw new SQLException("personnelSportif non trouvé dans la Dabatase identifiant :"+id_personne);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
	}
	
	public PersonnelSportif(int id_prof, String type_sport, int id_personne) throws SQLException
	{
		super(id_personne);
		this.id_prof = id_prof;
		this.type_sport = type_sport;
	}
	
	@Override
	public String toString() {
		return "PersonnelSportif[id_prof =" + id_prof+ ", type_sport ="+type_sport+ "] ====>"+super.toString();
	}
	
	public int getIdProf() {
		return id_prof;
	}
	
	public String getTypeSport() {
		return type_sport;
	}
	public void setTypeSport(String type_sport) {
		this.type_sport = type_sport;
	}

}
