package com.example.newsapp.feature_news.presentation.components

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object CustomTab {

    private var builder: CustomTabsIntent? = null
    fun launch(context: Context, url: String) {
            if (builder == null) {
                builder = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setExitAnimations(
                        context,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                    )
                    .build()
            }
            builder?.launchUrl(context, Uri.parse(url))
        }
    }
