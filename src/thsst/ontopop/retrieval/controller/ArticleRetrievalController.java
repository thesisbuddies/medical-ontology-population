package thsst.ontopop.retrieval.controller;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import thsst.ontopop.retrieval.controller.action.*;
import thsst.ontopop.retrieval.view.ArticleRetrievalView;

import javax.swing.*;

public class ArticleRetrievalController {

    private CrawlController crawlController;

    private ArticleRetrievalView view;
    private DefaultListModel<String> crawlerSeedListModel;

    public ArticleRetrievalController(ArticleRetrievalView view) {
        // Initialize models
        this.crawlerSeedListModel = new DefaultListModel<String>();

        // Initialize views
        this.view = view;
        this.view.setCrawlerSeedListModel(crawlerSeedListModel);
        initListeners();
    }

    public CrawlController getCrawlController() {
        return this.crawlController;
    }

    public void setCrawlController(CrawlController crawlController) {
        this.crawlController = crawlController;
    }

    public ArticleRetrievalView getView() {
        return this.view;
    }

    public DefaultListModel<String> getCrawlerSeedListModel() {
        return this.crawlerSeedListModel;
    }

    private void initListeners() {
        view.addStartCrawlerButtonListener(new StartCrawlerActionListener(this));
        view.addStopCrawlerButtonListener(new StopCrawlerActionListener(this));

        view.addAddUrlButtonListener(new AddUrlActionListener(this));
        view.addRemoveUrlButtonListener(new RemoveUrlActionListener(this));
        view.addClearCrawlerSeedsButtonListener(new ClearCrawlerSeedsActionListener(this));

    }
}
