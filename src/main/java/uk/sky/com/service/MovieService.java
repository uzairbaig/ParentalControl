package uk.sky.com.service;

import uk.sky.com.exception.TechnicalFailureException;
import uk.sky.com.exception.TitleNotFoundException;

public interface MovieService {

  String getParentalControlLevel(String movieId)
      throws TitleNotFoundException, TechnicalFailureException;
}
