package thsst.ontopop.core.controller.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import thsst.ontopop.core.view.CoreFrame;
import thsst.ontopop.validation.view.ValidationFrame;

public class ValidateButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Validate");
		
		ValidationFrame vf = new ValidationFrame();
	}

}
