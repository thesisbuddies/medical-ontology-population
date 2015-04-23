package thsst.ontopop.entity_recognition;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.corpora.RepositioningInfo;
import gate.corpora.RepositioningInfo.PositionInfo;
import gate.util.GateException;
import gate.util.Out;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.xml.sax.SAXException;

import thsst.ontopop.core.controller.SynonymIndexManager;
import thsst.ontopop.core.view.ProgressBarPanel;
import thsst.ontopop.entity_recognition.TokenModel;

public class EntityRecognizer  {

  private CorpusController annieController;

  private final String inputFolder = "tmp/pos tagged";
  private final String outputFolder = "tmp/ne tagged";
  private final String processedFolder = "tmp/pos tagged/_processed";
  
  public static void main(String[] args){
	  EntityRecognizer er = new EntityRecognizer();
	  er.startProcess();
  }
  
  public void startProcess(){
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
		        tag("file:"+file, name);
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
  
  public void initAnnie() throws GateException, IOException {
    Out.prln("Initialising ANNIE...");
   
    File pluginsHome = Gate.getPluginsHome();
    File anniePlugin = new File(pluginsHome, "ANNIE");
    File annieGapp = new File(anniePlugin, "ANNIE_with_defaults.gapp");
    annieController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);

    Out.prln("...ANNIE loaded");
  } 

  public void setCorpus(Corpus corpus) {
    annieController.setCorpus(corpus);
  }

  public void execute() throws GateException {
    Out.prln("Running ANNIE...");
    annieController.execute();
    Out.prln("...ANNIE complete");
  }
  
  /*private void synonymReplace(String src) throws SAXException, IOException, ParserConfigurationException{
	  String trimmedSrc = src.substring(5, src.length());
	  System.out.println(trimmedSrc);
	  //System.exit(0);
	  
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		
		File inputArticle = new File(trimmedSrc);
		
		org.w3c.dom.Document doc = db.parse(inputArticle);
		doc.getDocumentElement().normalize();
		
		SynonymRecognizer synRecognizer = new SynonymRecognizer();
		
		String article = synRecognizer.replaceSynonyms(doc.getDocumentElement().getTextContent().trim().toLowerCase());
		
		FileWriter writer = new FileWriter(trimmedSrc);
        writer.write(article);
        writer.close();
  }*/

