package com.test.product.controller;

import com.test.product.dao.CategoryRepositoryImpl;
import com.test.product.dao.ProductRepositoryImpl;
import com.test.product.model.Category;
import com.test.product.model.Product;
import com.test.product.service.CatalogService;
import com.test.product.service.CatalogServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML private TextField textFieldSearch;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldRef;
    @FXML private TextField textFieldPrice;
    @FXML private ComboBox<Category> comboBoxCategory;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<String, Product> idCol;
    @FXML private TableColumn<String, Product> nameCol;
    @FXML private TableColumn<String, Product> referenceCol;
    @FXML private TableColumn<Double, Product> priceCol;
    @FXML private TableColumn<Category, Product> CategoryNameCol;

    ObservableList<Product> productsData = FXCollections.observableArrayList();
    ObservableList<Category> categoriesData = FXCollections.observableArrayList();

    private CatalogService catalogService;

    private void prepareTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        referenceCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        CategoryNameCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void listenToSearch(){
        textFieldSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            productsData.clear();
            try {
                productsData.addAll(catalogService.findByQuery(newValue));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void listenToTableClick(){
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, product) -> {
            if (product != null) {
                textFieldName.setText(product.getName());
                textFieldRef.setText(product.getReference());
                textFieldPrice.setText(String.valueOf(product.getPrice()));
                comboBoxCategory.setValue(product.getCategory());
            }
        });
    }

    private Product getSelectedProduct(){
        Product product = productTable.getSelectionModel().getSelectedItem();
        if(product == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a product first!");
            alert.show();
        }
        return null;
    }

    private boolean isValidData(){
        String name = textFieldName.getText();
        String reference = textFieldRef.getText();
        String price = textFieldPrice.getText();
        Category category = comboBoxCategory.getSelectionModel().getSelectedItem();

        if(name.trim().isBlank()
              || reference.trim().isBlank()
              || price.trim().isBlank()
              || category == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please make sure to enter all the data accurately and completely.");
            alert.show();

            return false;
        }

        try{
            Double.parseDouble(price);
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please ensure that the price entered is a valid number.");
            alert.show();
            return false;
        }

        return true;
    }

    private void onClearData() {
        textFieldPrice.setText("");
        textFieldRef.setText("");
        textFieldName.setText("");
        comboBoxCategory.setValue(null);
    }

    @Override
    public void initialize(URL url,
                           ResourceBundle resourceBundle) {
        prepareTable();
        catalogService = new CatalogServiceImpl(
              new ProductRepositoryImpl(),
              new CategoryRepositoryImpl()
        );

        comboBoxCategory.setItems(categoriesData);
        productTable.setItems(productsData);

        try {
            categoriesData.setAll(catalogService.findAllCategories());
            productsData.setAll(catalogService.findAllProducts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        listenToTableClick();

        listenToSearch();
    }

    @FXML
    void onAddProduct() throws SQLException {
        if(!isValidData())
            return;

        Product product = new Product();
        product.setName(textFieldName.getText());
        product.setReference(textFieldRef.getText());
        product.setPrice(Double.parseDouble(textFieldPrice.getText()));
        product.setCategory(comboBoxCategory.getSelectionModel().getSelectedItem());
        this.catalogService.addProduct(product);
        onClearData();
        loadData();
    }

    @FXML
    void onDeleteProduct() throws SQLException {
        Product product = getSelectedProduct();
        if (product == null)
            return;
        catalogService.deleteProduct(product.getId());
        onClearData();
        loadData();
    }

    @FXML
    void onUpdateProduct() throws SQLException {
        if(!isValidData())
            return;

        Product product = getSelectedProduct();
        product.setName(textFieldName.getText());
        product.setReference(textFieldRef.getText());
        product.setPrice(Double.parseDouble(textFieldPrice.getText()));
        product.setCategory(comboBoxCategory.getSelectionModel().getSelectedItem());
        catalogService.updateProduct(product);
        onClearData();
        loadData();
    }

    void loadData() throws SQLException {
        productsData.clear();
        productsData.addAll(catalogService.findAllProducts());
    }
}
