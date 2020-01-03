package com.qii.ntsk.qii.ui.home.newposts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.model.datasource.PostsDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.state.NetworkState

class NewPostsViewModel(app: Application) : AndroidViewModel(app) {
    var newPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()
    var netWorkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        load()
    }

    private fun load() {
        val factory = PostsDataSource.Factory(viewModelScope, netWorkStateObserver, null)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        newPostsLiveData = LivePagedListBuilder(factory, config).build()
    }
}