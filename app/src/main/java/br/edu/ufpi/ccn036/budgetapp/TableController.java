package br.edu.ufpi.ccn036.budgetapp;

import br.edu.ufpi.ccn036.budgetapp.App.BudgetItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
