package com.qii.ntsk.qii.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.ActivityMainBinding
import com.qii.ntsk.qii.datasource.holder.TokenHolder
import com.qii.ntsk.qii.datasource.repository.TokenRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val AUTHORIZE_CALLBACK_URI = "qii://callback"
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var tokenRepository: TokenRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val pagerAdapter = MainPagerAdapter(this)
        binding.mainViewPager.let {
            it.adapter = pagerAdapter
            it.offscreenPageLimit = pagerAdapter.itemCount
            it.isUserInputEnabled = false
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_home
        binding.bottomNavigation.menu.findItem(R.id.nav_home).isChecked = true
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    binding.mainViewPager.setCurrentItem(MainPages.HOME.ordinal, false)
                    true
                }
                R.id.nav_search -> {
                    binding.mainViewPager.setCurrentItem(MainPages.SEARCH.ordinal, false)
                    true
                }
                R.id.nav_user -> {
                    binding.mainViewPager.setCurrentItem(MainPages.USER.ordinal, false)
                    true
                }
                R.id.nav_settings -> {
                    binding.mainViewPager.setCurrentItem(MainPages.SETTINGS.ordinal, false)
                    true
                }
                else -> {
                    binding.mainViewPager.currentItem = MainPages.HOME.ordinal
                    true
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
            reload()
        })

        viewModel.errorObserver.observe(this, Observer {
            Toast.makeText(this, R.string.message_fail_login, Toast.LENGTH_LONG).show()
        })
    }

    fun reload() {
        binding.mainViewPager.adapter = MainPagerAdapter(this)
        binding.mainViewPager.adapter?.notifyDataSetChanged()
        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }

    class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        private val fragments = listOf(
                MainPages.HOME.fragment,
                MainPages.SEARCH.fragment,
                MainPages.USER.fragment,
                MainPages.SETTINGS.fragment
        )

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}
