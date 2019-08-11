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
class GetMovieTest {

    @Mock
    lateinit var movieRepository: MoviesRepository

    lateinit var getMovie: GetMovie

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
        getMovie = GetMovie(movieRepository)
    }

    @Test
    fun `invoke GetMovie`() {
        runBlocking {
            val localMovie = mockedMovie.copy(1)

            whenever(movieRepository.getMovie(anyInt())).thenReturn(localMovie)

            val result = getMovie.invoke(1)

            Assert.assertEquals(localMovie, result)
        }
    }
}
