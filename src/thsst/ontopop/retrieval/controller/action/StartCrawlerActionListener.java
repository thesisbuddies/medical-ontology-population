package thsst.ontopop.retrieval.controller.action;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import thsst.ontopop.retrieval.model.ArticleRetrievalCrawler;
import thsst.ontopop.retrieval.controller.ArticleRetrievalController;
import thsst.ontopop.retrieval.model.LogEntry;
import thsst.ontopop.retrieval.view.ArticleRetrievalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StartCrawlerActionListener implements ActionListener {

    private static final String STORAGE_PATH = "tmp/";

    private ArticleRetrievalController controller;
    private ArticleRetrievalView view;

    public StartCrawlerActionListener(ArticleRetrievalController controller) {
        this.controller = controller;
        this.view = controller.getView();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws Exception {
                // Set up configuration for CrawlController
                CrawlConfig config = new CrawlConfig();
                config.setCrawlStorageFolder(STORAGE_PATH);
                config.setPolitenessDelay(1000);
                config.setResumableCrawling(true);
                config.setMaxDepthOfCrawling(view.getDepthText());

                PageFetcher pageFetcher = new PageFetcher(config);

                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

                CrawlController crawlController;

                try {
                    crawlController = new CrawlController(config, pageFetcher, robotstxtServer);
                    controller.setCrawlController(crawlController);

                    List<String> crawlerDomains = new ArrayList<String>();
                    DefaultListModel<String> seeds = controller.getCrawlerSeedListModel();

                    view.addLogEntry(new LogEntry("Article retrieval has been started."));
                    view.addLogEntry(new LogEntry("Crawling with a depth of " + config.getMaxDepthOfCrawling() + "."));
                    view.addLogEntry(new LogEntry("Crawling with " + seeds.size() + " initial seeds."));
                    view.addLogEntry(new LogEntry(seeds.size() + " crawler threads spawned."));

                    for (int i = 0; i < seeds.size(); i++) {
                        String url = seeds.elementAt(i);

                        int startIndex = url.indexOf("//") + 2;
                        String hostname = url.substring(0, url.indexOf("/", startIndex));

                        crawlerDomains.add(hostname);
                        crawlController.addSeed(url);
                    }

                    Object[] customData = {
                            crawlerDomains.toArray(new String[crawlerDomains.size()]),
                            controller
                    };

                    //crawlController.setCustomData(crawlerDomains.toArray(new String[crawlerDomains.size()]));
                    crawlController.setCustomData(customData);
                    crawlController.start(ArticleRetrievalCrawler.class, seeds.size());
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }

                return null;
            }

            @Override
            public void done() {
                view.addLogEntry(new LogEntry("Crawling complete!."));
            }
        }.execute();
    }
}
