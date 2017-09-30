import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Get {
	public void get (String[] argv) throws Exception {

		try {
			InetAddress address = InetAddress.getByName("www.httpbin.org");
			System.out.println("address is" +address);
			Socket s = new Socket(address, 80);
			PrintWriter pw = new PrintWriter(s.getOutputStream());

			pw.println("GET /get?course=networking&assignment=1 HTTP/1.1");
			pw.println("Host: www.httpbin.org");
			pw.println("");
			pw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			String t;
			while ((t = br.readLine()) != null)
				System.out.println(t);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
