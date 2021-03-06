package com.qii.ntsk.qii.ui.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentStocksBinding
import com.qii.ntsk.qii.datasource.repository.UserRepository
import com.qii.ntsk.qii.utils.CustomTabsStarter
import com.qii.ntsk.qii.utils.LoginIntentBuilder
import com.qii.ntsk.qii.ui.widget.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_please_login.view.*
import javax.inject.Inject

@AndroidEntryPoint
class StocksFragment : Fragment() {
    private val viewModel: StocksViewModel by viewModels()
    private var binding: FragmentStocksBinding by autoCleared()
    private lateinit var controller: StocksController

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_stocks, container, false)
        binding = FragmentStocksBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        controller = StocksController { post ->
            CustomTabsStarter.start(requireContext(), post.url)
        }
        binding.fragmentFavoriteRecyclerView.setController(controller)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loginStateLiveData.observe(viewLifecycleOwner, Observer { isLogin ->
            if (isLogin) showLoginView() else showLogoutView()
        })
    }

    private fun showLoginView() {
        viewModel.fetchAuthenticatedUser().observe(viewLifecycleOwner, Observer { user ->
            viewModel.fetchStocks(user.id).observe(viewLifecycleOwner, Observer {
                controller.submitList(it)
                controller.requestModelBuild()
            })
        })
    }

    private fun showLogoutView() {
        binding.fragmentStocksPleaseLogin.layout_please_login_button.setOnClickListener {
            startActivity(LoginIntentBuilder.build())
        }
    }

    fun refresh() {
        viewModel.fetchLoginState()
    }
}