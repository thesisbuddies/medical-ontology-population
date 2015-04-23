package thsst.ontopop.ontology_population;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InstanceExtractor {

    protected static final Logger logger = Logger.getLogger(InstanceExtractor.class.getName());

    private static final String SOURCE = "http://www.eswc2006.org/technologies/ontology";
    private static final String NS = SOURCE + "#";

    private List<String> conditionList;
    private List<String> symptomList;
    private List<String> deficientList;
    private List<String> excessList;
    private List<String> needList;
    private List<String> avoidList;

    private boolean autoCommit;

    public InstanceExtractor() {
        this(false);
    }

    public InstanceExtractor(boolean autoCommit) {
        this.autoCommit = autoCommit;
        this.conditionList = new ArrayList<String>();
        this.symptomList = new ArrayList<String>();
        this.deficientList = new ArrayList<String>();
        this.excessList = new ArrayList<String>();
        this.needList = new ArrayList<String>();
        this.avoidList = new ArrayList<String>();
    }

    public void extract(String pathToXmlFile) {
        File xmlFile = new File(pathToXmlFile);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);

        try {
            // Parse tags from xml file.
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new FileInputStream(xmlFile));

            NodeList nodeList = doc.getElementsByTagName("Entity");
            logger.log(Level.INFO, xmlFile.getName() + " parsed. " +
                    nodeList.getLength() + " entities found.");

            // Classify parsed tags

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                NamedNodeMap attributeMap = currentNode.getAttributes();

                for (int j = 0; j < attributeMap.getLength(); j++) {
                    Attr attribute = (Attr) attributeMap.item(j);
                    String attributeValue = attribute.getValue();

                    //logger.log(Level.INFO, attribute.getNodeName() + ": " + attributeValue);

                    if (attributeValue.equalsIgnoreCase("condition")){
                        conditionList.add(currentNode.getFirstChild().getNodeValue());
                    }
                    else if (attributeValue.equalsIgnoreCase("symptom")){
                        symptomList.add(currentNode.getFirstChild().getNodeValue());
                    }
                    else if (attributeValue.equalsIgnoreCase("deficient")){
                        deficientList.add(currentNode.getFirstChild().getNodeValue());
                    }
                    else if (attributeValue.equalsIgnoreCase("excess")){
                        excessList.add(currentNode.getFirstChild().getNodeValue());
                    }
                    else if (attributeValue.equalsIgnoreCase("need")){
                        needList.add(currentNode.getFirstChild().getNodeValue());
                    }
                    else if (attributeValue.equalsIgnoreCase("avoid")){
                        avoidList.add(currentNode.getFirstChild().getNodeValue());
                    }
                }
            }

            logger.log(Level.INFO, "Nodes classified.");

            //if (autoCommit) this.commit();

        }
        catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void commit(String pathToOntologyFile) {
        final String SOURCE = "http://www.eswc2006.org/technologies/ontology";
        final String NS = SOURCE + "#";

        File ontologyFile = new File(pathToOntologyFile);

        if (!ontologyFile.exists()) {
        	System.out.println("Create new!!");
        	System.exit(0);
            this.createOntology(pathToOntologyFile);
            System.out.println("ontology file created");
            return;
        }

        try {
            OntModel model = ModelFactory.createOntologyModel();
            model.read(new FileInputStream(ontologyFile), null, "TTL");

            FileWriter fw = new FileWriter(ontologyFile);

            //Resource resource = model.getResource("Entity Type: Condition/" + conditionList.get(0));
            Resource resource = model.getResource(NS + "Condition" + conditionList.get(0));
            System.out.println("Resource: " + resource);
            System.out.println("ArrayList: " + conditionList.get(0));

            boolean isSameName = false;

            // Name
            //Property name = model.getProperty("file:///C:/Users/DanielleLladoc/Workspace/OntologyPopulationModule/Name");
            Property name = model.getProperty(NS + "Name");
            StmtIterator iter = resource.listProperties(name);
            while(iter.hasNext()){
                String nametemp = iter.nextStatement().getObject().toString();
                if(nametemp.equals(conditionList.get(0))){
                    isSameName = true;
                }
                System.out.println("Name: " + nametemp);
            }

            if(isSameName) {
                // hasSymptom
                Property symptom = model.getProperty(NS + "hasSymptom");
                //Property symptom = model.getProperty("file:///C:/Users/DanielleLladoc/Workspace/OntologyPopulationModule/hasSymptom");
                StmtIterator iter1 = resource.listProperties(symptom);
                while(iter1.hasNext()){
                    String symptemp = iter1.nextStatement().getObject().toString();
                    for(int i = 0; i < symptomList.size(); i++){
                        if(symptemp.equals(symptomList.get(i))){
                            symptomList.remove(i);
                        }
                    }

                    System.out.println("Symptoms: " + symptemp);
                }

                // hasDeficiency
                Property deficient = model.getProperty(NS + "hasDeficiency");
                //Property deficient = model.getProperty("file:///C:/Users/DanielleLladoc/Workspace/OntologyPopulationModule/hasDeficiency");
                StmtIterator iter2 = resource.listProperties(deficient);
                while(iter2.hasNext()){
                    String deftemp = iter2.nextStatement().getObject().toString();
                    for(int i = 0; i < deficientList.size(); i++){
                        if(deftemp.equals(deficientList.get(i))){
                            deficientList.remove(i);
                        }
                    }
                    System.out.println("Nutrient Deficient: " +deftemp);
                }

                // hasExcess
                Property excess = model.getProperty(NS + "hasExcess");
                //Property excess = model.getProperty("file:///C:/Users/DanielleLladoc/Workspace/OntologyPopulationModule/hasExcess");
                StmtIterator iter3 = resource.listProperties(excess);
                while(iter3.hasNext()){
                    String excesstemp = iter3.nextStatement().getObject().toString();
                    for(int i = 0; i < excessList.size(); i++){
                        if(excesstemp.equals(excessList.get(i))){
                            excessList.remove(i);
                        }
                    }
                    System.out.println("Nutrient Excess: " +excesstemp);
                }

                // needs
                Property needs = model.getProperty(NS + "needs");
                StmtIterator iter4 = resource.listProperties(needs);
                while(iter4.hasNext()){
                    String needtemp = iter4.nextStatement().getObject().toString();
                    for(int i = 0; i < needList.size(); i++){
                        if(needtemp.equals(needList.get(i))){
                            needList.remove(i);
                        }
                    }
                    System.out.println("Food Needed: " +needtemp);
                }

                // avoids
                Property avoids = model.getProperty(NS + "avoids");
                StmtIterator iter5 = resource.listProperties(avoids);
                while(iter5.hasNext()){
                    String avoidtemp = iter5.nextStatement().getObject().toString();
                    for(int i = 0; i < avoidList.size(); i++){
                        if(avoidtemp.equals(avoidList.get(i))){
                            avoidList.remove(i);
                        }
                    }
                    System.out.println("Food to avoid: " +avoidtemp);
                }

                for(int i =0; i < symptomList.size(); i++){
                    System.out.println(symptomList.get(i));
                    resource.addProperty(symptom, symptomList.get(i));
                }
                for(int i =0; i < deficientList.size(); i++){
                    System.out.println(deficientList.get(i));
                    resource.addProperty(deficient, deficientList.get(i));
                }
                for(int i =0; i < excessList.size(); i++){
                    System.out.println(excessList.get(i));
                    resource.addProperty(excess, excessList.get(i));
                }
                for(int i =0; i < needList.size(); i++){
                    System.out.println(needList.get(i));
                    resource.addProperty(needs, needList.get(i));
                }
                for(int i =0; i < avoidList.size(); i++){
                    System.out.println(avoidList.get(i));
                    resource.addProperty(avoids, avoidList.get(i));
                }
                model.write(fw, "Turtle");
            }
            else{
                addToOntology(fw, model, isSameName);
            }


            model.write(System.out, "Turtle");

        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addToOntology(FileWriter fw, OntModel model, boolean isSameName) {
        final String SOURCE = "http://www.eswc2006.org/technologies/ontology";
        final String NS = SOURCE + "#";

        //OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //String NS = "Entity Type: ";
        Resource resourceCondition = null;

        if(isSameName){
            //resourceCondition = model.getResource("Entity Type: Condition/" + conditionList.get(0));
            resourceCondition = model.getResource(NS + "Condition" + conditionList.get(0));
        }
        else{
            //resourceCondition = model.createResource("Entity Type: Condition/"+conditionList.get(0));
            resourceCondition = model.createResource(NS + "Condition" + conditionList.get(0));
        }


        Property name = model.createProperty("Name");
        resourceCondition.addProperty(name, conditionList.get(0));

        Property hasSymptom = model.createProperty("hasSymptom");
        for(int i = 1; i <= symptomList.size(); i++){
            resourceCondition.addProperty(hasSymptom, symptomList.get(i-1));
        }

        Property hasDeficiency = model.createProperty("hasDeficiency");
        for(int i = 1; i <= deficientList.size(); i++){
            resourceCondition.addProperty(hasDeficiency, deficientList.get(i-1));
        }

        Property hasExcess = model.createProperty("hasExcess");
        for(int i = 1; i <= excessList.size(); i++){
            resourceCondition.addProperty(hasExcess, excessList.get(i-1));
        }

        Property needs = model.createProperty("needs");
        for(int i = 1; i <= needList.size(); i++){
            resourceCondition.addProperty(needs, needList.get(i-1));
        }

        Property avoids = model.createProperty("avoids");
        for(int i = 1; i <= avoidList.size(); i++){
            resourceCondition.addProperty(avoids, avoidList.get(i-1));
        }

        model.write(fw, "Turtle");
    }

    private void createOntology(String filename) {
        OntModel model = ModelFactory.createOntologyModel();
        FileWriter fw = null;


        try {
            File file = new File(filename);

            if (file.exists()) {
                logger.log(Level.ERROR, filename + " already exists.");
            }
            else {
                OntClass condition = model.createClass("Condition");
                condition.createIndividual("Scurvy");

                fw = new FileWriter(file);
                model.write(fw, "Turtle");

                logger.log(Level.INFO, "Successfully created ontology file!");
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fw != null) {
                try {
                    fw.close();
                }
                catch (IOException ex) {} // ignore exception
            }
        }


    }
}
