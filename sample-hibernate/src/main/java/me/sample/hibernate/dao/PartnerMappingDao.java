package me.sample.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import me.sample.hibernate.model.PartnerMapping;

public interface PartnerMappingDao {

  Serializable save(PartnerMapping partnerMapping);

  void update(PartnerMapping partnerMapping);

  List<PartnerMapping> findAll();

  List<PartnerMapping> findByCustomerId(String customerId);

  List<PartnerMapping> findByPartnerId(String parthnerId);

  PartnerMapping findByPartnerAccountId(String parthnerId, String accountId);

}
