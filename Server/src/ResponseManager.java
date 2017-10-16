import java.util.HashMap;

/**
 * 
 * @author RahulReddy
 *
 */
public class ResponseManager {
	public static void main(String[] args) throws Exception{
		String[] data = {"-v","-p","8080","-d","../"};
		ServerCmdParser parser = new ServerCmdParser();
		HashMap<String, String> input = parser.parse(data);
		System.out.println(Integer.parseInt(input.get("port")));
		Server server = new Server(Integer.parseInt(input.get("port")), Boolean.parseBoolean(input.get("verbose")), input.get("path"));
		server.init();
		server.listen();
		server.serve();
	}
}
