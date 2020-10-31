package com.qii.ntsk.qii.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentHomeBinding
import com.qii.ntsk.qii.ui.home.newposts.NewPostsFragment
import com.qii.ntsk.qii.ui.home.popularposts.PopularPostsFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val pagerAdapter = HomePagerAdapter(this)
        binding.homeViewPager.let {
            it.adapter = pagerAdapter
            it.offscreenPageLimit = pagerAdapter.itemCount
        }
        val mediator = TabLayoutMediator(binding.homeTab, binding.homeViewPager) { tab, position ->
            tab.text = pagerAdapter.getItemTitle(position)
        }
        mediator.attach()
    }
}

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val titles = arrayOf("New", "Popular")

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewPostsFragment()
            1 -> PopularPostsFragment()
            else -> NewPostsFragment()
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    fun getItemTitle(position: Int): String {
        return titles[position]
    }
}