package thsst.ontopop.validation.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

import thsst.ontopop.validation.controller.ArticleDetailsPanelController;
import thsst.ontopop.validation.model.ArticleModel;

public class ArticleDetailsPanel extends JPanel{
	
	private ArticleDetailsPanelController controller;
	
	private ArticleModel currentArticle;
	
	private JLabel conditionNameLabel;
	private JTextField conditionNameField;
	
	private JLabel urlLabel;
	private JTextField urlField;
	
	private JLabel synonymsLabel;
	private JTable synonymsTable;
	private JScrollPane synonymsScrollPane;
	private JButton synonymsDeleteButton;
	
	private JLabel symptomsLabel;
	private JTable symptomsTable;
	private JScrollPane symptomsScrollPane;
	private JButton symptomsDeleteButton;
	
	private JLabel needFoodLabel;
	private JTable needFoodTable;
	private JScrollPane needFoodScrollPane;
	private JButton needFoodDeleteButton;
	
	private JLabel avoidFoodLabel;
	private JTable avoidFoodTable;
	private JScrollPane avoidFoodScrollPane;
	private JButton avoidFoodDeleteButton;
	
	private JLabel needNutrientLabel;
	private JTable needNutrientTable;
	private JScrollPane needNutrientScrollPane;
	private JButton needNutrientDeleteButton;
	
	private JLabel avoidNutrientLabel;
	private JTable avoidNutrientTable;
	private JScrollPane avoidNutrientScrollPane;
	private JButton avoidNutrientDeleteButton;
	
	private static ArticleDetailsPanel instance;
	
	public static ArticleDetailsPanel getInstance(){
		if(instance == null){
			instance = new ArticleDetailsPanel();
		}
		return instance;
	}
	
	public ArticleDetailsPanel(){
		controller = new ArticleDetailsPanelController();
		
		initializePanel();
		initializeLabels();
		initializeFields();
		initializeTables();
		initializeButtons();
		setListeners();
	}
	
	private void initializePanel(){
		setLayout(null);
		
		setPreferredSize(new Dimension(200, 900));
		setBorder(BorderFactory.createTitledBorder("Article Details"));
	}
	
	private void initializeButtons(){
		synonymsDeleteButton = new JButton("Delete");
		synonymsDeleteButton.setBounds(360, 172, 70, 25);
		add(synonymsDeleteButton);
		
		symptomsDeleteButton = new JButton("Delete");
		symptomsDeleteButton.setBounds(360, 332, 70, 25);
		add(symptomsDeleteButton);
		
		needFoodDeleteButton = new JButton("Delete");
		needFoodDeleteButton.setBounds(360, 492, 70, 25);
		add(needFoodDeleteButton);

		avoidFoodDeleteButton = new JButton("Delete");
		avoidFoodDeleteButton.setBounds(360, 652, 70, 25);
		add(avoidFoodDeleteButton);
		
		needNutrientDeleteButton = new JButton("Delete");
		needNutrientDeleteButton.setBounds(360, 812, 70, 25);
		add(needNutrientDeleteButton);

		avoidNutrientDeleteButton = new JButton("Delete");
		avoidNutrientDeleteButton.setBounds(360, 972, 70, 25);
		add(avoidNutrientDeleteButton);
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
		urlField.setText(currentArticle.getUrl());
		
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
		synonymsDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					currentArticle.setSynonyms(removeRow(synonymsTable, currentArticle.getSynonyms()));
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		symptomsDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					currentArticle.setSymptoms(removeRow(symptomsTable, currentArticle.getSymptoms()));
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		needFoodDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					currentArticle.setNeededFood(removeRow(needFoodTable, currentArticle.getNeededFood()));
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});

		avoidFoodDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					currentArticle.setAvoidFood(removeRow(avoidFoodTable, currentArticle.getAvoidFood()));
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		needNutrientDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					currentArticle.setDeficientNutrients(removeRow(needNutrientTable, currentArticle.getDeficientNutrients()));
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});

		avoidNutrientDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					currentArticle.setExcessNutrients(removeRow(avoidNutrientTable, currentArticle.getExcessNutrients()));
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		/*addToOntologyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		removeArticleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this article?", "Remove Article", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(opt == JOptionPane.YES_OPTION){
					resetDetailsPanel();
					controller.removeArticle();
				}
			}
		});*/
	}
	
	private List<String> removeRow(JTable table, List<String> list){
		int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this item?", "Remove Item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(opt == JOptionPane.YES_OPTION){
			if(table.getSelectedRow()>=0){
				list.remove(table.getSelectedRow());
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.removeRow(table.getSelectedRow());
				table.setModel(model);
				model.fireTableDataChanged();
			}
			else{
				JOptionPane.showMessageDialog(null, "No item selected");
			}
		}
		return list;
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
