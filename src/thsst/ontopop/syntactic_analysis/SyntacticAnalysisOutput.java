package thsst.ontopop.syntactic_analysis;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;

import thsst.ontopop.core.view.ProgressBarPanel;
import thsst.ontopop.entity_recognition.SynonymRecognizer;

/**
 *Process the output of article retrieval module for lingpipe use.
 */
public class SyntacticAnalysisOutput {
	
	private final String inputFolder = "tmp/clean";
	private final String outputFolder = "tmp/pos tagged";
	private final String processedFolder = "tmp/clean/_processed";
	
    public void analyze() throws IOException{
        String line;
        String string;
        String URL;
        
        SyntacticAnalysisOutput synOut = new SyntacticAnalysisOutput(); 
        
        ArrayList<String> paths = synOut.readFiles(new File(inputFolder));
        
        ProgressBarPanel progressPanel = ProgressBarPanel.getInstance();
        progressPanel.updateAnalyzer("0/"+paths.size()+" processed", false);
        progressPanel.revalidate();
        progressPanel.repaint();
        
        for(int i=0; i<paths.size(); i++){
        	
        	String file = paths.get(i);
        	//Reads the contents of text file and stores it into a string.
	        BufferedReader reader = new BufferedReader(new FileReader(file));            
	        try {
	                      
				line = reader.readLine();
	            URL = line;
	            string = "";
	                      
	            while ((line = reader.readLine()) != null) { 
	            	string = string + "\n" + line;          
	            }
	            
	            System.out.println(string + "\n");
	            string.toString();
	                      
	        } finally {
	            reader.close();
						  
	        }
		
	        //Passes the read contents of text file for tagging.
	        //System.out.println(string.substring(211, 217));
	        SynonymRecognizer synRecognizer = new SynonymRecognizer();
	        string = synRecognizer.replaceSynonyms(string);
        	
	        POSTagger tagger = new POSTagger();
	        ArrayList<ArrayList<TokenModel>> tokens = tagger.tag(string);
	        
	        String name = paths.get(i).substring(paths.get(i).lastIndexOf("\\")+1, paths.get(i).lastIndexOf("."));
	        
	        String fileName = new String(outputFolder+"/"+name+".xml");
	        FileWriter writer = new FileWriter(fileName);
	        //String finalString = synOut.outputFormatter(tokens);
	        //System.out.println(finalString.substring(211, 217));
	        writer.write(synOut.outputFormatter(tokens, URL));
	        writer.close();
	        
	        File currFile = new File(paths.get(i));
	        currFile.renameTo(new File(processedFolder+"/"+name+".txt"));
	        
	        //Files.move(paths.get(i), , replace);
	        progressPanel.updateAnalyzer((i+1)+"/"+paths.size()+" processed", (i+1)==paths.size());
	        progressPanel.revalidate();
	        progressPanel.repaint();
        }

    }
    
    public ArrayList<String> readFiles(File folder){
    	ArrayList<String> paths = new ArrayList<String>();
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
    	
    	return paths;
    }
    
    public String outputFormatter(ArrayList<ArrayList<TokenModel>> tokens, String URL){
    	String output = "<article URL=\""+URL+"\"> ";
    	for(ArrayList<TokenModel> line: tokens){
    		if(line.size()>0){
		    	for(TokenModel token: line){
		    		output += "<"+token.getTag()+">"+token.getWord().toLowerCase()+"</"+token.getTag()+"> ";
		    	}
		    	//output+="<newLine>\\n</newLine>\n";
		    	output+="\n";
    		}
    	}
    	
    	output += "</article>";
    	return output;
    }
}

