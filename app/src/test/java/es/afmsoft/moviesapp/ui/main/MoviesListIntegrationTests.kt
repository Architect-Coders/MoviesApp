package es.afmsoft.moviesapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import es.afmsoft.data.sources.LocalDataSource
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.FakeLocalDataSource
import es.afmsoft.moviesapp.defaultFakeMovies
import es.afmsoft.moviesapp.initMockedDi
import es.afmsoft.usecases.GetPopularMovies
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesListIntegrationTests : AutoCloseKoinTest() {

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
    lateinit var observer: Observer<MoviesListViewModel.UiModel>

    private lateinit var vm: MoviesListViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MoviesListViewModel(get(), get()) }
            factory { GetPopularMovies(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(MoviesListViewModel.UiModel.Content(defaultFakeMovies))
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(MoviesListViewModel.UiModel.Content(fakeLocalMovies))
    }
}
