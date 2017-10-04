import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
/**
 * 
 * @author RahulReddy
 *
 */
public class Post {
	
	public void post(RequestParameters reqParams) throws Exception {
		
		String data = reqParams.getData();
		URL url = new URL(reqParams.getUrl());
		InetAddress address = InetAddress.getByName(url.getHost());
		ResponseParameters response =  new ResponseParameters();
		try {
			Socket socket = new Socket(address, 80);
			// System.out.println(url.getHost());
			// System.out.println(url.getFile());
			// System.out.println(data);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			wr.write("POST " + url.getFile() + " HTTP/1.0\r\n");
			wr.write("Content-Length: " + data.length() + "\r\n");
			wr.write(reqParams.getHeaderString() + "\r\n");
			wr.write("\r\n");
			wr.write(data);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			String[] splittedStatus = {""};
			int i = 0;
			while ((line = rd.readLine()) != null) {
//				if (reqParams.isVerbose()){
//					System.out.println(line);
//					//responseArray[i] = line;
//					i++;
//				}
//				else if (line.isEmpty()) {
//					while ((line = rd.readLine()) != null)
//						System.out.println(line);
//					break;
//				}
				if(line.startsWith("HTTP")){
					String[] temp = line.split(" ");
					response.setStatus(temp[1]);
				}else{
					//Reading The Header Section(After Status)
					if(!line.isEmpty()){
						String[] temp = line.split(": ");
						for(int ii =0; ii<temp.length; ii++)System.out.println(temp[ii]);
						//if(temp.length != 2) throw new Exception("BadSyntax");
						response.addHeaderItem(temp[0], temp[1]);
					}
					else break;
				}
			}
			//Reading the Data section
			while ((line = rd.readLine())!= null){
				response.appendRespondData(line);
			}
			for(int i2=0; i2 < i;i2++){
//				System.out.println(responseArray[i2]);

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
