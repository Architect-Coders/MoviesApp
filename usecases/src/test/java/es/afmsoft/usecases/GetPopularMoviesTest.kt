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
class GetPopularMoviesTest {

    @Mock
    lateinit var movieRepository: MoviesRepository

    lateinit var getPopularMovies: GetPopularMovies

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
        getPopularMovies = GetPopularMovies(movieRepository)
    }

    @Test
    fun `invoke GetPopularMovies`() {
        runBlocking {
            val localMovies = listOf(mockedMovie.copy(1))

            whenever(movieRepository.getPopularMovies()).thenReturn(localMovies)

            val result = getPopularMovies.invoke()

            Assert.assertEquals(localMovies, result)
        }
    }
}
