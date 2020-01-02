package com.qii.ntsk.qii.ui.home.popularposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentPopularPostsBinding

class PopularPostsFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(PopularPostsViewModel::class.java) }
    private val controller = PopularPostsController()
    private lateinit var binding: FragmentPopularPostsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_popular_posts, container, false)
        binding = FragmentPopularPostsBinding.bind(view)

        val recyclerView = binding.fragmentPopularPostsRecyclerView
        recyclerView.setController(controller)
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        controller.addModelBuildListener {
            if (binding.isLoading) {
                binding.isLoading = false
                recyclerView.scheduleLayoutAnimation()
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.isLoading = true
        viewModel.popularPostsObserver.observe(viewLifecycleOwner, Observer { pagedList ->
            controller.submitList(pagedList)
            controller.requestModelBuild()
        })

        viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
            binding.showError = true
            binding.isLoading = false
        })
    }
}