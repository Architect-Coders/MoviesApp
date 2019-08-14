package es.afmsoft.moviesapp

import es.afmsoft.domain.Movie
import org.junit.Test
import es.afmsoft.moviesapp.ui.model.Movie as UiMovie

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun movieClassTest() {
        var movieData = Movie(1, "movie", "backdrop", "poster", false, "overview")
        assertEquals("movie", movieData.title)
    }

    @Test
    fun moviesClassTest() {
        var movieData = UiMovie(1, "movie", "backdrop", "poster", "overview")
        assertEquals("movie", movieData.title)
    }}
