package com.example.volleyapicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.googlesheetsasbackend.UserRVAdapter
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var loadingPB: ProgressBar? = null
    private var userModalArrayList: ArrayList<model>? = null
    private var userRVAdapter: UserRVAdapter? = null
    private var userRV: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userModalArrayList = ArrayList()
        userRV = findViewById(R.id.idRVUsers)
        loadingPB = findViewById(R.id.idPBLoading)
        getDataFromAPI()
    }
    private fun getDataFromAPI() {
        val url =
            "https://run.mocky.io/v3/39bbed50-e5b4-4f6d-ac35-adad78dd999d"

        val queue = Volley.newRequestQueue(this@MainActivity)

        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {
                        loadingPB!!.visibility = View.GONE
                        try {
                            // val feedObj = response.getJSONObject("")
                            val entryArray = response.getJSONArray("values")

                            for (i in 1 until entryArray.length()) {
                                val entryObj = entryArray.getJSONArray(i)
                                val firstName =
                                    entryObj[1].toString()
                                val lastName = entryObj[2].toString()
                                //  entryObj.getJSONObject("gsx\$lastname").getString("\$t")
                                val email = entryObj[0].toString()
                                val contact = entryObj[3].toString()
                                val dob = entryObj[4].toString()
                                val hobby = entryObj[5].toString()

                                    userModalArrayList!!.add(
                                    model(
                                        firstName,
                                        lastName,
                                        email,
                                        contact,
                                        dob,
                                        hobby
                                    )
                                )
                                // passing array list to our adapter class.
                                userRVAdapter = UserRVAdapter(userModalArrayList!!)

                                // setting layout manager to our recycler view.
                                userRV!!.layoutManager = LinearLayoutManager(this@MainActivity)

                                // setting adapter to our recycler view.
                                userRV!!.adapter = userRVAdapter
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        // handline on error listener method.
                        Toast.makeText(this@MainActivity, "Fail to get data..", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        queue.add(jsonObjectRequest);
    }

}