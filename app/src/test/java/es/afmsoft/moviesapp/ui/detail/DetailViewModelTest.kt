package es.afmsoft.moviesapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.afmsoft.domain.Movie
import es.afmsoft.usecases.GetMovie
import es.afmsoft.usecases.ToggleFavouriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    val mockedMovie = Movie(
        0,
        0,
        "Title",
        "",
        "",
        false,
        "Overview"
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getMovie: GetMovie

    @Mock
    lateinit var toggleFavouriteMovie: ToggleFavouriteMovie

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        vm = DetailViewModel(1, getMovie, toggleFavouriteMovie, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the movie`() {

        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(getMovie.invoke(1)).thenReturn(movie)

            vm.model.observeForever(observer)
            verify(observer).onChanged(DetailViewModel.UiModel(movie))
        }
    }

    @Test
    fun `when favorite clicked, the toggleMovieFavorite use case is invoked`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(getMovie.invoke(1)).thenReturn(movie)
            whenever(toggleFavouriteMovie.invoke(movie)).thenReturn(movie.copy(favourite = !movie.favourite))
            vm.model.observeForever(observer)

            vm.onToggleFavouriteMovieClicked()
            verify(observer). onChanged(DetailViewModel.UiModel(movie))
        }
    }
}
