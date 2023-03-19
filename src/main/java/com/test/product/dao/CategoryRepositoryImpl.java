package com.test.product.dao;

import com.test.product.dao.connectiondb.DBConnection;
import com.test.product.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public List<Category> findAllItems(){
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sqlQuery = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                categories.add(new Category(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public Category saveItem(Category object) {
        return null;
    }

    @Override
    public Category updateItem(Category object) {
        return null;
    }

    @Override
    public void deleteItem(Long id) {

    }
}
