import java.net.Socket;

import javax.xml.ws.Response;

import HttpMsg.*;
public class Client {
	Socket socket;
	RequestParameters reqParams;
	ResponseParameters resParams;
	Client(Socket socket){
		this.socket = socket;
	}
	
	public boolean isConnected(){
		return this.socket!=null;
	}
	
	public void disconnect(){
		this.socket = null;
	}
	
	public void initResponse(){
		
	}
}
