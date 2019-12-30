package com.qii.ntsk.qii

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.databinding.ActivityMainBinding
import com.qii.ntsk.qii.favorite.FavoritesFragment
import com.qii.ntsk.qii.ui.home.HomeFragment
import com.qii.ntsk.qii.ui.search.SearchFragment
import com.qii.ntsk.qii.user.UserFragment

class MainActivity : AppCompatActivity() {
    companion object {
        private const val FRAGMENT_ID = R.id.fragment_container
    }

    private val toolbarTitles = arrayOf("Home", "Favorite", "Search", "User")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setDefaultFragment(HomeFragment())
        binding.toolbar.title = toolbarTitles[0]

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    binding.toolbar.title = toolbarTitles[0]
                    replaceFragment(HomeFragment())
                }
                R.id.nav_favorite -> {
                    binding.toolbar.title = toolbarTitles[1]
                    replaceFragment(FavoritesFragment())
                }
                R.id.nav_stock -> {
                    binding.toolbar.title = toolbarTitles[2]
                    replaceFragment(SearchFragment())
                }
                R.id.nav_user -> {
                    binding.toolbar.title = toolbarTitles[3]
                    replaceFragment(UserFragment())
                }
                else -> {
                    replaceFragment(HomeFragment())
                }
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No") { dialogInterface, _ -> dialogInterface.dismiss() }
                .show()
    }

    override fun onNewIntent(intent: Intent?) {

        val uri = intent?.data
        if (uri != null) {
            uri.getQueryParameter("code")
        }

        super.onNewIntent(intent)
    }

    private fun <F> setDefaultFragment(fragment: F) where F : Fragment {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(FRAGMENT_ID, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun <F> replaceFragment(fragment: F): Boolean where F : Fragment {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(FRAGMENT_ID, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        return true
    }
}
