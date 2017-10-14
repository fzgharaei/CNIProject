import HttpMsg.*;
/**
 * This class Performs the HTTP GET Redirect functionality only through sockets.
 * @author RahulReddy
 * @since 08/10/2017
 */
public class Redirect {
	Get get;

	public void redirect(RequestParameters request, Get getObject) {
		System.out.println("********************");
		System.out.println("Redirection should be done to Location:"+ request.getUrl() + "\r\n");
		System.out.println("********************");

		try {
			getObject.get(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
