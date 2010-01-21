package Coeur.TerrainSquash;

import java.util.EventListener;

public interface TerrainsSquashListener extends EventListener{
	void ajouterTerrain(TerrainSquash terrain);
	void supprimerTerrain(TerrainSquash terrain);
}
