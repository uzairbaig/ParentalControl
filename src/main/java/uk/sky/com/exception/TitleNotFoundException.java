package uk.sky.com.exception;

public class TitleNotFoundException extends RuntimeException {

  public TitleNotFoundException(final String message) {
    super(message);
  }
}
