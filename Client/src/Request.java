import java.net.MalformedURLException;
import java.net.URL;

import HttpMsg.RequestParameters;

public class Request {
	
	RequestParameters reqParams;
	boolean verbose;
	String inputFile;
	String outputFile;
	String url;
	
	
	public Request() {
		this.url = "";
		this.inputFile = "";
		this.outputFile = "";
		verbose = false;
		reqParams = new RequestParameters();
	}
	public String getUrl() {
		return url;
	}

	public RequestParameters getRequestParameters(){
		return this.reqParams;
	}
	public void setUrl(String surl) {
		try {
			URL uurl = new URL(surl);
			this.url = surl;
			String path = uurl.getFile();
			this.reqParams.setPath(path);
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
