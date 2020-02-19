package com.pratima.movietube.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pratima.movietube.R
import com.pratima.movietube.view.login.LoginActivity
import com.pratima.movietube.view.login.LoginActivity.Companion.PREF_NAME
import com.pratima.movietube.view.login.LoginActivity.Companion.PRIVATE_MODE
import com.pratima.movietube.view.main.MainActivity

class SplashScreen : AppCompatActivity() {

    private val ANIM_DURATION: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //animating views
        animateViews()
        //adding delay for splash screen
        val splashHandler = Handler()
        val runnable = Runnable {
            checkUserCredential()
        }
        splashHandler.postDelayed(runnable, ANIM_DURATION)

    }

    private fun animateViews() {
        val appTitleText = findViewById<TextView>(R.id.app_title)
        val appLogo = findViewById<ImageView>(R.id.splash_logo)

        val animationText = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.bottom_to_original
        )
        val animFadeIn = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_in
        )

        appLogo.startAnimation(animFadeIn)
        appTitleText.animation = animationText

    }


    private fun checkUserCredential() {
        if (isUserLoggedIn()) {
            launchHome()
        } else {
            launchLogin()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        return sharedPref.getBoolean(PREF_NAME, false)

    }

    private fun launchHome() {
        val homeIntent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    private fun launchLogin() {
        val loginIntent = Intent(this@SplashScreen, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }
}
