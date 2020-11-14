package com.qii.ntsk.qii.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSettingsBinding
import com.qii.ntsk.qii.model.state.LoginState
import com.qii.ntsk.qii.ui.main.MainActivity
import com.qii.ntsk.qii.utils.LoginIntentBuilder
import com.qii.ntsk.qii.ui.widget.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModels()
    private var binding: FragmentSettingsBinding by autoCleared()
    private lateinit var controller: SettingsController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        binding = FragmentSettingsBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginStateLiveData.observe(viewLifecycleOwner, Observer { isLogin ->
            initializeView(isLogin)
        })
    }

    private fun initializeView(state: LoginState) {
        controller = SettingsController(
                loginState = state,
                onLoginClick = View.OnClickListener {
                    login()
                },
                onLogoutClick = View.OnClickListener {
                    logout()
                }
        )
        binding.fragmentSettingsRecyclerView.setController(controller)
        binding.fragmentSettingsRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        controller.requestModelBuild()
    }

    private fun login() {
        startActivity(LoginIntentBuilder.build())
    }

    private fun logout() {
        AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.logout()
                    Toast.makeText(context, R.string.message_success_logout, Toast.LENGTH_LONG).show()
                    (activity as? MainActivity)?.reload()
                }
                .setNegativeButton("No") { dialogInterface, _ -> dialogInterface.dismiss() }
                .show()
    }
}