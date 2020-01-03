package com.qii.ntsk.qii.ui.home.popularposts

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

class PopularPostsViewModel(app: Application) : AndroidViewModel(app) {
    var popularPostsObserver: LiveData<PagedList<Post>> = MutableLiveData()
    var networkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        load()
    }

    private fun load() {
        val factory = PostsDataSource.Factory(viewModelScope, networkStateObserver, "stock:>30")
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setPrefetchDistance(40)
                .setMaxSize(100)
                .build()
        popularPostsObserver = LivePagedListBuilder(factory, config).build()
    }
}