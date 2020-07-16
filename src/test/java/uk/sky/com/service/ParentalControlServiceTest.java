package uk.sky.com.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.sky.com.exception.TechnicalFailureException;
import uk.sky.com.exception.TitleNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ParentalControlServiceTest {

  private ParentalControlService parentalControlService;
  private List<String> parentalControls;

  @Mock
  private MovieService movieService;

  @BeforeEach
  public void setup() {
    parentalControls = Arrays.asList("U", "PG", "12", "15", "18");
    parentalControlService = new ParentalControlService(movieService, parentalControls);
  }

  //Acceptance criteria

  @DisplayName("When movie parental control is PG and customer's preferred parental control is U")
  @Test
  public void shouldAllowToWatchMovieWhenParentalControlIsEqualsToCustomerPreferredParentalControl() {
    //Given
    when(movieService.getParentalControlLevel("myMovie")).thenReturn("PG");
    //When
    final boolean isClientAllowToWatchMovie = parentalControlService
        .isCustomerAllowedToWatchMovie("PG", "myMovie");
    //then
    assertThat(isClientAllowToWatchMovie, is(true));
  }

  @DisplayName("When movie parental control is U and customer's preferred parental control is 15")
  @Test
  public void shouldAllowToWatchMovieWhenParentalControlIsLessThenCustomerPreferredParentalControl() {
    //Given
    when(movieService.getParentalControlLevel("myMovie")).thenReturn("U");
    //When
    final boolean isClientAllowToWatchMovie = parentalControlService
        .isCustomerAllowedToWatchMovie("15", "myMovie");
    //Then
    assertThat(isClientAllowToWatchMovie, is(true));
  }

  @DisplayName("When movie parental control is 18 and customer's preferred parental control is 12")
  @Test
  public void shouldAllowToWatchMovieWhenParentalControlIsGreaterThenCustomerPreferredParentalControl() {
    //Given
    when(movieService.getParentalControlLevel("myMovie")).thenReturn("18");
    //When
    final boolean isClientAllowToWatchMovie = parentalControlService
        .isCustomerAllowedToWatchMovie("12", "myMovie");
    //Then
    assertThat(isClientAllowToWatchMovie, is(false));
  }

  @DisplayName("When movie title is not available should throw Title not found exception")
  @Test
  public void shouldThrowTitleNotFoundExceptionWhenNoMovieFound() {
    //Given
    when(movieService.getParentalControlLevel("myMovie"))
        .thenThrow(new TitleNotFoundException("Unable to find movie"));
    //When //Then
    Assertions.assertThrows(TitleNotFoundException.class, () -> {
      parentalControlService.isCustomerAllowedToWatchMovie("PG", "myMovie");
    });
  }

  @DisplayName("When some technical failure occur should throw Technical Failure Exception")
  @Test
  public void shouldThrowTechnicalFailureExceptionWhenMovieServiceIsDown() {
    //Given
    when(movieService.getParentalControlLevel("myMovie"))
        .thenThrow(new TechnicalFailureException("Unable to access service at the moment"));
    //When //Then
    Assertions.assertThrows(TechnicalFailureException.class, () -> {
      parentalControlService.isCustomerAllowedToWatchMovie("PG", "myMovie");
    });

  }
}