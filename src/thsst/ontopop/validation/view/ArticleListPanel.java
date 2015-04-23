package thsst.ontopop.validation.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import thsst.ontopop.validation.controller.ArticleListPanelController;
import thsst.ontopop.validation.model.ArticleModel;

public class ArticleListPanel extends JPanel{
	
	private ArticleListPanelController controller;
	private JTable articleListTable;
	private ArrayList<ArticleModel> articleList;
	private JLabel articleListLabel;
	private JScrollPane articleListScrollPane;
	private ListSelectionListener tableListener;
	
	private JButton addToOntologyButton;
	private JButton removeArticleButton;
	
	private static ArticleListPanel instance;
	
	public static ArticleListPanel getInstance(){
		if(instance == null){
			instance = new ArticleListPanel();
		}
		return instance;
	}
	
	public ArticleListPanel(){
		controller = new ArticleListPanelController();
		
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
		addToOntologyButton = new JButton("Validate");
		addToOntologyButton.setBounds(15, 540, 100, 25);
		add(addToOntologyButton);
		
		removeArticleButton = new JButton("Remove");
		removeArticleButton.setBounds(130, 540, 100, 25);
		add(removeArticleButton);
	}
	
	private void initializeLabel(){
		articleListLabel = new JLabel("Article List:");
		articleListLabel.setBounds(10, 5, 200, 20);
		
		add(articleListLabel);
	}
	
	private void initializeTable(){
		articleListTable = new JTable();
		articleListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		articleListTable.getSelectionModel().addListSelectionListener(tableListener);
		
		articleListScrollPane = new JScrollPane(articleListTable);
		articleListScrollPane.setBounds(3, 30, 240, 490);
		
		add(articleListScrollPane);
	}
	
	private void initializeListener(){
		tableListener = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				controller.loadArticleDetails(articleList.get(articleListTable.getSelectedRow()));
			}
		};
		
		removeArticleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(articleListTable.getSelectedRow()>=0){
					int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this article from the list?", "Remove article", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(opt == JOptionPane.YES_OPTION){
						removeRow();
					}
				}
				else{
					JOptionPane.showOptionDialog(null, "Not item selected!", "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, null, null);
				}
			}
		});
		
		addToOntologyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(articleListTable.getSelectedRow()>=0){
					int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to add this item to the ontology?", "Add to ontology", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(opt == JOptionPane.YES_OPTION){
						try {
							controller.addTonOntology(articleList.get(articleListTable.getSelectedRow()));
						} catch (TransformerConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TransformerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						removeRow();
						JOptionPane.showOptionDialog(null, "Article added to the ontology", "Add to ontology", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, null, null);
					}
				}
				else{
					JOptionPane.showOptionDialog(null, "Not item selected!", "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, null, null);
				}
			}
		});
	}
	
	public void loadArticles(String srcFolder){
		articleList = controller.getArticles(srcFolder);
		
		DefaultTableModel model = controller.generateTableModel(articleList);
		articleListTable.setModel(model);
		model.fireTableDataChanged();
		
		//articleListTable.setRowSelectionInterval(0, 0);
	}
	
	public void removeRow(){
		if(articleListTable.getSelectedRow()>=0){	
			articleList.remove(articleListTable.getSelectedRow());
			articleListTable.getSelectionModel().removeListSelectionListener(tableListener);
			DefaultTableModel model = (DefaultTableModel) articleListTable.getModel();
			model.removeRow(articleListTable.getSelectedRow());
			articleListTable.setModel(model);
			model.fireTableDataChanged();
			articleListTable.getSelectionModel().addListSelectionListener(tableListener);
			ArticleDetailsPanel.getInstance().resetDetailsPanel();
			OntologyDetailsPanel.getInstance().resetDetailsPanel();
		}
		else{
			JOptionPane.showMessageDialog(null, "No item selected");
		}
	}
	
	private void addToOntology(){
		
	}
	
}
