package com.goth.cryptograph

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class CryptoDetailActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_detail)
        val name: String? = intent.getStringExtra("name")
        val symbol: String? = intent.getStringExtra("symbol")
        val price: Double = intent.getStringExtra("price")?.toDouble() ?: 0.0
        val priceChangePercentage24h: Double = intent.getStringExtra("priceChangePercentage24h")?.toDouble() ?: 0.0
        val lastUpdated: String? = intent.getStringExtra("lastUpdated")
        val sparklineIn7d: String? = intent.getStringExtra("sparklineIn7d")

        val paint = Paint()
        paint.color = Color.MAGENTA
        val bgr = Bitmap.createBitmap(getScreenWidth(), getScreenHeight(), Bitmap.Config.ARGB_8888)
        val can = Canvas(bgr)
        var firstTime = true

        val backButton = Button(this)
        backButton.text = "<- Go back"
        backButton.width = getScreenWidth()
        backButton.height = getScreenHeight() / 8
        backButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }


        val relativeLayout: CustomRelativeLayout = findViewById(R.id.relative_layout_crypto_detail)
        relativeLayout.addView(backButton)

        val tableLayout = TableLayout(this)
        for (i in 0..10)
        {
            val tableRow = TableRow(this)
            val button = Button(this)

            button.text = when (i)
            {
                1 -> "$name (${symbol})"
                2 -> "Price change in 24h: " + (if (priceChangePercentage24h > 0.0) " +" else " ") + "${(priceChangePercentage24h * 100).toInt().toDouble() / 100}%"
                3 -> "Current price: $price $"
                4 -> "Last updated: $lastUpdated"
                5 -> "oo"//sparklineIn7d
                else -> ""
            }

            if (i == 2)
                button.setTextColor(if (priceChangePercentage24h > 0) Color.rgb(0, 160, 0) else Color.rgb(200, 0, 0))

            if (i == 5)
            {
                button.setBackgroundColor(Color.TRANSPARENT)
//                val paint = Paint()
//                paint.color = if (priceChangePercentage24h > 0) Color.rgb(0, 160, 0) else Color.rgb(200, 0, 0)
//
//                val canvas = Canvas()
//                canvas.drawLine(0f, 0f, 100f, 100f, paint)
//                relativeLayout.onDrawForeground(canvas)
            }

            button.width = getScreenWidth()
            button.setBackgroundColor(Color.TRANSPARENT)
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
//        scrollView.viewTreeObserver.addOnScrollChangedListener {
//            val canvas = Canvas()
//            can.drawRect(0f, 0f, getScreenWidth().toFloat(), getScreenHeight().toFloat(), paint.apply { paint.color = Color.WHITE })
//            can.drawLine(100f, 0f, 100f, getScreenHeight().toFloat() / 2f, paint.apply { paint.color = Color.RED })
//            relativeLayout.draw(can)
////            if (!firstTime) {
////                firstTime = false
////                relativeLayout.setBackgroundResource(0)
////            }
////
////            else
////            {                relativeLayout.invalidate()
////
////                relativeLayout.background = BitmapDrawable(this.resources, bgr)
////            }
//
//
//            //setContentView(relativeLayout)
//        }

        //relativeLayout.background = BitmapDrawable(this.resources, bgr)



        scrollView.addView(tableLayout)
        relativeLayout.addView(scrollView)
        //relativeLayout.apply { relativeLayout.onDraw() }
        //can.drawRect(0f, 0f, getScreenWidth().toFloat(), getScreenHeight().toFloat(), paint.apply { paint.color = Color.WHITE })
        //can.drawLine(100f, 0f, 100f, getScreenHeight().toFloat() / 2f, paint.apply { paint.color = Color.RED })
        //relativeLayout.draw(can)
        setContentView(relativeLayout)
    }


    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}
