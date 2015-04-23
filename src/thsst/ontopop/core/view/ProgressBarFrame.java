package thsst.ontopop.core.view;

import javax.swing.JButton;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class ProgressBarFrame extends JFrame{
	
	private ProgressBarPanel progressPanel;
	
	private static ProgressBarFrame instance;
	
	public static ProgressBarFrame getInstance(){
		if(instance == null){
			instance = new ProgressBarFrame();
		}
		
		return instance;
	}
	
	public static void main(String[] args){
		ProgressBarFrame.getInstance();
	}
	
	public ProgressBarFrame() {
		initializeFrame();
		initializePanel();
		//add(new JButton("progress"));
	}
	
	private void initializePanel(){
		progressPanel = ProgressBarPanel.getInstance();
		add(progressPanel);
	}
	
	private void initializeFrame(){
		setLayout(new MigLayout("",
                "[][grow, fill]",
                "[grow, fill][]"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Processing progress");
		setVisible(true);
		setResizable(false);
		setSize(250, 250);
	}
	
}
