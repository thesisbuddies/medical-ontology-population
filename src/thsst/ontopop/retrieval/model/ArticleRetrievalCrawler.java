package thsst.ontopop.retrieval.model;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import thsst.ontopop.retrieval.controller.cleaning.Cleaner;
import thsst.ontopop.retrieval.controller.ArticleRetrievalController;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class ArticleRetrievalCrawler extends WebCrawler {

    // Regex pattern for filtering web pages. This pattern filters out
    // stylesheets, js files, media files and archives.
    private final static Pattern FILTERS = Pattern.compile(
            ".*(\\.(css|js|bmp|gif|jpe?g" + "" +
            "|png|tiff?|mid|mp2|mp3|mp4" +
            "|wav|avi|mov|mpeg|ram|m4v|pdf" +
            "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private String[] domainsToCrawl;
    private ArticleRetrievalController controller;
    private Cleaner cleaner;

    @Override
    public void onStart() {
        Object[] customData = (Object[]) myController.getCustomData();
        this.domainsToCrawl = (String[]) customData[0];
        this.controller = (ArticleRetrievalController) customData[1];
        this.cleaner = new Cleaner(myController.getConfig().getCrawlStorageFolder());
    }

    /**
     * Specifies whether the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();

        // URL matches a filter, do not visit the page.
        if (FILTERS.matcher(href).matches()) {
            return false;
        }

        // Check if domain matches list of domains to crawl.
        for (String domain : domainsToCrawl) {
            if (href.startsWith(domain)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed.
     */
    @Override
    public void visit(Page page) {
        logger.log(Level.INFO, "Visiting: " + page.getWebURL().getURL());

        ParseData data = page.getParseData();

        if (data != null && data instanceof  HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) data;
            String html = ((HtmlParseData) data).getHtml();

            // Save the downloaded page to crawl storage folder.
            try {
                String filename = "article_" + System.currentTimeMillis() + ".html";
                File file = new File(myController.getConfig().getCrawlStorageFolder() + "/raw/" + filename);

                FileUtils.write(file, page.getWebURL().getURL() + "\n"); // write url to file
                FileUtils.write(file, html, true); // append content to file

                logger.log(Level.INFO, "Saved page with " + html.length() + " characters.");
                logger.log(Level.INFO, "Added  " + htmlParseData.getOutgoingUrls().size() + " links to frontier.");
                System.out.println();

                controller.getView().addLogEntry(new LogEntry("Saved: " + page.getWebURL().getURL()));

                // Clean downloaded page.
                cleaner.clean(file);
                FileUtils.moveFileToDirectory(file, new File("tmp/raw/_processed"), true);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
