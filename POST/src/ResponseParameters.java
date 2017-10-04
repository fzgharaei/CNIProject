import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author RahulReddy
 *
 */
public class ResponseParameters {
	
	private int status;
	private Map<String,String> responseHeaders;
	private String responseData;
	
	public ResponseParameters() {
		responseHeaders =  new HashMap<String,String>();
		this.responseData = "";
		this.status = 0;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	private void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	private String getResponseData() {
		return responseData;
	}

	private void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	
}
