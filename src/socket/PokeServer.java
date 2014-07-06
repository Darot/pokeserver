package socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import com.badlogic.gdx.math.Vector2;

import database.DatabaseProvider;



public class PokeServer {
	
	//Objects for the receiver Socket
	private static final String EXCHANGE_NAME = "pokeCom";
	private ConnectionFactory factory = new ConnectionFactory();
	private Connection connection;
	private Channel channel;
	private JSONParser parser;
	private DatabaseProvider db = new DatabaseProvider();
	
	//create the publisher socket to reply
	PublisherSocket publisher = new PublisherSocket();
	
	public PokeServer(){
		parser = new JSONParser();
		publisher.createSocket();
	}
	
	//the receving socket that handles client information
	public void runSocket() throws Exception{
		
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();
		
		channel.queueBind(queueName, EXCHANGE_NAME, "database.request");
		channel.queueBind(queueName, EXCHANGE_NAME, "player.set.position");
		channel.queueBind(queueName, EXCHANGE_NAME, "player.movement");
		channel.queueBind(queueName, EXCHANGE_NAME, "player.get.all");
		channel.queueBind(queueName, EXCHANGE_NAME, "player.join");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		
		
		while(true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(message);
			JSONObject msg = (JSONObject) parser.parse(message);
			
			String routingKey = delivery.getEnvelope().getRoutingKey();
			
			//Handle the messages
			switch(routingKey){
			case("database.request.login"):
				System.out.println("username and password received");
				break;
			case("player.get.all"):
				System.out.println("GETPLAYERS!!!!");
				break;
			case("player.movement"):
				publisher.sendPlayerMovement(msg);
				break;
			case("player.join"):
				
				break;
			}
		}
	}

	
}
