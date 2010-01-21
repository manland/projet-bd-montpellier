package Coeur.Casier;

import java.util.EventListener;

public interface ReservationsCasiersSeancesListener extends EventListener{
	void ajouterReservationCasierSeance(ReservationCasierSeance reservation_casier_seance);
	void supprimerReservationCasierSeance(ReservationCasierSeance reservation_casier_seance);

}
