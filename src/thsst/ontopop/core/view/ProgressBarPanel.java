package thsst.ontopop.core.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ProgressBarPanel extends JPanel{
	
	/*private JProgressBar analyzerBar;
	private JProgressBar recognizerBar;
	private JProgressBar annotationBar;*/
	
	private JLabel analyzerProgress;
	private JLabel recognizerProgress;
	private JLabel annotationProgress;
	
	private JLabel analyzerLabel;
	private JLabel recognizerLabel;
	private JLabel annotationLabel;
	
	private static ProgressBarPanel instance;
	
	public static ProgressBarPanel getInstance(){
		if(instance == null){
			instance = new ProgressBarPanel();
		}
		
		return instance;
	}
	
	public ProgressBarPanel() {
		initializePanel();
		initializeLabels();
	}
	
	private void initializePanel(){
		setLayout(new MigLayout());
		setSize(200, 300);
	}
	
	private void initializeLabels(){
		analyzerLabel = new JLabel("Syntactic Analyzer [Waiting]");
		add(analyzerLabel, "wrap");
		analyzerProgress = new JLabel("0/0 processed");
		add(analyzerProgress, "alignx right, wrap");
		add(new JLabel(" "), "wrap");
		add(new JLabel(" "), "wrap");

		
		recognizerLabel = new JLabel("Entity Recognizer [Waiting]");
		add(recognizerLabel, "wrap");
		recognizerProgress = new JLabel("0/0 processed");
		add(recognizerProgress, "alignx right, wrap");
		add(new JLabel(" "), "wrap");
		add(new JLabel(" "), "wrap");
		
		annotationLabel = new JLabel("Annotation [Waiting]");
		add(annotationLabel, "wrap");
		annotationProgress = new JLabel("0/0 processed");
		add(annotationProgress, "alignx right, wrap");
	}
	
	public void resetProgress(){
		analyzerLabel.setText("Syntactic Analyzer [Waiting]");
		analyzerProgress.setText("0/0 processed");
		
		recognizerLabel.setText("Entity Recognizer [Waiting]");
		recognizerProgress.setText("0/0 processed");
		
		annotationLabel.setText("Annotation [Waiting]");
		annotationProgress.setText("0/0 processed");
	}
	
	public void updateAnalyzer(String progress, boolean isComplete){
		analyzerProgress.setText(progress);
		if(isComplete){
			analyzerLabel.setText("Syntactic Analyzer [Complete]");
		}
		else{
			analyzerLabel.setText("Syntactic Analyzer [In progress]");
		}
	}
	
	public void updateRecognizer(String progress, boolean isComplete){
		recognizerProgress.setText(progress);
		if(isComplete){
			recognizerLabel.setText("Entity Recognizer [Complete]");
		}
		else{
			recognizerLabel.setText("Entity Recognizer [In progress]");
		}
	}
	
	public void updateAnnotation(String progress, boolean isComplete){
		annotationProgress.setText(progress);
		if(isComplete){
			annotationLabel.setText("Annotation [Complete]");
		}
		else{
			annotationLabel.setText("Annotation [In progress]");
		}
	}
	
	private void initializeBars(){
		/*analyzerBar = new JProgressBar();
		analyzerBar.setValue(25);
	    analyzerBar.setStringPainted(true);
	    Border analyzerBorder = BorderFactory.createTitledBorder("Syntactic Analyzer");
	    analyzerBar.setBorder(analyzerBorder);
	    add(analyzerBar);*/
		//add(new JButton("progress"));
	    
	    
	    /*recognizerBar = new JProgressBar();
		recognizerBar.setValue(0);
	    recognizerBar.setStringPainted(true);
	    Border recognizerBorder = BorderFactory.createTitledBorder("Entity Recognition");
	    recognizerBar.setBorder(recognizerBorder);
	    add(recognizerBar, "wrap");
	    
	    annotationBar = new JProgressBar();
		annotationBar.setValue(0);
	    annotationBar.setStringPainted(true);
	    Border annotationBorder = BorderFactory.createTitledBorder("Syntactic Analyzer");
	    annotationBar.setBorder(annotationBorder);
	    add(annotationBar);	*/
	}
	
}
