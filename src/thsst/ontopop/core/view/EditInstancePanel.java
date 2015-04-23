package thsst.ontopop.core.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import thsst.ontopop.api.Food;
import thsst.ontopop.core.controller.EditInstancePanelController;
import thsst.ontopop.core.controller.SynonymIndexManager;
import thsst.ontopop.validation.model.ArticleModel;

public class EditInstancePanel extends JPanel{
	private EditInstancePanelController controller;
	
	private ArticleModel currentArticle;
	
	private JLabel conditionNameLabel;
	private JTextField conditionNameField;
	
	private JLabel synonymsLabel;
	private JTable synonymsTable;
	private JScrollPane synonymsScrollPane;
	private JButton synonymsDeleteButton;
	private JButton synonymsEditButton;
	private JButton synonymsAddButton;
	
	private JLabel symptomsLabel;
	private JTable symptomsTable;
	private JScrollPane symptomsScrollPane;
	private JButton symptomsDeleteButton;
	private JButton symptomsEditButton;
	private JButton symptomsAddButton;
	
	private JLabel needFoodLabel;
	private JTable needFoodTable;
	private JScrollPane needFoodScrollPane;
	private JButton needFoodDeleteButton;
	private JButton needFoodEditButton;
	private JButton needFoodAddButton;
	
	private JLabel avoidFoodLabel;
	private JTable avoidFoodTable;
	private JScrollPane avoidFoodScrollPane;
	private JButton avoidFoodDeleteButton;
	private JButton avoidFoodEditButton;
	private JButton avoidFoodAddButton;
	
	private JLabel needNutrientLabel;
	private JTable needNutrientTable;
	private JScrollPane needNutrientScrollPane;
	private JButton needNutrientDeleteButton;
	private JButton needNutrientEditButton;
	private JButton needNutrientAddButton;
	
	private JLabel avoidNutrientLabel;
	private JTable avoidNutrientTable;
	private JScrollPane avoidNutrientScrollPane;
	private JButton avoidNutrientDeleteButton;
	private JButton avoidNutrientEditButton;
	private JButton avoidNutrientAddButton;
	
	private static EditInstancePanel instance;
	
	public static EditInstancePanel getInstance(){
		if(instance == null){
			instance = new EditInstancePanel();
		}
		
		return instance;
	}
	
	public EditInstancePanel(){
		controller = new EditInstancePanelController();
		
		initializePanel();
		initializeLabels();
		initializeFields();
		initializeTables();
		initializeButtons();
		setListeners();
	}
	
	private void initializePanel(){
		setLayout(null);
		
		setPreferredSize(new Dimension(200, 1020));
		setBorder(BorderFactory.createTitledBorder("Condition Details"));
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
		
		synonymsEditButton = new JButton("Edit");
		synonymsEditButton.setBounds(280, 172, 70, 25);
		add(synonymsEditButton);
		
		symptomsEditButton = new JButton("Edit");
		symptomsEditButton.setBounds(280, 332, 70, 25);
		add(symptomsEditButton);
		
		needFoodEditButton = new JButton("Edit");
		needFoodEditButton.setBounds(280, 492, 70, 25);
		add(needFoodEditButton);

		avoidFoodEditButton = new JButton("Edit");
		avoidFoodEditButton.setBounds(280, 652, 70, 25);
		add(avoidFoodEditButton);
		
		needNutrientEditButton = new JButton("Edit");
		needNutrientEditButton.setBounds(280, 812, 70, 25);
		add(needNutrientEditButton);

		avoidNutrientEditButton = new JButton("Edit");
		avoidNutrientEditButton.setBounds(280, 972, 70, 25);
		add(avoidNutrientEditButton);
		
		synonymsAddButton = new JButton("Add");
		synonymsAddButton.setBounds(200, 172, 70, 25);
		add(synonymsAddButton);
		
		symptomsAddButton = new JButton("Add");
		symptomsAddButton.setBounds(200, 332, 70, 25);
		add(symptomsAddButton);
		
		needFoodAddButton = new JButton("Add");
		needFoodAddButton.setBounds(200, 492, 70, 25);
		add(needFoodAddButton);

		avoidFoodAddButton = new JButton("Add");
		avoidFoodAddButton.setBounds(200, 652, 70, 25);
		add(avoidFoodAddButton);
		
		needNutrientAddButton = new JButton("Add");
		needNutrientAddButton.setBounds(200, 812, 70, 25);
		add(needNutrientAddButton);

		avoidNutrientAddButton = new JButton("Add");
		avoidNutrientAddButton.setBounds(200, 972, 70, 25);
		add(avoidNutrientAddButton);
	}
	
