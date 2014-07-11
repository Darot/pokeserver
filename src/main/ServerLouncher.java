package main;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;


import socket.PokeServer;

public class ServerLouncher {


	public static void main(String[] args) throws Exception {
			//System.out.println("Server is not Running!");
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						HttpServer server = HttpServerFactory.create("http://localhost:8080/rest");
						server.start();
						//JOptionPane.showMessageDialog( null, "Ende" );
						//server.stop( 0 );
						System.out.println("Server is Running!");
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
			
			PokeServer pokeServer = new PokeServer();
			pokeServer.runSocket();
	}

}
