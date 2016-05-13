package me.sample.hibernate;

import java.io.Serializable;

/**
 * An exception indicating that the required object is already exist.
 */
public class DuplicateObjectException extends SampleException {

  private static final long serialVersionUID = 1L;

  private Serializable id;

  public DuplicateObjectException(String message) {
    super(message);
  }

  public DuplicateObjectException(String message, Serializable id) {
    this(message, id, null);
  }

  public DuplicateObjectException(Serializable id) {
    this(null, id, null);
  }

  public DuplicateObjectException(String message, Serializable id, Throwable cause) {
    super(message, cause);
    this.id = id;
  }

  /**
   * The id of the object that was found.
   */
  public Serializable getId() {
    return id;
  }
}
