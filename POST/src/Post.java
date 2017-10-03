import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Post {

	public void post(RequestParameters reqParams) throws Exception {

		String data = reqParams.getData();
		URL url = new URL(reqParams.getUrl());
		InetAddress address = InetAddress.getByName(url.getHost());
		try {
			Socket socket = new Socket(address, 80);
			// System.out.println(url.getHost());
			// System.out.println(url.getFile());
			System.out.println(data);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			wr.write("POST " + url.getFile() + " HTTP/1.0\r\n");
			wr.write("Content-Length: " + data.length() + "\r\n");
			wr.write(reqParams.getHeaderString() + "\r\n");
			wr.write("\r\n");
			wr.write(data);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (reqParams.isVerbose())
					System.out.println(line);
				else if (line.isEmpty()) {
					while ((line = rd.readLine()) != null)
						System.out.println(line);
					break;
				}
			}

			wr.close();
			rd.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

// String data =
// "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
// System.out.println(data);
// Socket socket = new Socket("www.httpbin.org", 80);
// curl -H "Content-Type: application/json" -X POST -d
// '{"username":"xyz","password":"xyz"}' http://posttestserver.com/post.php
