package com.qii.ntsk.qii.ui.home.newposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentNewPostsBinding
import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.model.state.Status
import com.qii.ntsk.qii.utils.CustomTabsStarter
import com.qii.ntsk.qii.ui.widget.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewPostsFragment : Fragment() {
    private val viewModel: NewPostsViewModel by viewModels()
    private var binding: FragmentNewPostsBinding by autoCleared()
    private lateinit var controller: NewPostsController

    @Inject
    lateinit var repository: PostsRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_new_posts, container, false)
        binding = FragmentNewPostsBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        controller = NewPostsController { post ->
            CustomTabsStarter.start(requireContext(), post.url)
        }
        binding.fragmentNewPostsRecyclerView.setController(controller)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newPostsLiveData.observe(viewLifecycleOwner, Observer { pagedList ->
            controller.submitList(pagedList)
            controller.requestModelBuild()
        })

        viewModel.netWorkStateObserver.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.PAGING -> {
                    controller.isLoading = true
                }
                else -> controller.isLoading = false
            }
        })
    }
}
