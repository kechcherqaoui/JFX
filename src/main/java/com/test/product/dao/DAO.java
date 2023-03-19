package com.test.product.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T, U>{
    List<T> findAllItems() throws SQLException;
    T saveItem(T object);
    T updateItem(T object);
    void deleteItem(U id);

}
