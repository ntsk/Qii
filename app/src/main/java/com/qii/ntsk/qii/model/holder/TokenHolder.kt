package com.qii.ntsk.qii.model.holder

import android.content.Context
import com.qii.ntsk.qii.QiiApp

class TokenHolder {
    private val sharedPreference = QiiApp.instance.applicationContext.getSharedPreferences(TOKEN_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_PREF = "token_pref"
        private const val TOKEN_PREF_KEY = "token_pref_key"
    }

    fun save(token: String) {
        sharedPreference.edit().putString(TOKEN_PREF_KEY, token).apply()
    }

    fun load(): String? {
        return sharedPreference.getString(TOKEN_PREF_KEY, null)
    }

    fun delete() {
        sharedPreference.edit().remove(TOKEN_PREF_KEY).apply()
    }
}