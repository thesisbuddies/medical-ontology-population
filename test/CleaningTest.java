import org.apache.commons.io.FileUtils;
import thsst.ontopop.retrieval.controller.cleaning.Cleaner;

import java.io.File;
import java.io.IOException;

public class CleaningTest {

    private static final String STORAGE_PATH = "tmp/";

    public static void main(String[] args) throws IOException {
        Cleaner cleaner = new Cleaner(STORAGE_PATH);
        File[] fileList = new File(STORAGE_PATH + "/raw").listFiles();

        for (File file : fileList) {
            if (!file.isDirectory()) {
                System.out.println("Cleaning: " + file.getName());
                cleaner.clean(file);
                FileUtils.moveFileToDirectory(file, new File("tmp/raw/_processed"), true);

            }

        }

        System.out.println("Cleaning is finished!");
    }
}
