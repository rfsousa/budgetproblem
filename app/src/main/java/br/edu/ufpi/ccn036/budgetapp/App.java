package br.edu.ufpi.ccn036.budgetapp;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class App extends Application {
	private static final String APP_NAME = "Atividade Prática";
	
	private Scene scene;
	private Stage stage;
	private File openFile;
	
	private boolean hasBeenModified = false;

	public void init(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Parent root;
		this.stage = stage;
		
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
//		setupMenu();
//		setupLists();
//		setupButtons();
	}
	
	private void setupGUI() {
		// Impede a movimentação do divisor pelo usuário
		SplitPane splitPane = (SplitPane) scene.lookup("#splitPane");
		splitPane.lookupAll(".split-pane-divider").stream()
			.forEach(div -> div.setMouseTransparent(true));
		
		TableView table = (TableView) scene.lookup("#itemTable");
		table.columnResizePolicyProperty().set(false);
		
		// Esses campos de texto não devem ser alterados pelo usuário
//		TextField lengthTextField = (TextField) scene.lookup("#lengthTextField");
//		lengthTextField.setEditable(false);
//		TextField quantityTextField = (TextField) scene.lookup("#quantityTextField");
//		quantityTextField.setEditable(false);
//		TextField depthnessTextField = (TextField) scene.lookup("#depthnessTextField");
//		depthnessTextField.setEditable(false);
//		TextField selectedTextField = (TextField) scene.lookup("#selectedTextField");
//		selectedTextField.setEditable(false);
//		TextField heightTextField = (TextField) scene.lookup("#heightTextField");
//		heightTextField.setEditable(false);
	}
	
//	private void setupMenu() {
//		MenuBar menu = (MenuBar) scene.lookup("#menuBar");
//		
//		// Importar arquivo
//		menu.getMenus().get(0).getItems().get(0).setOnAction(evt -> importFile());
//		// Exportar arquivo
//		menu.getMenus().get(0).getItems().get(1).setOnAction(evt -> exportFile());
//		// Sair
//		menu.getMenus().get(0).getItems().get(2).setOnAction(evt -> exit());
//		// Sobre
//		menu.getMenus().get(1).getItems().get(0).setOnAction(evt -> about());
//	}
//	
//	private void setupLists() {
//		TextField selectedTextField = (TextField) scene.lookup("#selectedTextField");
//		TextField depthnessTextField = (TextField) scene.lookup("#depthnessTextField");
//		
//		@SuppressWarnings("unchecked")
//		ListView<String> preorder = (ListView<String>) scene.lookup("#preorderList");
//		preorder.setOnMouseClicked(evt -> {
//			if(preorder.getSelectionModel().getSelectedItem() == null) return;
//			String selectedItem = preorder.getSelectionModel().getSelectedItem();
//			selectedTextField.setText(selectedItem);
//			depthnessTextField.setText(String.valueOf(people.nodeDepth(selectedItem)));
//		});
//		
//		@SuppressWarnings("unchecked")
//		ListView<String> inorder = (ListView<String>) scene.lookup("#inorderList");
//		inorder.setOnMouseClicked(evt -> {
//			if(inorder.getSelectionModel().getSelectedItem() == null) return;
//			String selectedItem = inorder.getSelectionModel().getSelectedItem();
//			selectedTextField.setText(selectedItem);
//			depthnessTextField.setText(String.valueOf(people.nodeDepth(selectedItem)));
//		});
//		
//		@SuppressWarnings("unchecked")
//		ListView<String> postorder = (ListView<String>) scene.lookup("#postorderList");
//		postorder.setOnMouseClicked(evt -> {
//			if(postorder.getSelectionModel().getSelectedItem() == null) return;
//			String selectedItem = postorder.getSelectionModel().getSelectedItem();
//			selectedTextField.setText(selectedItem);
//			depthnessTextField.setText(String.valueOf(people.nodeDepth(selectedItem)));
//		});
//		
//	}
//	
//	private void setupButtons() {
//		TextField nameTextField = (TextField) scene.lookup("#nameTextField");
//		
//		Button addButton = (Button) scene.lookup("#addButton");
//		addButton.setOnAction(evt -> {
//			String name = nameTextField.getText();
//			if(!(name.length() > 0)) return;
//			if(people.search(name) == null) {
//				names.add(name);
//				people.insert(name);
//				updateLists();
//				updateFields();
//				hasBeenModified = true;
//			}
//		});
//		
//		Button removeButton = (Button) scene.lookup("#removeButton");
//		removeButton.setOnAction(evt -> {
//			String name = nameTextField.getText();
//			if(names.remove(name)) initTree();
//		});
//	}
//	
//	private void importFile() {
//		if(hasBeenModified) {
//			Alert info = new Alert(AlertType.WARNING,
//					"O arquivo atual não foi salvo. Deseja mesmo importar outro?",
//					ButtonType.YES,
//					ButtonType.NO);
//			info.showAndWait();
//			if(info.getResult() != ButtonType.YES)
//				return;
//		}
//		
//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setTitle("Importar arquivo");
//		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Arquivo de nomes", "*.txt"));
//		File file = fileChooser.showOpenDialog(stage);
//		if(file == null) return;
//		try {
//			names = FileHelper.readLinesFromFile(file);
//		} catch (IOException e) {
//			Alert error = new Alert(AlertType.ERROR, e.getLocalizedMessage());
//			error.showAndWait();
//			return;
//		}
//		
//		initTree();
//		hasBeenModified = false;
//		openFile = file;
//		stage.setTitle(APP_NAME + " - " + file.toString());
//	}
//	
//	private void exportFile() {
//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setTitle("Importar arquivo");
//		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Arquivo de nomes", "*.txt"));
//		File file = null;
//		if(openFile == null) {
//			fileChooser.showSaveDialog(stage);
//			if(file == null) return;
//		}
//		file = openFile;
//		
//		try {
//			FileHelper.writeLinesToFile(file, names);
//		} catch (IOException e) {
//			Alert error = new Alert(AlertType.ERROR, e.getLocalizedMessage());
//			error.showAndWait();
//			return;
//		}
//		
//		hasBeenModified = false;
//		stage.setTitle(APP_NAME + " - " + file.toString());
//	}
//	
//	private void exit() {
//		if(hasBeenModified) {
//			Alert info = new Alert(AlertType.WARNING,
//					"O arquivo atual não foi salvo. Deseja mesmo sair?",
//					ButtonType.YES,
//					ButtonType.NO);
//			info.showAndWait();
//			if(info.getResult() != ButtonType.YES)
//				return;
//		}
//		System.exit(0);
//	}
//	
//	private void about() {
//		Alert info = new Alert(AlertType.INFORMATION,
//				"UFPI - DC/CCN022 - Esturutras de Dados\n"
//				+ "Grupo 02, Árvore de Busca Binária\n"
//				+ "Atividade Prática 03");
//		info.setTitle("Sobre");
//		info.showAndWait();
//	}
//	
//	private void initTree() {
//		people = new BinarySearchTree<>();
//		names.stream().forEach(people::insert);
//		updateLists();
//		updateFields();
//	}
//	
//	private void updateFields() {
//		TextField lengthTextField = (TextField) scene.lookup("#lengthTextField");
//		lengthTextField.setText(String.valueOf(people.internalPathLength()));
//		TextField quantityTextField = (TextField) scene.lookup("#quantityTextField");
//		quantityTextField.setText(String.valueOf(people.size()));
//		TextField heightTextField = (TextField) scene.lookup("#heightTextField");
//		heightTextField.setText(String.valueOf(people.height()));
//	}
//	
//	private void updateLists() {
//		updateList("#preorderList", bst -> bst.preorderList());
//		updateList("#inorderList", bst -> bst.inorderList());
//		updateList("#postorderList", bst -> bst.postorderList());
//	}
//	
//	private void updateList(String list, Function<BinarySearchTree<String>, List<String>> method) {
//		@SuppressWarnings("unchecked")
//		ListView<String> listView = (ListView<String>) scene.lookup(list);
//		ObservableList<String> itemList = FXCollections.observableArrayList();
//		
//		List<String> namesList = method.apply(people);
//		namesList.stream().forEach(itemList::add);
//		
//		listView.setItems(itemList);
//	}
	
}