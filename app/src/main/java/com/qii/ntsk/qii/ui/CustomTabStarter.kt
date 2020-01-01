package com.qii.ntsk.qii.ui

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent

class CustomTabStarter {
    companion object {
        private const val CHROME_PACKAGE_NAME = "com.android.chrome"

        fun start(context: Context, url: String) {
            val packages = mutableListOf(CHROME_PACKAGE_NAME)
            val packageName = CustomTabsClient.getPackageName(context, packages, false)
            val builder = CustomTabsIntent.Builder()
            builder.enableUrlBarHiding()
            builder.setShowTitle(true)
            builder.setToolbarColor(Color.WHITE)
            val customTabIntent = builder.build()

            if (packageName != null) {
                customTabIntent.intent.setPackage(packageName)
            }

            customTabIntent.launchUrl(context, Uri.parse(url))
        }
    }
}