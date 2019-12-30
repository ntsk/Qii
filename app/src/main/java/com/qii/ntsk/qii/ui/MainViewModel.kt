package com.qii.ntsk.qii.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qii.ntsk.qii.model.entity.Token
import com.qii.ntsk.qii.model.repository.TokenRepository
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val tokenRepository = TokenRepository()

    private val tokenObserver: MutableLiveData<Token> = MutableLiveData()

    val errorObserver: MutableLiveData<Int> = MutableLiveData()

    internal fun getToken(code: String): MutableLiveData<Token> {
        viewModelScope.launch {
            val response = tokenRepository.getToken(code)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                tokenObserver.postValue(body)
            } else {
                errorObserver.postValue(response.code())
            }
        }
        return tokenObserver
    }
}