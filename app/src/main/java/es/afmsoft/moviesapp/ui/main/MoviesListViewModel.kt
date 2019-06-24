package es.afmsoft.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.Scope
import es.afmsoft.usecases.GetPopularMovies
import kotlinx.coroutines.launch

class MoviesListViewModel(private val getPopularMovies: GetPopularMovies) : ViewModel(),
    Scope by Scope.Impl {

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

    init {
        initScope()
    }

    override fun onCleared() {
        closeScope()
        super.onCleared()
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    sealed class UiModel {
        object Loading : UiModel()
        object RequestLocationPermission : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
        class Navigation(val movie: Movie) : UiModel()
    }
}
