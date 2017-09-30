import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Post{
  public void post(RequestParameters reqParams) throws Exception {
	//String data = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
	
	String data = "k=1&k=2";
	InetAddress address = InetAddress.getByName(reqParams.getUrl());
	Socket socket = new Socket(address, 80);
    //System.out.println(data);
    
    //Socket socket = new Socket("www.httpbin.org", 80);
    //curl -H "Content-Type: application/json" -X POST -d '{"username":"xyz","password":"xyz"}' http://posttestserver.com/post.php
    
    String path = "/post.php";
    BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
    wr.write("POST " + path + " HTTP/1.0\r\n");
    wr.write("Content-Length: " + data.length() + "\r\n");
    wr.write(reqParams.getHeaderString()+" \r\n");
    wr.write("\r\n");

    wr.write(data);
    wr.flush();

    BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String line;
    while ((line = rd.readLine()) != null) {
      System.out.println(line);
    }
    wr.close();
    rd.close();
    socket.close();
  }
}
