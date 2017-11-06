/**
 * COMP 6461 - Data Communications & Computer Networks
 * LAB ASSIGNMENT - 1
 * @author Rahul Reddy, Fatemah
 * 
 */
public class RequestManager {

	public static void main(String[] argv) {

		CmdParser parser = new CmdParser();
		Post postAgent = new Post();
		Get getAgent = new Get();
		try {
			Request request = parser.parse(argv);
			switch(request.getRequestParameters().getMethod()){
				case "get":
					System.out.println("******** GET *******");
					getAgent.get(request);
					System.out.println("******** GET *******");
					break;
				case "post":
					System.out.println("******** POST *******");
					postAgent.post(request);
					System.out.println("******** POST *******");
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
//String[] getData = { "get","https://httpbin.org/redirect-to?url=http%3A%2F%2Fexample.com%2F" };
//String[] getData = { "get","-v","https://github.com/Rahulreddy94/HackerEarthSolutions"};
//String[] getData = { "get", "-v","-h", "Content-Type:application/json","http://httpbin.org/get?course=networking&assignment=1" };
//String[] postData = { "post", "-v","-h", "Content-Type:application/json", "-d", "{\"username\":\"xyz\",\"password\":\"xyz\"}", "http://posttestserver.com/post.php"};
//String[] data = { "post","-v","-h", "Content-Type:application/json", "-d", "{'Assignment': 1}", "http://httpbin.org/post"};
//String[] data = { "post", "-v","-h", "Content-Type:application/json", "-f", "G:\\workspace\\CNIProject\\dataFile.txt", "https://postman-echo.com/post"};
//String[] data = { "post", "-v","-h", "Content-Type:application/json", "-d", "{key1:\"val1\"}", "https://postman-echo.com/post"};

//post -v -h Content-Type:application/json -d {\"username\":\"xyz\",\"password\":\"xyz\"} "http://posttestserver.com/post.php"
//get -v -h Content-Type:application/json "http://localhost:8080/post.txt"
// post -v -h Content-Type:application/json -d "CONTENT=HELLO JSP !&ONEMORECONTENT =HELLO POST!" "http://localhost:8080/post.txt"
//javac -cp ".;S:\CN\CNIProject\HTTPMSG.jar" RequestManager.java post -v -h Content-Type:application/json -d {\"username\":\"xyz\",\"password\":\"xyz\"} "http://localhost:8080/post.txt"
//java -cp ".;S:\CN\CNIProject\HTTPMSG.jar" RequestManager post -v -h Content-Type:application/json -d {\"username\":\"xyz\",\"password\":\"xyz\"} "http://localhost:8080/post.txt"
// get -v -h Content-Type:application/json http://httpbin.org/get?course=networking&assignment=1
//RequestParameters rp = parser.parse(data);


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