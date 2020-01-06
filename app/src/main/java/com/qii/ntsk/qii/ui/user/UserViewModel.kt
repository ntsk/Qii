package com.qii.ntsk.qii.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.model.datasource.AuthenticatedUserItemsDataSource
import com.qii.ntsk.qii.model.datasource.PostsDataSource
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.User
import com.qii.ntsk.qii.model.holder.TokenHolder
import com.qii.ntsk.qii.model.repository.UserRepository
import com.qii.ntsk.qii.model.state.NetworkState
import kotlinx.coroutines.launch

class UserViewModel(app: Application) : AndroidViewModel(app) {

    val loginStateLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val userLiveData: MutableLiveData<User> = MutableLiveData()

    private var userPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()

    private val userRepository = UserRepository()

    val userPostsNetWorkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        if (TokenHolder().load() != null) {
            loginStateLiveData.postValue(true)
        } else {
            loginStateLiveData.postValue(false)
        }
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

    internal fun fetchAuthenticatedUserItems(): LiveData<PagedList<Post>> {
        val factory = AuthenticatedUserItemsDataSource.Factory(viewModelScope, userPostsNetWorkStateObserver)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        userPostsLiveData = LivePagedListBuilder(factory, config).build()
        return userPostsLiveData
    }

    internal fun logout() {
        TokenHolder().delete()
        loginStateLiveData.postValue(false)
    }
}