package thsst.ontopop.core.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class CoreFrame extends JFrame{
	private CorePanel corePanel;
	
	private static CoreFrame instance;
	
	public static CoreFrame getInstance(){
		if(instance == null){
			instance = new CoreFrame();
		}

		return instance;
	}
	
	public CoreFrame(){
		initializeFrame();
		initializePanel();
		addPanelToFrame();
		
	}
	
	private void initializePanel(){
		corePanel = new CorePanel();
	}
	
	private void addPanelToFrame(){
		add(corePanel);
	}
	
	private void initializeFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Main");
		setVisible(true);
		setResizable(false);
		setSize(450, 230);
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
}
