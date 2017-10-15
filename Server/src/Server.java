import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	ServerSocket server;
	Socket currClient = null;
	Server(int port, boolean verbose, String dir){
		this.port = port;
		this.log = verbose;
		this.directory = dir;
		this.running = true;
	}
	
	public void init() throws IOException{
		this.server = new ServerSocket(this.port);
		printLog("Starting the socket server at port:" + this.port);
	}
	public void listen() throws IOException{
		printLog("Waiting for clients...");
		currClient = server.accept();
		printLog("The following client has connected:" + currClient.getInetAddress().getCanonicalHostName());
	}
	
	public void serve() throws IOException{
		BufferedReader reqbuff = new BufferedReader(new InputStreamReader(currClient.getInputStream()));
		BufferedWriter respbuff = new BufferedWriter(new OutputStreamWriter(currClient.getOutputStream()));
		
		String line = reqbuff.readLine();
		String response;
		if(line!=null){
			String[] headline = line.split(" ");
			if(headline[0] == "GET"){
				if(headline[1].equals("/") ){
					try{
						File mainDir = new File(this.directory);
						String[] subDirNames = mainDir.list();
						response = "Files and Directories in Server Main Dir are as follow:";
						for(String s:subDirNames)
							response += s +"\n";
						// write response to socket
					}catch(Exception e){
						// proper exception needed
					}
				}else if(!headline[1].equals("HTTP/1.0")){
					try{
						File mainDir = new File(this.directory);
						File[] subDirs = mainDir.listFiles();
//	/					if(subDirs.)
					}catch(Exception e){
						//handling file problems
					}
				}else{
					// handling no directory given exception
				}
			}else if(headline[0] == "POST"){
				
			}
			else{
				// write bad syntax on socket and listen again(don't know how yet!)
			}
				
		}
		while ((line = reqbuff.readLine()) != null) {
			
		}
	}
	
	
	
	public void printLog(String logMsg){
		if(log)
			System.out.println(logMsg);
		else
			return;
	}
	public void shutdown(){
		this.running = false;
	}
}

//https://github.com/iamprem/HTTPclient-server/blob/master/HTTPServer/src/MultiThreadedServer.java
