package es.afmsoft.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.afmsoft.moviesapp.ui.model.Movie

class DetailViewModel(private val movie: Movie) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private fun refresh() {
        _model.value = UiModel.Loading
        _model.value = UiModel.Content(movie)
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movie: Movie) : UiModel()
    }
}