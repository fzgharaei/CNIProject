import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;

public class Server {
	int port;
//    private static final Logger logger = LoggerFactory.getLogger(Server.class);
	boolean log;
	boolean running;
	String directory;
	ServerSocket server;
	Socket currClient = null;
	FileDirectory serverDirectory;
	Server(int port, boolean verbose, String dir){
		this.port = port;
		this.log = verbose;
		this.directory = dir;
		this.running = true;
		serverDirectory = new FileDirectory(this.directory);
	}
	
	public void init() throws IOException{
		this.server = new ServerSocket(this.port);
		printLog("Starting the socket server at port:" + this.port);
	}
	
	public void listen() throws IOException{
		printLog("Waiting for clients...");
		currClient = server.accept();
		printLog("The following client has connected:" + currClient.getInetAddress());
	}
	
	public void serve() throws IOException{
		BufferedReader reqbuff = new BufferedReader(new InputStreamReader(currClient.getInputStream()));
		BufferedWriter respbuff = new BufferedWriter(new OutputStreamWriter(currClient.getOutputStream()));
		
		String line = reqbuff.readLine();
		HttpStatus hs = null;
		String responsebody = "";
		if(line!=null){
			String[] headline = line.split(" ");
			if(headline[0].equals("GET")){
				if(headline[1].equals("/") ){
					try{
//						File mainDir = new File(this.directory);
//						String[] subDirNames = mainDir.list();
						ArrayList<String> subDirNames = serverDirectory.filesList();
						responsebody = "Files and Directories in Server Main Dir are as follow:";
						for(String s:subDirNames)
							responsebody += s +"\n";
						// write response to socket
						hs = HttpStatus.OK;
					}catch(Exception e){
						// proper exception needed
					}
				}else if(!headline[1].equals("HTTP/1.0")){
					try{
						File mainDir = new File(this.directory);
						FileReader respFile;
//						String[] subDirs = mainDir.list();
						ArrayList<String> subDirs = serverDirectory.filesList();
						for(String s:subDirs){
							if(s.equals(headline[1].substring(1)))
							{
								if(!serverDirectory.fileExist(headline[1].substring(1)))
								{
									hs = HttpStatus.NFOUND;
									responsebody += "File Not Found on the Server \r\n";
								}
								else if(!serverDirectory.isAccessible(headline[1].substring(1)))
								{
									hs = HttpStatus.FORBIDDEN;
									responsebody += "Access to the File is Forbidden \r\n";
								}
								else
									try{
									respFile = new FileReader(mainDir.getPath() + "/" + headline[1].substring(1));
									BufferedReader buff = new BufferedReader(respFile);
									String fileLine;
									while ((fileLine = buff.readLine()) != null) {
										if(fileLine.length()!=0)
											responsebody += (fileLine);
									}
									hs = HttpStatus.OK;
									buff.close();
									respFile.close();
								 }catch(IOException e){
									 throw new Exception("The given file doesn't exist or it's unable to be opened");
								 }
							}
						}
					}catch(Exception e){
						//handling file problems
					}
				}else{
					// handling no directory given exception
				}
			}else if(headline[0].equals("POST")){
				
				try {
					//File mainDir = new File(this.directory);
					//String[] filesList = mainDir.list();
					ArrayList<String> subDirs = serverDirectory.filesList();
					boolean isFound = false;
					for(String name : subDirs){
						//if(serverDirectory.fileExist(headline[1].substring(1))){
						
						if(name.equals(headline[1].substring(1))){
							isFound = true;
							break;
						}
						else
							isFound = false;
						
					}
					
					String data = "";
					String temp = "";
					// filename is found and name is not a directory
					
					if(isFound && !(new File(this.directory + "/" +headline[1].substring(1)).isDirectory())){
						
						
						if(serverDirectory.isAccessible(headline[1].substring(1)))
						{
						/*	String data = returnAppendData(reqbuff);
							if (data != null ) 
							{
								//Files.write(Paths.get(mainDir.getPath()+headline[1]), data.getBytes(), StandardOpenOption.APPEND);
								Files.write(Paths.get(this.directory + headline[1]), data.getBytes(), StandardOpenOption.APPEND);
							}*/
							while ((line = reqbuff.readLine()) != null ) {
//								System.out.println(line);
							    Files.write(Paths.get(headline[1]), line.getBytes(), StandardOpenOption.APPEND);
							     
								if(line.equals("")) 
								{
									temp = reqbuff.readLine();
									System.out.println(temp);
									data = temp + "\n";
									if (line != null ) 
									{
									    Files.write(Paths.get(headline[1]), line.getBytes(), StandardOpenOption.APPEND);										 
									} 
								} 
							} 
							hs= HttpStatus.OK;
						}else
						{
							hs = HttpStatus.FORBIDDEN;
							responsebody += "Access to the File is Forbidden \r\n";
						}
					}
						else{
							//File newFile = new File(mainDir.getPath() + "/" + headline[1].substring(1));
							File newFile = new File(this.directory + "/" + headline[1].substring(1));
							FileWriter writer = new FileWriter(newFile);
							BufferedWriter bw = new BufferedWriter(writer);
							//capture The data by reading the buffer after encountering \r\n in the request
//							String data = returnAppendData(reqbuff);
							
							while ((line = reqbuff.readLine()) != null ) {
							    Files.write(Paths.get(headline[1].substring(1)), line.getBytes(), StandardOpenOption.APPEND);
							     
								if(line.equals("")) 
								{
									temp = reqbuff.readLine();
									System.out.println(temp);
									data = temp + "\n";
									
									if (line != null ) 
									{
									    Files.write(Paths.get(headline[1].substring(1)), line.getBytes(), StandardOpenOption.APPEND);										 
									} 
								} 
							}
							
							if (data != null) 
							{
								bw.write(data);
								responsebody = data;
							}else{
								responsebody = "No post body!";
							}
							
							bw.close();
						}
				
//					else
//					{
//							hs = HttpStatus.FORBIDDEN;
//							responsebody += "Access to the File is Forbidden \r\n";
//					}
					
					// make response headers, append to response body and write response to socket
					
//					respbuff.close();
				}	catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				// write bad syntax on socket and listen again(don't know how yet!)
			}
			String finalResponse = HttpResponseBuilder(responsebody,hs);
			respbuff.write(finalResponse);
			respbuff.flush();
			responsebody = "";
		}
	}
		
