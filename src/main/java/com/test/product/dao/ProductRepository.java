package com.test.product.dao;

import com.test.product.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository extends DAO<Product, Long>{

    List<Product> findByQuery(String query) throws SQLException;

}
