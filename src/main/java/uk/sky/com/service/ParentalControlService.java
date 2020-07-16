package uk.sky.com.service;

import java.util.List;
import java.util.Optional;
import uk.sky.com.exception.TechnicalFailureException;
import uk.sky.com.exception.TitleNotFoundException;

public class ParentalControlService {

  private MovieService movieService;
  private List<String> parentalControls;

  public ParentalControlService(final MovieService movieService,
      final List<String> parentalControls) {
    this.movieService = movieService;
    this.parentalControls = parentalControls;
  }

  /**
   * Check if customer allowed to watch this movie based on provided parental control.
   * @param customerParentalControl
   * @param movieId
   * @return boolean
   * @throws TitleNotFoundException
   * @throws TechnicalFailureException
   */
  public boolean isCustomerAllowedToWatchMovie(final String customerParentalControl, final String movieId)
      throws TitleNotFoundException, TechnicalFailureException {

    final String movieParentalControl = movieService.getParentalControlLevel(movieId);
    final int indexOfClientParentalControl = parentalControls.indexOf(customerParentalControl) + 1;
    final Optional<String> first = parentalControls.stream()
        .limit(indexOfClientParentalControl)
        .filter(i -> i.equalsIgnoreCase(movieParentalControl)).findFirst();

    return first.isPresent();
  }
}
