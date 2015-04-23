package thsst.ontopop.validation.model;

import org.w3c.dom.Document;

public class ArticleModel extends ConditionModel{
	private Document srcDocument;
	private String srcPath;
	private String url;
	
	public ArticleModel(Document src, String path){
		srcDocument = src;
		srcPath = path;
	}

	public Document getSrcDocument() {
		return srcDocument;
	}

	public void setSrcDocument(Document srcDocument) {
		this.srcDocument = srcDocument;
	}
	
	@Override
	public String toString(){
		String name;
		if(srcPath.trim().length()>0){
			name = srcPath.substring(srcPath.lastIndexOf("\\")+1);
		}
		else{
			name = getConditionName();
		}
		return name;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
