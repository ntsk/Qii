package com.qii.ntsk.qii.ui.home.popularposts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.model.datasource.PopularPostsDataSource
import com.qii.ntsk.qii.model.entity.Post

class PopularPostsViewModel(app: Application) : AndroidViewModel(app) {
    var popularPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()

    init {
        load()
    }

    private fun load() {
        val factory = PopularPostsDataSource.Factory(viewModelScope)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        popularPostsLiveData = LivePagedListBuilder(factory, config).build()
    }
}