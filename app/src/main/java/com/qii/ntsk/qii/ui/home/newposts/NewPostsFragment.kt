package com.qii.ntsk.qii.ui.home.newposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentNewPostsBinding

class NewPostsFragment : Fragment() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(NewPostsViewModel::class.java) }
    private val controller = NewPostsController()
    private lateinit var binding: FragmentNewPostsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_new_posts, container, false)
        binding = FragmentNewPostsBinding.bind(view)
        binding.fragmentNewPostsRecyclerView.setController(controller)
        binding.fragmentNewPostsRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.newPostsLiveData.observe(viewLifecycleOwner, Observer { posts ->
            controller.addPosts(posts)
            controller.requestModelBuild()
        })
    }
}