	private String returnAppendData(BufferedReader reqbuff) {
		 		String line= "";
		 		StringBuilder stringBuilder = null;
		 			
		 			try {
		 				while ((line = reqbuff.readLine()) != null ) {
		 					System.out.println(line);  
		 				    stringBuilder = new StringBuilder();
		 					if(line.equals("\r\n\r\n"))
		 						while((line = reqbuff.readLine()) != null)
		 							stringBuilder.append(line + "\n");
		 					}
		 			} catch (IOException e) {
		 				e.printStackTrace();
		 			}
				return stringBuilder.toString();
		 	}

	public String HttpResponseBuilder(String respBody, HttpStatus hs){
		String response = "";
		if (hs.equals(HttpStatus.OK)) {

			response += hs.toString() + "\r\n";
			response += "Date:" + getTimeStamp() + "\r\n";
			response += "Server: localhost\r\n";
			response += "Content-Type: text/html\r\n";
			response += "Connection: Closed\r\n\r\n";

		} else if (hs.equals(HttpStatus.NFOUND)) {

			response += hs.toString() + "\r\n";
			response += "Date:" + getTimeStamp() + "\r\n";
			response += "Server: localhost\r\n";
			response += "\r\n";
		} else if (hs.equals(HttpStatus.NMODIFIED)) {
			
			response += hs.toString() + "\r\n";
			response += "Date:" + getTimeStamp() + "\r\n";
			response += "Server: localhost\r\n";
			response += "\r\n";
		}
		if(respBody!=null)
			response += respBody + "\r\n";
		
		return response;
	}
	private static String getTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		return formattedDate;
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
