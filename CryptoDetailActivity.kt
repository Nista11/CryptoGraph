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
import android.widget.*

class CryptoDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_detail)
        val name: String? = intent.getStringExtra("name")
        val symbol: String? = intent.getStringExtra("symbol")
        val price: Double = intent.getStringExtra("price")?.toDouble() ?: 0.0
        val priceChangePercentage24h: Double = intent.getStringExtra("priceChangePercentage24h")?.toDouble() ?: 0.0
        val lastUpdated: String? = intent.getStringExtra("lastUpdated")
        val sparklineIn7d: String? = intent.getStringExtra("sparklineIn7d")

        val backButton = Button(this)
        backButton.text = "<- Go back"
        backButton.width = getScreenWidth()
        backButton.height = getScreenHeight() / 8
        backButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }


        val relativeLayout: RelativeLayout = findViewById(R.id.relative_layout_crypto_detail)
        relativeLayout.addView(backButton)

        val tableLayout = TableLayout(this)
        for (i in 0..5)
        {
            val tableRow = TableRow(this)
            val button = Button(this)

            button.text = when (i)
            {
                1 -> "$name (${symbol})"
                2 -> "Price change in 24h: " + (if (priceChangePercentage24h > 0.0) " +" else " ") + "${(priceChangePercentage24h * 100).toInt().toDouble() / 100}%"
                3 -> "Current price: $price $"
                4 -> "Last updated: $lastUpdated"
                5 -> sparklineIn7d
                else -> ""
            }

            if (i == 2)
            {
                if (priceChangePercentage24h > 0)
                    button.setTextColor(Color.rgb(0, 160, 0))
                else
                    button.setTextColor(Color.rgb(200, 0, 0))
            }

            button.width = getScreenWidth()
            if (i != 0)
            {
                button.height = getScreenHeight() / 6
            }
            else
            {
                button.height = getScreenHeight() / 8
                button.setBackgroundColor(Color.TRANSPARENT)
            }

            tableRow.addView(button)
            tableLayout.addView(tableRow)
        }

        val scrollView = ScrollView(this)
        scrollView.addView(tableLayout)
        relativeLayout.addView(scrollView)
        setContentView(relativeLayout)
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}