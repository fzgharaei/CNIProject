import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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
		String response = "";
		if(line!=null){
			String[] headline = line.split(" ");
			if(headline[0].equals("GET")){
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
						FileReader respFile;
						String[] subDirs = mainDir.list();
						for(String s:subDirs){
							String temp = headline[1].substring(1);
							if(s.equals(headline[1].substring(1)))
								// if(restrictions on file access)
								try{
									respFile = new FileReader(mainDir.getPath() + "/" + headline[1].substring(1));
									BufferedReader buff = new BufferedReader(respFile);
									String fileLine;
									while ((fileLine = buff.readLine()) != null) {
										if(fileLine.length()!=0)
											response += (fileLine);
									}
									buff.close();
									respFile.close();
								 }catch(IOException e){
									 throw new Exception("The given file doesn't exist or it's unable to be opened");
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
					File mainDir = new File(this.directory);
					String[] filesList = mainDir.list();
					boolean isFound = false;
					for(String name : filesList){
						if(name.equals(headline[1].substring(1))){
							isFound = true;
							break;
						}
						else
							isFound = false;
					}
					
					// filename is found and name is not a directory
					if(isFound && !(new File(mainDir.getPath() + "/" +headline[1].substring(1)).isDirectory())){
						String data = returnAppendData(reqbuff);
							if (data != null ) {
								Files.write(Paths.get(mainDir.getPath()+headline[1]), data.getBytes(), StandardOpenOption.APPEND);
							}	
						}
						else{
							File newFile = new File(mainDir.getPath() + "/" + headline[1].substring(1));
							FileWriter writer = new FileWriter(newFile);
							BufferedWriter bw = new BufferedWriter(writer);
							//capture The data by reading the buffer after encountering \r\n in the request
							String data = returnAppendData(reqbuff);
							if (data != null) {
								bw.write(data);
							}
							bw.close();
						}
					// write response to socket
					
				}	catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				// write bad syntax on socket and listen again(don't know how yet!)
			}	
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