  public void tag(String src, String outputName) throws GateException, IOException {
    // initialise the GATE library
	  
	/*try {
		synonymReplace(src);
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	  
	BasicConfigurator.configure(); 
	  
    Out.prln("Initialising GATE...");
    Gate.init();
    //Gate.setGateHome(new File("C:/Program Files/GATE_Developer_8.0"));
    Out.prln("...GATE initialised");

    EntityRecognizer annie = new EntityRecognizer();
    annie.initAnnie();

    Corpus corpus = Factory.newCorpus("StandAloneAnnie corpus");
    
    URL u = new URL(src);
    FeatureMap params = Factory.newFeatureMap();
    params.put("sourceUrl", u);
    params.put("preserveOriginalContent", new Boolean(true));
    params.put("collectRepositioningInfo", new Boolean(true));
    Out.prln("Creating doc for " + u);
    Document doc1 = (Document)Factory.createResource("gate.corpora.DocumentImpl", params);

    corpus.add(doc1);

    annie.setCorpus(corpus);
    
    annie.execute();
    
    Document doc = (Document) corpus.get(0);
    AnnotationSet defaultAnnotSet = doc.getAnnotations();
    //defaultAnnotSet.get
    Set annotTypesRequired = new HashSet();
    annotTypesRequired.add("Condition");
    annotTypesRequired.add("Nutrient");
    annotTypesRequired.add("Food");
    
    //Print all annotation types
    for(String s: defaultAnnotSet.getAllTypes()){
  	  System.out.println(s);
    }
    
    Set<Annotation> peopleAndPlaces = new HashSet<Annotation>(defaultAnnotSet.get(annotTypesRequired));

    FeatureMap features = doc.getFeatures();
    String originalContent = (String)features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);

    RepositioningInfo info = (RepositioningInfo)features.get(GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME);
    // for each document, get an XML document with the
    // person and location names added
    
    Iterator it = peopleAndPlaces.iterator();
    Annotation currAnnot;
    SortedAnnotationList sortedAnnotations = new SortedAnnotationList();

    while(it.hasNext()) {
      currAnnot = (Annotation) it.next();
      sortedAnnotations.addSortedExclusive(currAnnot);
    } // while
    
    for(Object ann: sortedAnnotations){
    	Annotation an = (Annotation)ann;
    	//System.out.println(an.getType());
    }

    if(originalContent != null && info != null) {
    	ArrayList<ArrayList<TokenModel>> finalTokens = stringToTokenList(originalContent, sortedAnnotations, info);
    	/*for(TokenModel m: finalTokens){
    		System.out.println(m.getWord() + ", (" + m.getTag() + ")");
    	}*/
    	String url = originalContent.substring(originalContent.indexOf("=")+2, originalContent.indexOf(">")-2);
    	
    	System.out.println(finalTokens.size());
    	String finalString = "<article URL=\""+url+"\"> ";
    	for(ArrayList<TokenModel> lineModels: finalTokens){
	    	for(TokenModel m: lineModels){
	    		finalString += m.toString()+" ";
	    	}
	    	finalString += "<newLine>\\n</newLine>\n";
    	}
    	finalString += "</article>";
    	
        String fileName = new String(outputFolder+"/"+outputName+".xml");
        FileWriter writer = new FileWriter(fileName);
        writer.write(finalString);
        writer.close();
    }
    
  } // main

  /**
   *
   */
  
  private ArrayList<ArrayList<TokenModel>> stringToTokenList(String originalContent, SortedAnnotationList sortedAnnotations, RepositioningInfo info){
	  ArrayList<ArrayList<TokenModel>> tokenList = new  ArrayList<ArrayList<TokenModel>>();
	  //originalContent = originalContent.substring(7, originalContent.length()-7);
	  
	  //System.out.println(originalContent);
	  //String body = (originalContent.replace("<article>", ""));
	  String body = originalContent.substring(originalContent.indexOf(">")+1, originalContent.lastIndexOf("<"));
	  body = (body.replace("</article>", "")).trim();
	  String[] lines = body.split("\n");	
	  //String[] tokens = originalContent.split("\\s+");	
	  
	  ArrayList<ArrayList<String>> newTokens = new ArrayList<ArrayList<String>>();
	  for(int i=0; i<lines.length; i++){
		  if(lines[i].trim().length() > 0){
			  ArrayList<String> lineTokens = new ArrayList<String>();
			  String[] tokens = lines[i].split("\\s+");
			  for(int y=0; y<tokens.length; y++){
				  lineTokens.add(tokens[y]);
			  }
			  newTokens.add(lineTokens);
		  }
	  }

	  long currStart = 1;
	  
	  TokenModel tokenModel;
	  int qwe = 0;
	  for(ArrayList<String> lineTokens: newTokens){
		  System.out.println(qwe + " : " + newTokens.size());
		  ArrayList<TokenModel> lineModels = new ArrayList<TokenModel>();
		  for(String token: lineTokens){
			  tokenModel = new TokenModel();
			  //System.out.println((int)currStart+1 +", "+ token.indexOf(">"));
			  System.out.println(token);
			  String tag = token.substring(1, token.indexOf(">"));
			  tokenModel.setTag(tag);
			  tokenModel.setStart(currStart);
			  System.out.println(token);
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
  
  private ArrayList<TokenModel> adjustPosition(String originalContent, SortedAnnotationList sortedAnnotations, RepositioningInfo info){
	  System.out.println("ADJUSTING!!!!!!!!!!!!!!!!!");
	  
	  ArrayList<TokenModel> tokenList = new  ArrayList<TokenModel>();
	  //originalContent = originalContent.substring(7, originalContent.length()-7);
	  
	  //System.out.println(originalContent);

	  String[] tokens = originalContent.split("\\s+");	  
	  
	  ArrayList<String> newTokens = new ArrayList<String>();
	  for(int i=1; i<tokens.length-1; i++){
		  newTokens.add(tokens[i]);
	  }

	  long currStart = tokens[0].length();
	  System.out.println(currStart);
	  
	  boolean h = false;
	  
	  TokenModel tokenModel;
	  for(String token: newTokens){

		  tokenModel = new TokenModel();
		  //System.out.println((int)currStart+1 +", "+ token.indexOf(">"));
		  String tag = token.substring(1, token.indexOf(">"));
		  tokenModel.setTag(tag);
		  tokenModel.setStart(currStart + tag.length() + 3);
		  /*if(!h){
		  System.out.println(currStart + tag.length() + 3);
		  h=!h;
		  }*/
		  String word = token.substring(token.indexOf(">")+1, token.lastIndexOf("<"));
		  tokenModel.setWord(word);
		  tokenModel.setEnd(tokenModel.getStart()+word.length());
		  
		  currStart = tokenModel.getEnd()+ tag.length() + 3;
		  
		  tokenList.add(tokenModel);
	  }
	  System.out.println(tokenList.get(0).getStart() + "WHAAAT!!!!!!!!!!!!!!!!11");
	  
	  return tokenList;
  } 
  
  private ArrayList<ArrayList<TokenModel>> addNewTags(ArrayList<ArrayList<TokenModel>> tokens, SortedAnnotationList sortedAnnotations, RepositioningInfo info, String originalContent){
	  Annotation currAnnot;
	  TokenModel currToken;
	  long insertPositionStart;
      long insertPositionEnd;
	  
	  int y = 0;
	  
	  boolean adjusted = false;
	  boolean found = false;
	  
	  for(int i=0; i<sortedAnnotations.size(); i++){
		  currAnnot = (Annotation)sortedAnnotations.get(i);
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
		          
		          System.out.println(insertPositionStart +" "+ insertPositionEnd);
		          if(insertPositionEnd != -1 && insertPositionStart != -1){
		        	  System.out.println("Exist!");
		        	  insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
					  insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
		          }
		          else{
		        	  /*System.out.println("Does not Exist");
		        	  if(!adjusted){
		        		  tokens = adjustPosition(originalContent, sortedAnnotations, info);
		        		  currToken = tokens.get(y);
		        		  adjusted = !adjusted;
		        	  }*/
		        	  
		        	  insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
					  insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
					  System.out.println(insertPositionStart +" : "+ insertPositionEnd);
		          }
		          System.out.println(currToken.getStart());
		          System.out.println(currAnnot.getType());
		          System.out.println(currToken.toString());
		        
		          if(insertPositionStart == currToken.getStart()){
					  TokenModel tokenToTag = currToken;
					  lineModels.remove(j);
					  while(insertPositionEnd > currToken.getEnd()){
						  tokenToTag = mergeTokens(tokenToTag, lineModels.get(j));
						  currToken = lineModels.get(j);
						  lineModels.remove(j);
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
