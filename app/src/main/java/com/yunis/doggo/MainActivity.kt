package com.yunis.doggo


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    private lateinit var randomImageButton: Button
    private lateinit var searchBreed: TextView
    private lateinit var searchTerm: String


    private val serviceList: ArrayList<Service> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        randomImageButton = findViewById(R.id.button)

        searchBreed = findViewById(R.id.dog_breed_input)


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        val adapter = DogAdapter(serviceList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
        RequestQueue(cache, network).apply {
            start()
        }

        randomImageButton.setOnClickListener {
            searchTerm = searchBreed.text.toString()


            val url = "https://dog.ceo/api/breed/$searchTerm/images"

            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                    { response ->

                        var imgUrl: JSONArray = response.getJSONArray("message")

                        for (i in 0 until imgUrl.length()) {

                            serviceList.add(Service(imgUrl.getString(i)))

                        }

                        adapter.notifyDataSetChanged()

                        Toast.makeText(this, "New random image loaded", Toast.LENGTH_SHORT).show()

                    },

                    { error ->
                        // TODO: Handle error
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    }
            )

            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
            serviceList.clear()

        }
    }
}