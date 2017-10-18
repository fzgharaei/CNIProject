import java.net.Socket;
import java.util.Date;
import java.util.Map;

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
	
	public void setHttpStatus(String stat){
		resParams.setStatus(stat);
	}
	
	public Map<String, String> getReqHeaders(){
		return reqParams.getHeaders();
	}
	
	public void setDate(){
		Date now = new Date();
		this.reqParams.addHeader("Date", now.toString());
	}
	
	public void initResponse(){
		this.reqParams.addHeader("","");
		// for content-type
		switch (reqParams.getMethod()){
		case "Get":
		case "get":
			break;
		case "POST":
		case "post":
			break;
		}
		
	}
}
