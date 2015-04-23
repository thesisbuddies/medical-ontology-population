package thsst.ontopop.retrieval.controller.cleaning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebMDCleaningStrategy implements CleaningStrategy {
    @Override
    public String clean(String html) {
        Document doc = Jsoup.parse(html.replaceAll("(?i)<br[^>]*>", "br2n")
                .replaceAll("(?i)</li[^>]*>", "</li> br2n")
                .replaceAll("(?i)</p[^>]*>", "</p> br2n"));
        Element content = doc.getElementById("textArea");

        return content.text().replaceAll("br2n", "\n");
    }
}
