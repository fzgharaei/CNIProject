import java.util.HashMap;
import java.util.Map;

public class RequestParameters {
	String method;
	Map<String,String> headers;
	String url;
	String data;
	boolean verbose;
	String file;
	
	public RequestParameters() {
		// TODO Auto-generated constructor stub
		this.method = "";
		this.url="";
		this.data="";
		this.file="";
		headers = new HashMap<String,String>();
		verbose=false;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
