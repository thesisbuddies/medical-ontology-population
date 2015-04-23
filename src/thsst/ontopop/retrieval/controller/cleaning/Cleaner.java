package thsst.ontopop.retrieval.controller.cleaning;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Cleaner {

    private String storagePath;

    public Cleaner(String storagePath) {
        this.storagePath = storagePath;
    }

    public void clean(File file) {
        try {
            String filePath = file.getAbsolutePath();
            filePath = filePath.replaceAll("\\\\", "/");

            String filename = file.getName();
            filename = filename.replace(".html", ".txt");

            FileReader fr = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fr);

            String url = reader.readLine();

            StringBuilder contentBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }

            reader.close();

            String content = contentBuilder.toString();

            CleaningStrategy cleaningStrategy = identifyCleaningStrategy(url);
            content = cleaningStrategy.clean(content);

            File cleanedFile = new File(this.storagePath + "/clean/" + filename);
            FileUtils.write(cleanedFile, url + "\n");
            FileUtils.write(cleanedFile, content, true);
        }
        catch (Exception ignore) { }
    }

    private CleaningStrategy identifyCleaningStrategy(String url) {
        if (url.contains("webmd.com")) {
            return new WebMDCleaningStrategy();
        }
        else if (url.contains("nlm.nih.gov/medlineplus")) {
            return new MedlineCleaningStrategy();
        }
        else if (url.contains("infonet-biovision.org")) {
            return new InfonetCleaningStrategy();
        }
        else {
            return new DefaultCleaningStrategy();
        }
    }
}
