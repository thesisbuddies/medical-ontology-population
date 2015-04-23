import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import thsst.ontopop.validation.XMLParser;
import thsst.ontopop.validation.XMLReader;
import thsst.ontopop.validation.model.ArticleModel;


public class ValidationTest {
	public static void main(String[] args){
		String src = "bin/unvalidated articles";
		
		ValidationTest v = new ValidationTest();
		
		XMLReader r = new XMLReader();
		XMLParser p = new XMLParser();
		
		ArrayList<Document> files = r.loadXMLFiles(new File(src));
		ArrayList<String> paths = r.getSrcFiles();
		
		for(int i=0; i<files.size(); i++){
			Document doc = files.get(i);
			v.printArticle(p.parseXML(doc, paths.get(i)));
		}
		
		for(String s: paths){
			String last = s.substring(s.lastIndexOf("\\")+1);
			System.out.println(last);
		}
	}
	
	private void printArticle(ArticleModel a){
		System.out.println("Condotion: "+a.getConditionName()+"\n");
		printArray("Synonyms", a.getSynonyms());
		printArray("Symptoms", a.getSymptoms());
		printArray("Food needed", a.getNeededFood());
		printArray("Food to avoid", a.getAvoidFood());
		printArray("Nutrients needed", a.getDeficientNutrients());
		printArray("Nutrients to avoid", a.getExcessNutrients());
	}
	
	private void printArray(String title, List<String> list){
		System.out.println(title+":");
		for(String s: list){
			System.out.println("\t"+s);
		}
		System.out.println("\n");
	}
}
