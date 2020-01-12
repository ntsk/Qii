package com.qii.ntsk.qii.ui.stocks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.model.datasource.PostsDataSource
import com.qii.ntsk.qii.model.datasource.StocksDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.User
import com.qii.ntsk.qii.model.holder.TokenHolder
import com.qii.ntsk.qii.model.repository.UserRepository
import com.qii.ntsk.qii.model.state.NetworkState
import kotlinx.coroutines.launch

class StocksViewModel(app: Application) : AndroidViewModel(app) {
    val loginStateLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var netWorkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    private val userLiveData: MutableLiveData<User> = MutableLiveData()

    private var stocksPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()

    private val userRepository = UserRepository()

    init {
        if (TokenHolder().load() != null) {
            loginStateLiveData.postValue(true)
        } else {
            loginStateLiveData.postValue(false)
        }
    }

    internal fun fetchStocks(userId: String?): LiveData<PagedList<Post>> {
        if (userId == null) return stocksPostsLiveData
        val factory = StocksDataSource.Factory(viewModelScope, netWorkStateObserver, userId)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        stocksPostsLiveData = LivePagedListBuilder(factory, config).build()
        return stocksPostsLiveData
    }

    internal fun fetchAuthenticatedUser(): MutableLiveData<User> {
        viewModelScope.launch {
            val response = userRepository.getAuthenticatedUser()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                userLiveData.postValue(body)
            }
        }
        return userLiveData
    }
}