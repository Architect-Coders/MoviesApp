package es.afmsoft.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.afmsoft.data.repository.MoviesRepository
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.Scope
import es.afmsoft.usecases.GetMovie
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int, private val getMovie: GetMovie) :
    ViewModel(), Scope by Scope.Impl {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private fun refresh() =
    launch {
        _model.value = UiModel.Loading
        _model.value = UiModel.Content(getMovie.invoke(movieId))
    }


    init {
        initScope()
    }

    override fun onCleared() {
        closeScope()
        super.onCleared()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movie: Movie?) : UiModel()
    }
}