package Coeur.Mat;

import java.util.EventListener;

public interface LocationsListener extends EventListener{
	void ajouterLocation(Location location);
	void supprimerLocation(Location location);
}
