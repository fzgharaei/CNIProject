import java.util.HashMap;
/**
 * 
 * @author RahulReddy
 *
 */
public class ResponseManager {
	public static void main(String[] args) throws Exception{
		//Mon, 16 Oct 2017 02:01:32 GMT
		//Sun Oct 15 22:01:54 EDT 2017
		//	16 Oct 2017 02:02:40 GMT

		//String[] data = {"-v","-p","8080","-d","../"};
		ServerCmdParser parser = new ServerCmdParser();
		HashMap<String, String> input = parser.parse(args);
		if(input.containsKey("path"))
			System.setProperty("user.dir", input.get("path"));
		System.out.println("%%%%%%%");
		System.out.println(input.get("path"));
		System.out.println("%%%%%%%");
		
		System.out.println(Integer.parseInt(input.get("port")));
		Server server = new Server(Integer.parseInt(input.get("port")), Boolean.parseBoolean(input.get("verbose")), input.get("path"));
		server.init();
		server.listen();
		server.serve();
	}
}
