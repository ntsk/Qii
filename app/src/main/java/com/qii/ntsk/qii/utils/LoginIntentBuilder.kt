package com.qii.ntsk.qii.utils

import android.content.Intent
import android.net.Uri
import com.qii.ntsk.qii.BuildConfig

class LoginIntentBuilder {

    companion object {
        fun build(): Intent {
            val clientId = BuildConfig.CLIENT_ID
            val scope = "read_qiita"
            val state = RandomStringGenerator.generate(40)

            val uri = "https://qiita.com/api/v2/oauth/authorize?client_id=$clientId&scope=$scope&state=$state"
            return Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        }
    }
}