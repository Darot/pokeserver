package socket;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import database.DatabaseProvider;



public class PokeServer {

	private static final String EXCHANGE_NAME = "pokeCom";
	private ConnectionFactory factory = new ConnectionFactory();
	private Connection connection;
	private Channel channel;
	private JSONParser parser;
	private DatabaseProvider db;
	
	public PokeServer(){
		parser = new JSONParser();
	}
	
	public void runSocket() throws Exception{
		
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();
		
		channel.queueBind(queueName, EXCHANGE_NAME, "database.request");
		channel.queueBind(queueName, EXCHANGE_NAME, "player.setPosition");
		channel.queueBind(queueName, EXCHANGE_NAME, "player.movement");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		
		while(true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(message);
			JSONObject obj = (JSONObject) parser.parse(message);
			
			String routingKey = delivery.getEnvelope().getRoutingKey();
			
			//Handle the messages
			switch(routingKey){
			case("database.request.login"):
				System.out.println("username and password received");
				break;
			case("player.setPosition"):
				db.setPlayerPosition();
				break;
			case("player.movement"):
				publishPlayerMovement("map", 0);
			}
			
			
			System.out.println("[x] Received " + routingKey + " : " + message + "");
		}
	}

	private void publishPlayerMovement(String string, int i) {
		// send the movement informations to the other players on the map
		
	}
}
