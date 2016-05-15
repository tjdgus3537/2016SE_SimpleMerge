package controller.fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Donghwan on 5/12/2016.

 */
public class ComparisonFileReader {
	
	StringBuffer content = new StringBuffer();
	
    public StringBuffer readFile(File source) throws IOException{
    	try {
			FileReader fileReader = new FileReader(source);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String a= null;
			
			while((a=reader.readLine())!=null){
				content.append(a);
				content.append("\n");
			}
			content.deleteCharAt(content.length()-1);
		} catch (FileNotFoundException e) {
		}
        return content;
    }
}
