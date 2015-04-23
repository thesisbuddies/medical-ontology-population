package thsst.ontopop.core.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import thsst.ontopop.api.Food;
import thsst.ontopop.api.Ontology;
import thsst.ontopop.ontology_population.InstanceExtraction;

public class EditInstancePanelController {
	
	private final String ONTPATH = "ontology.xml";
	
	private InstanceExtraction ie;
	private Ontology ont;
	
	private List<Food> foodList;
	
	public EditInstancePanelController() {
		ie = InstanceExtraction.getInstance();
		ont = new Ontology();
		ont.loadOntology(ONTPATH);
		
		foodList = ont.getAllFood();
	}
	
	public DefaultTableModel generateTableModel(List<String> list){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> columns = new Vector<String>(Arrays.asList(new String[]{""}));
	    
		System.out.println(list.size());
	    Vector<Object> vector;
	    for(String s: list) {
	        vector = new Vector<Object>();
	       
	        vector.add(s);
	     
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columns){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
	}
	
	public void deleteFromOntology(String cName, String iName, int removeFrom){
		if(removeFrom == 0){
			ie.removeAvoidFood(cName, iName);
		}
		else if(removeFrom == 1){
			ie.removeDeficientNutrient(cName, iName);
		}
		else if(removeFrom == 2){
			ie.removeExcessNutrient(cName, iName);
		}
		else if(removeFrom == 3){
			ie.removeNeedFood(cName, iName);
		}
		else if(removeFrom == 4){
			ie.removeSymptom(cName, iName);
		}
		else if(removeFrom == 5){
			ie.removeSynonym(iName);
		}
	}
	
	public void editFromOntology(String cName, String oldName, String newName, int editFrom){
		if(editFrom == 0){
			ie.editAvoidFood(cName, oldName, newName);
		}
		else if(editFrom == 1){
			ie.editDeficientNutrient(cName, oldName, newName);
		}
		else if(editFrom == 2){
			ie.editExcessNutrient(cName, oldName, newName);
		}
		else if(editFrom == 3){
			ie.editNeedFood(cName, oldName, newName);
		}
		else if(editFrom == 4){
			ie.editSymptom(cName, oldName, newName);
		}
		else if(editFrom == 5){
			ie.editSynonym(cName, oldName, newName);
		}
		else if(editFrom == 6){
			ie.editConditionName(cName, newName);
		}
	}
	
	public void addToOntology(String cName, String newName, int addTo){
		if(addTo == 0){
			ie.addAvoidFood(cName, newName);
		}
		else if(addTo == 1){
			ie.addDeficientNutrient(cName, newName);
		}
		else if(addTo == 2){
			ie.addExcessNutrient(cName, newName);
		}
		else if(addTo == 3){
			ie.addNeedFood(cName, newName);
		}
		else if(addTo == 4){
			ie.addSymptom(cName, newName);
		}
		else if(addTo == 5){
			ie.addSynonym(cName, newName);
		}
	}
	
	
	public List<Food> getFoodList(){
		return foodList;
	}
}
