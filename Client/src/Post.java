import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import HttpMsg.*;
/**
 * This class Performs the Http POST functionality only through sockets.
 * @author Fatemah
 * @since 25/09/2017
 */
public class Post {
	
	public void post(RequestParameters reqParams) throws Exception {
		
		URL url = new URL(reqParams.getUrl());
		InetAddress address = InetAddress.getByName(url.getHost());
		ResponseParameters response =  new ResponseParameters();
		Socket socket;
		try {
			socket = new Socket(address, 80);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			wr.write("POST " + url.getFile() + " HTTP/1.0\r\n");
			wr.write("Content-Length: " + reqParams.getData().length() + "\r\n");
			wr.write(reqParams.getHeaderString() + "\r\n");
			wr.write("\r\n");
			wr.write(reqParams.getData());
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {

				if(line.startsWith("HTTP")){
					String[] temp = line.split(" ");
					response.setStatus(temp[1]);
				}else{
					//Reading The Header Section(After Status)
					if(!line.isEmpty()){
						String[] temp = line.split(": ");
						ArrayList<String> tmp = new ArrayList<String>();
						for(int i =0;i<temp.length;i++)
							tmp.add(temp[i]);
						if(temp.length<2)
							tmp.add("");
						if(tmp.size() != 2) throw new Exception("BadSyntax");
						response.addHeaderItem(tmp.get(0), tmp.get(1));
					}
					else break;
				}
			}
			//Reading the Data section
			while ((line = rd.readLine())!= null){
				response.appendRespondData(line);
			}
			
			if(reqParams.inOutput()){
				try{
					FileWriter outFile = new FileWriter(reqParams.getOutputFile());
					BufferedWriter buff = new BufferedWriter(outFile);
					buff.write(response.getResponseData());
					buff.append('\n');
					buff.close();
					outFile.close();
				}catch(IOException e){
					 throw new Exception("Could not open the file!");
				}
				System.out.println("Data Received is successfully saved in " + reqParams.getOutputFile());
			}else if(reqParams.isVerbose()){
				System.out.println("Http Status "+response.getStatus());
				System.out.println(response.getResponseHeaderString());
				System.out.println();
				System.out.println("Http Data:");
				System.out.println(response.getResponseData());
			}else{
				System.out.println("Http Data:");
				System.out.println(response.getResponseData());
			}
			
			wr.close();
			rd.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


//for(int ii =0; ii<temp.length; ii++)System.out.println(temp[ii]);
//System.out.println("%%%%");

//while ((line = rd.readLine()) != null) {
//System.out.println(line);
//}
//System.out.println(url.getHost());
			// System.out.println(url.getFile());
			// System.out.println(data);
			
//if (reqParams.isVerbose()){
//System.out.println(line);
////responseArray[i] = line;
//i++;
//}
//else if (line.isEmpty()) {
//while ((line = rd.readLine()) != null)
//	System.out.println(line);
//break;
//}
// String data =
// "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
// System.out.println(data);
// Socket socket = new Socket("www.httpbin.org", 80);
// curl -H "Content-Type: application/json" -X POST -d
// '{"username":"xyz","password":"xyz"}' http://posttestserver.com/post.php
