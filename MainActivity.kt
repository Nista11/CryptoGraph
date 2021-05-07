package com.goth.cryptograph

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var cryptoData: List<Crypto>? = null
    private lateinit var tableLayout: TableLayout
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCryptoData()
        if (cryptoData != null)
        {
            createTable()
            addTable()
        }
        else
            connectionErrorMessage()
    }

    private fun getCryptoData()
    {
        val thread = Thread {
            try {
                val netData = URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=true").readText()
                val jsonString = "{\"all_data\":$netData}"
                cryptoData = Response(jsonString).data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread.start()
        thread.join() // waits for thread to stop
    }

    private fun createTable()
    {
        tableLayout = TableLayout(this)
        for (currentCrypto in cryptoData!!)
        {
            val tableRow = TableRow(this)
            val button = Button(this)
            if (currentCrypto.priceChangePercentage24h > 0)
                button.setTextColor(Color.rgb(0, 200, 0))
            else
                button.setTextColor(Color.rgb(200, 0, 0))

            var text = "${currentCrypto.name} (${currentCrypto.symbol}) ${currentCrypto.price} \$"
            text += if (currentCrypto.priceChangePercentage24h > 0) " +" else " "
            text += "${(currentCrypto.priceChangePercentage24h * 100).toInt().toDouble() / 100}%"
            button.text = text

            button.width = getScreenWidth()
            button.height = getScreenHeight() / 6

            button.setOnClickListener{
                startActivity(Intent(this, CryptoDetailActivity::class.java).putExtra("cryptoName", currentCrypto.name))
            }

            tableRow.addView(button)
            tableLayout.addView(tableRow)
        }
    }

    private fun addTable()
    {
        val relativeLayout: RelativeLayout = findViewById(R.id.relative_layout)
        val scrollView = ScrollView(this)
        scrollView.addView(tableLayout)
        relativeLayout.addView(scrollView)
        setContentView(relativeLayout)
    }

    @SuppressLint("SetTextI18n")
    private fun connectionErrorMessage()
    {
        val textView = TextView(this)
        textView.text = "Unable to connect to services. Please check your connection and try again."
        val relativeLayout: RelativeLayout = findViewById(R.id.relative_layout)
        relativeLayout.addView(textView)
        setContentView(relativeLayout)
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    class Response(json: String) : JSONObject(json) {
        val data = this.optJSONArray("all_data")
            ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
            ?.map { Crypto(it.toString()) } // transforms each JSONObject of the array into Crypto
    }

    class Crypto(json: String) : JSONObject(json) {
        val name: String? = this.optString("name")
        val symbol: String? = this.optString("symbol")
        val price = this.optDouble("current_price")
        val priceChangePercentage24h = this.optDouble("price_change_percentage_24h")
        val lastUpdated: String? = this.optString("last_updated")
        val sparklineIn7d = this.optJSONObject("sparkline_in_7d")?.getJSONArray("price")
    }
}
