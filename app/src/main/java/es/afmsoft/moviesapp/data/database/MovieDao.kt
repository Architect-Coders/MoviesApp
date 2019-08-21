package es.afmsoft.moviesapp.data.database

import androidx.room.*

@Dao
interface MovieDao {
    @Query("select * from movie")
    fun getAll(): List<Movie>

    @Query("select * from movie where movieId = :movieId")
    fun getMovie(movieId: Int): Movie?

    @Query("select count(id) from movie")
    fun moviesCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun delete(movie: Movie)
}
