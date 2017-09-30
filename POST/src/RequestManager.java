public class RequestManager {
	Post postAgent;
	Get getAgent;

	public static void main(String[] argv) {

		CmdParser parser = new CmdParser();
		String[] data = { "get", "http://httpbin.org/get?course=networking&assignment=1" };
		String getDataHost = "www.httpbin.org";
		String headers = null;
		Get get;

		try {
			RequestParameters rp = parser.parse(data);
//			rp.setVerbose(true);
			get = new Get(rp);
			System.out.println("**********");
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