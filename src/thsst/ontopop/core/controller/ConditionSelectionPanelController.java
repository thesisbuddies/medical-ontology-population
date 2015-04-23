package thsst.ontopop.core.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.w3c.dom.Document;

import thsst.ontopop.core.view.EditInstancePanel;
import thsst.ontopop.ontology_population.InstanceExtraction;
import thsst.ontopop.validation.model.ArticleModel;
import thsst.ontopop.validation.model.ConditionModel;
import thsst.ontopop.validation.view.ArticleDetailsPanel;
import thsst.ontopop.validation.view.OntologyDetailsPanel;

public class ConditionSelectionPanelController {

	private InstanceExtraction ie;
	
	public ConditionSelectionPanelController(){
		ie = InstanceExtraction.getInstance();
	}
	
	public void deleteCondition(String cName){
		ie.removeCondition(cName);
	}
	
	public void addCondition(ArticleModel newCondition){
		ie.appendToOntology(newCondition);
	}
	
	public List<ArticleModel> getArticles(){
		List<ArticleModel> list = ie.getAllConditions();

		
		System.out.println(list.size());
		return list;
	}
	
	public DefaultTableModel generateTableModel(List<ArticleModel> list){
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
		//OntologyDetailsPanel.getInstance().resetDetailsPanel();
		//ArticleDetailsPanel.getInstance().resetDetailsPanel();
		
		if(article != null){
			EditInstancePanel.getInstance().loadArticleDetails(article);
			
			
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
				
				EditInstancePanel.getInstance().loadArticleDetails(oCond);
			}
		}
	}
}
