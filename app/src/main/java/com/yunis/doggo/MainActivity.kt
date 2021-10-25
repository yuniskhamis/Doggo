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

    private lateinit var dogBreedButton: Button
    private lateinit var searchBreed: TextView
    private lateinit var searchTerm: String


    private val serviceList: ArrayList<DogImage> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dogBreedButton = findViewById(R.id.button)

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

        dogBreedButton.setOnClickListener {
            searchTerm = searchBreed.text.toString()


            val url = "https://dog.ceo/api/breed/$searchTerm/images"

            // Formulate the request and handle the response.
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                    { response ->

                        val imgUrl: JSONArray = response.getJSONArray("message")

                        for (i in 0 until imgUrl.length()) {

                            serviceList.add(DogImage(imgUrl.getString(i)))

                        }

                        adapter.notifyDataSetChanged()

                        Toast.makeText(this, "New images loaded", Toast.LENGTH_SHORT).show()

                    },

                    { error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    }
            )
            // Access the RequestQueue through the singleton class.
            // Add a request (called jsonObjectRequest) to the RequestQueue.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
            serviceList.clear()

        }
    }
}