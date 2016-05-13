package me.sample.hibernate.model;

import java.io.Serializable;

public class Customer implements Serializable {
  private static final long serialVersionUID = 1L;

  private long customerId;
  private String fullName;
  private double balance;
  private CustomerStatus status;
  private String login;
  private String passwordHash;

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  // @Enumerated(EnumType.STRING)
  public CustomerStatus getStatus() {
    return status;
  }

  public void setStatus(CustomerStatus status) {
    this.status = status;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  @Override
  public String toString() {
    return "Customer [customerId=" + customerId + ", fullName=" + fullName + ", balance=" + balance + ", status="
        + status + ", login=" + login + ", passwordHash=" + passwordHash + "]";
  }

}
