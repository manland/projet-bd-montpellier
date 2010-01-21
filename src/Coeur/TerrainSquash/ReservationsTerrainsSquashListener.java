package Coeur.TerrainSquash;

import java.util.EventListener;

public interface ReservationsTerrainsSquashListener extends EventListener{
	void ajouterReservationTerrainSquash(ReservationTerrainSquash reservation_casier_seance);
	void supprimerReservationTerrainSquash(ReservationTerrainSquash reservation_casier_seance);
}
