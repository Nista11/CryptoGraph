package com.goth.cryptograph

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView

class CryptoDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_detail)
        val cryptoName = intent.getStringExtra("cryptoName")

        val backButton = Button(this)
        backButton.text = "<- $cryptoName"
        backButton.width = getScreenWidth()
        backButton.height = getScreenHeight() / 8
        backButton.left = 0
        backButton.top = 0
        backButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }


        val layout = RelativeLayout(this)
        layout.addView(backButton)
        setContentView(layout)
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}