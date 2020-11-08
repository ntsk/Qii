package com.qii.ntsk.qii.ui.settings

import android.view.View
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyController
import com.qii.ntsk.qii.ModelSettingsItemBindingModel_
import com.qii.ntsk.qii.model.state.LoginState

class SettingsController(
        var loginState: LoginState,
        private val onLoginClick: View.OnClickListener,
        private val onLogoutClick: View.OnClickListener
) : EpoxyController() {

    @AutoModel
    lateinit var loginView: ModelSettingsItemBindingModel_

    @AutoModel
    lateinit var logoutView: ModelSettingsItemBindingModel_

    override fun buildModels() {
        loginView
                .clickListener(onLoginClick)
                .title("Login")
                .subTitle("You need a qiita account to log in")
                .addIf(loginState == LoginState.LOGOUT, this)

        logoutView
                .clickListener(onLogoutClick)
                .title("Logout")
                .subTitle("When you log out, data acquisition is limited to 60 times per hour.")
                .addIf(loginState == LoginState.LOGIN, this)
    }
}