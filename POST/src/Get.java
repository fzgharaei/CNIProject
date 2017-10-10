import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

/**
 * This class Performs the Http GET functionality only through sockets.
 * @author RahulReddy
 * @Reference https://stackoverflow.com/questions/32086560/get-request-with-java-sockets
 * @since 24/09/2017
 */
public class Get {	

	private Redirect redirectObj;
	private ResponseParameters response;
	private URL url;
	

	public void get(RequestParameters requestParams) throws Exception {
		
		if(response == null)
			response = new ResponseParameters();
		
		if(requestParams != null ){
			url = new URL(requestParams.getUrl());
		}
		redirectObj = new Redirect();
		boolean isRedirect = false;
		try {	
			String path = url.getFile();
			String host = url.getHost();
			InetAddress address = InetAddress.getByName(host);
			Socket socket = new Socket(address, 80);
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			System.out.println(path);
			System.out.println(host);

			pw.println("GET " + path +" HTTP/1.1");
			pw.println("Host: " +host);
			pw.println("");
			pw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			
			while ((line = br.readLine()) != null) {
				if(line.startsWith("HTTP")){
					String[] temp = line.split(" ");
					System.out.println("Http Status "+response.getStatus());		
					
					if(temp[1].matches("301|302|304")){
						isRedirect = true;
						response.setRedirectStatus(temp[1]);
					}
					response.setStatus(temp[1]);
				}
				else if(isRedirect){
					System.out.println(line);
					
					if(line.contains("Location:")){
						String[] temp = line.split(": ");
						response.setRedirectUrl(temp[1]);
						requestParams.setUrl(temp[1]);
					}
				}
				else if(requestParams.isVerbose()){
					System.out.println(line);
				}
				else if(line.isEmpty()) {
					System.out.println("Http Data: ");		
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
					requestParams.verbose = false;
					break;
				}	
			}

			br.close();
			socket.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(isRedirect && response!=null && response.getRedirectStatus().matches("301|302|304")){ 
			redirectObj.redirect(requestParams,this);
			}
	}

}
