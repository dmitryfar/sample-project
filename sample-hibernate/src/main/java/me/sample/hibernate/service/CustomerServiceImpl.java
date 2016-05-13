package me.sample.hibernate.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.sample.hibernate.DuplicateObjectException;
import me.sample.hibernate.dao.CustomerDao;
import me.sample.hibernate.model.Customer;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerDao customerDao;

  @Override
  public long create(String login, String fullName, String passwordHash) {
    Customer customer = findByLogin(login);
    if (customer != null) {
      throw new DuplicateObjectException(customer.getCustomerId());
    }

    customer = new Customer();
    customer.setLogin(login);
    customer.setFullName(fullName);
    customer.setPasswordHash(passwordHash);

    Serializable id = customerDao.save(customer);
    return (Long) id;
  }

  @Override
  public List<Customer> findAll() {
    return customerDao.findAll();
  }

  @Override
  public void deleteByLogin(String login) {
    customerDao.deleteByLogin(login);
  }

  @Override
  public Customer findByLogin(String login) {
    return customerDao.findByLogin(login);
  }

  @Override
  public List<Customer> findByName(String name) {
    return customerDao.findByName(name);
  }

  @Override
  public void update(Customer customer) {
    customerDao.update(customer);
  }

}
