import gate.util.GateException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import thsst.ontopop.entity_recognition.EntityRecognizer;


public class EntityRecognizerTest {
	
	private final String inputFolder = "/tmp/pos tagged";
	private final String outputFolder = "/tmp/ne tagged";
	private final String processedFolder = "/tmp/ne tagged/_processed";
	
	public static void main(String[] args){
		String srcFolder = "/tmp/pos tagged";
		EntityRecognizer rec = new EntityRecognizer();
		try {
			ArrayList<String> paths = new ArrayList<String>();
			File folder = new File(srcFolder);
			if(folder.isDirectory()){
				for(File fileEntry: folder.listFiles()){
					if(fileEntry.isFile()){
						paths.add(fileEntry.getAbsolutePath());
					}
				}
			}
			else if(folder.isFile()){
				paths.add(folder.getAbsolutePath());
			}
			String name = "";
			 for(int i=0; i<paths.size(); i++){
		        String file = paths.get(i);
		        name = paths.get(i).substring(paths.get(i).lastIndexOf("\\")+1, paths.get(i).lastIndexOf("."));
				rec.tag("file:"+file, name);
			}
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
