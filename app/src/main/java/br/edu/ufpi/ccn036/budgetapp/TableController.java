package br.edu.ufpi.ccn036.budgetapp;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableController {
	
	@FXML
	TableView<BudgetItem> tableView;
	
	@FXML
	TableColumn<BudgetItem, String> title;
	
	@FXML
	TableColumn<BudgetItem, Double> weight;
	
	@FXML
	TableColumn<BudgetItem, Double> value;
	
}
