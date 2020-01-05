package com.qii.ntsk.qii.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.qii.ntsk.qii.BuildConfig
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentUserBinding
import com.qii.ntsk.qii.model.holder.TokenHolder
import com.qii.ntsk.qii.model.service.GlideApp
import com.qii.ntsk.qii.utils.RandomStringGenerator
import kotlinx.android.synthetic.main.layout_please_login.view.*

class UserFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(UserViewModel::class.java) }
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.loginStateLiveData.observe(viewLifecycleOwner, Observer { isLogin ->
            if (isLogin) showLoginView() else showLogoutView()
        })
    }

    private fun showLogoutView() {
        binding.fragmentUserLogoutView.visibility = View.VISIBLE
        binding.fragmentUserLogoutView.layout_please_login_button.setOnClickListener {
            val clientId = BuildConfig.CLIENT_ID
            val scope = "read_qiita"
            val state = RandomStringGenerator.generate(40)

            val uri = "https://qiita.com/api/v2/oauth/authorize?client_id=$clientId&scope=$scope&state=$state"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

    private fun showLoginView() {
        viewModel.fetchAuthenticatedUser().observe(viewLifecycleOwner, Observer {
            binding.iconUrl = it.profileImageUrl
            binding.fragmentUserLoginView.layoutUserLoginUserId.text = resources.getString(R.string.id_with_at, it.id)
            binding.fragmentUserLoginView.layoutUserLoginUserName.text = it.name
            binding.fragmentUserLoginView.layoutUserLoginDescription.text = it.description
        })
    }
}
