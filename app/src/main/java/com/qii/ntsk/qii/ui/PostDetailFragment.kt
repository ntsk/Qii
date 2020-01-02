package com.qii.ntsk.qii.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentPostDetailBinding
import com.qii.ntsk.qii.model.entity.Post

class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_post_detail, container, false)
        binding = FragmentPostDetailBinding.bind(view)

        arguments?.get(BUNDLE_KEY_POST_DETAIL).let {
            val post = it as Post
            binding.fragmentPostDetailWebView.loadUrl(post.url)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity as MainActivity
        activity.hideNavigation()
    }

    class Builder(post: Post) {
        private val args = Bundle()

        init {
            args.putSerializable(BUNDLE_KEY_POST_DETAIL, post)
        }

        fun build(): PostDetailFragment {
            return PostDetailFragment().also { it.arguments = args }
        }
    }

    companion object {
        const val BUNDLE_KEY_POST_DETAIL = "bundle_key_post_detail"
    }
}