package main;

import socket.PokeServer;

public class ServerLouncher {


	public static void main(String[] args) throws Exception {
			PokeServer pokeServer = new PokeServer();
			pokeServer.runSocket();
	}

}