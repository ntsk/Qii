package com.qii.ntsk.qii.model

import android.content.Context

class TokenHolder(context: Context) {
    private val sharedPreference = context.getSharedPreferences(TOKEN_PREF, Context.MODE_PRIVATE)

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
}