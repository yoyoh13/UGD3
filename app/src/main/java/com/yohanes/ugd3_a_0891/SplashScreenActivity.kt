package com.yohanes.ugd3_a_0891

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import java.lang.Boolean

class SplashScreenActivity : AppCompatActivity() {

    lateinit var splashScreenView: RelativeLayout
    var prevStarted = "yes"
    override fun onResume() {
        super.onResume()
        val sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean(prevStarted, Boolean.TRUE)
            editor.apply()
            splashScreenView = findViewById(R.id.splashScreenView)
            splashScreenView.setOnClickListener {
                moveToSecondary()
            }
        } else {
            moveToSecondary()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
    }

    fun moveToSecondary() {
        // use an intent to travel from one activity to another.
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }




}