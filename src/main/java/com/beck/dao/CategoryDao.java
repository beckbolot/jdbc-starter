package com.beck.dao;

import com.beck.model.Category;
import com.beck.util.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CategoryDao implements Dao<Integer, Category> {
    private static final CategoryDao INSTANCE = new CategoryDao();

    private static final String SAVE_CATEGORY_SQL = """
            insert into category(name) values(?); 
            """;

    private static final String FIND_BY_ID_SQL = """
            select *
            from category
            where id=?;
            """;
    private static final String DELETE_BY_ID_DQL = """
            delete from category
            where id=?;
            """;


    private CategoryDao() {

    }

    public Category save(Category category) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_CATEGORY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt("id"));
            }
            System.out.println(category + ": Successfully saved to Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public Optional<Category> findById(Integer id) {
        Category category = null;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(category);


    }



    @Override
    public void deleteById(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_DQL)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            System.out.println(id + " Successfully deleted from database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Category> findAll() {
        return null;
    }


    public static CategoryDao getInstance() {
        return INSTANCE;
    }
}
