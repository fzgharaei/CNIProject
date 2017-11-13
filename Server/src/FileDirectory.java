import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDirectory {
	ArrayList<DFile> files;
	FileDirectory(String path){
		files = new ArrayList<DFile>();
		File dir = new File(path);
		
	}
	public void fileList(File dir){
		String[] subDirpaths = dir.list();
		File[] subFiles = dir.listFiles();
		Pattern p1 = Pattern.compile(".class");
		Pattern p2 = Pattern.compile(".java");
		Pattern p3 = Pattern.compile(".jar");
		
		for(File file :subFiles){
			if(file.isFile()){
				Matcher m1 = p1.matcher(file.getPath());
				Matcher m2 = p2.matcher(file.getPath()); 
				Matcher m3 = p3.matcher(file.getPath());
				DFile temp;
				if(m1.find()||m2.find()||m3.find())
					temp = new DFile(file.getName(),file.getPath(),true);
				else
					temp = new DFile(file.getName(),file.getPath(),false);
				files.add(temp);
			}else if(file.isDirectory()){
				fileList(file);
			}
		}
	}
	boolean fileExist(String path){
		for(DFile df:files){
			if(df.address.equals(path))
				return true;
		}
		return false;
	}
	
	boolean isAccessible(String path){
		for(DFile df:files){
			if(df.address.equals(path) && df.isAccessible())
				return true;
		}
		return false;
	}
	
	ArrayList<String> filesList(){
		ArrayList<String> res = new ArrayList<String>();
		for(DFile f:files){
			if(f.isAccessible())
				res.add(f.address);
		}
		return res;
	}
}

//https://alvinalexander.com/blog/post/java/how-find-string-simple-regex-pattern-matcher