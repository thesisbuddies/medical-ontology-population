package thsst.ontopop.syntactic_analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;
import com.aliasi.tag.Tagging;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.FastCache;
import com.aliasi.util.Strings;

public class POSTagger {

	public ArrayList<ArrayList<TokenModel>> tag(String inputArticle){
		ArrayList<ArrayList<TokenModel>> articleTokens = new ArrayList<ArrayList<TokenModel>>();
		File hmmFile = new File("res/pos-en-bio-genia.HiddenMarkovModel");
	    int cacheSize = 10;
	    FastCache<String,double[]> cache = new FastCache<String,double[]>(cacheSize);

	    // read HMM for pos tagging
	    HiddenMarkovModel posHmm;

	    try {
	        posHmm = (HiddenMarkovModel) AbstractExternalizable.readObject(hmmFile);
            HmmDecoder posTagger  = new HmmDecoder(posHmm,null,cache);
            TokenizerFactory tokenizerFactory = new IndoEuropeanTokenizerFactory();
            
            for(String line: inputArticle.split("\n")){
	            char[] cs = Strings.toCharArray(line);
	            List<String> tokenList = new ArrayList<String>();
	            List<String> whiteList = new ArrayList<String>();
	            Tokenizer tokenizer = tokenizerFactory.tokenizer(cs,0,cs.length);
	            tokenizer.tokenize(tokenList,whiteList);
	            String[] tokens
	                    = tokenList.<String>toArray(new String[tokenList.size()]);
	            String[] whites
	                    = whiteList.<String>toArray(new String[whiteList.size()]);
	
	            // part-of-speech tag
	            Tagging<String> tagging = posTagger.tag(tokenList);
	
	            //print tokens with tags
	            /*for (int j = 0; j < tagging.size(); j++) {
	                System.out.print("<"+tagging.tag(j)+">"+tokens[j]+"</"+tagging.tag(j)+"> ");
	            }*/
	            System.out.println(inputArticle);
	            articleTokens.add(stringToTokenList(tagging, tokens));
	            
	            System.out.println("new line!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            return articleTokens;
	    }
        catch (IOException e) {
	        System.out.println("Exception reading model=" + e);
	        e.printStackTrace(System.out);
	    }
        catch (ClassNotFoundException e) {
	        System.out.println("Exception reading model=" + e);
	        e.printStackTrace(System.out);
	    }

        return null;
	}
	
	private ArrayList<TokenModel> stringToTokenList(Tagging<String> tags, String[] tokens) {
		ArrayList<TokenModel> tokenList = new ArrayList<TokenModel>();
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher("I am a string");
		boolean b = m.find();
		
		ArrayList<String> utags = new ArrayList<String>();
		
		for(int i = 0; i < tokens.length; i++){
			TokenModel tempToken = new TokenModel();
			tempToken.setWord(tokens[i]);
			String currTag = tags.tag(i);
			if(tokens[i].trim().length() == 1){
				m = p.matcher(tokens[i].trim());
				if(m.find()){
					currTag = "SPECIAL";
				}
				if(tokens[i].trim().equals("&")){
					tempToken.setWord("and");
				}
				if(tokens[i].trim().equals("<")){
					tempToken.setWord("less than");
				}
			}
			m = p.matcher(currTag.trim());
			if(m.find()){
				currTag = currTag.replaceAll("[^a-zA-Z]+","");
				if(currTag.trim().equals("")){
					currTag = "SPECIAL";
				}
			}
			
			if(!utags.contains(currTag.toLowerCase())){
				utags.add(currTag.toLowerCase());
			}
			
			tempToken.setTag(currTag);
			
			tokenList.add(tempToken);
		}
		
		for(String s: utags){
			System.out.println(s);
		}
		
		return tokenList;
	}
}
