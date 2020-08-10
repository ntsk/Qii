package com.qii.ntsk.qii.ui.stocks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.datasource.paging.StocksDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.User
import com.qii.ntsk.qii.datasource.holder.TokenHolder
import com.qii.ntsk.qii.datasource.repository.UserRepository
import com.qii.ntsk.qii.model.state.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StocksViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
) : ViewModel() {
    val loginStateLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var netWorkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    private val userLiveData: MutableLiveData<User> = MutableLiveData()

    private var stocksPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()

    init {
        if (TokenHolder().load() != null) {
            loginStateLiveData.postValue(true)
        } else {
            loginStateLiveData.postValue(false)
        }
    }

    internal fun fetchStocks(userId: String?): LiveData<PagedList<Post>> {
        if (userId == null) return stocksPostsLiveData
        val factory = StocksDataSource.Factory(userRepository, viewModelScope, netWorkStateObserver, userId)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        stocksPostsLiveData = LivePagedListBuilder(factory, config).build()
        return stocksPostsLiveData
    }

    internal fun fetchAuthenticatedUser(): MutableLiveData<User> {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.getAuthenticatedUser()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                userLiveData.postValue(body)
            }
        }
        return userLiveData
    }
}