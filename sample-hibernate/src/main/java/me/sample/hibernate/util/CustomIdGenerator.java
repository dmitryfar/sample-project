package me.sample.hibernate.util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomIdGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
    try {
      byte[] bytes = MessageDigest.getInstance("MD5").digest();
      StringBuffer sb = new StringBuffer();
      for (byte b : bytes) {
        sb.append(String.format("%02x", b & 0xff));
      }

      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

}
