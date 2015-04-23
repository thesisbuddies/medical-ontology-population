package thsst.ontopop.validation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLReader {
	private ArrayList<String> srcFiles;
	
	public ArrayList<Document> loadXMLFiles(File folder){
		
		ArrayList<Document> files = new ArrayList<Document>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
		
			srcFiles = new ArrayList<String>();
			
			if(folder.isDirectory()){
				for(File fileEntry: folder.listFiles()){
					if(fileEntry.isFile()){
						Document doc = db.parse(fileEntry);
						doc.getDocumentElement().normalize();
						files.add(doc);
						srcFiles.add(fileEntry.getAbsolutePath());
					}
				}
			}
			else if(folder.isFile()){
				Document doc = db.parse(folder);
				doc.getDocumentElement().normalize();
				files.add(doc);
				srcFiles.add(folder.getAbsolutePath());
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SAX");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IO");
		}
		
		return files;
		
	}

	public ArrayList<String> getSrcFiles() {
		return srcFiles;
	}

	public void setSrcFiles(ArrayList<String> srcFiles) {
		this.srcFiles = srcFiles;
	}
}
