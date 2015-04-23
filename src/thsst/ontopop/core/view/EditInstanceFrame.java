package thsst.ontopop.core.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import thsst.ontopop.validation.view.ArticleListPanel;
import thsst.ontopop.validation.view.DetailsPanel;

public class EditInstanceFrame extends JFrame{	

	private ConditionSelectionPanel conditionSelectionPanel;
	private EditInstancePanel detailsPanel;
	private JSplitPane mainPane;

	
	private static EditInstanceFrame instance;
	
	public static EditInstanceFrame getInstance(){
		if(instance == null){
			instance = new EditInstanceFrame();
		}
		return instance;
	}
	
	public EditInstanceFrame() {
		initializeFrame();
		initializePanels();
		initializeMainPane();
	}
	
	private void initializePanels(){
		conditionSelectionPanel = ConditionSelectionPanel.getInstance();
		conditionSelectionPanel.loadConditions();
		
		detailsPanel = EditInstancePanel.getInstance();
	}
	
	private void initializeFrame(){
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Modify Ontology");
		setVisible(true);
		setResizable(false);
		setBounds(150, 100, 750, 600);
		//setContentPane(contentPane);
	}
	
	private void initializeMainPane(){
		//add(new JButton());
		mainPane = new JSplitPane();
		mainPane.setResizeWeight(0.46);
		mainPane.setBounds(0, 0, 770, 450);
		mainPane.setEnabled(false);
		mainPane.setLeftComponent(conditionSelectionPanel);
		mainPane.setRightComponent(new JScrollPane(detailsPanel));
		
		add(mainPane);
		revalidate();
		repaint();
	}

	public EditInstancePanel getDetailsPanel() {
		return detailsPanel;
	}

	public void setDetailsPanel(EditInstancePanel detailsPanel) {
		this.detailsPanel = detailsPanel;
	}

	public ConditionSelectionPanel getArticleListPanel() {
		return conditionSelectionPanel;
	}

	public void setArticleListPanel(ConditionSelectionPanel articleListPanel) {
		this.conditionSelectionPanel = articleListPanel;
	}
}
