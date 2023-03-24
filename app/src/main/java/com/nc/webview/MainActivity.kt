package com.nc.webview

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen_fragments)

        val google: ImageView = findViewById(R.id.google)
        val flipkart: ImageView = findViewById(R.id.filikat)
        val amazon: ImageView = findViewById(R.id.amazon)
        val linkden: ImageView = findViewById(R.id.linkden)
        google.setOnClickListener {
            goToAnotherFragments("https://www.google.com")
        }
        flipkart.setOnClickListener {
            goToAnotherFragments("https://www.flipkart.com")
        }

        linkden.setOnClickListener {
            goToAnotherFragments("https://www.linkedin.com")
        }
        amazon.setOnClickListener {
            goToAnotherFragments("https://www.amazon.in")
        }


    }

    private fun goToAnotherFragments(s: String) {
        // checking internet Connection
        if (checkForInternet(this)) {
            val bundle = Bundle()
            bundle.putString("url", s)
            val intent = Intent(this, WebPageShow::class.java)
            intent.putExtra("url", s)
            startActivity(intent)

        } else {
            showErrorMessage()
        }

    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Internet problem", Toast.LENGTH_LONG).show()
    }

    var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}
