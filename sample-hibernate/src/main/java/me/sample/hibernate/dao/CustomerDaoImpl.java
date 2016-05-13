package me.sample.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import me.sample.hibernate.model.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao<Customer> implements CustomerDao {

/*  @SuppressWarnings("unchecked")
  @Override
  public List<Customer> findAll() {
    Criteria criteria = getSession().createCriteria(Customer.class);
    return (List<Customer>) criteria.list();
  }*/

  @Override
  public void deleteByLogin(String login) {
    Query query = getSession().createSQLQuery("delete from CUSTOMER where login = :login");
    query.setString("login", login);
    query.executeUpdate();
  }

  @Override
  public Customer findByLogin(String login) {
    Query query = getSession().getNamedQuery("findByLogin").setString("login", login);
    return (Customer) query.uniqueResult();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Customer> findByName(String name) {
    Criteria criteria = getSession().createCriteria(Customer.class);
    criteria.add(Restrictions.like("fullName", name));
    return criteria.list();
  }

}
