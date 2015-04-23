package thsst.ontopop.retrieval.view;

import net.miginfocom.swing.MigLayout;
import thsst.ontopop.retrieval.model.LogEntry;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ArticleRetrievalView extends JFrame {

    private JPanel contentPane;
    private ArticleRetrievalControlPanel controlPanel;
    private ArticleRetrievalLogPanel logPanel;
    private JLabel statusLabel;

    public ArticleRetrievalView() {
        super("Article Retrieval");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        this.pack();
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setStatusLabelText(String statusText) {
        this.statusLabel.setText(statusText);
    }

    // Control Panel

    public String getUrlText() {
        return controlPanel.getUrlText();
    }

    public int getDepthText() {
        return controlPanel.getDepthText();
    }

    public int[] getSelectedUrls() {
        return controlPanel.getSelectedUrls();
    }

    public void setUrlText(String text) {
        controlPanel.setUrlText(text);
    }

    public void setCrawlerSeedListModel(ListModel<String> model) {
        controlPanel.setCrawlerSeedListModel(model);
    }

    public void addStartCrawlerButtonListener(ActionListener listener) {
        controlPanel.addStartCrawlerButtonListener(listener);
    }

    public void addStopCrawlerButtonListener(ActionListener listener) {
        controlPanel.addStopCrawlerButtonListener(listener);
    }

    public void addAddUrlButtonListener(ActionListener listener) {
        controlPanel.addAddUrlButtonListener(listener);
    }

    public void addRemoveUrlButtonListener(ActionListener listener) {
        controlPanel.addRemoveUrlButtonListener(listener);
    }

    public void addClearCrawlerSeedsButtonListener(ActionListener listener) {
        controlPanel.addClearCrawlerSeedsButtonListener(listener);
    }

    // Log Panel
    public void addLogEntry(LogEntry logEntry) {
        logPanel.addLogEntry(logEntry);
    }

    public String getLogText() {
        return logPanel.getLogText();
    }

    private void initComponents() {
        contentPane = new JPanel(new MigLayout(
                "",
                "[][grow, fill]",
                "[grow, fill][]"
        ));

        this.setContentPane(contentPane);

        controlPanel = new ArticleRetrievalControlPanel();
        contentPane.add(controlPanel);

        logPanel = new ArticleRetrievalLogPanel();
        contentPane.add(logPanel, "wrap");

        statusLabel = new JLabel("Ready");
        //contentPane.add(statusLabel, "span");
    }
}
