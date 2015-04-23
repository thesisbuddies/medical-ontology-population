package thsst.ontopop.retrieval.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ArticleRetrievalControlPanel extends JPanel {

    private JButton startCrawlerButton;
    private JButton stopCrawlerButton;
    private JList<String> crawlerSeedList;

    private JTextField txtDepth;
    private JTextField txtUrl;
    private JButton addUrlButton;
    private JButton removeUrlButton;
    private JButton clearCrawlerSeedsButton;

    public ArticleRetrievalControlPanel() {
        this.setLayout(new MigLayout());
        initComponents();
    }

    public String getUrlText() {
        return txtUrl.getText();
    }

    public int getDepthText() { return Integer.parseInt(txtDepth.getText()); }

    public int[] getSelectedUrls() {
        return crawlerSeedList.getSelectedIndices();
    }

    public void setUrlText(String urlText) {
        this.txtUrl.setText(urlText);
    }

    public void setCrawlerSeedListModel(ListModel<String> model) {
        crawlerSeedList.setModel(model);
    }

    public void addStartCrawlerButtonListener(ActionListener listener) {
        startCrawlerButton.addActionListener(listener);
    }

    public void addStopCrawlerButtonListener(ActionListener listener) {
        stopCrawlerButton.addActionListener(listener);
    }

    public void addAddUrlButtonListener(ActionListener listener) {
        addUrlButton.addActionListener(listener);
    }

    public void addRemoveUrlButtonListener(ActionListener listener) {
        removeUrlButton.addActionListener(listener);
    }

    public void addClearCrawlerSeedsButtonListener(ActionListener listener) {
        clearCrawlerSeedsButton.addActionListener(listener);
    }

    private void initComponents() {
        startCrawlerButton = new JButton("Start");
        this.add(startCrawlerButton, "growx, wrap");

        stopCrawlerButton = new JButton("Stop");
        this.add(stopCrawlerButton, "growx, wrap");

        this.add(new JLabel("Crawl Depth"), "gaptop 30, growx, wrap");

        txtDepth = new JTextField();
        txtDepth.setText("3");
        this.add(txtDepth, "growx, wrap");

        this.add(new JLabel("Crawler Seeds"), "gaptop 30, growx, wrap");

        crawlerSeedList = new JList<String>();

        crawlerSeedList.setLayoutOrientation(JList.VERTICAL);
        crawlerSeedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        crawlerSeedList.setVisibleRowCount(5);

        JScrollPane listScroller = new JScrollPane(crawlerSeedList);
        listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(listScroller, "width 200px!, push, grow, wrap");

        txtUrl = new JTextField();
        this.add(txtUrl, "growx, wrap");

        addUrlButton = new JButton("Add URL");
        this.add(addUrlButton, "growx, wrap");

        removeUrlButton = new JButton("Remove URL");
        this.add(removeUrlButton, "growx, wrap");

        clearCrawlerSeedsButton = new JButton("Clear All");
        this.add(clearCrawlerSeedsButton, "growx, wrap");
    }


}
