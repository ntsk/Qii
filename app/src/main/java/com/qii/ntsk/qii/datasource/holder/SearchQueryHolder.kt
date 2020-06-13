package com.qii.ntsk.qii.datasource.holder

import android.content.Context
import com.qii.ntsk.qii.QiiApp

class SearchQueryHolder {
    private val sharedPreference = QiiApp.instance.applicationContext.getSharedPreferences(SEARCH_QUERY_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val SEARCH_QUERY_PREF = "search_query_pref"
        private const val SEARCH_QUERY_PREF_KEY = "search_query_pref_key"
    }

    fun save(query: String) {
        sharedPreference.edit().putString(SEARCH_QUERY_PREF_KEY, query).apply()
    }

    fun load(): String? {
        return sharedPreference.getString(SEARCH_QUERY_PREF_KEY, null)
    }
}