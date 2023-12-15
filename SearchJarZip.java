/**  This program finds a file from all the jar and zip files within a directory
 **  and its sub-directories.
 **  Author:  Subel Sunbeeb  
 **  Date  :  01/24/2003  
 **/

import java.io.*;
import java.util.*;
import java.util.jar.*;

public class SearchJarZip {

     static String fileName;
   
     static void searchDir(File dir) {

	String[] files = dir.list();;
	String path = dir.getAbsolutePath();

	try{
	   for (int i=0; i<files.length; i++) {   
	     if (files[i] != null) {
		File file = new File (path, files[i]);

		//System.out.println("file/dir: "+path);
		if (file.isDirectory()) 	
			searchDir(file); // recurse

		else if (files[i].endsWith(".jar") || files[i].endsWith(".zip")) 
		  	searchFile(file);
 	     }
	   }
	 }
	 catch(Exception e) {
		System.out.println("Error: "+path+ "; "+ e.getMessage());
	 }
      }


    static void searchFile(File file) {
      try{
     	JarFile jarFile = new JarFile(file);
        Enumeration enums = jarFile.entries();
	while (enums.hasMoreElements()) {
         	JarEntry entry = (JarEntry) enums.nextElement();
		if ((entry.getName().toLowerCase().indexOf(fileName)) > -1)
			System.out.println("  Found in "+file.getAbsolutePath()+ 
			"  name:"+entry.getName()+"  size:"+
			entry.getSize()+"  time:"+entry.getTime());
        }
      }
      catch(Exception e) {
	System.out.println("Error: searchFile(): "+e.getMessage());
      }

	 
     }

    public static void error() {
	System.err.println("Usage: java SearchJarZip <file-name> [<directory-name> (optional)]");
	System.exit(0);
    }	

    public static void main(String[] args) {

	String dirName;

	if (args.length > 0) 		
		fileName = args[0].toLowerCase();
	else
		error();
        
	if (args.length > 1) 	
		dirName = args[1];
	else
		dirName = System.getProperty("user.dir");

	File dir = new File(dirName);

	if (dir.isDirectory())
		searchDir(dir);
	else
		System.out.println("\n"+dirName+ " is not a directory.");
    }           
}