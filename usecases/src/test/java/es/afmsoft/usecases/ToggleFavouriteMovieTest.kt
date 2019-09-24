package es.afmsoft.usecases

import com.nhaarman.mockitokotlin2.*
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
class ToggleFavouriteMovieTest {

    @Mock
    lateinit var movieRepository: MoviesRepository

    lateinit var toggleFavouriteMovie: ToggleFavouriteMovie

    val mockedMovie = Movie(
        0,
        0,
        "Title",
        "",
        "",
        false,
        "Overview"
    )

    @Before
    fun setUp() {
        toggleFavouriteMovie = ToggleFavouriteMovie(movieRepository)
    }

    @Test
    fun `invoke ToggleFavouriteMovie`() {
        runBlocking {
            val localMovie = mockedMovie.copy(1)

            val result = toggleFavouriteMovie.invoke(localMovie)

            Assert.assertEquals(true, result.favourite)
            verify(movieRepository).update(any())

            reset(movieRepository)
            val result2 = toggleFavouriteMovie.invoke(result)
            Assert.assertEquals(false, result2.favourite)
            verify(movieRepository).update(any())
        }
    }
}