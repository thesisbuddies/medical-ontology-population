package thsst.ontopop.retrieval.controller.action;

import thsst.ontopop.retrieval.controller.ArticleRetrievalController;
import thsst.ontopop.retrieval.model.LogEntry;
import thsst.ontopop.retrieval.view.ArticleRetrievalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearCrawlerSeedsActionListener implements ActionListener {

    private ArticleRetrievalController controller;
    private ArticleRetrievalView view;

    public ClearCrawlerSeedsActionListener(ArticleRetrievalController controller) {
        this.controller = controller;
        this.view = controller.getView();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        int response = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to clear the list of crawler seeds?",
                "Clear Crawler Seeds",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            controller.getCrawlerSeedListModel().clear();

            LogEntry entry = new LogEntry("Crawler seeds list cleared.");
            view.addLogEntry(entry);
        }
    }

}
