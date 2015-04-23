package thsst.ontopop;

import java.io.File;

import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import thsst.ontopop.core.controller.SynonymIndexManager;
import thsst.ontopop.core.view.CoreFrame;

public class Main {

    public static void main(String[] args) throws Exception {
        // Set native operating system look and feel.
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        // Program entry point here
        initGUI();
    }

    private static void initGUI() {
        //ArticleRetrievalView view = new ArticleRetrievalView();
        //ArticleRetrievalController controller = new ArticleRetrievalController(view);
    	CoreFrame.getInstance();
    	
    	File idxFile = new File("tmp/synindex.xml");
    	if(!idxFile.exists()){
    		SynonymIndexManager synManager = new SynonymIndexManager();
    		try {
				synManager.createIndexFile(idxFile);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
}
