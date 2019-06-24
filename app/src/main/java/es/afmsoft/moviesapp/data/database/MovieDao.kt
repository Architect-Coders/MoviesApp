package es.afmsoft.moviesapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("select * from movie")
    fun getAll(): List<Movie>

    @Query("select * from movie where id = :movieId")
    fun getMovie(movieId: Int): Movie?

    @Query("select count(id) from movie")
    fun moviesCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)
}
