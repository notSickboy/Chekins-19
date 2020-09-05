package com.example.chekins_19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class login : AppCompatActivity() {

    var foursquare:Foursquare? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var bLogin = findViewById<Button>(R.id.bLoguin)

        bLogin.setOnClickListener {
            foursquare = Foursquare(this, PantallaPrincipal())
            foursquare?.iniciar_sesion()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        foursquare?.validarActivityResult(requestCode, resultCode, data)
    }
}