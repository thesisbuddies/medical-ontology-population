package thsst.ontopop.validation.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import thsst.ontopop.core.view.CoreFrame;


public class ValidationFrame extends JFrame{
	
	public final String srcFolder = "tmp/annotated";
	
	private ArticleListPanel articleListPanel;
	private DetailsPanel detailsPanel;
	private JSplitPane mainPane;
	
	private static ValidationFrame instance;
	
	public static void main(String[] args){
		ValidationFrame.getInstance();
	}
	
	public static ValidationFrame getInstance(){
		if(instance == null){
			instance = new ValidationFrame();
		}
		return instance;
	}

	public ValidationFrame(){
		initializeFrame();
		initializePanels();
		initializeMainPane();
	}
	
	private void initializePanels(){
		articleListPanel = ArticleListPanel.getInstance();
		articleListPanel.loadArticles(srcFolder);
		
		detailsPanel = DetailsPanel.getInstance();
	}
	
	private void initializeFrame(){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Validation");
		setVisible(true);
		setResizable(false);
		setBounds(150, 100, 1230, 600);
		//setContentPane(contentPane);
	}
	
	private void initializeMainPane(){
		//add(new JButton());
		mainPane = new JSplitPane();
		mainPane.setResizeWeight(0.24);
		mainPane.setBounds(0, 0, 770, 450);
		mainPane.setEnabled(false);
		mainPane.setLeftComponent(articleListPanel);
		JScrollPane p = new JScrollPane(detailsPanel);
		mainPane.setRightComponent(new JScrollPane(detailsPanel));
		
		add(mainPane);
		revalidate();
		repaint();
	}

	public DetailsPanel getArticleDetailsPanel() {
		return detailsPanel;
	}

	public void setDetailsPanel(DetailsPanel detailsPanel) {
		this.detailsPanel = detailsPanel;
	}

	public ArticleListPanel getArticleListPanel() {
		return articleListPanel;
	}

	public void setArticleListPanel(ArticleListPanel articleListPanel) {
		this.articleListPanel = articleListPanel;
	}
}
