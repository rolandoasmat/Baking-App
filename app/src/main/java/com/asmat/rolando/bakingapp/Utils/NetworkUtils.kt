package com.asmat.rolando.bakingapp.Utils

import android.content.Context
import android.net.Uri
import com.android.volley.Request
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue
import com.android.volley.Response


/**
 * Created by rolandoasmat on 7/4/17.
 */

class NetworkUtils {
    companion object {
        fun buildURL(endpoint: String, queryParameters: HashMap<String, String>): URL {
            val builder = Uri.parse(endpoint).buildUpon()
            val keys = queryParameters.keys
            for(key in keys) {
                val value = queryParameters[key] ?: ""
                builder.appendQueryParameter(key, value)
            }
            val uri = builder.build()
            return URL(uri.toString())
        }

        fun httpRequest(url: String, context: Context, onComplete: (String?) -> Unit) {
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(context)

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, url,
                    object : Response.Listener<String> {
                        override fun onResponse(response: String) {
                            onComplete(response)
                        }
                    }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    onComplete(null)
                }
            })
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            //queue.start()
        }
    }
}