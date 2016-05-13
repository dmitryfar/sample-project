package me.sample.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import me.sample.hibernate.model.PartnerMapping;

@Repository("partnerMappingDao")
public class PartnerMappingDaoImpl  extends AbstractDao<PartnerMapping> implements PartnerMappingDao {

  @Override
  public List<PartnerMapping> findAll() {
    Criteria criteria = getSession().createCriteria(PartnerMapping.class);
    return (List<PartnerMapping>) criteria.list();
  }

  @Override
  public List<PartnerMapping> findByCustomerId(String customerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<PartnerMapping> findByPartnerId(String parthnerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PartnerMapping findByPartnerAccountId(String parthnerId, String accountId) {
    // TODO Auto-generated method stub
    return null;
  }

}
