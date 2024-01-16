package com.beck;

import com.beck.dao.CustomerDao;
import com.beck.model.Customer;

public class CustomerRunner {

    private static final CustomerDao customerDao = CustomerDao.getInstance();

    public static void main(String[] args) {
        Customer beck = new Customer("Beck","+71111","beck@mail.com","USA,1.street,No.15");
        saveCustomer(beck);


    }

    public static Customer saveCustomer(Customer customer){
        customer.setName(customer.getName());
        customer.setPhoneNumber(customer.getPhoneNumber());
        customer.setEmail(customer.getEmail());
        customer.setAddress(customer.getAddress());
        customerDao.save(customer);

        return customer;
    }

    public static void deleteCustomer(Integer id){
        customerDao.deleteById(id);
    }
}
