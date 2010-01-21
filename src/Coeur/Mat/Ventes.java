package Coeur.Mat;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.swing.event.EventListenerList;

import oracle.jdbc.OracleResultSet;

import Coeur.SQL;

public class Ventes {
	private ArrayList<Vente> ventes;
	protected final EventListenerList listeners = new EventListenerList();
	
	@SuppressWarnings("unchecked")
	public Ventes() throws SQLException, ClassNotFoundException
	{
		ventes = new ArrayList<Vente>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".vente";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				Date date_vente = resultat.getDate("date_vente");
				int num_vente = resultat.getInt("num_vente");
				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				ArrayList<TypeMaterielAchete> materiel = new ArrayList<TypeMaterielAchete>();
				
				Array employeeArray = ((OracleResultSet)resultat).getArray(2);
				Map map = c.getTypeMap();
				map.put("TYPEMATERIELACHETE", Class.forName("Coeur.Mat.TypeMaterielAchete"));
				Object[] employees = (Object[]) employeeArray.getArray();
              	for (int i=0; i<employees.length; i++)
              	{
              		Struct struct =(Struct)employees[i];
          			Object[] valeurs = struct.getAttributes();
          			String type_materiel = (String) valeurs[0];
          			int quantite = Integer.parseInt(valeurs[1].toString());
          			int prix_base = Integer.parseInt(valeurs[2].toString());
          			TypeMaterielAchete t = new TypeMaterielAchete(type_materiel, quantite, prix_base);
          			materiel.add(t);
          		}
//				Array array = resultat.getArray("materiel");
//				ResultSet tableau = array.getResultSet();
//				while(tableau.next())
//				{
//					System.out.println(tableau.getString(1));
//					System.out.println(tableau.getObject(3));
//					String type_materiel = tableau.getString(1);
//					int quantite = tableau.getInt(2);
//					int prix_base = tableau.getInt(3);
//					TypeMaterielAchete type = new TypeMaterielAchete(type_materiel, quantite, prix_base);
//					materiel.add(type);
//				}
				Vente materiel_location = new Vente(num_vente, materiel, date_vente, id_personne, prix_paye);
				ventes.add(materiel_location);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addVentesListener(VentesListener listener) {
        listeners.add(VentesListener.class, listener);
    }
	
	public void removeVentesListener(VentesListener listener){
		 listeners.remove(VentesListener.class, listener);
	}
	
	public VentesListener[] getVentesListener()
	{
		return listeners.getListeners(VentesListener.class);
	}
	
	protected void fireAjouterVente(Vente vente) {
        for(VentesListener listener : getVentesListener()) {
            listener.ajouterVente(vente);
        }
    }
	
	protected void fireSupprimerVente(Vente vente) {
        for(VentesListener listener : getVentesListener()) {
            listener.supprimerVente(vente);
        }
    }
	
	public void supprimerVente(int num_vente) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".vente WHERE num_vente=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_vente);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				Vente location_a_supprimer=null;
				for(Vente location : ventes)
					if(location.getNumVente() == num_vente)
						location_a_supprimer = location;
				ventes.remove(location_a_supprimer);
				fireSupprimerVente(location_a_supprimer);
			}
			else
				throw new SQLException("Vente non supprimé dans la Dabatase num_vente:"+num_vente);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierVente(int num_vente, ArrayList<TypeMaterielAchete> materiel, Date date_vente, int id_personne, int prix_paye) throws SQLException
	{
		supprimerVente(num_vente);
		ajouterVente(num_vente, materiel, date_vente, id_personne, prix_paye);
	}
	
	public void ajouterVente(ArrayList<TypeMaterielAchete> materiel, Date date_vente, int id_personne, int prix_paye) throws SQLException
	{
		int num_vente = maxNumVente()+1;
		ajouterVente(num_vente, materiel, date_vente, id_personne, prix_paye);
	}
	
	private void ajouterVente(int num_vente, ArrayList<TypeMaterielAchete> materiel, Date date_vente, int id_personne, int prix_paye) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			Calendar date = Calendar.getInstance();
			date.setTime(date_vente);
			String sql = "INSERT INTO "+SQL.getProprietaire()+".vente VALUES(";
			sql+=num_vente+",";
			sql+="tab_materiel_achete(";
			int i = 0;
			for(TypeMaterielAchete t : materiel){
				if(i!=0)
					sql+=",";
				sql+= "type_materiel_achete(";
				sql+= "'"+t.getTypeMateriel()+"',";
				sql+= t.getQuantite()+",";
				sql+= t.getPrixBase();
				sql+= ")";
				i++;
			}
			sql += "),";
			sql+="?,";
			sql+=""+id_personne+",";
			sql+=""+prix_paye+"";
			sql += ")";

			pstmt = c.prepareStatement(sql);
			pstmt.setDate(1, date_vente);
			if(pstmt.executeUpdate()> 0)
			{
				Vente vente = new Vente(num_vente, materiel, date_vente, id_personne, prix_paye);
				ventes.add(vente);
				fireAjouterVente(vente);
			}
			else
				throw new SQLException("Vente non ajouté dans la Dabatase au num_vente:"+num_vente);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int maxNumVente() {
		// TODO Auto-generated method stub
		int maximum = 0;
		for(Vente v : ventes)
		{
			if(v.getNumVente() > maximum)
				maximum = v.getNumVente();
		}
		return maximum;
	}
	public ArrayList<Vente> getVentes() {
		return ventes;
	}

	public String toString() {
		String string;
		string = "Ventes = \n";
		for(Vente a : ventes)
			string += a+"\n";
		return string;
	}

}
