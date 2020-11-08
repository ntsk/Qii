package com.qii.ntsk.qii.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.qii.ntsk.qii.R

class CustomTabsStarter {
    companion object {
        private const val CHROME_PACKAGE_NAME = "com.android.chrome"

        fun start(context: Context, url: String) {
            val packageName = CustomTabsClient.getPackageName(context, listOf(CHROME_PACKAGE_NAME), false)
            val builder = CustomTabsIntent.Builder()
                    .enableUrlBarHiding()
                    .setShowTitle(true)
                    .setToolbarColor(ContextCompat.getColor(context, R.color.white))
            val intent = builder.build()
            if (packageName != null) {
                intent.intent.`package` = packageName
            }
            intent.launchUrl(context, Uri.parse(url))
        }
    }
}