package thsst.ontopop.entity_recognition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SynonymRecognizer {
	
	public List<ConditionSynonymModel> getSynonymList() throws ParserConfigurationException, SAXException, IOException{
		List<ConditionSynonymModel> synList = new ArrayList<ConditionSynonymModel>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		File idxFile = new File("tmp/synindex.xml");
		
		Document doc = db.parse(idxFile);
		doc.getDocumentElement().normalize();
		
		Element root = doc.getDocumentElement();
		NodeList condList = root.getElementsByTagName("condition");
		
		ConditionSynonymModel condModel;
		
		for(int i=0; i<condList.getLength(); i++){
			condModel = new ConditionSynonymModel();
			
			Element cond = (Element) condList.item(i);
			
			Element condName = (Element)cond.getElementsByTagName("name").item(0);
			
			condModel.setName(condName.getTextContent());
			
			NodeList synonymList = cond.getElementsByTagName("synonym");
			for(int y=0; y<synonymList.getLength(); y++){
				Element syn = (Element) synonymList.item(y);
				
				condModel.addSynonym(syn.getTextContent());
			}
			
			synList.add(condModel);
		}
		
		return synList;
	}
	
	public String replaceSynonyms(String article){
		
		try {
			List<ConditionSynonymModel> synList = getSynonymList();
			
			for(ConditionSynonymModel synModel: synList){
				for(String syn: synModel.getSynonyms()){
					article = article.trim().toLowerCase().replaceAll(syn.toLowerCase(), synModel.getName().toLowerCase());
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return article;
	}
	
}
