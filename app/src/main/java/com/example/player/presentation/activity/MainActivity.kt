package com.example.player.presentation.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.player.R
import com.example.player.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        if (destination.id == R.id.fragment_main) {
            if (checkLandscapeOrientation()) {
                changeOrientationToLandscape(false)
            }
            window.statusBarColor = ContextCompat.getColor(this, R.color.dark_main)
            showSystemUI()
        } else if (destination.id == R.id.fragment_player) {
            if (!checkLandscapeOrientation()) {
                changeOrientationToLandscape(true)
            }
            window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
            hideSystemUI()
        }
    }

    fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= 30) {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
            }
        } else {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
    private fun showSystemUI() {
        if (Build.VERSION.SDK_INT >= 30) {
            window.insetsController?.apply {
                show(WindowInsets.Type.statusBars())
            }
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

    private fun changeOrientationToLandscape(shouldLandscape: Boolean) {
        requestedOrientation = if (shouldLandscape) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun checkLandscapeOrientation(): Boolean {
        val orientation = resources.configuration.orientation
        return orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        findNavController(R.id.nav_host_fragment).removeOnDestinationChangedListener(listener)
        super.onPause()
    }
}