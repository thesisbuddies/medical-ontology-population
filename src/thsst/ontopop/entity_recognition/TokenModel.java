package thsst.ontopop.entity_recognition;

import java.util.Hashtable;

public class TokenModel {
	private Hashtable<String, String> attributes;
	private String word;
	private String tag;
	private String url;
	private long start;
	private long end;
	
	public TokenModel(){
		attributes = new Hashtable<String, String>();
	}
	
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

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
	
	@Override
	public String toString(){
		String startTag = "<"+tag+" ";
		for(String key: attributes.keySet()){
			startTag += (key+"=\""+attributes.get(key)+"\" ");
		}
		startTag = startTag.trim() + ">";
		return startTag+word+"</"+tag+">";
	}

	private String getUrl() {
		return url;
	}

	private void setUrl(String url) {
		this.url = url;
	}
}
