import java.net.URL;

public class RequestManager {
//	Post postAgent;
	Get getAgent;

	public static void main(String[] argv) {

		CmdParser parser = new CmdParser();
		String[] getData = { "get", "-v","-h", "Content-Type:application/json","http://httpbin.org/get?course=networking&assignment=1" };
		String[] postData = { "post", "-v","-h", "Content-Type:application/json", "-d", "{\"username\":\"xyz\",\"password\":\"xyz\"}", "http://posttestserver.com/post.php"};
		
		Get getAgent;
		Post postAgent;
	
		try {
			
			//RequestParameters rp = parser.parse(data);
			getAgent = new Get();
			System.out.println("******** GET *******");
			getAgent.get(parser.parse(getData));
			System.out.println("******** GET *******");
			postAgent = new Post();
			System.out.println("******** POST *******");
			postAgent.post(parser.parse(postData));
			System.out.println("******** POST *******");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}










//String url = "http://www.cs.princeton.edu/courses/archive/spr96/cs333/java/tutorial/networking/urls/urlInfo.html";
//String url = "https://www.google.ca/search?q=parse+url+java&ie=utf-8&oe=utf-8&gws_rd=cr&dcr=0&ei=qYrRWfbODpW-jwPcmbaoCA";
//String url = "http://www.httpbin.org/get?course=networking$assignment=1";

//URL aURL = new URL(url);
//System.out.println("**********");
//System.out.println("protocol = " + aURL.getProtocol());
//System.out.println("authority = " + aURL.getAuthority());
//System.out.println("host = " + aURL.getHost());
//System.out.println("port = " + aURL.getPort());
//System.out.println("path = " + aURL.getPath());
//System.out.println("query = " + aURL.getQuery());
//System.out.println("filename = " + aURL.getFile());
//System.out.println("ref = " + aURL.getRef());

//System.out.println("**********");
//System.out.println(rp.getUrl());
//System.out.println("**********");