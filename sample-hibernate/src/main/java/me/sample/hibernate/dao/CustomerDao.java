package me.sample.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import me.sample.hibernate.model.Customer;

public interface CustomerDao {

  Serializable save(Customer customer);

  void update(Customer customer);

  List<Customer> findAll();

  void deleteByLogin(String login);

  Customer findByLogin(String login);

  List<Customer> findByName(String name);

}
