package thsst.ontopop.retrieval.controller.cleaning;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class DefaultCleaningStrategy implements CleaningStrategy {

    @Override
    public String clean(String html) {
        //
        try {
            return ArticleExtractor.INSTANCE.getText(html);
        } catch (BoilerpipeProcessingException ignore) {  }

        return new String();



    }
}
