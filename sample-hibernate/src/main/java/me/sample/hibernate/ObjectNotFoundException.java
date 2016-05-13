package me.sample.hibernate;

/**
 * An exception indicating that the required object does not exist.
 */
public class ObjectNotFoundException extends SampleException {

  private static final long serialVersionUID = 1L;

  private Class<?> objectClass;

  public ObjectNotFoundException(String message) {
    super(message);
  }

  public ObjectNotFoundException(String message, Class<?> objectClass) {
    this(message, objectClass, null);
  }

  public ObjectNotFoundException(Class<?> objectClass) {
    this(null, objectClass, null);
  }

  public ObjectNotFoundException(String message, Class<?> objectClass, Throwable cause) {
    super(message, cause);
    this.objectClass = objectClass;
  }

  /**
   * The class of the object that was not found. Contains the interface-class of
   * the sample-object that was not found.
   */
  public Class<?> getObjectClass() {
    return objectClass;
  }
}
