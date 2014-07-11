package socket;

import org.json.JSONArray;
import org.json.simple.JSONObject;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PublisherSocket {
	private static final String EXCHANGE_NAME = "pokeCom2";
	
	private ConnectionFactory factory = new ConnectionFactory();
	private Channel channel;
	private Connection connection;
	
	public void createSocket(){
		factory.setHost("localhost");
		try{
			//Socket setup
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			
		}catch(Exception e){
			System.out.println("Something went horribly wrong!");
		}
	}
	
	public void sendPlayerMovement(JSONObject msg){
		//Packing the informations into a JSON Object
		try{
			channel.basicPublish(EXCHANGE_NAME, "player.movement", null, msg.toJSONString().getBytes());
		}catch(Exception e){
			System.out.println("no Connection!!!");
		}
	}
	
	public void sendPlayersOnMap(JSONArray msg, String mapName){
		try{
			channel.basicPublish(EXCHANGE_NAME, "map." + mapName, null, msg.toString().getBytes());
		}catch(Exception e){
			System.out.println("no Connection!!!");
		}
	}
	
	public void closeConnection(){
		try{
			connection.close();
		}catch(Exception e){
			System.out.println("No connection to close!");
		}
	}
}
