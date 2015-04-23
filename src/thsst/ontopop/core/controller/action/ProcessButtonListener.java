package thsst.ontopop.core.controller.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import thsst.annotation.Annotation;
import thsst.ontopop.core.view.CoreFrame;
import thsst.ontopop.core.view.CorePanel;
import thsst.ontopop.core.view.ProgressBarFrame;
import thsst.ontopop.core.view.ProgressBarPanel;
import thsst.ontopop.entity_recognition.EntityRecognizer;
import thsst.ontopop.syntactic_analysis.SyntacticAnalysisOutput;

public class ProcessButtonListener implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		SyntacticAnalysisOutput analyzer = new SyntacticAnalysisOutput();
		EntityRecognizer recognizer = new EntityRecognizer();
		Annotation annotation = new Annotation();
		
		try {
			ProgressBarFrame.getInstance().setVisible(true);
			ProgressBarPanel.getInstance().resetProgress();
			ProgressBarPanel.getInstance().revalidate();
			ProgressBarPanel.getInstance().repaint();
			
			analyzer.analyze();
			recognizer.startProcess();
			/*annotation.startProcess();
			
			ProgressBarFrame.getInstance().setVisible(false);		
			JOptionPane.showMessageDialog(null, "Done processing");*/
			
			CorePanel.getInstance().updateUnvalidatedCount();
			CoreFrame.getInstance().revalidate();
			CoreFrame.getInstance().repaint();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