	private void initializeFields(){
		conditionNameField = new JTextField();
		conditionNameField.setEditable(false);
		conditionNameField.setBounds(110, 22, 345, 20);
		
		add(conditionNameField);
	}
	
	private void initializeLabels(){
		conditionNameLabel = new JLabel("Condition name:");
		conditionNameLabel.setBounds(12, 26, 100, 15);
		add(conditionNameLabel);
		
		synonymsLabel = new JLabel("Alternative names:");
		synonymsLabel.setBounds(12, 50, 130, 15);
		add(synonymsLabel);
		
		symptomsLabel = new JLabel("Symptoms:");
		symptomsLabel.setBounds(12, 210, 100, 15);
		add(symptomsLabel);
		
		needFoodLabel = new JLabel("Food needed:");
		needFoodLabel.setBounds(12, 370, 100, 15);
		add(needFoodLabel);
		
		avoidFoodLabel = new JLabel("Food to avoid:");
		avoidFoodLabel.setBounds(12, 530, 100, 15);
		add(avoidFoodLabel);
		
		needNutrientLabel = new JLabel("Nutrient needed:");
		needNutrientLabel.setBounds(12, 690, 100, 15);
		add(needNutrientLabel);
		
		avoidNutrientLabel = new JLabel("Nutrient to avoid:");
		avoidNutrientLabel.setBounds(12, 850, 100, 15);
		add(avoidNutrientLabel);
	}
	
