import java.util.HashMap;

/**
 * Parses the Data from the Command prompt of the server
 * @author RahulReddy
 *
 */
public class ServerCmdParser {
	HashMap<String, String> headers;	
 
	public HashMap<String,String> parse(String[] args) throws Exception{
 		headers = new HashMap<String, String>();
 		int i = 0;
 		while(i < args.length){
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
				headers.put("path",args[i+1]);
				i+=2;
				break;
			}else
				throw new Exception("Missing Path Location or Bad Syntax");

		default:
			throw new Exception("BadSyntax");
		}
 		
 	}
		return headers;
		
	}
}
