package sg.nrk.openmoviedb.net

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    @SerializedName("Search") val search:T?,
    val totalResults:String?,
    val Response:Boolean?)