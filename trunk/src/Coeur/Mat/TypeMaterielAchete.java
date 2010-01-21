package Coeur.Mat;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class TypeMaterielAchete  implements SQLData {
	private String type_materiel;
	private int quantite;
	private int prix_base;
	private String typeSql; 
	
	public TypeMaterielAchete(String type_materiel, int quantite, int prix_base){
		this.type_materiel = type_materiel;
		this.quantite = quantite;
		this.prix_base = prix_base;
	}
	
	public void setQuantite(int quantite){
		this.quantite = quantite;
	}
	public int getQuantite(){
		return this.quantite;
	}
	public void setTypeMateriel(String type_materiel){
		this.type_materiel = type_materiel;
	}
	public String getTypeMateriel(){
		return this.type_materiel;
	}
	public void setPrixBase(int prix_base){
		this.prix_base = prix_base;
	}
	public int getPrixBase(){
		return this.prix_base;
	}

	@Override
	public String toString() {
		return "TypeMaterielAchete [prix_base=" + prix_base + ", quantite="
				+ quantite + ", type_materiel=" + type_materiel + "]";
	}

	@Override
	public String getSQLTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return typeSql;
	}

	@Override
	public void readSQL(SQLInput stream, String type) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("dans TypeMateriel");
		typeSql = type;
		type_materiel = stream.readString();
		quantite = stream.readInt();
		prix_base = stream.readInt();
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		// TODO Auto-generated method stub
		stream.writeString(type_materiel);
		stream.writeInt(quantite);
		stream.writeInt(prix_base);
		
	}

}
