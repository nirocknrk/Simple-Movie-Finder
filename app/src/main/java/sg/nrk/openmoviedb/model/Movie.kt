package sg.nrk.openmoviedb.model

import com.google.gson.annotations.SerializedName


data class Movie (
    @SerializedName("imdbID") var imdbID: String,
    @SerializedName("Title") var title: String,
    @SerializedName("Type") var type: String,
    @SerializedName("Poster") var poster:String,
    @SerializedName("Year") var year:String?,

    @SerializedName("Rated") var rated:String?,
    @SerializedName("Runtime") var runtime:String?,
    @SerializedName("Released") var released:String?,
    @SerializedName("Genre") var genre:String?,
    @SerializedName("Director") var director:String?,
    @SerializedName("Writer") var writer:String?,
    @SerializedName("Actors") var Actors:String?,
    @SerializedName("Language") var language:String?,
    @SerializedName("Country") var country:String?,
    @SerializedName("Awards") var awards:String?,
    @SerializedName("Plot") var plot:String?,
    @SerializedName("imdbRating") var imdbRating:String?,
    @SerializedName("imdbVotes") var imdbVotes:String?,
    @SerializedName("Production") var production:String?,
    @SerializedName("Metascore") var metascore:String?,
    @SerializedName("BoxOffice") var boxOffice:String?
){
    override fun toString(): String = "$title ($year) -:$imdbID"
}