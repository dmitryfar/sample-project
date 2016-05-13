package me.sample.hibernate.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<E> {

  @Autowired
  private SessionFactory sessionFactory;

  private Class<E> entityType;

  @SuppressWarnings("unchecked")
  public AbstractDao() {
    Type genericSuperclass = this.getClass().getGenericSuperclass();
    ParameterizedType pt = (ParameterizedType) genericSuperclass;
    Type entityType = pt.getActualTypeArguments()[0];
    this.entityType = (Class<E>) entityType;
  }

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  protected Session openSession() {
    return sessionFactory.openSession();
  }

  @SuppressWarnings("unchecked")
  public List<E> findAll() {
    Criteria criteria = getSession().createCriteria(entityType);
    return (List<E>) criteria.list();
  }

  public Serializable save(E entity) {
    return getSession().save(entity);
  }

  public void persist(E entity) {
    getSession().persist(entity);
  }

  public void delete(E entity) {
    getSession().delete(entity);
  }

  public void update(E entity) {
    getSession().update(entity);
  }
}
