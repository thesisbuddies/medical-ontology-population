package thsst.ontopop.validation;

import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import thsst.ontopop.validation.model.ArticleModel;

public class XMLParser {
	
	public ArticleModel parseXML(Document doc, String path){
		
		ArticleModel article = new ArticleModel(doc, path);
		
		Element rootNode = doc.getDocumentElement();
		String rootURL = rootNode.getAttribute("URL");
		
		NodeList nodeList = doc.getElementsByTagName("Entity");
		
		ArrayList<String> cond = new ArrayList<String>();
		ArrayList<String> synonym = new ArrayList<String>();
		ArrayList<String> symptom = new ArrayList<String>();
		ArrayList<String> nutDef = new ArrayList<String>();
		ArrayList<String> nutEx = new ArrayList<String>();
		ArrayList<String> foodAv = new ArrayList<String>();
		ArrayList<String> foodNe = new ArrayList<String>();
			
		for(int i=0; i<nodeList.getLength(); i++){
			Element node = (Element) nodeList.item(i);
			NamedNodeMap attributes = node.getAttributes();
			int numAttrs = attributes.getLength();
			ArrayList<String> attri = new ArrayList<String>();
			
			for(int y=0; y<numAttrs; y++){
				Attr attr = (Attr) attributes.item(y);
				String attrName = attr.getNodeName();
				String attrValue = attr.getNodeValue();
				attri.add(attrValue);
				System.out.println(attrName + ": " + attrValue);
				
				if(attrValue.equals("Condition")){
					cond.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
				}
				else if(attrValue.equals("Synonyms")){
					synonym.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
				}
				else if(attrValue.equals("Symptom")){
					symptom.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
				}
				else if(attrValue.equals("NutrientDeficient")){
					nutDef.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
				}
				else if(attrValue.equals("NutrientExcess")){
					nutEx.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
				}
				else if(attrValue.equals("Need")){
					foodNe.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
				}
				else if(attrValue.equals("Avoid")){
					foodAv.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
				}				
			}
			
		}
		
		article.setUrl(rootURL);
		article.setConditionName(cond.get(0));
		article.setSynonyms(synonym);
		article.setSymptoms(symptom);
		article.setNeededFood(foodNe);
		article.setAvoidFood(foodAv);
		article.setDeficientNutrients(nutDef);
		article.setExcessNutrients(nutEx);
		
		return article;
	}

}
