package net.ldasilva.expertisetest.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.ldasilva.expertisetest.data.db.entity.MovieEntry

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(moviesEntries: List<MovieEntry>)

    @Query("select * from movie order by popularity desc limit 20")
    fun getMostPopular(): List<MovieEntry>

    @Query("select * from movie order by voteAverage desc limit 20")
    fun getTopRated(): List<MovieEntry>

    @Query("select * from movie where releaseDate >= date('now') order by releaseDate asc limit 20")
    fun getUpcomings(): List<MovieEntry>

    @Query("select * from movie where id = :id")
    fun getMovie(id: Long): LiveData<MovieEntry>

    @Query("select * from movie where title like '%' || :query || '%' or overview like '%' || :query || '%' order by title asc limit 20")
    fun getMovies(query: String): List<MovieEntry>
}