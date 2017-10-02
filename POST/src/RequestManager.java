import java.net.URL;

public class RequestManager {
	Post postAgent;
	Get getAgent;

	public static void main(String[] argv) {

		CmdParser parser = new CmdParser();
		String[] data = { "get", "-v","-h", "Content-Type:application/json","http://httpbin.org/get?course=networking&assignment=1" };
		String getDataHost = "www.httpbin.org";
		String headers = null;
		Get get;

		String url = "http://www.cs.princeton.edu/courses/archive/spr96/cs333/java/tutorial/networking/urls/urlInfo.html";
//		String url = "https://www.google.ca/search?q=parse+url+java&ie=utf-8&oe=utf-8&gws_rd=cr&dcr=0&ei=qYrRWfbODpW-jwPcmbaoCA";
//		String url = "http://www.httpbin.org/get?course=networking$assignment=1";
		try {
			RequestParameters rp = parser.parse(data);
//			rp.setVerbose(true);
			get = new Get(rp);
			URL aURL = new URL(url);
			System.out.println("**********");
			System.out.println("protocol = " + aURL.getProtocol());
	        System.out.println("authority = " + aURL.getAuthority());
	        System.out.println("host = " + aURL.getHost());
	        System.out.println("port = " + aURL.getPort());
	        System.out.println("path = " + aURL.getPath());
	        System.out.println("query = " + aURL.getQuery());
	        System.out.println("filename = " + aURL.getFile());
	        System.out.println("ref = " + aURL.getRef());
			get.get(getDataHost, headers);
			System.out.println("**********");
			System.out.println(rp.getUrl());
			System.out.println("**********");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}