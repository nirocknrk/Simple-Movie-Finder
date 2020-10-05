package sg.nrk.openmoviedb.net

import retrofit2.http.GET
import retrofit2.http.Query
import sg.nrk.openmoviedb.model.Movie

interface MovieService {

    @GET(".")
    suspend fun getSearchResult(
        @Query("s")keyword: String,
        @Query("type")type:String): BaseResponse<List<Movie>>

    @GET(".")
    suspend fun getMovieInfo(@Query("i")id: String): Movie
}