	private void initializeTables(){
		//Synonyms area
		synonymsTable = new JTable();
		synonymsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		synonymsScrollPane = new JScrollPane(synonymsTable);
		synonymsScrollPane.setBounds(15, 70, 440, 100);
		
		add(synonymsScrollPane);
		
		//Symptoms area
		symptomsTable = new JTable();
		symptomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		symptomsScrollPane = new JScrollPane(symptomsTable);
		symptomsScrollPane.setBounds(15, 230, 440, 100);
		
		add(symptomsScrollPane);
		
		//Food needed area
		needFoodTable = new JTable();
		needFoodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		needFoodScrollPane = new JScrollPane(needFoodTable);
		needFoodScrollPane.setBounds(15, 390, 440, 100);
		
		add(needFoodScrollPane);
		
		//Food to avoid area
		avoidFoodTable = new JTable();
		avoidFoodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		avoidFoodScrollPane = new JScrollPane(avoidFoodTable);
		avoidFoodScrollPane.setBounds(15, 550, 440, 100);
		
		add(avoidFoodScrollPane);
		
		//Nutrients needed area
		needNutrientTable = new JTable();
		needNutrientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		needNutrientScrollPane = new JScrollPane(needNutrientTable);
		needNutrientScrollPane.setBounds(15, 710, 440, 100);
				
		add(needNutrientScrollPane);
				
		//Nutrients to avoid area
		avoidNutrientTable = new JTable();
		avoidNutrientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		avoidNutrientScrollPane = new JScrollPane(avoidNutrientTable);
		avoidNutrientScrollPane.setBounds(15, 870, 440, 100);
				
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
		synonymsDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					if(synonymsTable.getSelectedRow()>=0){
						SynonymIndexManager synManager = new SynonymIndexManager();
						
						String condName = currentArticle.getConditionName();
						String synName = currentArticle.getSynonyms().get(synonymsTable.getSelectedRow());
						
						controller.deleteFromOntology(condName, synName, 5);
						currentArticle.setSynonyms(removeRow(synonymsTable, currentArticle.getSynonyms()));
						try {
							synManager.removeFromIndex(condName, synName);
						} catch (ParserConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SAXException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (TransformerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
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
					if(symptomsTable.getSelectedRow()>=0){
						controller.deleteFromOntology(currentArticle.getConditionName(), currentArticle.getSymptoms().get(symptomsTable.getSelectedRow()), 4);
						currentArticle.setSymptoms(removeRow(symptomsTable, currentArticle.getSymptoms()));
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
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
					if(needFoodTable.getSelectedRow()>=0){
						controller.deleteFromOntology(currentArticle.getConditionName(), currentArticle.getNeededFood().get(needFoodTable.getSelectedRow()), 3);
						currentArticle.setNeededFood(removeRow(needFoodTable, currentArticle.getNeededFood()));
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
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
					if(avoidFoodTable.getSelectedRow()>=0){
						controller.deleteFromOntology(currentArticle.getConditionName(), currentArticle.getAvoidFood().get(avoidFoodTable.getSelectedRow()), 0);
						currentArticle.setAvoidFood(removeRow(avoidFoodTable, currentArticle.getAvoidFood()));
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
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
					if(needNutrientTable.getSelectedRow()>=0){
						controller.deleteFromOntology(currentArticle.getConditionName(), currentArticle.getDeficientNutrients().get(needNutrientTable.getSelectedRow()), 1);
						currentArticle.setDeficientNutrients(removeRow(needNutrientTable, currentArticle.getDeficientNutrients()));
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
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
					if(avoidNutrientTable.getSelectedRow()>=0){
						controller.deleteFromOntology(currentArticle.getConditionName(), currentArticle.getExcessNutrients().get(avoidNutrientTable.getSelectedRow()), 2);
						currentArticle.setExcessNutrients(removeRow(avoidNutrientTable, currentArticle.getExcessNutrients()));
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		synonymsEditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					if(synonymsTable.getSelectedRow()>=0){
						String newName = JOptionPane.showInputDialog("Change to"); 
						if(newName != null){
							controller.editFromOntology(currentArticle.getConditionName(), currentArticle.getSynonyms().get(synonymsTable.getSelectedRow()), newName, 5);
							currentArticle.setSynonyms(editRow(synonymsTable, currentArticle.getSynonyms(), newName));
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		symptomsEditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					if(symptomsTable.getSelectedRow()>=0){
						String newName = JOptionPane.showInputDialog("Change to"); 
						if(newName != null){
							controller.editFromOntology(currentArticle.getConditionName(), currentArticle.getSymptoms().get(symptomsTable.getSelectedRow()), newName, 4);
							currentArticle.setSymptoms(editRow(symptomsTable, currentArticle.getSymptoms(), newName));
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		needFoodEditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					if(needFoodTable.getSelectedRow()>=0){
						Object[] foodList = controller.getFoodList().toArray();
						Food newFood = (Food) JOptionPane.showInputDialog(null, "Change to", "Edit food", JOptionPane.QUESTION_MESSAGE, null, foodList, foodList[0]); 
						if(newFood != null){
							String newName = newFood.getName();
							controller.editFromOntology(currentArticle.getConditionName(), currentArticle.getNeededFood().get(needFoodTable.getSelectedRow()), newName, 3);
							currentArticle.setNeededFood(editRow(needFoodTable, currentArticle.getNeededFood(), newName));
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});

		avoidFoodEditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					if(avoidFoodTable.getSelectedRow()>=0){
						Object[] foodList = controller.getFoodList().toArray();
						Food newFood = (Food) JOptionPane.showInputDialog(null, "Change to", "Edit food", JOptionPane.QUESTION_MESSAGE, null, foodList, foodList[0]); 
						if(newFood != null){
							String newName = newFood.getName();
							controller.editFromOntology(currentArticle.getConditionName(), currentArticle.getAvoidFood().get(avoidFoodTable.getSelectedRow()), newName, 0);
							currentArticle.setAvoidFood(editRow(avoidFoodTable, currentArticle.getAvoidFood(), newName));
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		needNutrientEditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					if(needNutrientTable.getSelectedRow()>=0){
						String newName = JOptionPane.showInputDialog("Change to"); 
						if(newName != null){
							controller.editFromOntology(currentArticle.getConditionName(), currentArticle.getDeficientNutrients().get(needNutrientTable.getSelectedRow()), newName, 1);
							currentArticle.setDeficientNutrients(editRow(needNutrientTable, currentArticle.getDeficientNutrients(), newName));
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});

		avoidNutrientEditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
					if(avoidNutrientTable.getSelectedRow()>=0){
						String newName = JOptionPane.showInputDialog("Change to"); 
						if(newName != null){
							controller.editFromOntology(currentArticle.getConditionName(), currentArticle.getExcessNutrients().get(avoidNutrientTable.getSelectedRow()), newName, 2);
							currentArticle.setExcessNutrients(editRow(avoidNutrientTable, currentArticle.getExcessNutrients(), newName));
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		synonymsAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
						String newName = JOptionPane.showInputDialog("Add item"); 
						if(newName != null){
							controller.addToOntology(currentArticle.getConditionName(), newName, 5);
							currentArticle.setSynonyms(addRow(synonymsTable, currentArticle.getSynonyms(), newName));
						}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		symptomsAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
						String newName = JOptionPane.showInputDialog("Add item"); 
						if(newName != null){
							controller.addToOntology(currentArticle.getConditionName(), newName, 4);
							currentArticle.setSymptoms(addRow(symptomsTable, currentArticle.getSymptoms(), newName));
						}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		needFoodAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
						Object[] foodList = controller.getFoodList().toArray();
						Food newFood = (Food) JOptionPane.showInputDialog(null, "Add item", "Add food", JOptionPane.QUESTION_MESSAGE, null, foodList, foodList[0]); 
						if(newFood != null){
							String newName = newFood.getName();
							controller.addToOntology(currentArticle.getConditionName(), newName, 3);
							currentArticle.setNeededFood(addRow(needFoodTable, currentArticle.getNeededFood(), newName));
						}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});

		avoidFoodAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
						Object[] foodList = controller.getFoodList().toArray();
						Food newFood = (Food) JOptionPane.showInputDialog(null, "Add item", "Add food", JOptionPane.QUESTION_MESSAGE, null, foodList, foodList[0]); 
						if(newFood != null){
							String newName = newFood.getName();
							controller.addToOntology(currentArticle.getConditionName(), newName, 0);
							currentArticle.setAvoidFood(addRow(avoidFoodTable, currentArticle.getAvoidFood(), newName));
						}
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});
		
		needNutrientAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
						String newName = JOptionPane.showInputDialog("Add item"); 
						if(newName != null){
							controller.addToOntology(currentArticle.getConditionName(), newName, 1);
							currentArticle.setDeficientNutrients(addRow(needNutrientTable, currentArticle.getDeficientNutrients(), newName));
						}
					
				}
				else{
					JOptionPane.showMessageDialog(null, "No article selected");
				}
			}
		});

		avoidNutrientAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentArticle != null){
						String newName = JOptionPane.showInputDialog("Add item"); 
						if(newName != null){
							controller.addToOntology(currentArticle.getConditionName(), newName, 2);
							currentArticle.setExcessNutrients(addRow(avoidNutrientTable, currentArticle.getExcessNutrients(), newName));
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "No item selected");
					}
				
			}
		});
	}
	
	private List<String> removeRow(JTable table, List<String> list){
		int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this item?", "Remove Item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(opt == JOptionPane.YES_OPTION){
			//if(table.getSelectedRow()>=0){
				list.remove(table.getSelectedRow());
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.removeRow(table.getSelectedRow());
				table.setModel(model);
				model.fireTableDataChanged();
			/*}
			else{
				JOptionPane.showMessageDialog(null, "No item selected");
			}*/
		}
		return list;
	}
	
	private List<String> editRow(JTable table, List<String> list, String newName){
		
		int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to modify this item?", "Edit Item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(opt == JOptionPane.YES_OPTION){
			//if(table.getSelectedRow()>=0){
				list.set(table.getSelectedRow(), newName);
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setValueAt(newName, table.getSelectedRow(), 0);
				table.setModel(model);
				model.fireTableDataChanged();
			/*}
			else{
				JOptionPane.showMessageDialog(null, "No item selected");
			}*/
		}
		
		return list;
	}
	
	private List<String> addRow(JTable table, List<String> list, String newName){
		
		int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to add this item?", "Edit Item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(opt == JOptionPane.YES_OPTION){
			//if(table.getSelectedRow()>=0){
				list.add(newName);
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{newName});
				table.setModel(model);
				model.fireTableDataChanged();
			/*}
			else{
				JOptionPane.showMessageDialog(null, "No item selected");
			}*/
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
