
public class DFile {
	String fileName;
	String address;
	boolean restricted;
	DFile(String name, String adr, boolean restricted){
		this.fileName = name;
		this.address = adr;
		this.restricted = restricted;
	}
	
	boolean isAccessible(){
		return !restricted;
	}
}
