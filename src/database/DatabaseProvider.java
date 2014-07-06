package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseProvider {

	
	String url = "jdbc:postgresql://localhost/pokedb";
	Properties props = new Properties();
	
	public DatabaseProvider(){
		props.setProperty("user", "pokedb");
		props.setProperty("password", "Daniel90!");
		props.setProperty("ssl", "false");
		
		try{
			Connection conn = DriverManager.getConnection(url);
		}catch(Exception e){
			System.out.println("Could not connect to database!");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}	
	}
	
	
	public void setPlayerPosition(){
		
	}
	
}
