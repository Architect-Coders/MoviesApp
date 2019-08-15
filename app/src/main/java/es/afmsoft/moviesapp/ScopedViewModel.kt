package es.afmsoft.moviesapp

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

abstract class ScopedViewModel (uiDispatcher: CoroutineDispatcher): ViewModel(), Scope by Scope.Impl(uiDispatcher) {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        closeScope()
        super.onCleared()
    }
}