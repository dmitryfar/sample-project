package me.sample.hibernate.model;

import java.io.Serializable;

public class PartnerMapping implements Serializable {
  private static final long serialVersionUID = 1L;

  private long partnerMappingId;
  private String partnerId;
  private Customer customer;
  private String accountId;
  private String accountName;
  private String avatarUrl;

  public long getPartnerMappingId() {
    return partnerMappingId;
  }

  public void setPartnerMappingId(long partnerMappingId) {
    this.partnerMappingId = partnerMappingId;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override
  public String toString() {
    return "PartnerMapping [partnerMappingId=" + partnerMappingId + ", partnerId=" + partnerId + ", accountId="
        + accountId + ", accountName=" + accountName + ", avatarUrl=" + avatarUrl + "]";
  }

}
