package com.qii.ntsk.qii.ui.detail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentPostDetailBinding
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.ui.MainActivity

class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_post_detail, container, false)
        binding = FragmentPostDetailBinding.bind(view)

        val post = arguments?.get(BUNDLE_KEY_POST_DETAIL) as? Post ?: return view
        binding.fragmentPostDetailToolbar.title = post.title
        binding.fragmentPostDetailToolbar.setNavigationIcon(R.drawable.ic_arrow)
        val activity = activity as MainActivity
        activity.hideBottomNavigation()
        binding.fragmentPostDetailToolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
            activity.showBottomNavigation()
        }

        val client = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.isLoading = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.isLoading = false
            }
        }

        binding.fragmentPostDetailWebView.webViewClient = client
        binding.fragmentPostDetailWebView.loadUrl(post.url)
        return view
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