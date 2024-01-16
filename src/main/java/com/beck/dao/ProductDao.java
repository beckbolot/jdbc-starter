package com.beck.dao;

import com.beck.model.Category;
import com.beck.model.Product;
import com.beck.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Integer,Product> {

    private static final ProductDao INSTANCE = new ProductDao();

    private static final String SAVE_SQL_PRODUCT = """
            INSERT INTO products(name,price,category_id) values(?,?,?);
            """;

    private static final String UPDATE_PRODUCT = """
            update products 
            set name = ?,
            price = ?,
            category_id = ?
            where id = ?
            """;

    private static final String PRODUCT_BY_ID_SQL = """
            select id, name, price, category_id
            from products
            where id =?;
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM products
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            select products.id, products.name, products.price, products.category_id,
            c.id,c.name
            from products
            left join category c on c.id = products.category_id
            """;

    private static final String JOIN_SQL = """
            select p.id,
            p.name,
            p.price,
            p.category_id,
            c.id,
            c.name
            from products as p
            inner join category as c
            on p.id = c.id;
            """;

    private ProductDao() {
    }
    @Override
    public Product save(Product product) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getCategoryId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt("id"));
            }
            System.out.println(product + ": Successfully saved to the Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
    @Override
    public void update(Product product) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getCategory().getId());
            preparedStatement.setInt(4, product.getId());


            preparedStatement.executeUpdate();
            System.out.println(product + " Successfully updated");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Product> findById(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PRODUCT_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = buildProduct(resultSet);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Product with: " + id + " Successfully deleted from Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Product> findAll() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(buildProduct(resultSet));

            }

            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void joinExample() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(JOIN_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Product_id\tProduct_name\tProduct_price\tProduct_Category_id\tCategory_id\tCategory_name");
            while (resultSet.next()) {
                int p_id = resultSet.getInt(1);
                String p_name = resultSet.getString(2);
                BigDecimal p_price = resultSet.getBigDecimal(3);
                int category_id = resultSet.getInt(4);
                int c_id = resultSet.getInt(5);
                String c_name = resultSet.getString(6);
                System.out.println(p_id + "\t" + p_name + "\t" + p_price + "\t" + category_id + "\t" + c_id + "\t" + c_name);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Product buildProduct(ResultSet resultSet) throws SQLException {
        Category category = new Category(
                resultSet.getInt("id"),
                resultSet.getString("name")

        );
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("category_id"));
        }


    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
