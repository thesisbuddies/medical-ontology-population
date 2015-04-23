/*package thsst.ontopop.ontology_population;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import thsst.ontopop.validation.model.ArticleModel;
import thsst.ontopop.validation.model.ConditionModel;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;


public class OntologyPopulation {
	
	private static String conditionName;
	private static ArrayList<String> synonyms = new ArrayList<String>();
	private static ArrayList<String> symptoms = new ArrayList<String>();
	private static ArrayList<String> neededFood = new ArrayList<String>();
	private static ArrayList<String> avoidFood = new ArrayList<String>();
	private static ArrayList<String> neededNutrients = new ArrayList<String>();
	private static ArrayList<String> avoidNutrients = new ArrayList<String>();
	private static ArrayList<ConditionModel> conditionList  = new ArrayList<ConditionModel>();
	final String SOURCE = "http://www.eswc2006.org/technologies/ontology";
    final String NS = SOURCE + "#";
    
	public void CreateOntology(){
		
		try {
			File file = new File("ontology.ttl");
			FileWriter fw = new FileWriter(file);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void reinitializeLists(){
		synonyms = new ArrayList<String>();
		symptoms = new ArrayList<String>();
		neededFood = new ArrayList<String>();
		avoidFood = new ArrayList<String>();
		neededNutrients = new ArrayList<String>();
		avoidNutrients = new ArrayList<String>();
		conditionList  = new ArrayList<ConditionModel>();
	}
	
	public void AddToOntology(ArticleModel aModel) throws IOException{
        
		try {
			File ontologyFile = new File("ontology.ttl");
			if(!ontologyFile.exists()){
				CreateOntology();
			}
			OntModel model = connectToOntology();
			FileWriter fw = new FileWriter("ontology.ttl");
			
			int cnt = 1;
			int count = 1;
			boolean end = false;
			boolean isSameName = false;
			Resource resource = null;
			do{
				boolean hasNext = false;
				resource = model.getResource(NS + "Condition " + cnt);
			
				// Name 
				Property name = model.getProperty(NS + "Name");
				StmtIterator iter = resource.listProperties(name);
				
				while(iter.hasNext()){
					hasNext = true;
					String nametemp = iter.nextStatement().getObject().toString();
			
					if(nametemp.equals(aModel.getConditionName())){
						isSameName = true;
						end = true;
					}
					else{
						cnt++;
						count++;
					}
				}

				if(hasNext == false){
					end = true;
				}
			
			}while(end != true);
			
			if(isSameName == true){
				
				// hasSynonym
				Property synonym = model.getProperty(NS + "Synonyms");
				StmtIterator iter = resource.listProperties(synonym);
				while(iter.hasNext()){
					String syntemp = iter.nextStatement().getObject().toString();
					for(int i = 0; i < aModel.getSynonyms().size(); i++){
						if(syntemp.equals(aModel.getSynonyms().get(i))){
							aModel.getSynonyms().remove(i);
						}
					}
				}
				
				// hasSymptom
				Property symptom = model.getProperty(NS + "Symptoms");
				StmtIterator iter1 = resource.listProperties(symptom);
				while(iter1.hasNext()){
					String symptemp = iter1.nextStatement().getObject().toString();
					for(int i = 0; i < aModel.getSymptoms().size(); i++){
						if(symptemp.equals(aModel.getSymptoms().get(i))){
							aModel.getSymptoms().remove(i); 
						}
					}
				}
				
				// hasDeficiency
				Property deficient = model.getProperty(NS + "NutrientDeficient");
				StmtIterator iter2 = resource.listProperties(deficient);
				while(iter2.hasNext()){
					String deftemp = iter2.nextStatement().getObject().toString();
					for(int i = 0; i < aModel.getNeededNutrients().size(); i++){
						if(deftemp.equals(aModel.getNeededNutrients().get(i))){
							aModel.getNeededNutrients().remove(i);
						}
					}	
				}
				
				// hasExcess
				Property excess = model.getProperty(NS + "NutrientExcess");
				StmtIterator iter3 = resource.listProperties(excess);
				while(iter3.hasNext()){
					String excesstemp = iter3.nextStatement().getObject().toString();
					for(int i = 0; i < aModel.getAvoidNutrients().size(); i++){
						if(excesstemp.equals(aModel.getAvoidNutrients())){
							aModel.getAvoidNutrients().remove(i);
						}
					}
				}
				
				// Food needed
				Property needs = model.getProperty(NS + "FoodNeeded");
				StmtIterator iter4 = resource.listProperties(needs);
				while(iter4.hasNext()){
					String needtemp = iter4.nextStatement().getObject().toString();
					for(int i = 0; i < aModel.getNeededFood().size(); i++){
						if(needtemp.equals(aModel.getNeededFood().get(i))){
							aModel.getNeededFood().remove(i);
						}
					}
				}
				
				// Food to avoid
				Property avoids = model.getProperty(NS + "FoodToAvoid");
				StmtIterator iter5 = resource.listProperties(avoids);
				while(iter5.hasNext()){
					String avoidtemp = iter5.nextStatement().getObject().toString();
					for(int i = 0; i < aModel.getAvoidFood().size(); i++){
						if(avoidtemp.equals(aModel.getAvoidFood().get(i))){
							aModel.getAvoidFood().remove(i);
						}
					}
				}
				
				for(int i =0; i < aModel.getSynonyms().size(); i++){
					resource.addProperty(synonym, aModel.getSynonyms().get(i).toString());
				}
				for(int i =0; i < aModel.getSymptoms().size(); i++){
					resource.addProperty(symptom, aModel.getSymptoms().get(i).toString());
				}
				for(int i =0; i < aModel.getNeededNutrients().size(); i++){
					resource.addProperty(deficient, aModel.getNeededNutrients().get(i).toString());
				}
				for(int i =0; i < aModel.getAvoidNutrients().size(); i++){
					resource.addProperty(excess, aModel.getAvoidNutrients().get(i).toString());
				}
				for(int i =0; i < aModel.getNeededFood().size(); i++){
					resource.addProperty(needs, aModel.getNeededFood().get(i).toString());
				}
				for(int i =0; i < aModel.getAvoidFood().size(); i++){
					resource.addProperty(avoids, aModel.getAvoidFood().get(i).toString());
				}
				
				model.write(fw, "Turtle").toString();
			}
			else{
				AddToOntology(fw, model, count, aModel);
			}

				
			model.write(System.out, "Turtle").toString();
			fw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AddToOntology(FileWriter fw, OntModel model, 
			int count, ArticleModel aModel){
	
		Resource resourceCondition = null;
	
		resourceCondition = model.createResource(NS + "Condition " + (count));
		
        Property name = model.createProperty(NS + "Name");
        resourceCondition.addProperty(name, aModel.getConditionName());
        
        Property hasSynonym = model.createProperty(NS + "Synonyms");
        for(int i = 1; i <=  aModel.getSynonyms().size(); i++){
        	resourceCondition.addProperty(hasSynonym,  aModel.getSynonyms().get(i-1).toString());
		}
        
        Property hasSymptom = model.createProperty(NS + "Symptoms");
        for(int i = 1; i <= aModel.getSymptoms().size(); i++){
        	resourceCondition.addProperty(hasSymptom, aModel.getSymptoms().get(i-1).toString());
		}
        
        Property hasDeficiency = model.createProperty(NS + "NutrientDeficient");
        for(int i = 1; i <= aModel.getNeededNutrients().size(); i++){
        	resourceCondition.addProperty(hasDeficiency, aModel.getNeededNutrients().get(i-1).toString());
        	}
        
        Property hasExcess = model.createProperty(NS + "NutrientExcess");
        for(int i = 1; i <= aModel.getAvoidNutrients().size(); i++){
        	resourceCondition.addProperty(hasExcess, aModel.getAvoidNutrients().get(i-1).toString());
		}
        
        Property needs = model.createProperty(NS + "FoodNeeded");
        for(int i = 1; i <= aModel.getNeededFood().size(); i++){
        	resourceCondition.addProperty(needs, aModel.getNeededFood().get(i-1).toString());
		}
        
        Property avoids = model.createProperty(NS + "FoodToAvoid");
        for(int i = 1; i <= aModel.getAvoidFood().size(); i++){
        	resourceCondition.addProperty(avoids, aModel.getAvoidFood().get(i-1).toString());
		}
		
        model.write(fw, "Turtle").toString();
	}
	
	public OntModel connectToOntology(){
		
		try {
			
			OntModel model = ModelFactory.createOntologyModel();
			model.read(new FileInputStream("ontology.ttl"), null, "TTL");
			return model;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
	public void getAllConditions(){
		reinitializeLists();
		
		OntModel model = connectToOntology();
		
		boolean end1 = false;
		int count1 = 1;
		Resource resource = null;
		while(end1 != true){
			synonyms = new ArrayList<String>();
			symptoms = new ArrayList<String>();
			neededFood = new ArrayList<String>();
			avoidFood = new ArrayList<String>();
			neededNutrients = new ArrayList<String>();
			avoidNutrients = new ArrayList<String>();
			
			boolean hasNext1 = false;
			resource = model.getResource(NS + "Condition " + count1);
			Property name1 = model.getProperty(NS + "Name");
			StmtIterator iterName = resource.listProperties(name1);
			while(iterName.hasNext()){
				
				hasNext1= true;
				String nametemp = iterName.nextStatement().getObject().toString();
				conditionName = nametemp;
				count1++;
			}
			
			if(hasNext1 == true){
				ConditionModel cModel = new ConditionModel();
				cModel.setConditionName(conditionName);
				
				// hasSynonym
				Property synonym = model.getProperty(NS + "Synonyms");
				StmtIterator iter = resource.listProperties(synonym);
				while(iter.hasNext()){
					String syntemp = iter.nextStatement().getObject().toString();
					synonyms.add(syntemp);
				}
				
				// hasSymptom
				Property symptom = model.getProperty(NS + "Symptoms");
				StmtIterator iter1 = resource.listProperties(symptom);
				while(iter1.hasNext()){
					String symptemp = iter1.nextStatement().getObject().toString();
					symptoms.add(symptemp);
				}
				
				// hasDeficiency
				Property deficient = model.getProperty(NS + "NutrientDeficient");
				StmtIterator iter2 = resource.listProperties(deficient);
				while(iter2.hasNext()){
					String deftemp = iter2.nextStatement().getObject().toString();
					neededNutrients.add(deftemp);
				}
				
				// hasExcess
				Property excess = model.getProperty(NS + "NutrientExcess");
				StmtIterator iter3 = resource.listProperties(excess);
				while(iter3.hasNext()){
					String excesstemp = iter3.nextStatement().getObject().toString();
					avoidNutrients.add(excesstemp);
				}
				
				// needs
				Property needs = model.getProperty(NS + "FoodNeeded");
				StmtIterator iter4 = resource.listProperties(needs);
				while(iter4.hasNext()){
					String needtemp = iter4.nextStatement().getObject().toString();
					neededFood.add(needtemp);
				}
				
				// avoids
				Property avoids = model.getProperty(NS + "FoodToAvoid");
				StmtIterator iter5 = resource.listProperties(avoids);
				while(iter5.hasNext()){
					String avoidtemp = iter5.nextStatement().getObject().toString();
					avoidFood.add(avoidtemp);
				}
				
				cModel.setSynonyms(synonyms);
				cModel.setSymptoms(symptoms);
				cModel.setNeededNutrients(neededNutrients);
				cModel.setAvoidNutrients(avoidNutrients);
				cModel.setAvoidFood(avoidFood);
				cModel.setNeededFood(neededFood);
				conditionList.add(cModel);
			}
			else{
				end1 = true;
			}
		}
	}
	
	public ConditionModel getCondition(String cName){
		System.out.println(conditionList.size());
		boolean same = false;
		int index = 0;
		
		for(ConditionModel cm : conditionList){
			System.out.println(conditionList.get(index).getConditionName()+" : "+cName);
			if(cm.getConditionName().toLowerCase().equals(cName.toLowerCase())){
				return cm;
			}
		}
		
		while(same != true){
			System.out.println(conditionList.get(index).getConditionName()+" : "+cName);
			if(conditionList.get(index).getConditionName().toLowerCase().equals(cName.toLowerCase())){
				same = true;
				return conditionList.get(index);
			}
			index++;
		}
		return null;
	}
}

*/