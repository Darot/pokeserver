package database;

import helper.ResultSetConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.json.JSONArray;
import org.json.simple.JSONObject;

public class DatabaseProvider {

	
	private String url = "jdbc:postgresql://localhost/pokedb";
	private Properties props = new Properties();
	private Connection db;
	private Statement st = null;
	private ResultSet rs = null;
	private ResultSetConverter converter = new ResultSetConverter();
	
	public DatabaseProvider(){
		props.setProperty("user", "pokedb");
		props.setProperty("password", "daniel90");
		props.setProperty("ssl", "false");
		
		try{
			db = DriverManager.getConnection(url, "pokedb" , "daniel90");
		}catch(Exception e){
			System.out.println("Could not connect to database!");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}	
	}
	
	/*
	 * INSERTIONS
	 */
	public void setPlayerPosition(String trainername, String positionmap, String coordx){
		
	}
	
	
	/*
	 * QUERIES
	 */
	public void getPlayerPosition (){
		try{
			String query = "SELECT trainername " +
					"FROM trainer " +
					"WHERE positionmap = 'adminmap'";
			st = db.createStatement();
			rs = st.executeQuery(query);
			rs.next();
			System.out.println(rs.getString(1));
			
		}catch(SQLException e){
			System.out.println(e.getMessage());	
			}
	}
	
	public JSONArray getPlayerOnMap(String map){
		JSONArray result = new JSONArray();
		try{
			
			String query = "SELECT trainername, coordx, coordy " +
					"FROM trainer " +
					"WHERE positionmap = 'adminmap' " +
					"AND status = 'online'";
			
			st = db.createStatement();
			rs = st.executeQuery(query);
			try{
				
				result = converter.convert(rs);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
		}catch(SQLException e){
			System.out.println(e.getMessage());	
			}
		
		return result;
	}
	
}
