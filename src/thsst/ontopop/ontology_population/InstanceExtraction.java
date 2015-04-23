package thsst.ontopop.ontology_population;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import thsst.ontopop.api.Condition;
import thsst.ontopop.api.Food;
import thsst.ontopop.api.Nutrient;
import thsst.ontopop.validation.model.ArticleModel;
import thsst.ontopop.validation.model.ConditionModel;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.XSD;

public class InstanceExtraction {
	
	private static List<String> Synonym;
	private static List<String> Symptom;
	private static List<String> Deficient;
	private static List<String> Excess;
	private static List<String> Need;
	private static List<String> Avoid;
	private static List<ArticleModel> conditionList;
	
	private  static final String ONTOLOGY_FILE_PATH = "ontology.xml";
    private  static final String ONTOLOGY_FORMAT = "RDF/XML-ABBREV";

    private  static final String SOURCE = "http://www.dlsu.edu.ph/technologies/ontology";
    private  static final String NS = SOURCE + "#";
    
    private static  List<Food> foodList;
    private static  List<String> idList;
    
    private static InstanceExtraction instance;
    
    public static InstanceExtraction getInstance(){
    	if(instance == null){
    		instance = new InstanceExtraction();
    	}
    	
    	return instance;
    }
    
    public InstanceExtraction(){
    	getAllConditions();
    	getAllFood();
    }
    
    public static  OntModel connectToOntology() throws IOException {		
		
		OntModel model;

        // STAGE 1: Read ontology file
        try {
            // Try to read an existing ontology file.
            model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(new FileInputStream(ONTOLOGY_FILE_PATH), null, ONTOLOGY_FORMAT);
        }
        catch (FileNotFoundException ex) {
            // File not found, call createOntology()
            model = createOntology();
        }	

        model.setNsPrefix("obj", NS);
        
        return model;
	}
    
    public static  OntModel createOntology() {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

        // Ontology classes
        OntClass food = model.createClass(NS + "Food");
        OntClass condition = model.createClass(NS + "Condition");
        OntClass nutrient = model.createClass(NS + "Nutrient");
        OntClass symptom = model.createClass(NS + "Symptom");

        // Ontology relationships: Condition
        OntProperty needsFood = model.createObjectProperty(NS + "needsFood");
        OntProperty avoidsFood = model.createObjectProperty(NS + "avoidsFood");
        OntProperty hasExcessNutrient = model.createObjectProperty(NS + "hasExcessNutrient");
        OntProperty hasDeficientNutrient = model.createObjectProperty(NS + "hasDeficientNutrient");
        OntProperty hasSymptom = model.createObjectProperty(NS + "hasSymptom");

        // Ontology properties
        OntProperty name = model.createDatatypeProperty(NS + "name");
        name.addDomain(food);
        name.addDomain(nutrient);
        name.addDomain(condition);
        name.addDomain(symptom);
        name.addRange(XSD.xstring);
        
        OntProperty synonym = model.createDatatypeProperty(NS + "synonym");
        synonym.addDomain(condition);
        synonym.addRange(XSD.xstring);

        FileWriter writer;

        try {
            writer = new FileWriter(ONTOLOGY_FILE_PATH);
            model.write(writer, ONTOLOGY_FORMAT);
            writer.close();
        }
        catch (IOException ignore) {}

        return model;
    }
    
    public void addSynonym(String cname, String synonym){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating condition 
		Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
				
		indiv.addProperty(model.getDatatypeProperty(NS + "synonym"), synonym);
				
		write(model);
    }

    public void addSymptom(String cname, String symptom){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
    	OntClass symptomClass = model.getOntClass(NS + "Symptom");
    	
    	Individual symptomInstance = symptomClass.createIndividual(NS + "symptom/" + symptom.replace(' ', '-'));
    	
    	symptomInstance.addProperty(model.getDatatypeProperty(NS + "name"), symptom);
    	
    	model.add(indiv, model.getObjectProperty(NS + "hasSymptom"), symptomInstance);
    	
    	write(model);
    }
    
    public void addExcessNutrient(String cname, String excess){
    	OntModel model = null;
		try { 
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		OntClass nutrient = model.getOntClass(NS + "Nutrient");
		
		Individual nutrientInstance = nutrient.createIndividual(NS + "excessNutrient/" + excess.replace(' ', '-'));
    	
   	 	nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), excess);
   
   	 	model.add(indiv, model.getObjectProperty(NS + "hasExcessNutrient"), nutrientInstance);
   	 	
