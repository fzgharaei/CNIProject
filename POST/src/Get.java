import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Get {	

	public void get(RequestParameters rp) throws Exception {

		// Split the host and get part
		// get information related to header types
		// Print verbose
		try {
			URL url = new URL(rp.getUrl());
			String path = url.getFile();
			String host = url.getHost();
			InetAddress address = InetAddress.getByName(host);
			//
			Socket socket = new Socket(address, 80);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			System.out.println(path);
			System.out.println(host);

			pw.println("GET " + path +" HTTP/1.1");
			pw.println("Host: " +host);
			pw.println("");
			pw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if(rp.isVerbose())// handle output
					System.out.println(line);
				else if(line.isEmpty() && !rp.isVerbose()) {
					while ((line = br.readLine()) != null) {
						//get headers content type values passed by the user 
						System.out.println(line);
					}
					rp.verbose = false;
					break;
				}
			}
			br.close();
			socket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
