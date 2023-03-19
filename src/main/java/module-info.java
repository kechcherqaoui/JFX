module com.test.product {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.test.product to javafx.fxml;
    opens com.test.product.controller to javafx.fxml;
    opens com.test.product.model to javafx.base;
    exports com.test.product;
}