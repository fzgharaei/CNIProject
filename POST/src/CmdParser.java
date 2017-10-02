
public class CmdParser {
		private RequestParameters reqParams;
		public RequestParameters parse(String[] args) throws Exception{
			int i;
			reqParams = new RequestParameters();
			switch(args[0]){
				case "help":
					if(args.length>1)
						switch(args[1]){
							case "post":
								System.out.println("httpc help post");
								System.out.println();
								System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL");
								System.out.println();
								System.out.println("Post executes a HTTP POST request for a given URL with inline data or from file.");
								System.out.println("\t-v \t\t Prints the detail of the response such as protocol, status, and headers.");
								System.out.println("\t-h \t\t key:value Associates headers to HTTP Request with the format 'key:value'");
								System.out.println("\t-d string \t\t Associates an inline data to the body HTTP POST request.");
								System.out.println("\t-v file \t\t Associates the content of a file to the body HTTP POST");
								System.out.println();
								System.out.println("Either [-d] or [-f] can be used but not both.");
								break;
							case "get":
								System.out.println("httpc help get");
								System.out.println();
								System.out.println("usage: httpc get [-v] [-h key:value] URL");
								System.out.println();
								System.out.println("Get executes a HTTP GET request for a given URL.");
								System.out.println("\t-v \t\t Prints the detail of the response such as protocol, status, and headers.");
								System.out.println("\t-h \t\t key:value Associates headers to HTTP Request with the format 'key:value'");
								break;
							default:
								throw new Exception("BadSyntax");
						}
					else{
						System.out.println("httpc help");
						System.out.println();
						System.out.println("httpc is a curl-like application but supports HTTP protocol only. Usage:");
						System.out.println("\t httpc command [arguments]");
						System.out.println("The commands are:");
						System.out.println("\tget \texecutes a HTTP GET request and prints the response.");
						System.out.println("\tpost \texecutes a HTTP POST request and prints the response.");
						System.out.println("\thelp \tprints this screen.");
						System.out.println();
						System.out.println("Use \"httpc help [command]\" for more information about a command.");
						break;
					}
				case "post":
					i=1;
					while(i<args.length){
						switch(args[i]){
						 case "-v":
							 reqParams.setVerbose(true);
							 i++;
							 break;
						 case "-d":
							 if(args.length > i+1){
								 reqParams.setData(args[i+1]);
								 i+=2;
							 }else
								 throw new Exception("BadSyntax");
							 break;
						 case "-f":
							 if(args.length > i+1){
								 reqParams.setFile(args[i+1]);
								 i+=2;
							 }else
								 throw new Exception("BadSyntax");
							 break;
						 case "-h":
							 if(args.length > i+1){
								 String[] temp = args[i+1].split(":");
							 	 if(temp.length !=2)
							 		throw new Exception("BadSyntax");
							 	 else{
							 		 reqParams.addHeader(temp[0], temp[1]);
							 		 i+=2;
								 }
							 }else
								 throw new Exception("BadSyntax");
							}
								break;
					}
					break;
				case "get":
					i=1;
					while(i<args.length){
						switch(args[i]){
						 case "-v":
							 reqParams.setVerbose(true);
							 i++;
							 break;
						case "-h":
							 if(args.length > i+1){
								 String[] temp = args[i+1].split(":");
							 	 if(temp.length !=2)
							 		throw new Exception("BadSyntax");
							 	 else{
							 		 reqParams.addHeader(temp[0], temp[1]);
							 		 i+=2;
								 }
							 }else
								 throw new Exception("BadSyntax");
						}
						break;
					}
					break;
				default:
					throw new Exception("BadSyntax");
			}
//			System.out.println(args);
			reqParams.setUrl(args[args.length-1]);
			return reqParams;
		}
}
