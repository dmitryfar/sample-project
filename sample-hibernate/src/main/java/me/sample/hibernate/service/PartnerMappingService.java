package me.sample.hibernate.service;

import java.util.List;

import me.sample.hibernate.model.PartnerMapping;

public interface PartnerMappingService {

  void save(PartnerMapping partnerMapping);

  void update(PartnerMapping partnerMapping);

  List<PartnerMapping> findAll();

  List<PartnerMapping> findByCustomerId(String customerId);

  List<PartnerMapping> findByPartnerId(String parthnerId);

  PartnerMapping findByPartnerAccountId(String parthnerId, String accountId);

}
