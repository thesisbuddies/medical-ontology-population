package thsst.ontopop.validation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import thsst.ontopop.validation.view.ArticleListPanel;

public class ArticleDetailsPanelController {
	public DefaultTableModel generateTableModel(List<String> list){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> columns = new Vector<String>(Arrays.asList(new String[]{""}));
	    
		System.out.println(list.size());
	    Vector<Object> vector;
	    for(String s: list) {
	        vector = new Vector<Object>();
	       
	        vector.add(s);
	     
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columns){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
	}
	
	public void removeArticle(){
		ArticleListPanel.getInstance().removeRow();
	}
}
