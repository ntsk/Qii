package com.qii.ntsk.qii.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qii.ntsk.qii.datasource.holder.TokenHolder
import com.qii.ntsk.qii.model.state.LoginState

class SettingsViewModel() : ViewModel() {
    val loginStateLiveData: MutableLiveData<LoginState> = MutableLiveData()

    init {
        if (TokenHolder().load() != null) {
            loginStateLiveData.postValue(LoginState.LOGIN)
        } else {
            loginStateLiveData.postValue(LoginState.LOGOUT)
        }
    }

    fun login() {

    }

    fun logout() {
        TokenHolder().delete()
        loginStateLiveData.postValue(LoginState.LOGOUT)
    }
}