import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDirectory {
	ArrayList<DFile> files;
	FileDirectory(String path){
		files = new ArrayList<DFile>();
		File dir = new File(path);
		String[] subDirNames = dir.list();
		File[] subFiles = dir.listFiles();
		for(int i=0;i<subDirNames.length;i++){
			Pattern p1 = Pattern.compile(".class");
			Pattern p2 = Pattern.compile(".java");
			Pattern p3 = Pattern.compile(".jar");
			Matcher m1 = p1.matcher(subDirNames[i]);
			Matcher m2 = p2.matcher(subDirNames[i]); 
			Matcher m3 = p3.matcher(subDirNames[i]);
			DFile temp;
			if(m1.find()||m2.find()||m3.find())
				temp = new DFile(subFiles[i].getName(),subDirNames[i],true);
			else
				temp = new DFile(subFiles[i].getName(),subDirNames[i],false);
			files.add(temp);
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
}

//https://alvinalexander.com/blog/post/java/how-find-string-simple-regex-pattern-matcher