package com.qii.ntsk.qii

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.qii.ntsk.qii.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val navController: NavController
        get() = findNavController(R.id.nav_host)

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)

        navController.addOnNavigatedListener { _, destination ->
            val isDrawer = destination.isDrawerDestination()
            binding.toolbar.setNavigationIcon(if (isDrawer) R.drawable.ic_menu else R.drawable.ic_back)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId ?: 0) {
        android.R.id.home -> {
            if (navController.currentDestination != null && navController.currentDestination!!.isDrawerDestination()) {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            } else {
                navController.navigateUp()
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun NavDestination.isDrawerDestination() = id == R.id.favoritesFragment || id == R.id.stocksFragment
}
