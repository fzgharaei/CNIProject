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
	String url;
	String data;
	boolean verbose;
	String inputFile;
	String outputFile;

	public RequestParameters() {
		this.method = "";
		this.url = "";
		this.data = "";
		this.inputFile = "";
		this.outputFile = "";
		headers = new HashMap<String, String>();
		verbose = false;
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

	public String getInputFile() {
		return this.inputFile;
	}

	public void setInputFile(String file) {
		this.inputFile = file;
	}

	public String getOutputFile() {
		return this.outputFile;
	}

	public void setOutputFile(String file) {
		this.outputFile = file;
	}

	public boolean inOutput() {
		return !this.outputFile.equals("");
	}
}
