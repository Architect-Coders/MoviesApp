package es.afmsoft.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.afmsoft.data.sources.LocalDataSource
import es.afmsoft.data.sources.RemoteDataSource
import es.afmsoft.domain.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var moviesRepository: MoviesRepository

    var apiKey = "8979fea4ecc57850778fa624133234d8"

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
        moviesRepository = MoviesRepository(localDataSource,
            remoteDataSource,
            apiKey,
            regionRepository)
    }

    @Test
    fun `getPopularMovies gets from local data source first`() {
        runBlocking {
            val localMovies = listOf(mockedMovie.copy(1))

            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)

            val result = moviesRepository.getPopularMovies()

            assertEquals(localMovies, result)
        }
    }

    @Test
    fun `getPopularMovies gets from remote data source if no local data`() {
        runBlocking {
            val localMovies = listOf(mockedMovie.copy(1))

            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getPopularMovies(apiKey, regionRepository)).thenReturn(localMovies)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)

            val result = moviesRepository.getPopularMovies()

            assertEquals(localMovies, result)
            verify(localDataSource).saveMovies(localMovies)
            verify(localDataSource).getPopularMovies()
        }
    }

    @Test
    fun `refreshMovies updates movies list`() {
        runBlocking {
            val localMovies = listOf(mockedMovie.copy(1))

            whenever(remoteDataSource.getPopularMovies(apiKey, regionRepository)).thenReturn(localMovies)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)

            val result = moviesRepository.refreshMovies()

            assertEquals(localMovies, result)
            verify(localDataSource).saveMovies(localMovies)
            verify(localDataSource).getPopularMovies()
        }
    }

    @Test
    fun `getMovie get movie by id`() {
        runBlocking {
            val movie = mockedMovie.copy(1)

            whenever(localDataSource.getMovie(anyInt())).thenReturn(movie)

            val result = moviesRepository.getMovie(1)

            assertEquals(movie, result)
            verify(localDataSource).getMovie(1)
        }
    }

    @Test
    fun `update updates a movie`() {
        runBlocking {
            val movie = mockedMovie.copy(1)

            moviesRepository.update(movie)

            verify(localDataSource).update(movie)
        }
    }
}
