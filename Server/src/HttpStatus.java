
public enum HttpStatus {
	OK(200,"OK"), 
	NFOUND(404, "Not Found"), 
	NMODIFIED(304, "Not Modified");
	
	private int value;
	private String msg;
	HttpStatus(int stat, String message){
		this.value=stat;
		this.msg = message;
	}
	@Override
	  public String toString() {
		return "HTTP/1.0 "+ this.value + " " + this.msg;
	 }
}
