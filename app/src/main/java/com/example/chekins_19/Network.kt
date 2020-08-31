package com.example.chekins_19

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Network(var activity: AppCompatActivity) {
    fun hay_red():Boolean{
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    fun solicitud_http(context:Context, url:String, httpResponse: HttpResponse){

        if(hay_red()){
            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String> {
                    response ->

                httpResponse.http_response_success(response)

            }, Response.ErrorListener {
                    error ->

                Log.d("HTTP_REQUEST", error.message.toString())
                Mensaje.mensaje_error(context, Errores.HTTP_ERROR)

            })
        } else{
                Mensaje.mensaje_error(context, Errores.NO_HAY_RED)
        }
    }

}