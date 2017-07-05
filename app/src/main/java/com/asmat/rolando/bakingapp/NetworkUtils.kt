package com.asmat.rolando.bakingapp

import android.net.Uri
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

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

        fun httpRequest(url: URL): String? {
            val urlConnection = url.openConnection() as HttpURLConnection
            val inputStream = urlConnection.inputStream
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")
            val hasInput = scanner.hasNext()
            if(hasInput) {
                val response = scanner.next()
                urlConnection.disconnect()
                return response
            } else {
                urlConnection.disconnect()
                return null
            }

        }
    }
}