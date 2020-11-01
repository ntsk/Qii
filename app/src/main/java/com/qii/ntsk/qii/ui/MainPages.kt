package com.qii.ntsk.qii.ui

import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.ui.home.HomeFragment
import com.qii.ntsk.qii.ui.search.SearchFragment
import com.qii.ntsk.qii.ui.stocks.StocksFragment
import com.qii.ntsk.qii.ui.user.UserFragment

enum class MainPages {
    HOME,
    SEARCH,
    STOCKS,
    USER;

    val fragment: Fragment
        get() = when (this) {
            HOME -> HomeFragment()
            SEARCH -> SearchFragment()
            STOCKS -> StocksFragment()
            USER -> UserFragment()
        }
}