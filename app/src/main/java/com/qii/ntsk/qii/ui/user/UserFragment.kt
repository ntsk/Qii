package com.qii.ntsk.qii.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.BuildConfig
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentUserBinding
import com.qii.ntsk.qii.model.holder.TokenHolder
import com.qii.ntsk.qii.utils.RandomStringGenerator
import kotlinx.android.synthetic.main.layout_please_login.view.*

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        if (TokenHolder().load() == null) {
            showLogoutView()
            return
        }
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
}
