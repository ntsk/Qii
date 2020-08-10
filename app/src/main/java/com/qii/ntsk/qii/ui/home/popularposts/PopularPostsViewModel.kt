package com.qii.ntsk.qii.ui.home.popularposts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.datasource.paging.PostsDataSource
import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.state.NetworkState

class PopularPostsViewModel @ViewModelInject constructor(
        private val repository: PostsRepository
) : ViewModel() {
    var popularPostsObserver: LiveData<PagedList<Post>> = MutableLiveData()
    var networkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        load()
    }

    private fun load() {
        val factory = PostsDataSource.Factory(repository, viewModelScope, networkStateObserver, "stock:>30")
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        popularPostsObserver = LivePagedListBuilder(factory, config).build()
    }
}