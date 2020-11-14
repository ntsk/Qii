package com.qii.ntsk.qii.ui.main

import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.ui.home.HomeFragment
import com.qii.ntsk.qii.ui.search.SearchFragment
import com.qii.ntsk.qii.ui.settings.SettingsFragment
import com.qii.ntsk.qii.ui.user.UserFragment

enum class MainPages {
    HOME,
    SEARCH,
    USER,
    SETTINGS;

    val fragment: Fragment
        get() = when (this) {
            HOME -> HomeFragment()
            SEARCH -> SearchFragment()
            USER -> UserFragment()
            SETTINGS -> SettingsFragment()
        }
}