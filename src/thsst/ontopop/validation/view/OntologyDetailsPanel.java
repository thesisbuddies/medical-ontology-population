package thsst.ontopop.validation.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import thsst.ontopop.core.view.EditInstanceFrame;
import thsst.ontopop.validation.controller.OntologyDetailsPanelController;
import thsst.ontopop.validation.model.ArticleModel;

public class OntologyDetailsPanel extends JPanel{
	
	private OntologyDetailsPanelController controller;
	
	private ArticleModel currentArticle;
	
	private JLabel conditionNameLabel;
	private JTextField conditionNameField;
	
	private JLabel urlLabel;
	private JTextField urlField;
	
	private JLabel synonymsLabel;
	private JTable synonymsTable;
	private JScrollPane synonymsScrollPane;
	
	private JLabel symptomsLabel;
	private JTable symptomsTable;
	private JScrollPane symptomsScrollPane;
	
	private JLabel needFoodLabel;
	private JTable needFoodTable;
	private JScrollPane needFoodScrollPane;
	
	private JLabel avoidFoodLabel;
	private JTable avoidFoodTable;
	private JScrollPane avoidFoodScrollPane;
	
	private JLabel needNutrientLabel;
	private JTable needNutrientTable;
	private JScrollPane needNutrientScrollPane;
	
	private JLabel avoidNutrientLabel;
	private JTable avoidNutrientTable;
	private JScrollPane avoidNutrientScrollPane;
	
	private JButton modifyButton;
	
	private static OntologyDetailsPanel instance;
	
	public static OntologyDetailsPanel getInstance(){
		if(instance == null){
			instance = new OntologyDetailsPanel();
		}
		return instance;
	}
	
	public OntologyDetailsPanel() {

		controller = new OntologyDetailsPanelController();
		
		initializePanel();
		initializeLabels();
		initializeFields();
		initializeTables();
		initializeButton();
		setListeners();
	}
	
	
	
	private void initializePanel(){
		setLayout(null);
		
		setPreferredSize(new Dimension(200, 1150));
		setBorder(BorderFactory.createTitledBorder("Ontology Details"));
	}
	
	private void initializeButton(){
		modifyButton = new JButton("Modify");
		modifyButton.setBounds(170, 1010, 150, 25);
		add(modifyButton);
	}
	
	private void initializeFields(){
		urlField = new JTextField();
		urlField.setEditable(false);
		urlField.setBounds(110, 22, 345, 20);
		
		conditionNameField = new JTextField();
		conditionNameField.setEditable(false);
		conditionNameField.setBounds(110, 52, 345, 20);
		
		add(urlField);
		add(conditionNameField);
	}
	
	private void initializeLabels(){
		urlLabel = new JLabel("Source URL:");
		urlLabel.setBounds(12, 26, 100, 15);
		add(urlLabel);
		
		conditionNameLabel = new JLabel("Condition name:");
		conditionNameLabel.setBounds(12, 56, 100, 15);
		add(conditionNameLabel);
		
		synonymsLabel = new JLabel("Alternative names:");
		synonymsLabel.setBounds(12, 80, 130, 15);
		add(synonymsLabel);
		
		symptomsLabel = new JLabel("Symptoms:");
		symptomsLabel.setBounds(12, 240, 100, 15);
		add(symptomsLabel);
		
		needFoodLabel = new JLabel("Food needed:");
		needFoodLabel.setBounds(12, 400, 100, 15);
		add(needFoodLabel);
		
		avoidFoodLabel = new JLabel("Food to avoid:");
		avoidFoodLabel.setBounds(12, 550, 100, 15);
		add(avoidFoodLabel);
		
		needNutrientLabel = new JLabel("Nutrient needed:");
		needNutrientLabel.setBounds(12, 720, 100, 15);
		add(needNutrientLabel);
		
		avoidNutrientLabel = new JLabel("Nutrient to avoid:");
		avoidNutrientLabel.setBounds(12, 880, 100, 15);
		add(avoidNutrientLabel);
	}
	
