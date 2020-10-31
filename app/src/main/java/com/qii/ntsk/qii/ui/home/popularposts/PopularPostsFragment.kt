package com.qii.ntsk.qii.ui.home.popularposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentPopularPostsBinding
import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.model.state.Status
import com.qii.ntsk.qii.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularPostsFragment : Fragment() {
    private val viewModel: PopularPostsViewModel by viewModels()
    private lateinit var controller: PopularPostsController
    private lateinit var binding: FragmentPopularPostsBinding

    @Inject
    lateinit var repository: PostsRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_popular_posts, container, false)
        binding = FragmentPopularPostsBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        controller = PopularPostsController { post ->
            val activity = requireActivity() as MainActivity
            activity.showPostDetail(post)
        }
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
