import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import thsst.ontopop.retrieval.model.ArticleRetrievalCrawler;

public class InformationRetrievalTest {

    private static final String STORAGE_PATH = "tmp/";

    public static void main(String[] args) throws Exception {

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(STORAGE_PATH);
        config.setPolitenessDelay(1000);

        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        String[] crawlerDomains = new String[] { "http://www.nlm.nih.gov/", "http://www.webmd.com/",
                "http://www.infonet-biovision.org/" };

        controller.setCustomData(crawlerDomains);

        controller.addSeed("http://www.nlm.nih.gov/medlineplus/healthtopics.html");
        controller.addSeed("http://www.webmd.com/a-to-z-guides/common-topics/default.htm");
        controller.addSeed("http://www.infonet-biovision.org/default/ovvImg/-1/nutrition");

        controller.startNonBlocking(ArticleRetrievalCrawler.class, 1);
        controller.waitUntilFinish();

        System.out.println("Crawling is finished.");
    }
}
