package thsst.ontopop.retrieval.controller.action;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import thsst.ontopop.retrieval.controller.ArticleRetrievalController;
import thsst.ontopop.retrieval.model.LogEntry;
import thsst.ontopop.retrieval.view.ArticleRetrievalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopCrawlerActionListener implements ActionListener {

    private ArticleRetrievalController controller;
    private ArticleRetrievalView view;

    public StopCrawlerActionListener(ArticleRetrievalController controller) {
        this.controller = controller;
        this.view = controller.getView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final CrawlController crawlController = controller.getCrawlController();

        if (!crawlController.isFinished()) {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    view.addLogEntry(new LogEntry("Article retrieval is shutting down."));
                    crawlController.shutdown();

                    crawlController.waitUntilFinish();
                    return null;
                }

                public void done() {
                    view.addLogEntry(new LogEntry("Article retrieval is stopped."));
                }
            }.execute();
        }
    }

}
