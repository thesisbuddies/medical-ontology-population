package thsst.ontopop.entity_recognition;

import java.util.ArrayList;
import java.util.List;

public class ConditionSynonymModel {
	private String name;
	private List<String> synonyms;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}
	
	public void addSynonym(String syn){
		if(synonyms == null){
			synonyms = new ArrayList<String>();
		}
		this.synonyms.add(syn);
	}
}