	private void initializeTables(){
		//Synonyms area
		synonymsTable = new JTable();
		synonymsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		synonymsScrollPane = new JScrollPane(synonymsTable);
		synonymsScrollPane.setBounds(15, 100, 440, 100);
				
		add(synonymsScrollPane);
				
		//Symptoms area
		symptomsTable = new JTable();
		symptomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		symptomsScrollPane = new JScrollPane(symptomsTable);
		symptomsScrollPane.setBounds(15, 260, 440, 100);
				
		add(symptomsScrollPane);
				
		//Food needed area
		needFoodTable = new JTable();
		needFoodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		needFoodScrollPane = new JScrollPane(needFoodTable);
		needFoodScrollPane.setBounds(15, 420, 440, 100);
				
		add(needFoodScrollPane);
				
		//Food to avoid area
		avoidFoodTable = new JTable();
		avoidFoodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		avoidFoodScrollPane = new JScrollPane(avoidFoodTable);
		avoidFoodScrollPane.setBounds(15, 580, 440, 100);
				
		add(avoidFoodScrollPane);
				
		//Nutrients needed area
		needNutrientTable = new JTable();
		needNutrientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		needNutrientScrollPane = new JScrollPane(needNutrientTable);
		needNutrientScrollPane.setBounds(15, 740, 440, 100);
						
		add(needNutrientScrollPane);
						
		//Nutrients to avoid area
		avoidNutrientTable = new JTable();
		avoidNutrientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						
		avoidNutrientScrollPane = new JScrollPane(avoidNutrientTable);
		avoidNutrientScrollPane.setBounds(15, 900, 440, 100);
						
		add(avoidNutrientScrollPane);
	}
	
	public void loadArticleDetails(ArticleModel article){
		currentArticle = article;
		
		conditionNameField.setText(currentArticle.getConditionName());
		
		DefaultTableModel model = controller.generateTableModel(currentArticle.getSynonyms());
		synonymsTable.setModel(model);
		model.fireTableDataChanged();
		
		model = controller.generateTableModel(currentArticle.getSymptoms());
		symptomsTable.setModel(model);
		model.fireTableDataChanged();
		
		model = controller.generateTableModel(currentArticle.getNeededFood());
		needFoodTable.setModel(model);
		model.fireTableDataChanged();
		
		model = controller.generateTableModel(currentArticle.getAvoidFood());
		avoidFoodTable.setModel(model);
		model.fireTableDataChanged();
		
		model = controller.generateTableModel(currentArticle.getDeficientNutrients());
		needNutrientTable.setModel(model);
		model.fireTableDataChanged();
		
		model = controller.generateTableModel(currentArticle.getExcessNutrients());
		avoidNutrientTable.setModel(model);
		model.fireTableDataChanged();
	}
	
	public void setListeners(){
		modifyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EditInstanceFrame.getInstance().setVisible(true);
			}
		});
	}
	
	private ArrayList<String> removeRow(JTable table, ArrayList<String> srcList){
		int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this item?", "Remove Item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(opt == JOptionPane.YES_OPTION){
			if(table.getSelectedRow()>=0){
				srcList.remove(table.getSelectedRow());
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.removeRow(table.getSelectedRow());
				table.setModel(model);
				model.fireTableDataChanged();
			}
			else{
				JOptionPane.showMessageDialog(null, "No item selected");
			}
		}
		return srcList;
	}
	
	public void resetDetailsPanel(){
		currentArticle = null;
		
		conditionNameField.setText("");
		
		DefaultTableModel model = (DefaultTableModel) synonymsTable.getModel();
		model.getDataVector().removeAllElements();
		synonymsTable.setModel(model);
		model.fireTableDataChanged();
		
		model = (DefaultTableModel) symptomsTable.getModel();
		model.getDataVector().removeAllElements();
		symptomsTable.setModel(model);
		model.fireTableDataChanged();
		
		model = (DefaultTableModel) needFoodTable.getModel();
		model.getDataVector().removeAllElements();
		needFoodTable.setModel(model);
		model.fireTableDataChanged();
		
		model = (DefaultTableModel) avoidFoodTable.getModel();
		model.getDataVector().removeAllElements();
		avoidFoodTable.setModel(model);
		model.fireTableDataChanged();
		
		model = (DefaultTableModel) needNutrientTable.getModel();
		model.getDataVector().removeAllElements();
		needNutrientTable.setModel(model);
		model.fireTableDataChanged();
		
		model = (DefaultTableModel) avoidNutrientTable.getModel();
		model.getDataVector().removeAllElements();
		avoidNutrientTable.setModel(model);
		model.fireTableDataChanged();
	}
}
