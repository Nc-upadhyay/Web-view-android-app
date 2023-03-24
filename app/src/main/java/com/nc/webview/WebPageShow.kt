package com.nc.webview

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


class WebPageShow : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var progrssBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_page_show_fragments)
        val intent = intent
        val url = intent.getStringExtra("url")

        webView = findViewById(R.id.webview)
        progrssBar = findViewById(R.id.progress_bar)
        goToWebPage(webView, progrssBar, url)
    }

    private fun goToWebPage(webView: WebView, progrssBar: ProgressBar, url: String?) {
        webView.loadUrl(Uri.parse(url).toString())
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progrssBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progrssBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }

    }
}