package com.qii.ntsk.qii.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.ActivityMainBinding
import com.qii.ntsk.qii.favorite.FavoritesFragment
import com.qii.ntsk.qii.model.holder.TokenHolder
import com.qii.ntsk.qii.ui.home.HomeFragment
import com.qii.ntsk.qii.ui.search.SearchFragment
import com.qii.ntsk.qii.ui.user.UserFragment

class MainActivity : AppCompatActivity() {
    companion object {
        private const val FRAGMENT_ID = R.id.fragment_container
        private const val AUTHORIZE_CALLBACK_URI = "qii://callback"
    }

    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(this.application).create(MainViewModel::class.java) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setDefaultFragment(HomeFragment())

        binding.toolbar.title = "Qiita"

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.nav_favorite -> {
                    replaceFragment(FavoritesFragment())
                }
                R.id.nav_stock -> {
                    replaceFragment(SearchFragment())
                }
                R.id.nav_user -> {
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
        if (uri != null && uri.toString().contains(AUTHORIZE_CALLBACK_URI)) {
            authorize(uri)
        }

        super.onNewIntent(intent)
    }

    private fun authorize(uri: Uri) {
        val code = uri.getQueryParameter("code") ?: return
        viewModel.getToken(code).observe(this, Observer {
            val tokenHolder = TokenHolder()
            tokenHolder.save(it.token)
            Toast.makeText(this, R.string.message_success_login, Toast.LENGTH_LONG).show()
        })

        viewModel.errorObserver.observe(this, Observer {
            Toast.makeText(this, R.string.message_fail_login, Toast.LENGTH_LONG).show()
        })
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
