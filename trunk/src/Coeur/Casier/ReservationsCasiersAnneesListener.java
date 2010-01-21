package Coeur.Casier;

import java.util.EventListener;

public interface ReservationsCasiersAnneesListener extends EventListener {
	void ajouterReservationCasierAnnee(ReservationCasierAnnee reservation_casier_annee);
	void supprimerReservationCasierAnnee(ReservationCasierAnnee reservation_casier_annee);
}
