package com.test.product.service;

import com.test.product.dao.CategoryRepository;
import com.test.product.dao.ProductRepository;
import com.test.product.model.Category;
import com.test.product.model.Product;

import java.sql.SQLException;
import java.util.List;

public class CatalogServiceImpl implements CatalogService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CatalogServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.saveItem(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.updateItem(product);
    }

    @Override
    public List<Product> findByQuery(String query) throws SQLException {
        return productRepository.findByQuery(query);
    }

    @Override
    public List<Product> findAllProducts() throws SQLException {
        return productRepository.findAllItems();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteItem(id);
    }

    @Override
    public List<Category> findAllCategories() throws SQLException {
        return categoryRepository.findAllItems();
    }
}
