package com.qii.ntsk.qii.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.datasource.paging.PostsDataSource
import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.datasource.repository.TagsRepository
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.model.state.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
        private val postsRepository: PostsRepository,
        private val tagsRepository: TagsRepository
) : ViewModel() {
    private val tagsLiveData: MutableLiveData<Tags> = MutableLiveData()

    private var searchObserver: LiveData<PagedList<Post>> = MutableLiveData()

    var networkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    var tags : Tags = Tags(mutableListOf())

    var searchWord: String = ""

    var selectedTags: MutableList<Tag> = mutableListOf()

    internal fun fetchTags(): MutableLiveData<Tags> {
        viewModelScope.launch(Dispatchers.IO) {
            val tagsResponse = tagsRepository.fetch("1", "50", "count")
            val tagsBody = tagsResponse.body()
            if (tagsResponse.isSuccessful && tagsBody != null) {
                tags = Tags(tagsBody)
                tagsLiveData.postValue(tags)
            }
        }
        return tagsLiveData
    }

    internal fun search(query: String): LiveData<PagedList<Post>> {
        val factory = PostsDataSource.Factory(postsRepository, viewModelScope, networkStateObserver, query)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        searchObserver = LivePagedListBuilder(factory, config).build()
        return searchObserver
    }
}