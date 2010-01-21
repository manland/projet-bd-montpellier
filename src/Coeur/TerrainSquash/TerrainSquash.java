package Coeur.TerrainSquash;

public class TerrainSquash {
	private int num_terrain;
	
	public TerrainSquash(int num_terrain)
	{
		this.num_terrain = num_terrain;
	}
	
	public int getNumTerrain(){
		return this.num_terrain;
	}

	@Override
	public String toString() {
		return "Terrain [num_terrain=" + num_terrain + "]";
	}
	
}
