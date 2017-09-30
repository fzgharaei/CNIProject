

public class RequestManager{
	Post postAgent;
	Get getAgent;
	public static void main(String[] argv){
		
		CmdParser parser = new CmdParser();
		String[] data = {"get", "http://httpbin.org/get?course=networking&assignment=1"};
		
		try {
			RequestParameters rp = parser.parse(data);
			System.out.println("**********");
			System.out.println(rp.getUrl());
			System.out.println("**********");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
