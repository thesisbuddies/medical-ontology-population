package thsst.ontopop.core.controller.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import thsst.ontopop.core.view.CoreFrame;
import thsst.ontopop.core.view.CorePanel;
import thsst.ontopop.retrieval.controller.ArticleRetrievalController;
import thsst.ontopop.retrieval.view.ArticleRetrievalView;

public class RetrieveButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Retrieve");
		ArticleRetrievalView view = new ArticleRetrievalView();
        ArticleRetrievalController controller = new ArticleRetrievalController(view);
        
        CorePanel.getInstance().updateUnvalidatedCount();
		CoreFrame.getInstance().revalidate();
		CoreFrame.getInstance().repaint();
	}

}
