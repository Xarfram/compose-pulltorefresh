package com.github.fengdai.compose.pulltorefresh.sample

import android.graphics.Bitmap
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Content(contentPadding: PaddingValues,
            refreshing: Boolean,
            darkMode: Boolean = isSystemInDarkTheme()) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(1) {
            var backEnabled by remember { mutableStateOf(false) }
            var webView: WebView? = null

            // Declare a string that contains a url
            val mUrl = "https://www.raddle.me"
            //val mUrl = "https://www.youtube.com"

            // Adding a WebView inside AndroidView
            // with layout as full screen
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    if (darkMode) {
                        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                            WebSettingsCompat.setForceDark(
                                this.settings,
                                WebSettingsCompat.FORCE_DARK_ON
                            )
                        }
                    } else {
                        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                            WebSettingsCompat.setForceDark(
                                this.settings,
                                WebSettingsCompat.FORCE_DARK_OFF
                            )
                        }
                    }

                    webViewClient = WebViewClient()

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            backEnabled = view.canGoBack()
                        }
                    }

                    //settings.javaScriptEnabled = true
                    loadUrl(mUrl)
                    webView = this
                }
            }, update = {
                webView = it
                if (refreshing) {
                    it.reload()
                }
            })
            BackHandler(enabled = backEnabled) {
                webView?.goBack()
            }
        }
    }
}