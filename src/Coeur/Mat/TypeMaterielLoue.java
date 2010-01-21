package Coeur.Mat;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class TypeMaterielLoue implements SQLData {
	private int num_materiel;
	private String typeSql; 

	public TypeMaterielLoue(int num_materiel){
		this.num_materiel = num_materiel;
	}
	
	public int getNumMateriel(){
		return this.num_materiel;
	}
	public void setNumMateriel(int num_materiel){
		this.num_materiel = num_materiel;
	}

	@Override
	public String toString() {
		return "TypeMaterielLoue [num_materiel=" + num_materiel + "]";
	}

	@Override
	public String getSQLTypeName() throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("dans TypeMateriel");
		return typeSql;
	}

	@Override
	public void readSQL(SQLInput stream, String type) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("dans TypeMateriel");
		typeSql = type;
		num_materiel = stream.readInt();
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("dans TypeMateriel");
		stream.writeInt(num_materiel);
	}
}
