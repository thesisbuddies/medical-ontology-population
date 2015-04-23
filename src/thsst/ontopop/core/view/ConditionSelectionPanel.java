package thsst.ontopop.core.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import thsst.ontopop.core.controller.ConditionSelectionPanelController;
import thsst.ontopop.validation.model.ArticleModel;
import thsst.ontopop.validation.view.OntologyDetailsPanel;

public class ConditionSelectionPanel extends JPanel{
	private ConditionSelectionPanelController controller;
	
	private JTable conditionListTable;
	private List<ArticleModel> conditionList;
	private JLabel conditionListLabel;
	private JScrollPane conditionListScrollPane;
	private ListSelectionListener tableListener;
	
	private JButton removeConditionButton;
	//private JButton addConditionButton;
	
	private static ConditionSelectionPanel instance;
	
	public static ConditionSelectionPanel getInstance(){
		if(instance == null){
			instance = new ConditionSelectionPanel();
		}
		
		return instance;
	}
	
	public ConditionSelectionPanel(){
		controller = new ConditionSelectionPanelController();
		
		initializePanel();
		initializeButtons();
		initializeListener();
		initializeLabel();
		initializeTable();
	}
	
	private void initializePanel(){
		setLayout(null);
		//setSize(250, 750);
	}
	
	private void initializeButtons(){
		removeConditionButton = new JButton("Remove");
		removeConditionButton.setBounds(130, 540, 100, 25);
		add(removeConditionButton);
		
		/*addConditionButton = new JButton("Add");
		addConditionButton.setBounds(15, 540, 100, 25);
		add(addConditionButton);*/
	}
	
	private void initializeLabel(){
		conditionListLabel = new JLabel("Condition List:");
		conditionListLabel.setBounds(10, 5, 200, 20);
		
		add(conditionListLabel);
	}
	
	private void initializeTable(){
		conditionListTable = new JTable();
		conditionListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		conditionListTable.getSelectionModel().addListSelectionListener(tableListener);
		
		conditionListScrollPane = new JScrollPane(conditionListTable);
		conditionListScrollPane.setBounds(3, 30, 240, 490);
		
		add(conditionListScrollPane);
	}
	
	private void initializeListener(){
		tableListener = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				controller.loadArticleDetails(conditionList.get(conditionListTable.getSelectedRow()));
			}
		};
		
		removeConditionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(conditionListTable.getSelectedRow()>=0){
					int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this condition from the list?", "Remove condition", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(opt == JOptionPane.YES_OPTION){
						ArticleModel selected = (ArticleModel)conditionListTable.getValueAt(conditionListTable.getSelectedRow(), 0);
						controller.deleteCondition(selected.getConditionName());
						removeRow();
					}
				}
				else{
					JOptionPane.showOptionDialog(null, "Not item selected!", "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, null, null);
				}
			}
		});
		
		/*addConditionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String newName = JOptionPane.showInputDialog("Condition name");
				if(newName != null){
					ArticleModel newCondition = new ArticleModel(null, "");
					newCondition.setConditionName(newName);
					newCondition.setAvoidFood(new ArrayList<String>());
					newCondition.setDeficientNutrients(new ArrayList<String>());
					newCondition.setExcessNutrients(new ArrayList<String>());
					newCondition.setNeededFood(new ArrayList<String>());
					newCondition.setSymptoms(new ArrayList<String>());
					newCondition.setSynonyms(new ArrayList<String>());
					controller.addCondition(newCondition);
				}
			}
		});*/
		
	}
	
	public void loadConditions(){
		conditionList = controller.getArticles();
		
		DefaultTableModel model = controller.generateTableModel(conditionList);
		conditionListTable.setModel(model);
		model.fireTableDataChanged();
		
		//conditionListTable.setRowSelectionInterval(0, 0);
	}
	
	public void removeRow(){
		if(conditionListTable.getSelectedRow()>=0){	
			conditionList.remove(conditionListTable.getSelectedRow());
			conditionListTable.getSelectionModel().removeListSelectionListener(tableListener);
			DefaultTableModel model = (DefaultTableModel) conditionListTable.getModel();
			model.removeRow(conditionListTable.getSelectedRow());
			conditionListTable.setModel(model);
			model.fireTableDataChanged();
			conditionListTable.getSelectionModel().addListSelectionListener(tableListener);
			EditInstancePanel.getInstance().resetDetailsPanel();
			OntologyDetailsPanel.getInstance().resetDetailsPanel();
		}
		else{
			JOptionPane.showMessageDialog(null, "No item selected");
		}
	}
	
	public void addRow(ArticleModel newCondition){
		conditionList.add(newCondition);
		conditionListTable.getSelectionModel().removeListSelectionListener(tableListener);
		DefaultTableModel model = (DefaultTableModel) conditionListTable.getModel();
		model.addRow(new Object[]{newCondition});
		conditionListTable.setModel(model);
		model.fireTableDataChanged();
		conditionListTable.getSelectionModel().addListSelectionListener(tableListener);
		EditInstancePanel.getInstance().resetDetailsPanel();
		OntologyDetailsPanel.getInstance().resetDetailsPanel();
	}
	
	private void addToOntology(){
		
	}
}
