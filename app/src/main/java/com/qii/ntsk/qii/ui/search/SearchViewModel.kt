package com.qii.ntsk.qii.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.model.datasource.PostsDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.model.repository.TagsRepository
import com.qii.ntsk.qii.model.state.NetworkState
import kotlinx.coroutines.launch

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private val tagsRepository = TagsRepository()

    private val tagsLiveData: MutableLiveData<Tags> = MutableLiveData()

    private var searchObserver: LiveData<PagedList<Post>> = MutableLiveData()

    var networkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    internal fun fetchTags(): MutableLiveData<Tags> {
        viewModelScope.launch {
            val tagsResponse = tagsRepository.fetch("1", "50", "count")
            val tagsBody = tagsResponse.body()
            if (tagsResponse.isSuccessful && tagsBody != null) {
                tagsLiveData.postValue(Tags(tagsBody))
            }
        }
        return tagsLiveData
    }

    internal fun search(query: String): LiveData<PagedList<Post>> {
        val factory = PostsDataSource.Factory(viewModelScope, networkStateObserver, query)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        searchObserver = LivePagedListBuilder(factory, config).build()
        return searchObserver
    }
}