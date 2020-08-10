package com.qii.ntsk.qii.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qii.ntsk.qii.datasource.repository.TokenRepository
import com.qii.ntsk.qii.model.entity.Token
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
        private val tokenRepository: TokenRepository
) : ViewModel() {

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