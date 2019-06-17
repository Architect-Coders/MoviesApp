package es.afmsoft.moviesapp

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

interface Scope : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var job: Job

    fun initScope() {
        job = SupervisorJob()
    }

    fun closeScope() {
        job.cancel()
    }

    object Impl : Scope {
        override lateinit var job: Job
    }
}
