package com.test.product.service;

import com.test.product.model.Category;
import com.test.product.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface CatalogService {

    Product addProduct(Product product);
    Product updateProduct(Product product);
    List<Product> findByQuery(String query) throws SQLException;
    List<Product> findAllProducts() throws SQLException;
    void deleteProduct(Long id);
    List<Category> findAllCategories() throws SQLException;
}
