package me.sample.hibernate.service;

import java.util.List;

import me.sample.hibernate.model.Customer;

public interface CustomerService {

  List<Customer> findAll();

  void deleteByLogin(String login);

  Customer findByLogin(String login);

  List<Customer> findByName(String name);

  void update(Customer customer);

  long create(String login, String fullName, String passwordHash);
}
