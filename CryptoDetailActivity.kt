package com.goth.cryptograph

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


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
        backButton.textSize = min(getScreenHeight(), getScreenWidth()) / 90f
        backButton.width = getScreenWidth()
        backButton.height = getScreenHeight() / 8
        backButton.setOnClickListener{
            finish()
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
                5 -> "View 7 days graph"
                else -> ""
            }

            if (i == 2)
                button.setTextColor(if (priceChangePercentage24h > 0) Color.rgb(0, 160, 0) else Color.rgb(200, 0, 0))

            if (i != 5)
                button.setBackgroundColor(Color.TRANSPARENT)

            else
            {
                button.setOnClickListener {
                   startActivity(Intent(this, GraphActivity::class.java)
                       .putExtra("sparklineIn7d", sparklineIn7d.toString())
                   )
                }
            }


            button.width = getScreenWidth()
            button.height = getScreenHeight() / if (i == 0) 8 else 6

            tableRow.addView(button)
            tableLayout.addView(tableRow)
        }

        val scrollView = ScrollView(this)
        scrollView.addView(tableLayout)
        relativeLayout.addView(scrollView)
    }


    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}
