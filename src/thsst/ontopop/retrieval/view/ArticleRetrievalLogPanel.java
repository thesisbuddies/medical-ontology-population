package thsst.ontopop.retrieval.view;

import net.miginfocom.swing.MigLayout;
import thsst.ontopop.retrieval.model.LogEntry;

import javax.swing.*;

public class ArticleRetrievalLogPanel extends JPanel {

    private JTextArea log;

    public ArticleRetrievalLogPanel() {
        this.setLayout(new MigLayout());
        initComponents();
    }

    private void initComponents() {
        this.add(new JLabel("Retrieval Log"), "wrap");

        log = new JTextArea();
        log.setEditable(false);
        log.setFont(new JLabel().getFont());

        JScrollPane logScrollPane = new JScrollPane(log);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(logScrollPane, "width 400px:400px, push, grow");
    }

    public void addLogEntry(LogEntry logEntry) {
        log.append(logEntry.toString() + "\n");
    }

    public String getLogText() {
        return log.getText();
    }

}
