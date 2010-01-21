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

public class Locations {
	private ArrayList<Location> locations;
	protected final EventListenerList listeners = new EventListenerList();
	
	@SuppressWarnings("unchecked")
	public Locations() throws SQLException, ClassNotFoundException
	{
		locations = new ArrayList<Location>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".location";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int num_location = resultat.getInt("num_location");
				Date date_location = resultat.getDate("date_location");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				ArrayList<TypeMaterielLoue> materiel = new ArrayList<TypeMaterielLoue>();

				Array employeeArray = ((OracleResultSet)resultat).getArray(5);
				Map map = c.getTypeMap();
				map.put("TYPEMATERIELLOUE", Class.forName("Coeur.Mat.TypeMaterielLoue"));
				Object[] employees = (Object[]) employeeArray.getArray();
              	for (int i=0; i<employees.length; i++)
              	{
              		Struct struct =(Struct)employees[i];
          			Object[] valeurs = struct.getAttributes();
          			TypeMaterielLoue t = new TypeMaterielLoue(Integer.parseInt(valeurs[0].toString()));
          			materiel.add(t);
              	}
				
//				Array array = resultat.getArray("materiel");
//				TypeMaterielLoue[] object = (TypeMaterielLoue[])array.getArray(map);
//				System.out.println(object.toString());
//				ResultSet tableau = array.getResultSet(map);
//				while(tableau.next()){
//					System.out.println(tableau.getObject(1));
//				}


				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				Location location = new Location(num_location, date_location, heure_debut, heure_fin, materiel, id_personne, prix_paye);
				locations.add(location);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addLocationsListener(LocationsListener listener) {
        listeners.add(LocationsListener.class, listener);
    }
	
	public void removeLocationsListener(LocationsListener listener){
		 listeners.remove(LocationsListener.class, listener);
	}
	
	public LocationsListener[] getLocationsListener()
	{
		return listeners.getListeners(LocationsListener.class);
	}
	
	protected void fireAjouterLocation(Location location) {
        for(LocationsListener listener : getLocationsListener()) {
            listener.ajouterLocation(location);
        }
    }
	
	protected void fireSupprimerLocation(Location location) {
        for(LocationsListener listener : getLocationsListener()) {
            listener.supprimerLocation(location);
        }
    }
	
	public void supprimerLocation(int num_location) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".location WHERE num_location=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_location);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				Location location_a_supprimer=null;
				for(Location location : locations)
					if(location.getNumLocation() == num_location)
						location_a_supprimer = location;
				locations.remove(location_a_supprimer);
				fireSupprimerLocation(location_a_supprimer);
			}
			else
				throw new SQLException("Location non supprimé dans la Dabatase num_materiel:"+num_location);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierLocation(int num_location, Date date_location, int heure_debut, int heure_fin,ArrayList<TypeMaterielLoue> materiel, int id_personne, int prix_paye) throws SQLException
	{
		supprimerLocation(num_location);
		ajouterLocation(num_location, date_location, heure_debut, heure_fin, materiel, id_personne, prix_paye);
	}
	
	public void ajouterLocation(Date date_location, int heure_debut, int heure_fin, ArrayList<TypeMaterielLoue> materiel, int id_personne, int prix_paye) throws SQLException
	{
		int num_location = maxNumLocation()+1;
		ajouterLocation(num_location, date_location, heure_debut, heure_fin, materiel, id_personne, prix_paye);
	}
	
	private void ajouterLocation(int num_location, Date date_location, int heure_debut, int heure_fin, ArrayList<TypeMaterielLoue> materiel, int id_personne, int prix_paye) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{	
			Calendar date = Calendar.getInstance();
			date.setTime(date_location);
			String sql = "INSERT INTO "+SQL.getProprietaire()+".location VALUES(";
			sql+=num_location+",";
			sql+="?,";
			sql+=""+heure_debut+",";
			sql+=""+heure_fin+",";
			sql+="tab_materiel_loue(";
			int i = 0;
			for(TypeMaterielLoue t : materiel){
				if(i!=0)
					sql+=",";
				sql+= "type_materiel_loue(";
				sql+= t.getNumMateriel();
				sql+= ")";
				i++;
			}
			sql+="),";
			sql+=""+id_personne+",";
			sql+=""+prix_paye+"";
			sql += ")";
			pstmt = c.prepareStatement(sql);
			pstmt.setDate(1, date_location);
			if(pstmt.executeUpdate()> 0)
			{
				Location location = new Location(num_location, date_location, heure_debut, heure_fin, materiel, id_personne, prix_paye);
				locations.add(location);
				fireAjouterLocation(location);
			}
			else
				throw new SQLException("Location non ajouté dans la Dabatase au num_materiel:"+num_location);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public int maxNumLocation(){
		int maximum =0;
		for(Location l : locations)
		{
			if(l.getNumLocation() > maximum)
				maximum = l.getNumLocation();
		}
		return maximum;
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

	public String toString() {
		String string;
		string = "Locations = \n";
		for(Location a : locations)
			string += a+"\n";
		return string;
	}

}
