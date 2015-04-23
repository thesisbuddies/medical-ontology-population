package thsst.ontopop.core.view;

import java.awt.Font;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import thsst.ontopop.core.controller.action.ProcessButtonListener;
import thsst.ontopop.core.controller.action.RetrieveButtonListener;
import thsst.ontopop.core.controller.action.ValidateButtonListener;

public class CorePanel extends JPanel{
	private JLabel welcomeMessage1Label;
	private JLabel welcomeMessage2Label;
	private JLabel articleCountLabel;
	private JLabel toDoLabel;
	
	private JButton retrieveButton;
	private JButton processButton;
	private JButton validateButton;
	
	private static CorePanel instance;
	
	public static CorePanel getInstance(){
		if(instance == null){
			instance = new CorePanel();
		}
		
		return instance;
	}
	
	public CorePanel(){
		initializePanel();
		initializeLabels();
		initializeButtons();
		addButtonListeners();
		addLabelsToPanel();
		addButtonsToPanel();
		
		updateUnvalidatedCount();
	}
	
	private void initializePanel(){
		setBounds(0, 0, 450, 230);
		setLayout(null);
	}
	
	private void initializeLabels(){
		welcomeMessage1Label = new JLabel("Welcome! There are");
		welcomeMessage1Label.setFont(new Font("Serif", Font.PLAIN, 18));
		welcomeMessage1Label.setBounds(30, 20, 150, 30);
		
		welcomeMessage2Label = new JLabel("unprocessed articles");
		welcomeMessage2Label.setFont(new Font("Serif", Font.PLAIN, 18));
		welcomeMessage2Label.setBounds(32, 140, 150, 30);
		
		articleCountLabel = new JLabel("000");
		articleCountLabel.setFont(new Font("Serif", Font.PLAIN, 75));
		articleCountLabel.setBounds(65, 55, 160, 75);
		
		toDoLabel = new JLabel("What do you want to do?");
		toDoLabel.setFont(new Font("Serif", Font.PLAIN, 16));
		toDoLabel.setBounds(230, 22, 170, 30);
	}
	
	private void initializeButtons(){
		retrieveButton = new JButton("Retrieve articles");
		retrieveButton.setBounds(230, 55, 180, 30);
		
		processButton = new JButton("Process retrieved articles");
		processButton.setBounds(230, 95, 180, 30);
		
		validateButton = new JButton("Validate extracted instances");
		validateButton.setBounds(230, 135, 180, 30);
	}
	
	private void addButtonListeners(){
		retrieveButton.addActionListener(new RetrieveButtonListener());
		processButton.addActionListener(new ProcessButtonListener());
		validateButton.addActionListener(new ValidateButtonListener());
	}
	
	private void addLabelsToPanel(){
		add(welcomeMessage1Label);
		add(articleCountLabel);
		add(welcomeMessage2Label);
		add(toDoLabel);
	}
	
	private void addButtonsToPanel(){
		add(retrieveButton);
		add(processButton);
		add(validateButton);
	}
	
	public void updateUnvalidatedCount(){
		String unvalidatedPath = "tmp/clean";
		int count = 0;
		File unvalidated = new File(unvalidatedPath);
		
		for(File file: unvalidated.listFiles()){
			if(file.isFile()){
				count++;
			}
		}
		
		articleCountLabel.setText(String.format("%02d", count));
		
		revalidate();
		repaint();
	}
}
