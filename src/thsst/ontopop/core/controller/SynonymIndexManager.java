package thsst.ontopop.core.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import thsst.ontopop.api.Condition;
import thsst.ontopop.api.Ontology;
import thsst.ontopop.entity_recognition.ConditionSynonymModel;

public class SynonymIndexManager {
	
	public void createIndexFile(File idxFile) throws ParserConfigurationException, TransformerException{
    	Ontology ont = new Ontology();
    	ont.loadOntology("ontology.xml");
    	List<Condition> conditionList = ont.getAllConditions();
    	
    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("syntable");
		
		doc.appendChild(rootElement);
		
		for(Condition condition: conditionList){
			List<String> otherNames = condition.getOtherNames();
			if(otherNames.size() > 0){
				Element cond = doc.createElement("condition");
				rootElement.appendChild(cond);
				
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(condition.getName()));
				cond.appendChild(name);
				
				for(String syn: otherNames){
					Element synonym = doc.createElement("synonym");
					synonym.appendChild(doc.createTextNode(syn.trim()));
					cond.appendChild(synonym);
				}
			}
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(idxFile);
		
		transformer.transform(source, result);
		
    }
	
	public void appendToIndex(ConditionSynonymModel synModel) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		File idxFile = new File("tmp/synindex.xml");
		
		Document doc = db.parse(idxFile);
		doc.getDocumentElement().normalize();
		
		NodeList condList = doc.getElementsByTagName("condition");
		
		int inList = inConditionList(condList, synModel.getName());
		
		System.out.println(inList);
		
		if(inList > -1){
			for(String syn: synModel.getSynonyms()){
				if(inSynonymList(((Element)condList.item(inList)).getElementsByTagName("synonym"), syn) < 0){
					Element synonym = doc.createElement("synonym");
					synonym.appendChild(doc.createTextNode(syn.trim().toLowerCase()));
					doc.getElementsByTagName("condition").item(inList).appendChild(synonym);
				}
			}
		}
		else{
			Element cond = doc.createElement("condition");
			doc.getDocumentElement().appendChild(cond);
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(synModel.getName().toLowerCase()));
			cond.appendChild(name);
			
			for(String syn: synModel.getSynonyms()){
				Element synonym = doc.createElement("synonym");
				synonym.appendChild(doc.createTextNode(syn.trim().toLowerCase()));
				cond.appendChild(synonym);
			}
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(idxFile);
		
		transformer.transform(source, result);
	}
	
	private int inConditionList(NodeList condList, String condName){
		for(int i=0; i<condList.getLength(); i++){
			Element cond = (Element)condList.item(i);
			Element conditionName = (Element)cond.getElementsByTagName("name").item(0);
			
			if(conditionName.getTextContent().trim().toLowerCase().equals(condName.trim().trim().toLowerCase())){
				return i;
			}	
		}
		
		return -1;
	}
	
	private int inSynonymList(NodeList synList, String synName){
		for(int i=0; i<synList.getLength(); i++){
			Element syn = (Element)synList.item(i);
			
			if(syn.getTextContent().trim().toLowerCase().equals(synName.trim().trim().toLowerCase())){
				return i;
			}	
		}
		
		return -1;
	}
	
	public void removeFromIndex(String condName, String synName) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		File idxFile = new File("tmp/synindex.xml");
		
		Document doc = db.parse(idxFile);
		doc.getDocumentElement().normalize();
		
		NodeList condList = doc.getElementsByTagName("condition");
		
		int inList = inConditionList(condList, condName);
		
		System.out.println(inList);
		
		if(inList > -1){
			NodeList synList = ((Element)condList.item(inList)).getElementsByTagName("synonym");
			for(int i=0; i<synList.getLength(); i++){
				Element syn = (Element)synList.item(i);
				if(syn.getTextContent().trim().toLowerCase().equals(synName.trim().trim().toLowerCase())){
					Node node = synList.item(i);
					node.getParentNode().removeChild(node);
					break;
				}
			}
		}
		
		doc.getDocumentElement().normalize();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(idxFile);
		
		transformer.transform(source, result);
	}
}
