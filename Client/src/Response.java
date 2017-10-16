import HttpMsg.ResponseParameters;

public class Response {
	
	String redirectUrl;
	String redirectStatus;
	ResponseParameters resParams;
	
	Response(){
		this.redirectStatus = "";
		this.redirectUrl = "";
		this.resParams = new ResponseParameters();
	}
	
	public ResponseParameters getResponseParameters(){
		return this.resParams;
	}
	
	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectStatus() {
		return redirectStatus;
	}

	public void setRedirectStatus(String redirectStatus) {
		this.redirectStatus = redirectStatus;
	}
}
