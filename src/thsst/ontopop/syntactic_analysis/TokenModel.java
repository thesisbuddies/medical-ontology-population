package thsst.ontopop.syntactic_analysis;

import java.util.Hashtable;

public class TokenModel {
	private Hashtable<String, String> attributes;
	private String word;
	private String tag;
	
	public Hashtable<String, String> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Hashtable<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
