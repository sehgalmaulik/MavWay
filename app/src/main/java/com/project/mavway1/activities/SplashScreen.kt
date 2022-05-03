package com.project.mavway1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.project.mavway1.R
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val intent = Intent(this, StartScreen::class.java)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(intent)
                finish()
            }
        }, 2500)

    }
}