package HttpMsg;
import java.util.HashMap;
import java.util.Map;
/**
 * This Model class gets and sets all the parameters provided by the Client 
 * @author Fatemah
 * @since 1/10/2017
 */
public class RequestParameters {
	String method;
	Map<String, String> headers;
	String path;
	String data;
	

	public RequestParameters() {
		this.method = "";
		this.data = "";
		headers = new HashMap<String, String>();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHeaderString() {
		String res = "";
		for (Map.Entry<String, String> entry : this.headers.entrySet()) {
			res += entry.getKey() + ":" + entry.getValue() + " \r\n";
		}
		return res;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return this.path;
	}
}
