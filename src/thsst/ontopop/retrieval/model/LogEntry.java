package thsst.ontopop.retrieval.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntry {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private Date date;
    private String entry;

    public LogEntry(String entry) {
        this.date = new Date();
        this.entry = entry;
    }

    @Override
    public String toString() {
        return formatter.format(date) + ": " + entry;
    }
}
