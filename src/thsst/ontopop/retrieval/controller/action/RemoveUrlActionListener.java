package thsst.ontopop.retrieval.controller.action;

import thsst.ontopop.retrieval.controller.ArticleRetrievalController;
import thsst.ontopop.retrieval.model.LogEntry;
import thsst.ontopop.retrieval.view.ArticleRetrievalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveUrlActionListener implements ActionListener{

    private ArticleRetrievalController controller;
    private ArticleRetrievalView view;

    public RemoveUrlActionListener(ArticleRetrievalController controller) {
        this.controller = controller;
        this.view = controller.getView();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        int[] selectedUrls = view.getSelectedUrls();

        DefaultListModel<String> urlSeeds = controller.getCrawlerSeedListModel();

        for (int i = 0; i < selectedUrls.length; i++) {
            LogEntry entry = new LogEntry("Removed " + urlSeeds.get(selectedUrls[i]) + " from crawler seeds.");
            view.addLogEntry(entry);

            urlSeeds.removeElementAt(selectedUrls[i]);
        }



    }
}
