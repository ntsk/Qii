package com.qii.ntsk.qii.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.qii.ntsk.qii.datasource.holder.TokenHolder
import com.qii.ntsk.qii.datasource.paging.AuthenticatedUserItemsDataSource
import com.qii.ntsk.qii.datasource.repository.UserRepositoryImpl
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.model.entity.User
import com.qii.ntsk.qii.model.state.LoginState
import com.qii.ntsk.qii.model.state.NetworkState
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(
        private val userRepository: UserRepositoryImpl
) : ViewModel() {

    val loginStateLiveData: MutableLiveData<LoginState> = MutableLiveData()

    private val userLiveData: MutableLiveData<User> = MutableLiveData()

    private var userPostsLiveData: LiveData<PagedList<Post>> = MutableLiveData()

    val userPostsNetWorkStateObserver: MutableLiveData<NetworkState> = MutableLiveData()

    init {
        if (TokenHolder().load() != null) {
            loginStateLiveData.postValue(LoginState.LOGIN)
        } else {
            loginStateLiveData.postValue(LoginState.LOGOUT)
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
        val factory = AuthenticatedUserItemsDataSource.Factory(userRepository, viewModelScope, userPostsNetWorkStateObserver)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setMaxSize(100)
                .build()
        userPostsLiveData = LivePagedListBuilder(factory, config).build()
        return userPostsLiveData
    }

    internal fun deleteToken() {
        TokenHolder().delete()
        loginStateLiveData.postValue(LoginState.LOGOUT)
    }
}