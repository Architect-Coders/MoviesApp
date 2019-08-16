package es.afmsoft.moviesapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import es.afmsoft.data.sources.LocalDataSource
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.FakeLocalDataSource
import es.afmsoft.moviesapp.defaultFakeMovies
import es.afmsoft.moviesapp.initMockedDi
import es.afmsoft.usecases.GetMovie
import es.afmsoft.usecases.ToggleFavouriteMovie
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests : AutoCloseKoinTest() {

    val mockedMovie = Movie(
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
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var vm: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
            factory { GetMovie(get()) }
            factory { ToggleFavouriteMovie(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(1) }
        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = defaultFakeMovies
    }

    @Test
    fun `observing LiveData finds the movie`() {
        vm.model.observeForever(observer)

        verify(observer).onChanged(DetailViewModel.UiModel(mockedMovie.copy(1)))
    }

    @Test
    fun `favorite is updated in local data source`() {
        vm.model.observeForever(observer)

        vm.onToggleFavouriteMovieClicked()

        runBlocking {
            assertTrue(localDataSource.getMovie(1).favourite)
        }
    }
}
