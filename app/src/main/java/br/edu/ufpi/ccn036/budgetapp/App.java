package br.edu.ufpi.ccn036.budgetapp;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ufpi.ccn036.budgetapp.algorithms.Knapsack;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class App extends Application {
	private static final String APP_NAME = "Atividade Prática";
	
	private Scene scene;
	
	private boolean hasBeenModified = false;

	private Knapsack knapsack = new Knapsack(0).toggleMemoization();

	public void init(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Parent root;
		// this.stage = stage;
		
		try {
			root = FXMLLoader.load(getClass().getResource("/main.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		scene = new Scene(root, 600, 425);
		
		stage.setTitle(APP_NAME);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
		stage.setOnCloseRequest(evt -> {
			if(hasBeenModified) {
				Alert info = new Alert(AlertType.WARNING,
						"O arquivo atual não foi salvo. Deseja mesmo sair?",
						ButtonType.YES,
						ButtonType.NO);
				info.showAndWait();
				if(info.getResult() != ButtonType.YES)
					evt.consume();
			}
		});
		
		setupGUI();
		setupMenu();
		setupButtons();
	}
	
	private void setupGUI() {
		// Impede a movimentação do divisor pelo usuário
		SplitPane splitPane = (SplitPane) scene.lookup("#splitPane");
		splitPane.lookupAll(".split-pane-divider").stream()
			.forEach(div -> div.setMouseTransparent(true));
		
		// Esses campos de texto não devem ser alterados pelo usuário
		TextField quantityTextField = (TextField) scene.lookup("#ansQuantityTextField");
		quantityTextField.setEditable(false);
		TextField costTextField = (TextField) scene.lookup("#ansCostTextField");
		costTextField.setEditable(false);
		TextField ansBenefitTextField = (TextField) scene.lookup("#ansBenefitTextField");
		ansBenefitTextField.setEditable(false);
	}
	
	private void setupMenu() {
		MenuBar menu = (MenuBar) scene.lookup("#menuBar");
		menu.getMenus().get(1).getItems().get(0).setOnAction(evt -> about());
	}
	
	private void setupButtons() {
		TextField nameTextField = (TextField) scene.lookup("#titleTextField");
		TextField costTextField = (TextField) scene.lookup("#costTextField");
		TextField benefitTextField = (TextField) scene.lookup("#benefitTextField");
		TextField budgetTextField = (TextField) scene.lookup("#budgetTextField");
		TextField ansQuantityTextField = (TextField) scene.lookup("#ansQuantityTextField");
		TextField ansCostTextField = (TextField) scene.lookup("#ansCostTextField");
		TextField ansBenefitTextField = (TextField) scene.lookup("#ansBenefitTextField");
		
		Button addButton = (Button) scene.lookup("#addButton");
		addButton.setOnAction(evt -> {
			String name = nameTextField.getText();
			double cost = Double.parseDouble(costTextField.getText()),
				   benefit = Double.parseDouble(benefitTextField.getText());
			if(!(name.length() > 0)) return;
			knapsack.addBudgetItem(new BudgetItem(name, cost, benefit));
			updateSolution("#itemTable", knapsack.getItems().stream().map(x -> (BudgetItem) x).collect(Collectors.toList()));
		});
		
		Button changeButton = (Button) scene.lookup("#changeButton");
		changeButton.setOnAction(evt -> {
			double budget = Double.parseDouble(budgetTextField.getText());
			knapsack.setCapacity(budget);
		});
		
		Button solveButton = (Button) scene.lookup("#solveButton");
		solveButton.setOnAction(evt -> {
			long time = System.nanoTime();
			var solution = knapsack.solve();
			double totalCost = solution.stream().mapToDouble(x -> x.getWeight()).sum(),
					totalBenefit = solution.stream().mapToDouble(x -> x.getValue()).sum();
			time -= System.nanoTime();
			time *= -1;
			System.out.println("Took " + time + "ns to solve");
			updateSolution("#solutionTable", solution.stream().map(x -> (BudgetItem) x).collect(Collectors.toList()));
			ansQuantityTextField.setText(String.valueOf(solution.size()));
			ansCostTextField.setText(String.valueOf(totalCost));
			ansBenefitTextField.setText(String.valueOf(totalBenefit));
		});

		 Button removeButton = (Button) scene.lookup("#removeButton");
		 removeButton.setOnMouseClicked(evt -> {
			try {
				removeUpdate("#itemTable");
				var solution = knapsack.solve();
				double totalCost = solution.stream().mapToDouble(x -> x.getWeight()).sum(),
						totalBenefit = solution.stream().mapToDouble(x -> x.getValue()).sum();
				updateSolution("#solutionTable", solution.stream().map(x -> (BudgetItem) x).collect(Collectors.toList()));
				ansQuantityTextField.setText(String.valueOf(solution.size()));
				ansCostTextField.setText(String.valueOf(totalCost));
				ansBenefitTextField.setText(String.valueOf(totalBenefit));
				System.out.println("tam. sol: " + solution.size() + " tam. itens: " + knapsack.getItems().size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 });
	}
	
	private void about() {
		Alert info = new Alert(AlertType.INFORMATION,
				"UFPI - DC/CCN036 - Projeto e Análise de Algoritmos\n"
				+ "Problema do Orçamento com base no Problema da Mochila Binária\n"
				+ "Atividade Principal");
		info.setTitle("Sobre");
		info.showAndWait();
	}
	
	private void updateSolution(String list, List<BudgetItem> items) {
		@SuppressWarnings("unchecked")
		TableView<BudgetItem> table = (TableView<BudgetItem>) scene.lookup(list);
		ObservableList<BudgetItem> itemList = FXCollections.observableArrayList();
		items.stream().forEach(itemList::add);
		table.setItems(itemList);
		table.refresh();
	}
	
	private void removeUpdate(String list){
		@SuppressWarnings("unchecked")
		TableView<BudgetItem> table = (TableView<BudgetItem>) scene.lookup(list);
		int selected = table.getSelectionModel().getSelectedIndex();
		System.out.println("selected: " + selected);
		try{
			knapsack.remove(selected);
		}catch (Exception e){
			e.printStackTrace();
		};
		List<BudgetItem> items = knapsack.getItems().stream().map(x -> (BudgetItem) x).collect(Collectors.toList());
		ObservableList<BudgetItem> itemList = FXCollections.observableArrayList();
		items.forEach(itemList::add);
		table.setItems(itemList);
		table.refresh();
	}
	
}