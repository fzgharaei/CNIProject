import java.util.HashMap;
import java.util.Map;

import HttpMsg.*;

public class serverCmdParser {
	HashMap<String, String> headers;	
 
	public HashMap<String,String> parse(String[] args) throws Exception{
 		headers = new HashMap<String, String>();
 		int i = 0;
 		switch (args[i]) {
		case "-v":
				headers.put("verbose", "true");
				i++;
				break;

		case "-p":
				headers.put("port",args[i+1]);
				i+=2;
				break;

		case "-d":
			if(args.length>i){
				headers.put("port",args[i+1]);
				break;
			}else
				throw new Exception("Missing Path Location or Bad Syntax");

		default:
			throw new Exception("BadSyntax");
		}
		
		return headers;
		
	}
}
