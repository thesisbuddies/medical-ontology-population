package thsst.annotation;

import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.LanguageAnalyser;
import gate.corpora.RepositioningInfo;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;
import gate.util.Out;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import thsst.ontopop.core.view.ProgressBarPanel;
import thsst.ontopop.entity_recognition.SortedAnnotationList;
import thsst.ontopop.entity_recognition.TokenModel;

public class Annotation {
	
	private final String inputFolder = "tmp/ne tagged";
	private final String outputFolder = "tmp/annotated";
	private final String processedFolder = "tmp/ne tagged/_processed";
	
	public static void main(String[] args){
		Annotation a = new Annotation();
		a.startProcess();
	}
	
	public void startProcess(){
		System.out.println("1");
		try {
			ArrayList<String> paths = new ArrayList<String>();
			File folder = new File(inputFolder);
			if(folder.isDirectory()){
				for(File fileEntry: folder.listFiles()){
					if(fileEntry.isFile()){
						paths.add(fileEntry.getAbsolutePath());
					}
				}
			}
			else if(folder.isFile()){
				paths.add(folder.getAbsolutePath());
			}
			String name = "";
			
			ProgressBarPanel progressPanel = ProgressBarPanel.getInstance();
	        progressPanel.updateRecognizer("0/"+paths.size()+" processed", false);
	        progressPanel.revalidate();
	        progressPanel.repaint();
			
			 for(int i=0; i<paths.size(); i++){
		        String file = paths.get(i);
		        name = paths.get(i).substring(paths.get(i).lastIndexOf("\\")+1, paths.get(i).lastIndexOf("."));
		        System.out.println("2");
				annotateXML("file:"+file, name);
				File currFile = new File(file);
				currFile.renameTo(new File(processedFolder+"/"+name+".xml"));
				
				progressPanel.updateRecognizer((i+1)+"/"+paths.size()+" processed", (i+1)==paths.size());
		        progressPanel.revalidate();
		        progressPanel.repaint();
			}
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Run from the command-line, with a list of URLs as argument.
	 * <P>
	 * <B>NOTE:</B><BR>
	 * This code will run with all the documents in memory - if you want to
	 * unload each from memory after use, add code to store the corpus in a
	 * DataStore.
	 */
	public void annotateXML(String src, String outputName) throws GateException, IOException {
	
	BasicConfigurator.configure(); 
	// initialise the GATE library
	// load ANNIE plugin - you must do this before you can create tokeniser
	// or JAPE transducer resources.
	Gate.getCreoleRegister().registerDirectories(
	new File(Gate.getPluginsHome(), "ANNIE").toURI().toURL());
	
	//Annotation annie = new Annotation();
	//annie.initAnnie();

	 // Build the pipeline
	  SerialAnalyserController pipeline =
	 (SerialAnalyserController)Factory.createResource(
	   "gate.creole.SerialAnalyserController");
	  LanguageAnalyser tokeniser = (LanguageAnalyser)Factory.createResource(
	  "gate.creole.tokeniser.DefaultTokeniser");
	  
	LanguageAnalyser newLine = (LanguageAnalyser)Factory.createResource(
	 "gate.creole.Transducer", gate.Utils.featureMap(
	     "grammarURL", new     
	 File("jape/newLine.jape").toURI().toURL(),
	   "encoding", "UTF-8")); // ensure this matches the file
	LanguageAnalyser symptom = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/symptom.jape").toURI().toURL(),
			   "encoding", "UTF-8")); // ensure this matches the file
	LanguageAnalyser synonym = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/synonym.jape").toURI().toURL(),
			   "encoding", "UTF-8")); 
	LanguageAnalyser foodAvoid = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/foodavoid.jape").toURI().toURL(),
			   "encoding", "UTF-8"));
	LanguageAnalyser foodNeed = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/foodneeded.jape").toURI().toURL(),
			   "encoding", "UTF-8"));
	LanguageAnalyser nutrientExcess = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/nutrientExcess.jape").toURI().toURL(),
			   "encoding", "UTF-8"));
	LanguageAnalyser nurtrientDef = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/nutrientdeficientphase.jape").toURI().toURL(),
			   "encoding", "UTF-8"));
	
	LanguageAnalyser symptom1 = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/symptom1.jape").toURI().toURL(),
			   "encoding", "UTF-8")); // ensure this matches the file
	LanguageAnalyser symptom2 = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/symptom2.jape").toURI().toURL(),
			   "encoding", "UTF-8")); // ensure this matches the file
	LanguageAnalyser symptom3 = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/symptom3.jape").toURI().toURL(),
			   "encoding", "UTF-8")); // ensure this matches the file
	LanguageAnalyser symptom4 = (LanguageAnalyser)Factory.createResource(
			 "gate.creole.Transducer", gate.Utils.featureMap(
			     "grammarURL", new     
	File("jape/symptom4.jape").toURI().toURL(),
			   "encoding", "UTF-8")); // ensure this matches the file
	
	pipeline.add(tokeniser);
	pipeline.add(newLine);
	pipeline.add(symptom);
	pipeline.add(symptom1);
	pipeline.add(symptom2);
	pipeline.add(symptom3);
	pipeline.add(symptom4);
	pipeline.add(synonym);
	pipeline.add(foodAvoid);
	pipeline.add(foodNeed);
	pipeline.add(nurtrientDef);
	pipeline.add(nutrientExcess);

	// create document and corpus
	// create a GATE corpus and add a document for each command-line
	// argument
	Corpus corpus = Factory.newCorpus("JAPE corpus");

	 URL u = new URL(src);
	 FeatureMap params = Factory.newFeatureMap();
	 params.put("sourceUrl", u);
	 params.put("preserveOriginalContent", new Boolean(true));
	 params.put("collectRepositioningInfo", new Boolean(true));
	 Out.prln("Creating doc for " + u);
	 Document doc = (Document)
	   Factory.createResource("gate.corpora.DocumentImpl", params);
	 corpus.add(doc);
	 pipeline.setCorpus(corpus);

	// run it
	pipeline.execute();

	// extract results
	/*System.out.println("Found annotations of the following types: " +
	  doc.getAnnotations().getAllTypes());*/

	Document doc1 = (Document) corpus.get(0);
    AnnotationSet defaultAnnotSet = doc1.getAnnotations();
    //defaultAnnotSet.get
    Set annotTypesRequired = new HashSet();
    annotTypesRequired.add("Condition");
    annotTypesRequired.add("Nutrient");
    annotTypesRequired.add("Food");
    annotTypesRequired.add("Symptom");
    annotTypesRequired.add("Synonyms");
    annotTypesRequired.add("NutrientDeficient");
    annotTypesRequired.add("NutrientExcess");
    annotTypesRequired.add("Need");
    annotTypesRequired.add("Avoid");
    
    //Print all annotation types
    for(String s: defaultAnnotSet.getAllTypes()){
  	  System.out.println(s);
    }
    
    //System.exit(0);
    
    Set<gate.Annotation> peopleAndPlaces = new HashSet<gate.Annotation>(defaultAnnotSet.get(annotTypesRequired));

    FeatureMap features = doc.getFeatures();
    String originalContent = (String)features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
    
    RepositioningInfo info = (RepositioningInfo)features.get(GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME);
    // for each document, get an XML document with the
    // person and location names added
    
    Iterator it = peopleAndPlaces.iterator();
    gate.Annotation currAnnot;
    SortedAnnotationList sortedAnnotations = new SortedAnnotationList();

    while(it.hasNext()) {
      currAnnot = (gate.Annotation) it.next();
      sortedAnnotations.addSortedExclusive(currAnnot);
    } // while
    
    for(Object ann: sortedAnnotations){
    	gate.Annotation an = (gate.Annotation)ann;
    	//System.out.println(an.getType());
    }
    if(originalContent != null && info != null) {
    	ArrayList<ArrayList<TokenModel>> finalTokens = stringToTokenList(originalContent, sortedAnnotations, info);

    	/*for(TokenModel m: finalTokens){
    		System.out.println(m.getWord() + ", (" + m.getTag() + ")");
    	}*/
    	String url = originalContent.substring(originalContent.indexOf("=")+2, originalContent.indexOf(">")-2);
    	
    	System.out.println(finalTokens.size());
    	String finalString = "<article URL=\""+url+"\">\n";
    	for(ArrayList<TokenModel> lineModels: finalTokens){
	    	for(TokenModel m: lineModels){
	    		if(m.getTag().trim().toLowerCase().equals("entity")){
	    			finalString += m.toString()+"\n";
	    		}
	    	}
    	}
    	finalString += "</article>";
    	
        String fileName = new String(outputFolder+"/"+outputName+".xml");
        FileWriter writer = new FileWriter(fileName);
        writer.write(finalString);
        writer.close();
    }
    
	}
	
	private ArrayList<ArrayList<TokenModel>> stringToTokenList(String originalContent, SortedAnnotationList sortedAnnotations, RepositioningInfo info){
		  ArrayList<ArrayList<TokenModel>> tokenList = new  ArrayList<ArrayList<TokenModel>>();
		  //originalContent = originalContent.substring(7, originalContent.length()-7);
		  
		  //System.out.println(originalContent);
		  
		  //String body = (originalContent.replace("<article>", ""));
		  String body = originalContent.substring(originalContent.indexOf(">")+1, originalContent.lastIndexOf("<"));
		  //body = (body.replace("</article>", "")).trim();
		  String[] lines = body.split("\n");	
		  //String[] tokens = originalContent.split("\\s+");	
		  
		  ArrayList<ArrayList<String>> newTokens = new ArrayList<ArrayList<String>>();
		  for(int i=0; i<lines.length; i++){
			  if(lines[i].trim().length() > 0){
				  ArrayList<String> lineTokens = new ArrayList<String>();
				  String[] tokens = lines[i].trim().split(" <");
				  for(int y=0; y<tokens.length; y++){
					  lineTokens.add(tokens[y].trim());
				  }
				  newTokens.add(lineTokens);
			  }
		  }

		  long currStart = 1;
		  
		  TokenModel tokenModel;
		  int qwe = 0;
		  for(ArrayList<String> lineTokens: newTokens){
			  //System.out.println(qwe + " : " + newTokens.size());
			  ArrayList<TokenModel> lineModels = new ArrayList<TokenModel>();
			  for(String token: lineTokens){
				  if(token.charAt(0) != '<'){
					  token = "<"+token;
				  }
				  tokenModel = new TokenModel();
				  //System.out.println((int)currStart+1 +", "+ token.indexOf(">"));
				  System.out.println(token);
				  String tag = token.substring(1, token.indexOf(">"));
				  String[] tagSplit = tag.split(" ");
				  tokenModel.setTag(tagSplit[0].trim());
				  Hashtable<String, String> features = new Hashtable<String, String>();
				  for(int count = 1; count<tagSplit.length; count++){
					  String currFeat = tagSplit[count];
					  if(currFeat.trim().length() > 1){
						  String[] keyValue = currFeat.split("=");
						  features.put(keyValue[0].trim(), keyValue[1].trim().replace("\"", ""));
					  }
				  }
				  tokenModel.setAttributes(features);
				  tokenModel.setStart(currStart);
				  String word = token.substring(token.indexOf(">")+1, token.lastIndexOf("<"));
				  tokenModel.setWord(word);
				  tokenModel.setEnd(tokenModel.getStart()+word.length());
				  
				  currStart = tokenModel.getEnd()+1;
				  
				  lineModels.add(tokenModel);
			  }
			  tokenList.add(lineModels);
			  currStart += 1;
			  qwe++;
		  }
		  
		  return addNewTags(tokenList, sortedAnnotations, info, originalContent);
	  } 
	  
	  private ArrayList<ArrayList<TokenModel>> adjustPosition(ArrayList<ArrayList<TokenModel>> tokens){
		  //System.out.println("ADJUSTING!!!!!!!!!!!!!!!!!");
		  
		  ArrayList<ArrayList<TokenModel>> newTokens = new ArrayList<ArrayList<TokenModel>>();
		  ArrayList<TokenModel> newList;
		  
		  int currCount = 1;
		  
		  for(ArrayList<TokenModel> tokenList : tokens){
			  newList = new ArrayList<TokenModel>();
			  for(TokenModel token: tokenList){
				  token.setStart(currCount);
				  currCount += token.getWord().length()-1;
				  token.setEnd(currCount);
				  
				  currCount += 2;
				  
				  newList.add(token);
			  }
			  newTokens.add(newList);
		  }
		  //System.out.println(tokenList.get(0).getStart() + "WHAAAT!!!!!!!!!!!!!!!!11");
		  
		  for(ArrayList<TokenModel> listM: newTokens){
			  for(TokenModel m: listM){
				  System.out.println(m.getWord());
				  System.out.println(m.getStart()+" : "+m.getEnd());
			  }
		  }
		  
		  //System.exit(0);
		  
		  return newTokens;
	  } 
	  
	  private ArrayList<ArrayList<TokenModel>> addNewTags(ArrayList<ArrayList<TokenModel>> tokens, SortedAnnotationList sortedAnnotations, RepositioningInfo info, String originalContent){
		  gate.Annotation currAnnot;
		  TokenModel currToken;
		  long insertPositionStart;
	      long insertPositionEnd;
		  
		  int y = 0;
		  
		  boolean adjusted = false;
		  boolean found = false;
		  
		  for(int i=0; i<sortedAnnotations.size(); i++){
			  currAnnot = (gate.Annotation)sortedAnnotations.get(i);
			  found = false;
			  //System.out.println(currAnnot.getType());
			  for(; y<tokens.size(); y++){
				  ArrayList<TokenModel> lineModels = tokens.get(y);
				  for(int j=0; j<lineModels.size(); j++){
					  currToken = lineModels.get(j);
					  insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
					  insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
					  
					  //System.out.println("pre: "+insertPositionStart +" "+ insertPositionEnd);
					  //System.out.println("token: "+currToken.getStart() +" "+ currToken.getEnd());
					  
			          insertPositionStart = info.getOriginalPos(insertPositionStart);
			          insertPositionEnd = info.getOriginalPos(insertPositionEnd, true);
			          
			          //System.out.println(insertPositionStart +" "+ insertPositionEnd);
			          if(insertPositionEnd != -1 && insertPositionStart != -1){
			        	  //System.out.println("Exist!");
			        	  insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
						  insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
			          }
			          else{
			        	  //System.out.println("Does not Exist");
			        	  if(!adjusted){
			        		  tokens = adjustPosition(tokens);
			        		  currToken = tokens.get(y).get(j);
			        		  adjusted = !adjusted;
			        	  }
			        	  insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
						  insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
						  //System.out.println(insertPositionStart +" : "+ insertPositionEnd);
			          }
			          
			          System.out.println(currAnnot.getType()+" : "+insertPositionStart +" : "+ insertPositionEnd);
			          System.out.println(currToken.getStart() +" : "+ currToken.getEnd());
			          System.out.println(currToken.toString());
			          System.out.println();
			          //System.out.println(currToken.getStart());
			          //System.out.println(currAnnot.getType());
			          //System.out.println(currToken.toString());
			        
			          if(insertPositionStart == currToken.getStart()){
						  TokenModel tokenToTag = currToken;
						  lineModels.remove(j);
						  while(insertPositionEnd > currToken.getEnd() && j<lineModels.size()){
							  tokenToTag = mergeTokens(tokenToTag, lineModels.get(j));
							  currToken = lineModels.get(j);
							  lineModels.remove(j);
							  if(j >= lineModels.size()){
								  y++;
								  lineModels = tokens.get(y);
								  j = 0;
							  }
						  }
						  tokenToTag.setTag("Entity");
						  tokenToTag.getAttributes().put("Type", currAnnot.getType());
						  lineModels.add(j, tokenToTag);
						  found = true;
						  break;
					  }
			         
				  }
				  if(found){
		        	  break;
		          }
			  }
		  }
		  
		  return tokens;
	  }
	  
	  private TokenModel mergeTokens(TokenModel currToken, TokenModel nextToken){
		  TokenModel mergedToken = new TokenModel();
		  
		  mergedToken.setWord(currToken.getWord() + " " + nextToken.getWord());
		  mergedToken.setStart(currToken.getStart());
		  mergedToken.setEnd(nextToken.getEnd());
		  mergedToken.setTag("");
		  
		  return mergedToken;
	  }
	
}


