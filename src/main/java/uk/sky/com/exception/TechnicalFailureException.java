package uk.sky.com.exception;

public class TechnicalFailureException extends RuntimeException {

  public TechnicalFailureException(final String message) {
    super(message);
  }
}
