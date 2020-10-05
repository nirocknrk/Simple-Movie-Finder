package sg.nrk.openmoviedb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sg.nrk.openmoviedb.model.Movie
import sg.nrk.openmoviedb.net.MovieRepo
import sg.nrk.openmoviedb.utils.Event

class MovieViewModel(app:Application):AndroidViewModel(app){

    private val movieRepo:MovieRepo by lazy { MovieRepo() }

    private val searchResultLD = MutableLiveData<Event<List<Movie>>>()

    private val progressStatusLD = MutableLiveData<Event<Boolean>>()

    var resetSearch: Boolean = true

    fun getSearchResults():LiveData<Event<List<Movie>>> = searchResultLD

    fun getProgressStatus():LiveData<Event<Boolean>> = progressStatusLD

    fun submitSearchKeyword(keyword: String) {
        resetSearch = false
        progressStatusLD.value = Event(true)
        viewModelScope.launch(Dispatchers.Default) {
            val searchResult = movieRepo.getMovieList(keyword, "movie")
            withContext(Dispatchers.Main) {
                searchResultLD.value = Event(searchResult)
                progressStatusLD.value = Event(false)
            }
        }
    }

    fun resetSearch() {
        this.resetSearch = true
        searchResultLD.value = Event(emptyList())
    }

    suspend fun getMovieInfo(id:String) = movieRepo.getMovieInfo(id)


}