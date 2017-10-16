package HttpMsg;
import java.util.HashMap;
import java.util.Map;
/**
 * /**
 * This Model class gets and sets all the parameters provided by the HTTP server 
 * @author RahulReddy
 * @since 2/10/2017
 */
public class ResponseParameters {
	
	private String status;
	private Map<String,String> responseHeaders;
	private String responseData;
	
	
	public ResponseParameters() {
		responseHeaders =  new HashMap<String,String>();
		this.responseData = "";
		this.status = "";
		
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String temp) {
		this.status = temp;
	}

	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}
	
	public void addHeaderItem(String key, String val){
		this.responseHeaders.put(key, val);
	}

	public void setResponseHeaders(Map<String, String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	
	public String getResponseHeaderString(){
		String result="";
		for(Map.Entry<String,String> e : this.responseHeaders.entrySet())
			result+=(e.getKey()+": "+e.getValue()+"\r\n");
		return result;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public void appendRespondData(String appendString){
		this.responseData += (appendString+'\n');
	}
	

}
