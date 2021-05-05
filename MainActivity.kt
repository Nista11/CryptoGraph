package com.goth.cryptograph

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    var netData: String? = null
    lateinit var jsonString: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thread = Thread {
            try {
                netData = URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false").readText()
                jsonString = "{\"all_data\":" + netData + "}"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread.start()
        thread.join() // waits for thread to stop

        val tableLayout = TableLayout(this)
        for (i in 1..10)
        {
            val tableRow = TableRow(this)
            for (j in 1..3)
            {
                val button = Button(this)
                button.setText("$i $j")
                //button.left = i * 10 + j * 10
                tableRow.addView(button)
            }
            tableLayout.addView(tableRow)
        }
        setContentView(tableLayout)

        //Log.d("my_tag1", jsonString)
        val foos = Response(jsonString)
        foos.data?.forEach { i -> Log.d("my_tag", "name: " + i.name + ", symbol: " + i.symbol + ", price: " + i.current_price + ", price change 24h: " + i.price_change_percentage_24h + "%, last updated: " + i.last_updated)
        }

//        tabLayout = findViewById(R.id.tabLayout)
//        //title = "KotlinApp"
//        tabLayout = findViewById(R.id.tabLayout)
//        tabLayout.addTab(tabLayout.newTab().setText("Football"))
//        tabLayout.addTab(tabLayout.newTab().setText("Cricket"))
//        tabLayout.addTab(tabLayout.newTab().setText("NBA"))
//        tabLayout.addTab(tabLayout.newTab().setText("yo"))
        //tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    class Response(json: String) : JSONObject(json) {
        //val type: String? = this.optString("type")
        val data = this.optJSONArray("all_data")
            ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
            ?.map { Foo(it.toString()) } // transforms each JSONObject of the array into Foo
    }

    class Foo(json: String) : JSONObject(json) {
        val id: String? = this.optString("id")
        val name: String? = this.optString("name")
        val symbol: String? = this.optString("symbol")
        val current_price = this.optDouble("current_price")
        val price_change_percentage_24h = this.optDouble("price_change_percentage_24h")
        val last_updated = this.optString("last_updated")
    }
}