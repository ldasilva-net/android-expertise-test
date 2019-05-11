package net.ldasilva.expertisetest.data.network.response

import com.google.gson.annotations.SerializedName
import net.ldasilva.expertisetest.data.db.entity.MovieEntry

data class MoviesResponse(
    @SerializedName("results")
    val movies: List<MovieEntry>
)