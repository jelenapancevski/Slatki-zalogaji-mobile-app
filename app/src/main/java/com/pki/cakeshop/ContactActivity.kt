package com.pki.cakeshop

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class ContactActivity: AppCompatActivity() {

    private lateinit var map: WebView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact)

        // Find the WebView by its ID
        map = findViewById<WebView>(R.id.map)

        // Enable JavaScript (required for Google Maps Embed API)

        // Enable JavaScript (required for Google Maps Embed API)
        val webSettings: WebSettings = map.getSettings()
        webSettings.javaScriptEnabled = true

        // Load the Google Maps Embed API URL

        // Load the Google Maps Embed API URL
        map.loadUrl("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d14226.67454086537!2d20.474460641957112!3d44.803906730688155!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x475a7a8298bc0e27%3A0x242c819b1bc3009!2z0JHRg9C70LXQstCw0YAg0LrRgNCw0ZnQsCDQkNC70LXQutGB0LDQvdC00YDQsCAxOTgsINCR0LXQvtCz0YDQsNC0!5e0!3m2!1ssr!2srs!4v1701739654817!5m2!1ssr!2srs")

        // Set a WebViewClient to handle page navigation within the WebView

        // Set a WebViewClient to handle page navigation within the WebView
        map.setWebViewClient(WebViewClient())


    }

}