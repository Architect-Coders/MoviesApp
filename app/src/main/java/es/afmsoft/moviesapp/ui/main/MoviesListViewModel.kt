package es.afmsoft.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.Scope
import es.afmsoft.moviesapp.ScopedViewModel
import es.afmsoft.usecases.GetPopularMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val getPopularMovies: GetPopularMovies,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
    get() {
        if (_model.value == null) refresh()
        return _model
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getPopularMovies.invoke())
        }
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    fun onListRefresh() {
        launch {
            _model.value = UiModel.Content(getPopularMovies.invoke())
        }
    }

    sealed class UiModel {
        object Loading : UiModel()
        object RequestLocationPermission : UiModel()
        data class Content(val movies: List<Movie>) : UiModel()
        data class Navigation(val movie: Movie) : UiModel()
    }
}
