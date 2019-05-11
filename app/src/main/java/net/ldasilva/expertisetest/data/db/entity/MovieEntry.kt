package net.ldasilva.expertisetest.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieEntry(
    @PrimaryKey
    val id: Long,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String
)