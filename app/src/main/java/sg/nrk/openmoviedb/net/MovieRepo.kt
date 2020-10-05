package sg.nrk.openmoviedb.net

import android.util.Log
import sg.nrk.openmoviedb.model.Movie


class MovieRepo {
    private val movieService: MovieService by lazy { NetModule.provideMovieService() } // inject

    suspend fun getMovieList(keyword: String, type: String): List<Movie> =
        try {
            movieService.getSearchResult(keyword, type).search ?: emptyList()
        } catch (e: Exception) {
            Log.e("MovieRepo", e.toString())
            emptyList()
        }


    suspend fun getMovieInfo(id: String):Movie = movieService.getMovieInfo(id)
}