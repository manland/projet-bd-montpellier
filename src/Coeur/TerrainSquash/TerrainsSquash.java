package Coeur.TerrainSquash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class TerrainsSquash {
	private ArrayList<TerrainSquash> terrains_squash;
	private int nombre_terrain_maximum = 6;
	protected final EventListenerList listeners = new EventListenerList();
	
	public TerrainsSquash() throws SQLException
	{
		terrains_squash = new ArrayList<TerrainSquash>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM terrainSquash";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int num_terrain = resultat.getInt("num_terrain_squash");
				TerrainSquash terrain = new TerrainSquash(num_terrain);
				terrains_squash.add(terrain);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addTerrainsListener(TerrainsSquashListener listener) {
        listeners.add(TerrainsSquashListener.class, listener);
    }
	
	public void removeTerrainsListener(TerrainsSquashListener listener){
		 listeners.remove(TerrainsSquashListener.class, listener);
	}
	
	public TerrainsSquashListener[] getTerrainsListener()
	{
		return listeners.getListeners(TerrainsSquashListener.class);
	}
	
	protected void fireAjouterTerrain(TerrainSquash terrain) {
        for(TerrainsSquashListener listener : getTerrainsListener()) {
            listener.ajouterTerrain(terrain);
        }
    }
	
	protected void fireSupprimerTerrain(TerrainSquash terrain) {
        for(TerrainsSquashListener listener : getTerrainsListener()) {
            listener.supprimerTerrain(terrain);
        }
    }
	
	public void supprimerTerrain(int num_terrain) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM terrainSquash WHERE num_terrain_squash=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_terrain);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				TerrainSquash terrain_a_supprimer=null;
				for(TerrainSquash terrain : terrains_squash)
					if(terrain.getNumTerrain() == num_terrain)
						terrain_a_supprimer = terrain;
				terrains_squash.remove(terrain_a_supprimer);
				fireSupprimerTerrain(terrain_a_supprimer);
			}
			else
				throw new SQLException("Terrain non supprimé dans la Dabatase numero terrain :"+num_terrain);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterTerrain( ) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int num_terrain = maximumNumTerrainSquash() +1;
			if(num_terrain > nombre_terrain_maximum)
				throw new SQLException("Impossibles de créer d'autres terrains");
			String sql = "INSERT INTO terrainSquash VALUES(?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_terrain);
			if(pstmt.executeUpdate()> 0)
			{
				TerrainSquash terrain = new TerrainSquash(num_terrain);
				terrains_squash.add(terrain);
				fireAjouterTerrain(terrain);
			}
			else
				throw new SQLException("Terrain non ajouté dans la Dabatase au numero :"+num_terrain);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	private int maximumNumTerrainSquash() {
		// TODO Auto-generated method stub
		int maximum = 0;
		for(TerrainSquash t : terrains_squash)
		{
			if(t.getNumTerrain() > maximum)
				maximum = t.getNumTerrain();
		}
		return maximum;
	}

	public ArrayList<TerrainSquash> getTerrains() {
		return terrains_squash;
	}

	public String toString() {
		String string;
		string = "Terrains = \n";
		for(TerrainSquash a : terrains_squash)
			string += a+"\n";
		return string;
	}

}
