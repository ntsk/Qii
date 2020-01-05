package com.qii.ntsk.qii.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qii.ntsk.qii.model.entity.User
import com.qii.ntsk.qii.model.holder.TokenHolder
import com.qii.ntsk.qii.model.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(app: Application) : AndroidViewModel(app) {

    val loginStateLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val userLiveData: MutableLiveData<User> = MutableLiveData()

    private val userRepository = UserRepository()

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

    internal fun logout() {
        TokenHolder().delete()
        loginStateLiveData.postValue(false)
    }
}