package thsst.ontopop.validation.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import thsst.ontopop.core.controller.SynonymIndexManager;
import thsst.ontopop.entity_recognition.ConditionSynonymModel;
import thsst.ontopop.ontology_population.InstanceExtraction;
import thsst.ontopop.validation.XMLParser;
import thsst.ontopop.validation.XMLReader;
import thsst.ontopop.validation.model.ArticleModel;
import thsst.ontopop.validation.model.ConditionModel;
import thsst.ontopop.validation.view.ArticleDetailsPanel;
import thsst.ontopop.validation.view.OntologyDetailsPanel;

public class ArticleListPanelController {
	private final String processedFolder = "tmp/annotated/_processed";
	
	private XMLReader reader;
	private XMLParser parser;
	private InstanceExtraction ie;
	
	public ArticleListPanelController(){
		reader = new XMLReader(); 
		parser = new XMLParser();
		
		ie = new InstanceExtraction();
	}
	
	public ArrayList<ArticleModel> getArticles(String srcFolder){
		ArrayList<ArticleModel> list = new ArrayList<ArticleModel>();
		ArrayList<Document> files = reader.loadXMLFiles(new File(srcFolder));
		ArrayList<String> paths = reader.getSrcFiles();
		
		for(int i=0; i<files.size(); i++){
			Document doc = files.get(i);
			list.add(parser.parseXML(doc, paths.get(i)));
		}
		
		System.out.println(list.size());
		return list;
	}
	
	public DefaultTableModel generateTableModel(ArrayList<ArticleModel> list){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> columns = new Vector<String>(Arrays.asList(new String[]{""}));
	    
		
	    Vector<Object> vector;
	    for(ArticleModel article: list) {
	        vector = new Vector<Object>();
	       
	        vector.add(article);
	     
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columns){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
	}
	
	public void loadArticleDetails(ArticleModel article){
		OntologyDetailsPanel.getInstance().resetDetailsPanel();
		ArticleDetailsPanel.getInstance().resetDetailsPanel();
		
		if(article != null){
			ArticleDetailsPanel.getInstance().loadArticleDetails(article);
			
			
			ConditionModel cm = ie.getCondition(article.getConditionName());
			
			if(cm!=null){
				ArticleModel oCond = new ArticleModel(null, "");
				oCond.setAvoidFood(cm.getAvoidFood());
				oCond.setExcessNutrients(cm.getExcessNutrients());
				oCond.setConditionName(cm.getConditionName());
				oCond.setNeededFood(cm.getNeededFood());
				oCond.setDeficientNutrients(cm.getDeficientNutrients());
				oCond.setSymptoms(cm.getSymptoms());
				oCond.setSynonyms(cm.getSynonyms());
				
				OntologyDetailsPanel.getInstance().loadArticleDetails(oCond);
			}
		}
	}
	
	public void addTonOntology(ArticleModel article) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		ie.appendToOntology(article);
		String name = article.getSrcPath().substring(article.getSrcPath().lastIndexOf("\\")+1, article.getSrcPath().lastIndexOf("."));
		File currFile = new File(article.getSrcPath());
		currFile.renameTo(new File(processedFolder+"/"+name+".xml"));
		
		SynonymIndexManager synManager = new SynonymIndexManager();
		if(article.getSynonyms() != null){
			if(article.getSynonyms().size() > 0){
				ConditionSynonymModel condModel = new ConditionSynonymModel();
				condModel.setName(article.getConditionName());
				condModel.setSynonyms(article.getSynonyms());
				synManager.appendToIndex(condModel);
			}
		}
		//Files.move(article.getSrcPath(), processedFolder+"/"+name+".xml", options)
	}
}
