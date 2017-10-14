import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import HttpMsg.*;
public class Server {
	int port;
//    private static final Logger logger = LoggerFactory.getLogger(Server.class);
	boolean log;
	boolean running;
	String directory;
	Server(int port, boolean verbose, String dir){
		this.port = port;
		this.log = verbose;
		this.directory = dir;
		this.running = true;
	}
	
	public void init() throws IOException{
		ServerSocket serverSocket = new ServerSocket(this.port);
		if(log)
			System.out.println("Starting the socket server at port:" + this.port);
		Socket client = null;
	}
	
	
	public void shutdown(){
		this.running = false;
	}
}

//https://github.com/iamprem/HTTPclient-server/blob/master/HTTPServer/src/MultiThreadedServer.java
