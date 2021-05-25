package com.goth.cryptograph

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import kotlin.math.max
import kotlin.math.min

class GraphActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val sparklineIn7d: String? = intent.getStringExtra("sparklineIn7d")

        val backButton = Button(this)
        backButton.text = "<- Go back"
        backButton.width = getScreenWidth()
        backButton.height = getScreenHeight() / 8
        backButton.setBackgroundColor(Color.TRANSPARENT)
        backButton.setOnClickListener{
            finish()
        }

        val s = sparklineIn7d!!.split(",", "[", "]")
        val sparklineArray = arrayOfNulls<Double>(s.size - 1)
        var min = Double.MAX_VALUE
        var max = -min
        for (i in 1..s.size - 2)
        {
            sparklineArray[i] = s[i].toDouble()
            min = min(sparklineArray[i]!!, min)
            max = max(sparklineArray[i]!!, max)
        }

        backButton.text = backButton.text as String + "\nmin: ${(min * 100).toInt() / 100.0} $, max: ${(max * 100).toInt() / 100.0} $"
        val view = CustomView(this, sparklineArray, min, max)
        val relativeLayout: RelativeLayout = findViewById(R.id.relative_layout_graph)
        relativeLayout.addView(backButton)
        relativeLayout.addView(view)
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}
