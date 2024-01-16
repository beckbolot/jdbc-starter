package com.beck.dao;

import com.beck.model.Customer;
import com.beck.model.Product;
import com.beck.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements Dao<Integer,Customer> {

    private static final CustomerDao INSTANCE = new CustomerDao();

    private CustomerDao(){
    }

    private static final String SAVE_SQL = """
            insert into customers(name,phone_number,email,address)
            values(?,?,?,?)
            """;

    private static final String DELETE_SQL = """
            delete from 
            customers 
            where id = ?;
            """;

    @Override
    public Customer save(Customer customer){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhoneNumber());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getAddress());


            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                customer.setId(generatedKeys.getInt("id"));
            }
            System.out.println(customer + " Successfully saved to the Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public void update(Customer entity) {

    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            System.out.println(id + " Successfully deleted from Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Customer> findAll() {
        return null;
    }


    public static CustomerDao getInstance(){
        return INSTANCE;
    }


}
