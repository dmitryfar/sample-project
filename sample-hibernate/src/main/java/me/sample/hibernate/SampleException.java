package me.sample.hibernate;

/**
 * Runtime exception that is the superclass of all Sample exceptions.
 *
 */
public class SampleException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public SampleException(String message, Throwable cause) {
    super(message, cause);
  }

  public SampleException(String message) {
    super(message);
  }
}
