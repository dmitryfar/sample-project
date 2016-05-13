package me.sample.hibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.sample.hibernate.dao.PartnerMappingDao;
import me.sample.hibernate.model.PartnerMapping;

@Service("partnerMappingService")
@Transactional
public class PartnerMappingServiceImpl implements PartnerMappingService {

  @Autowired
  private PartnerMappingDao partnerMappingDao;

  @Override
  public void save(PartnerMapping partnerMapping) {
    partnerMappingDao.save(partnerMapping);
  }

  @Override
  public void update(PartnerMapping partnerMapping) {
    partnerMappingDao.update(partnerMapping);
  }

  @Override
  public List<PartnerMapping> findAll() {
    return partnerMappingDao.findAll();
  }

  @Override
  public List<PartnerMapping> findByCustomerId(String customerId) {
    return partnerMappingDao.findByCustomerId(customerId);
  }

  @Override
  public List<PartnerMapping> findByPartnerId(String parthnerId) {
    return partnerMappingDao.findByPartnerId(parthnerId);
  }

  @Override
  public PartnerMapping findByPartnerAccountId(String parthnerId, String accountId) {
    return partnerMappingDao.findByPartnerAccountId(parthnerId, accountId);
  }

}
