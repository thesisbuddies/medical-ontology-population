package thsst.ontopop.validation.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailsPanel extends JPanel{
	
	private OntologyDetailsPanel ontologyDetails;
	private ArticleDetailsPanel articleDetails;
	
	private JButton addToOntologyButton;
	private JButton removeArticleButton;
	
	private static DetailsPanel instance;
	
	public static DetailsPanel getInstance(){
		
		if(instance == null){
			instance = new DetailsPanel();
		}
		
		return instance;
	}
	
	public DetailsPanel() {
		initializePanel();
		initializeDetails();
	}
	
	private void initializePanel(){
		setLayout(null);
		//add(new JLabel("HELOOOOOOOOOO"));
		setPreferredSize(new Dimension(200, 1060));
	}
	
	private void initializeDetails(){
		ontologyDetails = OntologyDetailsPanel.getInstance();
		ontologyDetails.setBounds(475, 0, 470, 1150);
		
		add(ontologyDetails);
		
		articleDetails = ArticleDetailsPanel.getInstance();
		articleDetails.setBounds(0, 0, 470, 1150);
		
		add(articleDetails);
	}
}