   	 	write(model);
    }
    
    public void addDeficientNutrient(String cname, String deficient){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		OntClass nutrientClass = model.getOntClass(NS + "Nutrient");
		
		Individual nutrientInstance = nutrientClass.createIndividual(NS + "deficientNutrient/" + deficient.replace(' ', '-'));
   	 
		nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), deficient);
   
   	 	model.add(indiv, model.getObjectProperty(NS + "hasDeficientNutrient"), nutrientInstance);
   	 	
   	 	write(model);
    }
    
    public void addAvoidFood(String cname, String food){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));

    	for(int y = 0; y < foodList.size(); y++){
    		System.out.println("FOODLIST: " + foodList.get(y).getName());
    		if(foodList.get(y).getName().contains(food)){
    			
    			
    			System.out.println("IDLIST: " + idList.get(y));
    			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
    			
    			model.add(indiv, model.getObjectProperty(NS + "avoidsFood"), foodInstance);
    		}
    	}

            	
            
   	 	
   	 	write(model);
    }
    
    public void addNeedFood(String cname, String food){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
    	for(int y = 0; y < foodList.size(); y++){
    		System.out.println("FOODLIST: " + foodList.get(y).getName());
    		if(foodList.get(y).getName().contains(food)){
    			System.out.println("IDLIST: " + idList.get(y));
    			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
    			model.add(indiv, model.getObjectProperty(NS + "needsFood"), foodInstance);
    		}
    	}
   	 	
   	 	write(model);
    }

    public void removeCondition(String cname){
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	String name = null;
        OntClass conditionclass = model.getOntClass(NS + "Condition");
        ExtendedIterator it = conditionclass.listInstances();
        while(it.hasNext()){
        	OntResource instance = (OntResource)it.next();
        	if(instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString().equalsIgnoreCase(cname)){
       
        		name = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
        		Individual indiv = model.getIndividual(NS + "condition/" + name.replace(' ', '-'));
        		
        		NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasSymptom"));
        		ArrayList<RDFNode> list = null;
        		list = new ArrayList<RDFNode>();
        		while(it2.hasNext()){
        			list.add(it2.nextNode());
        		}
        		for(int i = 0; i < list.size(); i++){
        			Individual symptomInstance = model.getIndividual(list.get(i).toString());
        			System.out.println("HAHAHAL: " + list.get(i).toString());
                   	symptomInstance.remove();
                   	model.remove(indiv, model.getObjectProperty(NS + "hasSymptom"), list.get(i));
        		}
                
                it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasExcessNutrient"));
                list = new ArrayList<RDFNode>();
                while (it2.hasNext()) {
        			list.add(it2.nextNode());
                }
                for(int i = 0; i < list.size(); i++){
                   	 Individual excessInstance = model.getIndividual(list.get(i).toString());
                   	 excessInstance.remove();
                   	 model.remove(indiv, model.getObjectProperty(NS + "hasExcessNutrient"), list.get(i));
                }
                
                it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasDeficientNutrient"));
                list = new ArrayList<RDFNode>();
                while (it2.hasNext()) {
        			list.add(it2.nextNode());
                }
                for(int i = 0; i < list.size(); i++){
                   	 Individual deficientInstance = model.getIndividual(list.get(i).toString());
                   	 deficientInstance.remove();
                   	 model.remove(indiv, model.getObjectProperty(NS + "hasDeficientNutrient"), list.get(i));
                }
                
                /*it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "avoidsFood"));
                list = new ArrayList<RDFNode>();
                while (it2.hasNext()) {
        			list.add(it2.nextNode());
                }
                for(int i = 0; i < list.size(); i++){
                   	 Individual avoidInstance = model.getIndividual(list.get(i).toString());
                   	 avoidInstance.remove();
                   	 model.remove(indiv, model.getObjectProperty(NS + "avoidsFood"), list.get(i));
                }
                
                it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "needsFood"));
               	list = new ArrayList<RDFNode>();
                while (it2.hasNext()) {
        			list.add(it2.nextNode());
                }
                for(int i = 0; i < list.size(); i++){
                   	 Individual needInstance = model.getIndividual(list.get(i).toString());
                   	 needInstance.remove();
                   	 model.remove(indiv, model.getObjectProperty(NS + "needsFood"), list.get(i));
                }*/
                
        		System.out.println("Condition: " +name);
            	indiv.remove();
            	break;
        	}
        }      
        write(model);
    }
    
    public void removeSynonym(String synonym){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OntClass conditionclass = model.getOntClass(NS + "Condition");
		
		ExtendedIterator it1 = conditionclass.listInstances();
  		 while(it1.hasNext()){
 	       OntResource instance = (OntResource)it1.next();
 	       NodeIterator nit =  instance.listPropertyValues(model.getDatatypeProperty(NS + "synonym"));
 	       //System.out.println("Synonym: " +instance.getPropertyValue(model.getDatatypeProperty(NS + "synonym")));
 	       while (nit.hasNext()) {
 	    	   String syn = nit.nextNode().toString();
 	    	   if(syn.equalsIgnoreCase(synonym)){
 	    			nit.remove();
 	    			System.out.println("Synonym: " + syn);
 	    			break;
 	    	   }
 	       }
 	    }
  		 
  		write(model);
    }
    
    public void removeSymptom(String cname, String symptom){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
		 NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasSymptom"));
         while (it2.hasNext()) {
             RDFNode indivSymptom = it2.nextNode();
             String symptomName = indivSymptom.asResource().getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
             
             if(symptomName.equalsIgnoreCase(symptom)){
            	 Individual symptomInstance = model.getIndividual(indivSymptom.toString());
            	 symptomInstance.remove();
            	 //Individual indiv1 = model.getIndividual(NS + "symptom/" + symptom.replace(' ', '-'));
            	 //indiv1.remove();
            	 model.remove(indiv, model.getObjectProperty(NS + "hasSymptom"), indivSymptom);
            	 System.out.println("Symptom: " + symptom);
            	 break;
            	 //it2.remove();
             }
         }
  		write(model);
    }
    
    public void removeExcessNutrient(String cname, String nutrient){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
		NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasExcessNutrient"));
        while (it2.hasNext()) {
            RDFNode indivExcess = it2.nextNode();
            String excessName = indivExcess.asResource().getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
            
            if(excessName.equalsIgnoreCase(nutrient)){
           	 Individual excessInstance = model.getIndividual(indivExcess.toString());
           	 excessInstance.remove();
           	 model.remove(indiv, model.getObjectProperty(NS + "hasExcessNutrient"), indivExcess);
           	 System.out.println("Excess: " + nutrient);
           	 break;
            }
        }
		
		write(model);
    }
    
    public void removeDeficientNutrient(String cname, String nutrient){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
		 NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasDeficientNutrient"));
        while (it2.hasNext()) {
            RDFNode indivDef = it2.nextNode();
            String deficientName = indivDef.asResource().getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
            
            if(deficientName.equalsIgnoreCase(nutrient)){
           	 Individual deficientInstance = model.getIndividual(indivDef.toString());
           	 deficientInstance.remove();
           	 model.remove(indiv, model.getObjectProperty(NS + "hasDeficientNutrient"), indivDef);
           	 System.out.println("Deficient: " + nutrient);
           	 break;
            }
        }
		
		write(model);
    }
    
    public void removeAvoidFood(String cname, String food){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
		 NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "avoidsFood"));
        while (it2.hasNext()) {
            RDFNode indivAvoid = it2.nextNode();
            String avoidName = indivAvoid.asResource().getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
            
            if(avoidName.contains(food)){
           	 Individual avoidInstance = model.getIndividual(indivAvoid.toString());
           	 //avoidInstance.remove();
           	 model.remove(indiv, model.getObjectProperty(NS + "avoidsFood"), avoidInstance);
           	 System.out.println("Avoid Food: " + food);
           	 break;
            }
        }
		
		write(model);
    }

    public void removeNeedFood(String cname, String food){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
		NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "needsFood"));
        while (it2.hasNext()) {
            RDFNode indivNeed = it2.nextNode();
            String needName = indivNeed.asResource().getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
            
            if(needName.contains(food)){
           	 Individual needInstance = model.getIndividual(indivNeed.toString());
           	 //needInstance.remove();
           	 model.remove(indiv, model.getObjectProperty(NS + "needsFood"), needInstance);
           	 System.out.println("Need Food: " + food);
           	 break;
            }
        }
		
		write(model);
    }
    
    public void editConditionName(String cname, String newname){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean isSame = false;
    	String name = null;
        OntClass conditionclass = model.getOntClass(NS + "Condition");
        ExtendedIterator it = conditionclass.listInstances();
        while(it.hasNext()){
        	OntResource instance = (OntResource)it.next();
        	if(instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString().equalsIgnoreCase(cname)){
        		isSame = true;
        		System.out.println("SAME");
        		name = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
        	}
        	System.out.println("Condition: " +name);
        }
        
        if(isSame == true){
        	// get Condition instance
        	ArticleModel cModel = new ArticleModel(null, "");
        	Synonym = new ArrayList<String>();
        	Symptom = new ArrayList<String>();
        	Deficient = new ArrayList<String>();
        	Excess = new ArrayList<String>();
        	Need = new ArrayList<String>();
        	Avoid = new ArrayList<String>();
        	
   		 	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
   		 	
   		 	// get Synonyms
   		 	it = conditionclass.listInstances();
	   		while(it.hasNext()){
	  	       OntResource instance = (OntResource)it.next();
	  	       NodeIterator nit =  instance.listPropertyValues(model.getDatatypeProperty(NS + "synonym"));
	  	       //System.out.println("Synonym: " +instance.getPropertyValue(model.getDatatypeProperty(NS + "synonym")));
	  	       while (nit.hasNext()) {
	  	    	   String syn = nit.nextNode().toString();
	  	    	   Synonym.add(syn);
	  	    	   System.out.println("Synonym: " + syn);
	  	       }
	  	     }
   		 	
   		 	// get Symptoms
            NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasSymptom"));
            while (it2.hasNext()) {
                Resource indivNutrient = it2.nextNode().asResource();
                String symptomName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Symptom.add(symptomName);
                System.out.println("Symptom: " + symptomName);
            }
            
            // get excess nutrients
            NodeIterator it3 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasExcessNutrient"));
            while (it3.hasNext()) {
                Resource indivNutrient = it3.nextNode().asResource();
                String excessNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Excess.add(excessNutrientName);
                System.out.println("Excess Nutrient: " + excessNutrientName);
            }
        	 
            // get deficient nutrients
            NodeIterator it4 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasDeficientNutrient"));
            while (it4.hasNext()) {
                Resource indivNutrient = it4.nextNode().asResource();
                String deficientNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Deficient.add(deficientNutrientName);
                System.out.println("Deficient Nutrient: " + deficientNutrientName);
            }
            
            // get avoids Food
            NodeIterator it5 = indiv.listPropertyValues(model.getObjectProperty(NS + "avoidsFood"));
            while (it5.hasNext()) {
                Resource indivNutrient = it5.nextNode().asResource();
                String avoidsFoodName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Avoid.add(avoidsFoodName);
                System.out.println("Food to Avoid: " + avoidsFoodName);
            }
            
            // get needs Food
            NodeIterator it6 = indiv.listPropertyValues(model.getObjectProperty(NS + "needsFood"));
            while (it6.hasNext()) {
                Resource indivNutrient = it6.nextNode().asResource();
                String needsFoodName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Need.add(needsFoodName);
                System.out.println("Food Needed: " + needsFoodName);
            }
            System.out.println("REMOVE: "+cname);
            
            Individual indiv1 = model.getIndividual(NS + "condition/" + name.replace(' ', '-'));
    		
    		it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasSymptom"));
    		ArrayList<RDFNode> list = null;
    		list = new ArrayList<RDFNode>();
    		while(it2.hasNext()){
    			list.add(it2.nextNode());
    		}
    		for(int i = 0; i < list.size(); i++){
    			Individual symptomInstance = model.getIndividual(list.get(i).toString());
               	symptomInstance.remove();
               	model.remove(indiv, model.getObjectProperty(NS + "hasSymptom"), list.get(i));
    		}
            
            it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasExcessNutrient"));
            list = new ArrayList<RDFNode>();
            while (it2.hasNext()) {
    			list.add(it2.nextNode());
            }
            for(int i = 0; i < list.size(); i++){
               	 Individual excessInstance = model.getIndividual(list.get(i).toString());
               	 excessInstance.remove();
               	 model.remove(indiv, model.getObjectProperty(NS + "hasExcessNutrient"), list.get(i));
            }
            
            it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasDeficientNutrient"));
            list = new ArrayList<RDFNode>();
            while (it2.hasNext()) {
    			list.add(it2.nextNode());
            }
            for(int i = 0; i < list.size(); i++){
               	 Individual deficientInstance = model.getIndividual(list.get(i).toString());
               	 deficientInstance.remove();
               	 model.remove(indiv, model.getObjectProperty(NS + "hasDeficientNutrient"), list.get(i));
            }
            indiv1.remove();
            //removeCondition(cname);
            /*String name1 = null;
            OntClass conditionclass1 = model.getOntClass(NS + "Condition");
            ExtendedIterator itC = conditionclass1.listInstances();
            while(itC.hasNext()){
            	OntResource instance = (OntResource)itC.next();
            	if(instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString().equalsIgnoreCase(cname)){
            		isSame = true;
            		name1 = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
            		Individual indiv1 = model.getIndividual(NS + "condition/" + name1.replace(' ', '-'));
            		System.out.println("Condition: " +name1);
                	indiv1.remove();
                	break;
            	}
            }*/
            
            cModel.setConditionName(newname);
            cModel.setSynonyms(Synonym);
            cModel.setSymptoms(Symptom);
            cModel.setExcessNutrients(Excess);
            cModel.setDeficientNutrients(Deficient);
            cModel.setNeededFood(Need);
            cModel.setAvoidFood(Avoid);
            
            addToOntology(cModel, model);
        }
    }
    
    public void editSynonym(String cname, String synonym, String newSynonym){
    	
		removeSynonym(synonym);
		
		OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// creating condition 
		Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
		indiv.addProperty(model.getDatatypeProperty(NS + "synonym"), newSynonym);
		
		write(model);
    }
    
    public void editSymptom(String cname, String symptom, String newSymptom){
    	
    	removeSymptom(cname, symptom);
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
    	OntClass symptomClass = model.getOntClass(NS + "Symptom");
    	
    	Individual symptomInstance = symptomClass.createIndividual(NS + "symptom/" + newSymptom.replace(' ', '-'));
    	
    	symptomInstance.addProperty(model.getDatatypeProperty(NS + "name"), newSymptom);
    	
    	model.add(indiv, model.getObjectProperty(NS + "hasSymptom"), symptomInstance);
    	
    	write(model);
    }
    
    public void editExcessNutrient(String cname, String excess, String newExcess){
    	
    	removeExcessNutrient(cname, excess);
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		OntClass nutrient = model.getOntClass(NS + "Nutrient");
		
		Individual nutrientInstance = nutrient.createIndividual(NS + "excessNutrient/" + newExcess.replace(' ', '-'));
    	
   	 	nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), newExcess);
   
   	 	model.add(indiv, model.getObjectProperty(NS + "hasExcessNutrient"), nutrientInstance);
   	 	
   	 	write(model);
    }
    
    public void editDeficientNutrient(String cname, String nutrient, String newNutrient){
    	
    	removeDeficientNutrient(cname, nutrient);
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		OntClass nutrientClass = model.getOntClass(NS + "Nutrient");
		
		Individual nutrientInstance = nutrientClass.createIndividual(NS + "deficientNutrient/" + newNutrient.replace(' ', '-'));
   	 
		nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), newNutrient);
   
   	 	model.add(indiv, model.getObjectProperty(NS + "hasDeficientNutrient"), nutrientInstance);
   	 	
   	 	write(model);
    }
    
    public void editAvoidFood(String cname, String food, String newFood){
    	
    	removeAvoidFood(cname, food);
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
    	
    	for(int y = 0; y < foodList.size(); y++){
    		System.out.println("FOODLIST: " + foodList.get(y).getName());
    		if(foodList.get(y).getName().contains(food)){
    			
    			
    			System.out.println("IDLIST: " + idList.get(y));
    			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
    			
    			model.add(indiv, model.getObjectProperty(NS + "avoidsFood"), foodInstance);
    		}
    	}
   	 	
   	 	write(model);
    }
    
    public void editNeedFood(String cname, String food, String newFood){
    	
    	removeNeedFood(cname, food);
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get condition 
    	Individual indiv = model.getIndividual(NS + "condition/" + cname.replace(' ', '-'));
		
    	for(int y = 0; y < foodList.size(); y++){
    		System.out.println("FOODLIST: " + foodList.get(y).getName());
    		if(foodList.get(y).getName().contains(food)){
    			System.out.println("IDLIST: " + idList.get(y));
    			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
    			model.add(indiv, model.getObjectProperty(NS + "needsFood"), foodInstance);
    		}
    	}
   	 	
   	 	write(model);
    }
    
    public static void main(String[] args){
    	InstanceExtraction ie = new InstanceExtraction();
    	
    	/*OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 //getAllConditions();
		 //addSynonym("Sample name1", "add synonym");
		 //addSymptom("Sample name1", "add symptom");
		 //addExcessNutrient("Sample name1", "add excess nutrient");
		 //addDeficientNutrient("Sample name1", "add deficient nutrient");
		 //addAvoidFood("Sample name1", "Apple");
		 //addNeedFood("Sample name", "Apple juice");
		 //removeCondition("HAHAHAHA");
		 //removeSynonym("SaaampleSyn1");
		 //removeSymptom("Sample name",+5 "sample Symptom");
		 //removeExcessNutrient("Sample name", "sample excess nutrient");
		 //removeDeficientNutrient("Sample name", "sample deficient nutrient");
		 //removeAvoidFood("Sample name1", "Beef");
		 //removeNeedFood("Sample name", "Apple");
		 //editConditionName("Sample name", "HAHAHAHA");
		 //editSynonym("Sample name1", "SaaampleSyn", "New sampleSyn1");
		 //editSymptom("Sample name1", "LITTLETHINGS1", "NEW LITTLETHINGS1");
		 //editExcessNutrient("Sample name1", "sample excess nutrient1", "new sample excess nutrient1");
		 //editDeficientNutrient("Sample name1", "sample deficient nutrient1", "new sample deficient nutrient1");
		 //editAvoidFood("Sample name1", "sample food avoid1", "new sample food avoid1");
		 //editNeedFood("Sample name1", "sample food needed1", "new sample food needed1");
		 /*List<String> sampleSynonyms = new ArrayList<String>();
		 List<String> sampleSymptoms = new ArrayList<String>();
		 List<String> sampleExcessNutrient = new ArrayList<String>();
		 List<String> sampleDeficientNutrient = new ArrayList<String>();
		 List<String> sampleFoodNeeded = new ArrayList<String>();
		 List<String> sampleFoodAvoid = new ArrayList<String>();
		 
		 sampleSynonyms.add("sampleSyn");
		 sampleSynonyms.add("SaaampleSyn");
		 sampleSymptoms.add("sample Symptom");
		 sampleSymptoms.add("sample Symptoooom");
		 sampleExcessNutrient.add("sample excess nutrient");
		 sampleDeficientNutrient.add("sample deficient nutrient");
		 sampleFoodNeeded.add("apple juice");
		 sampleFoodAvoid.add("Beef");
		 ArticleModel sample = new ArticleModel(null, "");
		 
		 sample.setConditionName("Sample name");
		 sample.setExcessNutrients(sampleExcessNutrient);
		 sample.setDeficientNutrients(sampleDeficientNutrient);
		 sample.setSynonyms(sampleSynonyms);
		 sample.setSymptoms(sampleSymptoms);
		 sample.setAvoidFood(sampleFoodAvoid);
		 sample.setNeededFood(sampleFoodNeeded);
		 
		 appendToOntology(sample);*/
    }
    
    public static List<ArticleModel> getAllConditions(){
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*List<String> synonyms;
		List<String> symptoms;
		List<Food> neededFood;
		List<String> neededFoodList;
		List<Food> avoidFood;
		List<String> avoidFoodList;
		List<String> deficientNutrients;
		List<String> excessNutrients;
		OntClass conditionclass = model.getOntClass(NS + "Condition");
        ExtendedIterator it = conditionclass.listInstances();
        String cname; 
        while(it.hasNext()){
        	OntResource instance = (OntResource)it.next();
        	cname = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
        	System.out.println("Condition: " +instance.getPropertyValue(model.getDatatypeProperty(NS + "name")));
        	
        	// get Condition instance
            synonyms = new ArrayList<String>();
            symptoms = new ArrayList<String>();
            deficientNutrients = new ArrayList<String>();
            excessNutrients = new ArrayList<String>();
            neededFood = new ArrayList<Food>();
            neededFoodList = new ArrayList<String>();
            avoidFood = new ArrayList<Food>();
            avoidFoodList = new ArrayList<String>();
            conditionList = new ArrayList<ArticleModel>();
            
            Condition condition = new Condition(cname);
            // get Synonyms
            synonyms = condition.getOtherNames();
            // get Symptoms
            symptoms = condition.getSymptoms();
            // get excess nutrients
            excessNutrients = condition.getNutrientExcesses();
            // get deficient nutrients
            deficientNutrients = condition.getNutrientDeficiencies();
            // get avoids Food
            avoidFood = condition.getDiscouragedFoodItems();
            // get needs Food
            neededFood = condition.getRecommendedFoodItems();
            
            ArticleModel aModel = new ArticleModel(null, "");
            aModel.setConditionName(cname);
            aModel.setSynonyms(synonyms);
            aModel.setSymptoms(symptoms);
            aModel.setExcessNutrients(excessNutrients);
            aModel.setDeficientNutrients(deficientNutrients);
            for(int i = 0; i < avoidFood.size(); i++){
            	avoidFoodList.add(avoidFood.get(i).getName());
            }
            aModel.setAvoidFood(avoidFoodList);
            for(int i = 0; i < neededFood.size(); i++){
            	neededFoodList.add(neededFood.get(i).getName());
            }
            aModel.setNeededFood(neededFoodList);
            
            conditionList.add(aModel);
        }
		return conditionList;*/
		
		String name;
		int count = 0;
        OntClass conditionclass = model.getOntClass(NS + "Condition");
        ExtendedIterator it = conditionclass.listInstances();
        conditionList = new ArrayList<ArticleModel>();
        while(it.hasNext()){
        	OntResource ConditionInstance = (OntResource)it.next();
        	count++;
        	name = ConditionInstance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
        	System.out.println("Condition: " +ConditionInstance.getPropertyValue(model.getDatatypeProperty(NS + "name")));
        	// get Condition instance
        	ArticleModel cModel = new ArticleModel(null, "");
        	Synonym = new ArrayList<String>();
        	Symptom = new ArrayList<String>();
        	Deficient = new ArrayList<String>();
        	Excess = new ArrayList<String>();
        	Need = new ArrayList<String>();
        	Avoid = new ArrayList<String>();
        	
   		 	Individual indiv = model.getIndividual(NS + "condition/" + name.replace(' ', '-'));
   		 	
   		 	// get Synonyms
   		 	ExtendedIterator it1 = conditionclass.listInstances();
	   		 while(it1.hasNext()){
	  	       OntResource instance = (OntResource)it1.next();
	  	       NodeIterator nit =  instance.listPropertyValues(model.getDatatypeProperty(NS + "synonym"));
	  	       //System.out.println("Synonym: " +instance.getPropertyValue(model.getDatatypeProperty(NS + "synonym")));
	  	       while (nit.hasNext()) {
	  	    	   String syn = nit.nextNode().toString();
	  	    	   Synonym.add(syn);
	  	    	   System.out.println("Synonym: " + syn);
	  	       }
	  	     }
   		 	
   		 	// get Symptoms
            NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasSymptom"));
            while (it2.hasNext()) {
                Resource indivNutrient = it2.nextNode().asResource();
                String symptomName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Symptom.add(symptomName);
                System.out.println("Symptom: " + symptomName);
            }
            
            // get excess nutrients
            NodeIterator it3 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasExcessNutrient"));
            while (it3.hasNext()) {
                Resource indivNutrient = it3.nextNode().asResource();
                String excessNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Excess.add(excessNutrientName);
                System.out.println("Excess Nutrient: " + excessNutrientName);
            }
        	 
            // get deficient nutrients
            NodeIterator it4 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasDeficientNutrient"));
            while (it4.hasNext()) {
                Resource indivNutrient = it4.nextNode().asResource();
                String deficientNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Deficient.add(deficientNutrientName);
                System.out.println("Deficient Nutrient: " + deficientNutrientName);
            }
            
            // get avoids Food
            NodeIterator it5 = indiv.listPropertyValues(model.getObjectProperty(NS + "avoidsFood"));
            while (it5.hasNext()) {
                Resource indivNutrient = it5.nextNode().asResource();
                String avoidsFoodName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Avoid.add(avoidsFoodName);
                System.out.println("Food to Avoid: " + avoidsFoodName);
            }
            
            // get needs Food
            NodeIterator it6 = indiv.listPropertyValues(model.getObjectProperty(NS + "needsFood"));
            while (it6.hasNext()) {
                Resource indivNutrient = it6.nextNode().asResource();
                String needsFoodName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                Need.add(needsFoodName);
                System.out.println("Food Needed: " + needsFoodName);
            }
            
            cModel.setConditionName(name);
            cModel.setSynonyms(Synonym);
            cModel.setSymptoms(Symptom);
            cModel.setExcessNutrients(Excess);
            cModel.setDeficientNutrients(Deficient);
            cModel.setNeededFood(Need);
            cModel.setAvoidFood(Avoid);
            conditionList.add(cModel);
        }

        return conditionList;
    
	}
    
    public ArticleModel getCondition(String cname){
    	
    	for(int i = 0; i < conditionList.size(); i++){
    		if(conditionList.get(i).getConditionName().equalsIgnoreCase(cname)){
    			return conditionList.get(i);
    		}
    	}
		return null;
    }
    
    public static void appendToOntology(ArticleModel aModel){
    	
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
   
    	boolean isSame = false;
    	String name = null;
        OntClass conditionclass = model.getOntClass(NS + "Condition");
        ExtendedIterator it = conditionclass.listInstances();
        while(it.hasNext()){
        	OntResource instance = (OntResource)it.next();
        	if(instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString().equalsIgnoreCase(aModel.getConditionName())){
        		isSame = true;
        		System.out.println("SAME");
        		name = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
        	}
        	System.out.println("Condition: " +name);
        }
        
        if(isSame == true){
        	// get Condition instance
        	
   		 	Individual indiv = model.getIndividual(NS + "condition/" + name.replace(' ', '-'));
   		 	
   		 	// get Synonyms
   		 	ExtendedIterator it1 = conditionclass.listInstances();
	   		 while(it1.hasNext()){
	  	       OntResource instance = (OntResource)it1.next();
	  	       NodeIterator nit =  instance.listPropertyValues(model.getDatatypeProperty(NS + "synonym"));
	  	       //System.out.println("Synonym: " +instance.getPropertyValue(model.getDatatypeProperty(NS + "synonym")));
	  	       while (nit.hasNext()) {
	  	    	   String syn = nit.nextNode().toString();
	  	    	   for(int i = 0; i < aModel.getSynonyms().size(); i++){
	  	    		   if(syn.equalsIgnoreCase(aModel.getSynonyms().get(i))){
	  	    			   aModel.getSynonyms().remove(i);
	  	    			   break;
	  	    		   }
	  	    	   }
	  	    	   System.out.println("Synonym: " + syn);
	  	       }
	  	     }
   		 	
   		 	// get Symptoms
            NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasSymptom"));
            while (it2.hasNext()) {
                Resource indivNutrient = it2.nextNode().asResource();
                String symptomName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                for(int i = 0; i < aModel.getSymptoms().size(); i++){
                	if(symptomName.equalsIgnoreCase(aModel.getSymptoms().get(i))){
                		aModel.getSymptoms().remove(i);
                		break;
                	}
                }
                System.out.println("Symptom: " + symptomName);
            }
            
            // get excess nutrients
            NodeIterator it3 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasExcessNutrient"));
            while (it3.hasNext()) {
                Resource indivNutrient = it3.nextNode().asResource();
                String excessNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                for(int i = 0; i < aModel.getExcessNutrients().size(); i++){
                	if(excessNutrientName.equalsIgnoreCase(aModel.getExcessNutrients().get(i))){
                		aModel.getExcessNutrients().remove(i);
                	}
                }
                System.out.println("Excess Nutrient: " + excessNutrientName);
            }
        	 
            // get deficient nutrients
            NodeIterator it4 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasDeficientNutrient"));
            while (it4.hasNext()) {
                Resource indivNutrient = it4.nextNode().asResource();
                String deficientNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                for(int i = 0; i < aModel.getDeficientNutrients().size(); i++){
                	if(deficientNutrientName.equalsIgnoreCase(aModel.getDeficientNutrients().get(i))){
                		aModel.getDeficientNutrients().remove(i);
                		break;
                	}
                }
                System.out.println("Deficient Nutrient: " + deficientNutrientName);
            }
            
            // get avoids Food
            NodeIterator it5 = indiv.listPropertyValues(model.getObjectProperty(NS + "avoidsFood"));
            while (it5.hasNext()) {
                Resource indivNutrient = it5.nextNode().asResource();
                String avoidsFoodName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                for(int i = 0; i < aModel.getAvoidFood().size(); i++){
                	if(avoidsFoodName.equalsIgnoreCase(aModel.getAvoidFood().get(i))){
                		aModel.getAvoidFood().remove(i);
                		break;
                	}
                }
                System.out.println("Food to Avoid: " + avoidsFoodName);
            }
            
            // get needs Food
            NodeIterator it6 = indiv.listPropertyValues(model.getObjectProperty(NS + "needsFood"));
            while (it6.hasNext()) {
                Resource indivNutrient = it6.nextNode().asResource();
                String needsFoodName = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
                for(int i = 0; i < aModel.getNeededFood().size(); i++){
                	if(needsFoodName.equalsIgnoreCase(aModel.getNeededFood().get(i))){
                		aModel.getNeededFood().remove(i);
                		break;
                	}
                }
                System.out.println("Food Needed: " + needsFoodName);
            }
            
            OntClass symptom = model.getOntClass(NS + "Symptom");
            OntClass food = model.getOntClass(NS + "Food");
            OntClass nutrient = model.getOntClass(NS + "Nutrient");
            
            // creating synonyms
            for(int i = 0; i < aModel.getSynonyms().size(); i++){
            	indiv.addProperty(model.getDatatypeProperty(NS + "synonym"), aModel.getSynonyms().get(i));
            }
            
            // creating symptoms
            for(int i = 0; i < aModel.getSymptoms().size(); i++){
            	Individual symptomInstance = symptom.createIndividual(NS + "symptom/" + aModel.getSymptoms().get(i).replace(' ', '-'));
            	
            	symptomInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getSymptoms().get(i));
            	
            	model.add(indiv, model.getObjectProperty(NS + "hasSymptom"), symptomInstance);
            }
            
            // creating excess nutrients
            for(int i = 0; i < aModel.getExcessNutrients().size(); i++){
            	 Individual nutrientInstance = nutrient.createIndividual(NS + "excessNutrient/" + aModel.getExcessNutrients().get(i).replace(' ', '-'));
            	 
            	 nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getExcessNutrients().get(i));
            
            	 model.add(indiv, model.getObjectProperty(NS + "hasExcessNutrient"), nutrientInstance);
            }
            
            // creating deficient nutrients
            for(int i = 0; i < aModel.getDeficientNutrients().size(); i++){
            	 Individual nutrientInstance = nutrient.createIndividual(NS + "deficientNutrient/" + aModel.getDeficientNutrients().get(i).replace(' ', '-'));
            	 
            	 nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getDeficientNutrients().get(i));
            
            	 model.add(indiv, model.getObjectProperty(NS + "hasDeficientNutrient"), nutrientInstance);
            }
            
            // creating avoids food
            for(int i = 0; i <aModel.getAvoidFood().size(); i++){
            	for(int y = 0; y < foodList.size(); y++){
            		if(aModel.getAvoidFood().get(i).contains(foodList.get(y).getName())){
            			System.out.println("AMODEL AVOID: " + aModel.getAvoidFood().get(i));
            			System.out.println("FOODLIST: " + foodList.get(y).getName());
            			System.out.println("IDLIST: " + idList.get(y));
            			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
            			
            			model.add(indiv, model.getObjectProperty(NS + "avoidsFood"), foodInstance);
            		}
            	}
            }
            
            // creating needs food
            for(int i = 0; i <aModel.getNeededFood().size(); i++){
            	for(int y = 0; y < foodList.size(); y++){
            		if(aModel.getAvoidFood().get(i).contains(foodList.get(y).getName())){
            			System.out.println("AMODEL NEED: " + aModel.getNeededFood().get(i));
            			System.out.println("FOODLIST: " + foodList.get(y).getName());
            			System.out.println("IDLIST: " + idList.get(y));
            			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
            			
            			model.add(indiv, model.getObjectProperty(NS + "needsFood"), foodInstance);
            		}
            	}
            }
            
            write(model);
            
            
        }
        else {
        	addToOntology(aModel, model);
        }
    }
    
    public static void addToOntology(ArticleModel aModel, OntModel model){
    
		
    	OntClass condition = model.getOntClass(NS + "Condition");
        OntClass symptom = model.getOntClass(NS + "Symptom");
        OntClass food = model.getOntClass(NS + "Food");
        OntClass nutrient = model.getOntClass(NS + "Nutrient");
           
        // creating condition
        Individual conditionInstance = condition.createIndividual(NS + "condition/" + aModel.getConditionName().replace(' ', '-'));
        
        conditionInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getConditionName());
        // creating synonyms
        for(int i = 0; i < aModel.getSynonyms().size(); i++){
        	conditionInstance.addProperty(model.getDatatypeProperty(NS + "synonym"), aModel.getSynonyms().get(i));
        }
        // creating symptoms
        for(int i = 0; i < aModel.getSymptoms().size(); i++){
        	Individual symptomInstance = symptom.createIndividual(NS + "symptom/" + aModel.getSymptoms().get(i).replace(' ', '-'));
        	
        	symptomInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getSymptoms().get(i));
        	
        	model.add(conditionInstance, model.getObjectProperty(NS + "hasSymptom"), symptomInstance);
        }
        
        // creating excess nutrients
        for(int i = 0; i < aModel.getExcessNutrients().size(); i++){
        	 Individual nutrientInstance = nutrient.createIndividual(NS + "excessNutrient/" + aModel.getExcessNutrients().get(i).replace(' ', '-'));
        	
        	 nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getExcessNutrients().get(i));
        
        	 model.add(conditionInstance, model.getObjectProperty(NS + "hasExcessNutrient"), nutrientInstance);
        }
        
        // creating deficient nutrients
        for(int i = 0; i < aModel.getDeficientNutrients().size(); i++){
        	 Individual nutrientInstance = nutrient.createIndividual(NS + "deficientNutrient/" + aModel.getDeficientNutrients().get(i).replace(' ', '-'));
        	 
        	 nutrientInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getDeficientNutrients().get(i));
        
        	 model.add(conditionInstance, model.getObjectProperty(NS + "hasDeficientNutrient"), nutrientInstance);
        }

        // creating avoids food
        for(int i = 0; i <aModel.getAvoidFood().size(); i++){
        	System.out.println("AMODEL AVOID: " + aModel.getAvoidFood().get(i));
        	for(int y = 0; y < foodList.size(); y++){
        		System.out.println("FOODLIST: " + foodList.get(y).getName());
        		if(foodList.get(y).getName().contains(aModel.getAvoidFood().get(i))){
        			
        			
        			System.out.println("IDLIST: " + idList.get(y));
        			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
        			
        			model.add(conditionInstance, model.getObjectProperty(NS + "avoidsFood"), foodInstance);
        		}
        	}
        }
        /*for(int i = 0; i < aModel.getAvoidFood().size(); i++){
        	 Individual foodInstance = food.createIndividual(NS + "avoidsFood/" + aModel.getAvoidFood().get(i).replace(' ', '-'));
        	 
        	 foodInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getAvoidFood().get(i));
        
        	 model.add(conditionInstance, model.getObjectProperty(NS + "avoidsFood"), foodInstance);
        }*/
        
     // creating needs food
        for(int i = 0; i <aModel.getNeededFood().size(); i++){
        	System.out.println("AMODEL NEED: " + aModel.getNeededFood().get(i));
        	for(int y = 0; y < foodList.size(); y++){
        		System.out.println("FOODLIST: " + foodList.get(y).getName());
        		if(foodList.get(y).getName().contains(aModel.getNeededFood().get(i))){
        			
        			
        			System.out.println("IDLIST: " + idList.get(y));
        			Individual foodInstance = model.getIndividual(NS + "food" + idList.get(y));
        			
        			model.add(conditionInstance, model.getObjectProperty(NS + "needsFood"), foodInstance);
        		}
        	}
        }
        /*for(int i = 0; i < aModel.getNeededFood().size(); i++){
        	 Individual foodInstance = food.createIndividual(NS + "needsFood/" + aModel.getNeededFood().get(i).replace(' ', '-'));
        	 
        	 foodInstance.addProperty(model.getDatatypeProperty(NS + "name"), aModel.getNeededFood().get(i));
        
        	 model.add(conditionInstance, model.getObjectProperty(NS + "needsFood"), foodInstance);
        }*/
        
        write(model);
    } 
    
    public void getAllFood(){
    	OntModel model = null;
		try {
			model = connectToOntology();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	OntClass foodClass = model.getOntClass(NS + "Food");
        ExtendedIterator it = foodClass.listInstances();
        foodList = new ArrayList<Food>();
        idList = new ArrayList<String>();
        String fname;
        String foodGroup;
        List<Nutrient> nutrientList;
        String Nname;
        Double Nvalue;
        String Nunit;
        while(it.hasNext()){
        	OntResource instance = (OntResource)it.next();
        	String[] tokens = instance.toString().split("#");
        	String[] id = null;
        	
        	if(tokens[1].charAt(0) == 'f'){

        		id = tokens[1].split("food");
        		System.out.println(id[1]);
        		
        		// get Food
	        	fname = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
	        	foodGroup = instance.getPropertyValue(model.getDatatypeProperty(NS + "group")).toString();
	        	System.out.println("Food name: " + fname);
	        	System.out.println("Food group: " + foodGroup);
	        	
	       		Individual indiv = model.getIndividual(NS + "food" + id[1]);
	       		 		
	       		
	       		// get Nutrients
	            NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasNutrient"));
	            nutrientList = new ArrayList<Nutrient>();
	            while (it2.hasNext()) {
	            	Nutrient nutrient;
	                Resource indivNutrient = it2.nextNode().asResource();
	                Nname = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
	                Nunit = indivNutrient.getProperty(model.getDatatypeProperty(NS + "unit")).getObject().toString();
	                Nvalue = Double.parseDouble(indivNutrient.getProperty(model.getDatatypeProperty(NS + "value")).getObject().toString());
	                nutrient = new Nutrient(Nname, Nvalue, Nunit);
	                nutrientList.add(nutrient);
	                System.out.println("Nutrient name: " + Nname);
	                System.out.println("Nutrient unit: " + Nunit);
	                System.out.println("Nutrient value: " + Nvalue);
	                
	            }
	            Food food = new Food(fname, foodGroup, nutrientList);
                foodList.add(food);
                idList.add(id[1]);
        	}
        }
        System.out.println(foodList.size());
    }
    
    public static void write(OntModel model){
    	FileWriter writer = null;
        try {
            writer = new FileWriter(ONTOLOGY_FILE_PATH);
            model.write(writer, ONTOLOGY_FORMAT);
        }
        catch (IOException ignore) {}
        finally {
            if (writer != null) {
            	
                try {writer.close();} catch (IOException ignore) {}
            }
        }
    }
}