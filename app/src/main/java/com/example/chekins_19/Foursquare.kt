package com.example.chekins_19

import android.content.Intent
import android.provider.Contacts
import androidx.appcompat.app.AppCompatActivity
import com.foursquare.android.nativeoauth.FoursquareOAuth

class Foursquare(var activity: AppCompatActivity, var activity_destino:AppCompatActivity) {

    private var CODIGO_DE_CONEXION = 200
    private var CODIGO_DE_INTERCAMBIO_DE_TOKEN = 201

    private val CLIENT_ID = ""
    private val CLIENT_SECRET = ""

    private val SETTINGS = "settings"
    private val ACCESS_TOKEN = "accessToken"

    init {

    }

    fun iniciar_sesion(){
        val intent = FoursquareOAuth.getConnectIntent(activity.applicationContext,CLIENT_ID)

        if(FoursquareOAuth.isPlayStoreIntent(intent)){
          Mensaje.mensaje_error(activity.applicationContext, Errores.NO_HAY_APP_DE_FOURSQUARE)
            activity.startActivity(intent)
        }else{
        activity.startActivityForResult(intent, CODIGO_DE_CONEXION)
        }
    }

    private fun validarActivityResult(requestCode:Int, resultCode:Int, data:Intent?){
        when(requestCode){
            CODIGO_DE_CONEXION->{ conexionCompleta(resultCode, data) }

            CODIGO_DE_INTERCAMBIO_DE_TOKEN->{ intercambioTokenCompleta(resultCode, data) }
        }
    }

    private fun conexionCompleta(resultCode: Int, data: Intent?){
        val codigo_de_respuesta = FoursquareOAuth.getAuthCodeFromResult(resultCode, data)
        val exception = codigo_de_respuesta.exception

        if(exception == null){
            val codigo = codigo_de_respuesta.code
            realizarIntercambioToken(codigo)
        }else{
            Mensaje.mensaje_error(activity.applicationContext, Errores.NO_HAY_CONEXION_DE_FOURSQUARE)
        }
    }

    private fun realizarIntercambioToken(codigo:String){
        val intent = FoursquareOAuth.getTokenExchangeIntent(activity.applicationContext, CLIENT_ID, CLIENT_SECRET, codigo)
        activity.startActivityForResult(intent,CODIGO_DE_INTERCAMBIO_DE_TOKEN)
    }

    private fun intercambioTokenCompleta(resultCode:Int, data: Intent?){
        val respuestaToken = FoursquareOAuth.getTokenFromResult(resultCode, data)
        val exception = respuestaToken.exception

        if(exception == null){
            val accesToken = respuestaToken.accessToken
            if(!guardarToken(accesToken)){
                Mensaje.mensaje_error(activity.applicationContext, Errores.ERROR_AL_GUARDAR_TOKEN)
            }
            navegarSiguienteActividad(activity_destino)
        }else{
            Mensaje.mensaje_error(activity.applicationContext, Errores.ERROR_INTERCAMBIO_TOKEN)
        }
    }

    private fun hayToken():Boolean{
        if(obtenerToken() == ""){
            return false
        }else{
            return true
        }
    }

    fun obtenerToken():String{
        val settings = activity.getSharedPreferences(SETTINGS,0)
        val token = settings.getString(ACCESS_TOKEN, "").toString()
        return token
    }

    private fun guardarToken(token:String):Boolean{

        if(token.isEmpty()){
            return false
        }
        val settings = activity.getSharedPreferences(SETTINGS,0)
        val editor = settings.edit()

        editor.putString(ACCESS_TOKEN,token)
        editor.apply()

        return true
    }

    private fun navegarSiguienteActividad(activityDest: AppCompatActivity){
        activity.startActivity(Intent(this.activity, activityDest::class.java))
        activity.finish()
    }
}
