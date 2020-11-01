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
import com.qii.ntsk.qii.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewPostsFragment : Fragment() {
    private val viewModel: NewPostsViewModel by viewModels()
    private lateinit var controller: NewPostsController
    private lateinit var binding: FragmentNewPostsBinding

    @Inject
    lateinit var repository: PostsRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_new_posts, container, false)
        binding = FragmentNewPostsBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        controller = NewPostsController { post ->
            val activity = requireActivity() as MainActivity
            activity.showPostDetail(post)
        }
        binding.fragmentNewPostsRecyclerView.setController(controller)

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
        return view
    }
}
