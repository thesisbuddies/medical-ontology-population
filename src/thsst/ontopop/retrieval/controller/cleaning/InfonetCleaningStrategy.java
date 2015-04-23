package thsst.ontopop.retrieval.controller.cleaning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class InfonetCleaningStrategy implements CleaningStrategy {

    @Override
    public String clean(String html) {
        Document doc = Jsoup.parse(html.replaceAll("(?i)<br[^>]*>", "br2n")
                .replaceAll("(?i)</li[^>]*>",  "</li> br2n")
                .replaceAll("(?i)</p[^>]*>", "</p> br2n"));
        Element content = doc.getElementById("contentArea");

        String contentText = content.text().replaceAll("br2n", "\n");

        return contentText;
    }
}
