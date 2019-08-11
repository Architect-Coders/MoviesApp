package es.afmsoft.usecases

import com.nhaarman.mockitokotlin2.whenever
import es.afmsoft.data.repository.MoviesRepository
import es.afmsoft.domain.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RefreshMoviesTest {

    @Mock
    lateinit var movieRepository: MoviesRepository

    lateinit var refreshMovies: RefreshMovies

    val mockedMovie = Movie(
        0,
        "Title",
        "",
        "",
        false,
        "Overview"
    )

    @Before
    fun setUp() {
        refreshMovies = RefreshMovies(movieRepository)
    }

    @Test
    fun `invoke RefreshMovies`() {
        runBlocking {
            val localMovies = listOf(mockedMovie.copy(1))

            whenever(movieRepository.refreshMovies()).thenReturn(localMovies)

            val result = refreshMovies.invoke()

            Assert.assertEquals(localMovies, result)
        }
    }
}