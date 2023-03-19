package com.test.product.dao;

import com.test.product.dao.ProductRepository;
import com.test.product.dao.connectiondb.DBConnection;
import com.test.product.model.Category;
import com.test.product.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private List<Product> prepareProducts(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (rs.next()){
            products.add(new Product(
                  rs.getLong("id"),
                  rs.getString("name"),
                  rs.getString("reference"),
                  rs.getDouble("price"),
                  new Category(
                        rs.getLong("id_category"),
                        rs.getString("categoryName")
                  )
            ));
        }
        return products;
    }

    @Override
    public List<Product> findAllItems() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sqlQuery = "SELECT p.*, c.name as categoryName FROM product p INNER JOIN category c on p.id_category = c.id";
        try {
            ps = connection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prepareProducts(rs);
    }

    @Override
    public Product saveItem(Product product) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement;
        String sqlQuery = "INSERT INTO product(name, reference, price, id_category) VALUES (?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getReference());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setLong(4,product.getCategory().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public Product updateItem(Product product) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement;
        String sqlQuery = "UPDATE product set name = ?, reference = ?, price = ?, id_category = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getReference());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.setLong(5,product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public void deleteItem(Long id) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement;
        String sqlQuery = "DELETE FROM product WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByQuery(String query) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sqlQuery = "SELECT p.*, c.name as categoryName FROM product p INNER JOIN category c on p.id_category = c.id WHERE p.name like ? or p.reference like ? or p.price like ?";
        try {
            ps = connection.prepareStatement(sqlQuery);

            ps.setString(1,"%"+query+"%");
            ps.setString(2,"%"+query+"%");
            ps.setString(3,"%"+query+"%");
            rs = ps.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prepareProducts(rs);
    }
}
