import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Get {
	private RequestParameters parameters;

	public Get(RequestParameters rp) {
		parameters = rp;
	}

	public void get(String url, String headers) throws Exception {

		// Split the host and get part
		// get information related to header types
		// Print verbose
		try {
			InetAddress address = InetAddress.getByName(url);
			Socket s = new Socket(address, 80);
			PrintWriter pw = new PrintWriter(s.getOutputStream());

			pw.println("GET /get?course=networking&assignment=1 HTTP/1.1");
			pw.println("Host: www.httpbin.org");
			pw.println("");
			pw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if(parameters.isVerbose())// handle output
					System.out.println(line);
				else if(line.isEmpty() && !parameters.isVerbose()) {
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
					parameters.verbose = false;
					break;
				}
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
