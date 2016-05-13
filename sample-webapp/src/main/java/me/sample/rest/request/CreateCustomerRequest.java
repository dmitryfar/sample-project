package me.sample.rest.request;

import java.io.Serializable;

public class CreateCustomerRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  private String login;
  private String passwordHash;
  private String fullName;

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

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @Override
  public String toString() {
    return "CreateCustomerRequest [login=" + login + ", passwordHash=" + passwordHash + ", fullName=" + fullName + "]";
  }

}
