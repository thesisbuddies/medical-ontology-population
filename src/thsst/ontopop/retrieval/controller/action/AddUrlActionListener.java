package thsst.ontopop.retrieval.controller.action;

import thsst.ontopop.retrieval.controller.ArticleRetrievalController;
import thsst.ontopop.retrieval.model.LogEntry;
import thsst.ontopop.retrieval.view.ArticleRetrievalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUrlActionListener implements ActionListener{

    private ArticleRetrievalController controller;
    private ArticleRetrievalView view;

    public AddUrlActionListener(ArticleRetrievalController controller) {
        this.controller = controller;
        this.view = controller.getView();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String url = view.getUrlText();

        ListModel<String> urlSeeds = controller.getCrawlerSeedListModel();
        boolean found = false;

        for (int i = 0; i < urlSeeds.getSize(); i++) {
            String item = urlSeeds.getElementAt(i);
            if (item.equalsIgnoreCase(url)) {
                found = true;
            }
        }

        view.setUrlText(new String());

        if (!found) {
            controller.getCrawlerSeedListModel().addElement(url);

            LogEntry entry = new LogEntry("Added " + url + " to crawler seeds.");
            view.addLogEntry(entry);
        }



    }
}
