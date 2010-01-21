package Coeur.Casier;


public class Casier {
	private int num_casier;
	
	public Casier(int num_casier)
	{
		this.num_casier = num_casier;
	}
	
	public int getNumCasier(){
		return this.num_casier;
	}

	@Override
	public String toString() {
		return "Casier [num_casier=" + num_casier + "]";
	}
}
