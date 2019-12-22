package com.qii.ntsk.qii.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
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

        val pagerAdapter = HomePagerAdapter(childFragmentManager)
        binding.homeViewPager.adapter = pagerAdapter
        binding.homeViewPager.offscreenPageLimit = pagerAdapter.count
        binding.homeTab.setupWithViewPager(binding.homeViewPager)
    }
}

class HomePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val titles = arrayOf("Popular", "New")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PopularPostsFragment()
            1 -> NewPostsFragment()
            else -> PopularPostsFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}