import java.net.Socket;
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
import java.io.PrintWriter;
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
		HttpStatus hs = HttpStatus.OK;
		String responsebody = "";

		if(line!=null){
			String[] headline = line.split(" ");
			boolean fileFound = false;
			if(headline[0].equals("GET")){
				if(headline[1].equals("/") ){
					try{
//						File mainDir = new File(this.directory);
//						String[] subDirNames = mainDir.list();
						ArrayList<String> subDirNames = serverDirectory.filesList();
						responsebody = "Files and Directories in Server Main Dir are as follow: \n";
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
						ArrayList<String> subDirs = serverDirectory.filesList();
						
						for(String s:subDirs){
							
							System.out.println(s);
							File file1 = new File(s);
							File file2 = new File(this.directory + headline[1]);
							
							System.out.println("*******");
							System.out.println(file1.getCanonicalPath());
							System.out.println(file2.getCanonicalPath());

							if(file1.compareTo(file2) == 0)
							{
								
								if(!serverDirectory.isAccessible(this.directory + headline[1]))
								{
									fileFound = true;
									hs = HttpStatus.FORBIDDEN;
									responsebody += "Access to the File is Forbidden \r\n";
								}
								else
									try{
									fileFound = true;
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
								 }
								catch(IOException e){
									 throw new Exception("The given file doesn't exist or it's unable to be opened");
								 }
							}
						}
					}catch(Exception e){
						//handling file problems
					}
				}
				
				if(!fileFound){
					hs = HttpStatus.NFOUND;
					responsebody += "the Directory was Not Found on the Server \r\n";			
				}
			}else if(headline[0].equals("POST")){
				
				try {
					//File mainDir = new File(this.directory);
					//String[] filesList = mainDir.list();
					ArrayList<String> subDirs = serverDirectory.filesList();
					boolean isFound = false;
					System.out.println("******IN POST*******");
					for(String name : subDirs){
						//if(serverDirectory.fileExist(headline[1].substring(1))){
							
						System.out.println(name);
						File file1 = new File(name);
						File file2 = new File(this.directory + headline[1]);
						
						
						System.out.println("****CANONICAL PATHS***");
						System.out.println(file1.getCanonicalPath());
						System.out.println(file2.getCanonicalPath());
						
						if(file1.compareTo(file2) == 0){
							isFound = true;
							break;
						}
						else
							isFound = false;
						
					}

					// filename is found and name is not a directory
					if(isFound && !(new File(headline[1]).isDirectory())){
						if(serverDirectory.isAccessible(headline[1].substring(1)))
						{
//							PrintWriter writer = new PrintWriter(this.directory + headline[1].substring(1), "UTF-8");
							while(reqbuff.ready()){	
								System.out.println(line);
								line = reqbuff.readLine();
								if(line.equals("")) 
								{
									line = reqbuff.readLine();
									System.out.println(line);
									FileWriter outFile = new FileWriter(this.directory +headline[1].substring(1));
									BufferedWriter buff = new BufferedWriter(outFile);
									buff.write(line);
									buff.append('\n');
									buff.close();
									outFile.close();
//									Files.write(Paths.get(headline[1].substring(1)), line.getBytes(), StandardOpenOption.APPEND);
//									writer.println(line);
									responsebody += line;
//									if (line == null ) break;
								} 
								if(line == null)
								{
									System.out.println("Encountered Bad Response.. No Empty Line between Headers and Data");
									break;
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
							
							File newFile = new File(this.directory + headline[1].substring(1));
							
							if (!newFile.isDirectory()) {
								
								  File parentDir = newFile.getParentFile();
								  boolean success = true;
								  
								  if(!parentDir.exists()){
									  success = parentDir.mkdirs();
								  }
								 // boolean success = parentDir.mkdirs();
								  if (success) {
								    System.out.println("Created path: " + newFile.getPath());
								    
									PrintWriter writer = new PrintWriter(this.directory + headline[1].substring(1), "UTF-8");
									while(reqbuff.ready())
									{	
										System.out.println(line);
										line = reqbuff.readLine();
										if(line == null)
										{
											System.out.println("Encountered Bad Response.. No Empty Line between Headers and Data");
											break;
										}
										if(line.equals("")) 
										{
											line = reqbuff.readLine();
											//Files.write(Paths.get(headline[1]), line.getBytes(), StandardOpenOption.APPEND);
											writer.println(line);
											responsebody += line;
											if (line == null ) break;
										} 
										
									}

									writer.close();
								  } else {
									    System.out.println("Could not create path: " + newFile.getPath());
									  }
									} else {
									  System.out.println("Path exists: " + newFile.getPath());
									}
						}

				}	catch (Exception e) {
					e.printStackTrace();
				}

			}
			else{
				hs = HttpStatus.BADREQUEST;
				responsebody += "Error! Bad Syntax! \nYou have entered a command which we do not support. \nPlease try again";
			}
		}
		String finalResponse = HttpResponseBuilder(responsebody,hs);
//		System.out.println(finalResponse);
		respbuff.write(finalResponse);
		respbuff.flush();
		responsebody = "";
		finalResponse = "";
		respbuff.close();
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
		else if(hs.equals(HttpStatus.FORBIDDEN)){
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
/*		line = reqbuff.readLine();
File mainDir = new File(this.directory);
FileWriter respFile = new FileWriter(mainDir.getPath() + "/" + headline[1].substring(1));
BufferedWriter buff = new BufferedWriter(respFile);
if(line.equals("")){
	line = reqbuff.readLine();
	buff.write();
	//Files.write(Paths.get(headline[1].substring(1)), line.getBytes(), StandardOpenOption.APPEND);
	if(line == null){
		break;
	}
}
if(line == null){
	break;
}

}*/


//if (data != null) 
//{
//	responsebody = data;
//}else{
//	responsebody = "No post body!";
//}
