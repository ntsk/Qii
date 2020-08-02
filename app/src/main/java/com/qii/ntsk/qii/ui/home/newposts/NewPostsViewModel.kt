package com.qii.ntsk.qii.ui.home.newposts

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

class NewPostsViewModel @ViewModelInject constructor(
        private val repository: PostsRepository
) : ViewModel() {
    var newPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()
    var netWorkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        load()
    }

    private fun load() {
        val factory = PostsDataSource.Factory(repository, viewModelScope, netWorkStateObserver, null)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        newPostsLiveData = LivePagedListBuilder(factory, config).build()
    }
}