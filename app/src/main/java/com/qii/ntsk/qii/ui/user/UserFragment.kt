package com.qii.ntsk.qii.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentUserBinding
import com.qii.ntsk.qii.datasource.repository.UserRepositoryImpl
import com.qii.ntsk.qii.model.state.LoginState
import com.qii.ntsk.qii.model.state.Status
import com.qii.ntsk.qii.ui.MainActivity
import com.qii.ntsk.qii.utils.CustomTabsStarter
import com.qii.ntsk.qii.utils.LoginIntentBuilder
import com.qii.ntsk.qii.widget.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_please_login.view.*
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val viewModel: UserViewModel by viewModels()
    private var binding: FragmentUserBinding by autoCleared()
    private lateinit var controller: UserItemsController

    @Inject
    lateinit var userRepository: UserRepositoryImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        binding = FragmentUserBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.fragmentUserLogoutView.root.visibility = View.GONE
        binding.fragmentUserLoginView.root.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginStateLiveData.observe(viewLifecycleOwner, Observer { loginState ->
            when (loginState) {
                LoginState.LOGOUT -> showLogoutView()
                LoginState.LOGIN -> showLoginView()
                else -> showLoginView()
            }
        })
    }

    private fun showLogoutView() {
        binding.fragmentUserLoginView.root.visibility = View.GONE
        binding.fragmentUserLogoutView.root.visibility = View.VISIBLE
        binding.fragmentUserLoading.root.visibility = View.GONE

        binding.fragmentUserLogoutView.layoutUserLogoutEmpty.layout_please_login_button.setOnClickListener {
            login()
        }
    }

    private fun showLoginView() {
        binding.fragmentUserLoginView.root.visibility = View.VISIBLE
        binding.fragmentUserLogoutView.root.visibility = View.GONE
        binding.fragmentUserLoading.root.visibility = View.GONE

        controller = UserItemsController { post ->
            CustomTabsStarter.start(requireContext(), post.url)
        }
        binding.fragmentUserLoginView.layoutUserLoginRecyclerView.setController(controller)
        viewModel.fetchAuthenticatedUser().observe(viewLifecycleOwner, Observer {
            binding.iconUrl = it.profileImageUrl
            binding.fragmentUserLoginView.layoutUserLoginUserId.text = resources.getString(R.string.id_with_at, it.id)
            binding.fragmentUserLoginView.layoutUserLoginUserName.text = it.name
            binding.fragmentUserLoginView.layoutUserLoginDescription.text = it.description
        })

        viewModel.fetchAuthenticatedUserItems().observe(viewLifecycleOwner, Observer {
            controller.submitList(it)
            controller.requestModelBuild()
        })

        viewModel.userPostsNetWorkStateObserver.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.PAGING -> controller.isLoading = true
                else -> controller.isLoading = false
            }
        })
    }

    private fun login() {
        startActivity(LoginIntentBuilder.build())
    }

    private fun logout() {
        AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteToken()
                    Toast.makeText(context, R.string.message_success_logout, Toast.LENGTH_LONG).show()
                    (activity as? MainActivity)?.reload()
                }
                .setNegativeButton("No") { dialogInterface, _ -> dialogInterface.dismiss() }
                .show()
    }
}
