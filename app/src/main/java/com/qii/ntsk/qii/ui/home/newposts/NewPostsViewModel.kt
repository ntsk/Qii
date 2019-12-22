package com.qii.ntsk.qii.ui.home.newposts

import android.app.Application
import android.support.v4.app.INotificationSideChannel
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.model.datasource.NewPostsDataSource
import com.qii.ntsk.qii.model.entity.Post

class NewPostsViewModel(app: Application) : AndroidViewModel(app) {
    var newPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()
    var errorObserver: MutableLiveData<Int> = MutableLiveData()

    init {
        load()
    }

    private fun load() {
        val factory = NewPostsDataSource.Factory(viewModelScope, errorObserver)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        newPostsLiveData = LivePagedListBuilder(factory, config).build()
    }
}