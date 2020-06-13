package com.qii.ntsk.qii.ui.home.popularposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentPopularPostsBinding
import com.qii.ntsk.qii.model.state.Status

class PopularPostsFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(PopularPostsViewModel::class.java) }
    private val controller = PopularPostsController()
    private lateinit var binding: FragmentPopularPostsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_popular_posts, container, false)
        binding = FragmentPopularPostsBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recyclerView = binding.fragmentPopularPostsRecyclerView
        recyclerView.setController(controller)
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        viewModel.popularPostsObserver.observe(viewLifecycleOwner, Observer { pagedList ->
            controller.submitList(pagedList)
            controller.requestModelBuild()
        })

        viewModel.networkStateObserver.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.PAGING -> {
                    controller.isLoading = true
                }
                else -> controller.isLoading = false
            }
        })
        return view
    }
}
