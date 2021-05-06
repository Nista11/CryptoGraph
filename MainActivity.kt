package com.goth.cryptograph

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var cryptoData: List<Foo>? = null
    private lateinit var tableLayout: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCryptoData()
        createTable()
        addTable()
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
            button.text = currentCrypto.name
            button.width = 1050
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

    class Response(json: String) : JSONObject(json) {
        val data = this.optJSONArray("all_data")
            ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
            ?.map { Foo(it.toString()) } // transforms each JSONObject of the array into Foo
    }

    class Foo(json: String) : JSONObject(json) {
        val name: String? = this.optString("name")
        val symbol: String? = this.optString("symbol")
        val current_price = this.optDouble("current_price")
        val price_change_percentage_24h = this.optDouble("price_change_percentage_24h")
        val last_updated = this.optString("last_updated")
        val sparkline_in_7d = this.optJSONObject("sparkline_in_7d")?.getJSONArray("price")
    }
}
